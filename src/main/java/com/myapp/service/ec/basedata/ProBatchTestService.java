package com.myapp.service.ec.basedata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.myapp.core.entity.UserInfo;
import com.myapp.entity.ec.basedata.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.myapp.core.exception.db.AddNewException;
import com.myapp.core.exception.db.SaveException;
import com.myapp.core.model.WebDataModel;
import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.core.util.BaseUtil;
import com.myapp.enums.ec.ProWbsType;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年12月18日 
 * @system:
 * 项目级检验批划分
 *-----------MySong---------------
 */
@Service("proBatchTestService")
public class ProBatchTestService extends BaseInterfaceService<ProBatchTestInfo> {
	
	public Object saveEntity(Object entity) throws SaveException {
		if(entity!=null&&entity instanceof ProBatchTestInfo){
			ProBatchTestInfo info = (ProBatchTestInfo)entity;
			if(info.getProject()!=null&&info.getProBaseWbs()!=null){
				String hql = " from ProBatchTestInfo where project.id=? and proBaseWbs.id=?";
				List params = new ArrayList();
				params.add(info.getProject().getId());
				params.add(info.getProBaseWbs().getId());
				if(!BaseUtil.isEmpty(info.getId())){
					hql+= " and id !=?";
					params.add(info.getId());
				}
				Object dbInfo = queryEntity(hql, params.toArray());
				if(dbInfo!=null){
					throw new SaveException("在项目["+info.getProject().getName()+"]中已经存在了("+info.getProBaseWbs().getName()+")检验批划分办法,不能重复添加!");
				}
			}
		}
		return super.saveEntity(entity);
	}
	
	public WebDataModel batchInitProData(String proJectId) throws SaveException{
		WebDataModel wdm = new WebDataModel();
		wdm.setData(null);
		int code = 0;
		String mesg = "";
		if(BaseUtil.isEmpty(proJectId)){
			code = -1;
			mesg = "对应的工程项目为空，无法完成对应项目的基础数据导入!";
		}else{
			String hql = " from BatchTestInfo as bt where bt.enabled=? and bt.proBaseWbs.wbsType=?"
					+ " and not exists(from ProBatchTestInfo as pbt where pbt.proBaseWbs = bt.proBaseWbs and pbt.project.id=?)";
			List params = new ArrayList();
			params.add(Boolean.TRUE);
			params.add(ProWbsType.FXGC);
			params.add(proJectId);
			List<BatchTestInfo> datas = findByHQL(hql, params.toArray());
			if(datas!=null&&datas.size()>0){
				code = 0;
				mesg = "检验批划分标准数据成功导入["+datas.size()+"]个!";
				for(BatchTestInfo pbInfo:datas){
					ProBatchTestInfo pbtInfo = new ProBatchTestInfo();
					ProjectInfo pinfo = new ProjectInfo();
					pinfo.setId(proJectId);
					pbtInfo.setProject(pinfo);
					pbtInfo.setName(pbInfo.getName());
					pbtInfo.setNumber(pbInfo.getNumber());
					pbtInfo.setProBaseWbs(pbInfo.getProBaseWbs());
					pbtInfo.setContent(pbInfo.getContent());
					pbtInfo.setRemark(pbInfo.getRemark());
					addNewEntity(pbtInfo);
				}
			}else{
				code = 1;
				mesg = "没有对应的检验批标准数据可供导入!";
			}
		}
		wdm.setStatusCode(code);
		wdm.setStatusMesg(mesg);
		return wdm;
	}

	/**
	 * 功能：检验批标准数据导入
	 * @param wbsIds
	 * @param userInfo
	 */
	public WebDataModel batchSave(String structId,String structCode,String wbsIds,UserInfo userInfo) throws SaveException {
		if(StringUtils.isEmpty(wbsIds)){
			throw new RuntimeException("请选择检验批标准数据");
		}
		WebDataModel wdm = new WebDataModel();
		wdm.setData(null);
		int code = 0;
		String mesg = "";
		if(BaseUtil.isEmpty(structId)){
			code = -1;
			mesg = "对应的工程项目为空，无法完成对应项目的基础数据导入!";
		}else{
			String[] wbsIdArr = wbsIds.split(",");
			StringBuffer wbsIdStr = new StringBuffer();
			for(String wbsId:wbsIdArr){
				wbsIdStr.append("'").append(wbsId).append("',");
			}
			String hql = " from BatchTestInfo as bt where bt.enabled=? and bt.proBaseWbs.wbsType=? and bt.proBaseWbs.id in ("
					+wbsIdStr.toString().substring(0,wbsIdStr.toString().length()-1)+") "
					+ " and not exists(from ProBatchTestInfo as pbt where pbt.proBaseWbs = bt.proBaseWbs and pbt.project.id=?)";
			List params = new ArrayList();
			params.add(Boolean.TRUE);
			params.add(ProWbsType.FXGC);

			params.add(structId);
			List<BatchTestInfo> datas = findByHQL(hql, params.toArray());
			if(datas!=null&&datas.size()>0){
				code = 0;
				mesg = "检验批划分标准数据成功导入["+datas.size()+"]个!";
				for(BatchTestInfo pbInfo:datas){
					ProBatchTestInfo pbtInfo = new ProBatchTestInfo();
					ProjectInfo pinfo = new ProjectInfo();
					pinfo.setId(structId);
					pbtInfo.setProject(pinfo);
					pbtInfo.setName(pbInfo.getName());
					pbtInfo.setNumber(pbInfo.getNumber());
					pbtInfo.setProBaseWbs(pbInfo.getProBaseWbs());
					pbtInfo.setContent(pbInfo.getContent());
					pbtInfo.setRemark(pbInfo.getRemark());
					addNewEntity(pbtInfo);
				}
			}else{
				code = 1;
				mesg = "没有对应的检验批标准数据可供导入!";
			}
		}
		wdm.setStatusCode(code);
		wdm.setStatusMesg(mesg);
		return wdm;
	}
}
