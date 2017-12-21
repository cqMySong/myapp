package com.myapp.service.ec.basedata;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.myapp.core.entity.basedate.DataGroupInfo;
import com.myapp.core.exception.db.AddNewException;
import com.myapp.core.model.WebDataModel;
import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.core.util.BaseUtil;
import com.myapp.entity.ec.basedata.BatchTestInfo;
import com.myapp.entity.ec.basedata.ProBatchTestInfo;
import com.myapp.entity.ec.basedata.ProEcDrawInfo;
import com.myapp.entity.ec.basedata.ProjectInfo;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年12月21日 
 * @system:
 * 项目施工图资料
 *-----------MySong---------------
 */
@Service("proEcDrawService")
public class ProEcDrawService extends BaseInterfaceService<ProEcDrawInfo> {
	/**
	 * 项目级施工图分类标准
	 */
	public WebDataModel batchInitProData(String proJectId) throws AddNewException{
		WebDataModel wdm = new WebDataModel();
		wdm.setData(null);
		int code = 0;
		String mesg = "";
		if(BaseUtil.isEmpty(proJectId)){
			code = -1;
			mesg = "对应的工程项目为空，无法完成对应项目的基础数据导入!";
		}else{
			String hql = " from DataGroupInfo as dg where dg.enabled=? and dg.code=? and dg.isLeaf=?"
					+ " and not exists(from ProEcDrawInfo as ped where ped.group.id = dg.id and ped.project.id=?)";
			List params = new ArrayList();
			params.add(Boolean.TRUE);
			params.add("ecdrawing");
			params.add(Boolean.TRUE);
			params.add(proJectId);
			List<DataGroupInfo> datas = findByHQL(hql, params.toArray());
			if(datas!=null&&datas.size()>0){
				code = 0;
				mesg = "施工图分类目录数据成功导入["+datas.size()+"]个!";
				for(DataGroupInfo dgInfo:datas){
					ProEcDrawInfo pedInfo = new ProEcDrawInfo();
					ProjectInfo pinfo = new ProjectInfo();
					pinfo.setId(proJectId);
					pedInfo.setProject(pinfo);
					pedInfo.setName(dgInfo.getName());
					pedInfo.setNumber(dgInfo.getNumber());
					pedInfo.setGroup(dgInfo);
					addNewEntity(pedInfo);
				}
			}else{
				code = 1;
				mesg = "没有对应的施工图分类目录数据可供导入!";
			}
		}
		wdm.setStatusCode(code);
		wdm.setStatusMesg(mesg);
		return wdm;
	}
}
