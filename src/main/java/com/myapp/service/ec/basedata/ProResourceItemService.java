package com.myapp.service.ec.basedata;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.myapp.core.entity.UserInfo;
import com.myapp.entity.ec.basedata.*;
import com.myapp.enums.ec.SkillType;
import org.springframework.stereotype.Service;

import com.myapp.core.exception.db.SaveException;
import com.myapp.core.model.WebDataModel;
import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.core.util.BaseUtil;
import com.myapp.enums.ec.ResourceType;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2018年1月1日 
 * @system:
 * 项目级资料目录
 *-----------MySong---------------
 */
@Service("proResourceItemService")
public class ProResourceItemService extends BaseInterfaceService<ProResourceItemInfo> {
	/**
	 * 项目级资料目录
	 */
	public WebDataModel batchInitProData(String proJectId,ResourceType rt) throws SaveException{
		WebDataModel wdm = new WebDataModel();
		wdm.setData(null);
		int code = 0;
		String mesg = "";
		if(BaseUtil.isEmpty(proJectId)||rt==null){
			code = -1;
			mesg = "对应的工程项目为空，无法完成对应项目的基础数据导入!";
		}else{
			String hql = " from ResourceItemInfo as ri where ri.enabled=? and ri.resourceType=?"
					+ " and not exists(from ProResourceItemInfo as pri where pri.resourceItem.id = ri.id and pri.project.id=? and pri.resourceType=?)";
			List params = new ArrayList();
			params.add(Boolean.TRUE);
			params.add(rt);
			params.add(proJectId);
			params.add(rt);
			List<ResourceItemInfo> datas = findByHQL(hql, params.toArray());
			if(datas!=null&&datas.size()>0){
				code = 0;
				mesg = rt.getName()+"数据成功导入["+datas.size()+"]个!";
				for(ResourceItemInfo dgInfo:datas){
					ProResourceItemInfo priInfo = new ProResourceItemInfo();
					ProjectInfo pinfo = new ProjectInfo();
					pinfo.setId(proJectId);
					priInfo.setProject(pinfo);
					priInfo.setName(dgInfo.getName());
					priInfo.setNumber(dgInfo.getNumber());
					priInfo.setResourceItem(dgInfo);
					priInfo.setResourceType(rt);
					priInfo.setRemark(dgInfo.getRemark());
					addNewEntity(priInfo);
				}
			}else{
				code = 1;
				mesg = "没有对应的"+rt.getName()+"数据可供导入!";
			}
		}
		wdm.setStatusCode(code);
		wdm.setStatusMesg(mesg);
		return wdm;
	}

	/**
	 * 功能：项目级资料目录
	 * @param projectId
	 * @param userInfo
	 * @param wbsIds
	 */
	public WebDataModel batchSave(String projectId, UserInfo userInfo, String wbsIds, ResourceType rt) throws SaveException {
		WebDataModel wdm = new WebDataModel();
		wdm.setData(null);
		int code = 0;
		String mesg = "";
		if(BaseUtil.isEmpty(projectId)){
			wdm.setStatusCode(-1);
			wdm.setStatusMesg("对应的工程项目单位工程为空，无法完成对应项目工程下的数据导入!");
			return wdm;
		}
		if(BaseUtil.isEmpty(wbsIds)){
			wdm.setStatusCode(-1);
			wdm.setStatusMesg("未选择对应的标准信息!");
			return wdm;
		}

		String hql = " from ResourceItemInfo as ri where ri.enabled=? and ri.resourceType=? " +
				" and ri.id in('"+wbsIds.replaceAll(",","','")+"') "
				+ " and not exists(from ProResourceItemInfo as pri where pri.resourceItem.id = ri.id and pri.project.id=? and pri.resourceType=?)";
		List params = new ArrayList();
		params.add(Boolean.TRUE);
		params.add(rt);
		params.add(projectId);
		params.add(rt);

		List<ResourceItemInfo> datas = findByHQL(hql, params.toArray());
		if(datas!=null&&datas.size()>0){
			code = 0;
			mesg = rt.getName()+"数据成功导入["+datas.size()+"]个!";
			for(ResourceItemInfo dgInfo:datas){
				ProResourceItemInfo priInfo = new ProResourceItemInfo();
				ProjectInfo pinfo = new ProjectInfo();
				pinfo.setId(projectId);
				priInfo.setProject(pinfo);
				priInfo.setName(dgInfo.getName());
				priInfo.setNumber(dgInfo.getNumber());
				priInfo.setResourceItem(dgInfo);
				priInfo.setResourceType(rt);
				priInfo.setRemark(dgInfo.getRemark());
				addNewEntity(priInfo);
			}
		}else{
			code = 1;
			mesg = "没有对应的"+rt.getName()+"数据可供导入!";
		}
		wdm.setStatusCode(code);
		wdm.setStatusMesg(mesg);
		return wdm;
	}
}
