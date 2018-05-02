package com.myapp.service.ec.basedata;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.SchemeState;
import com.myapp.core.exception.db.QueryException;
import com.myapp.core.model.PageModel;
import com.myapp.entity.ec.basedata.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.myapp.core.exception.db.SaveException;
import com.myapp.core.model.WebDataModel;
import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.core.util.BaseUtil;
import com.myapp.enums.ec.SkillType;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年12月20日 
 * @system:
 * 技术交底：施工技术和安全技术
 *-----------MySong---------------
 */
@Service("proSkillDisclosureService")
public class ProSkillDisclosureService extends
		BaseInterfaceService<ProSkillDisclosureInfo> {

	//导入标准的技术交底内容
	public WebDataModel batchInitProData(String proJectId,SkillType st) throws SaveException{
		WebDataModel wdm = new WebDataModel();
		wdm.setData(null);
		int code = 0;
		String mesg = "";
		if(BaseUtil.isEmpty(proJectId)||st==null){
			code = -1;
			mesg = "对应的工程项目为空，无法完成对应项目的基础数据导入!";
		}else{
			String hql = " from SkillItemInfo as si where si.enabled=? and si.skillType=?"
					+ " and not exists(from ProSkillDisclosureInfo as pds where pds.skillItem.id = si.id and pds.project.id=?)";
			List params = new ArrayList();
			params.add(Boolean.TRUE);
			params.add(st);
			params.add(proJectId);
			List<SkillItemInfo> datas = findByHQL(hql, params.toArray());
			if(datas!=null&&datas.size()>0){
				code = 0;
				mesg = st.getName()+"标准数据成功导入["+datas.size()+"]个!";
				for(SkillItemInfo siInfo:datas){
					ProSkillDisclosureInfo psdInfo = new ProSkillDisclosureInfo();
					ProjectInfo pinfo = new ProjectInfo();
					pinfo.setId(proJectId);
					psdInfo.setProject(pinfo);
					psdInfo.setSkillClass(siInfo.getSkillClass());
					psdInfo.setSkillType(st);
					psdInfo.setSkillItem(siInfo);
					psdInfo.setName(siInfo.getName());
					psdInfo.setNumber(siInfo.getNumber());
					addNewEntity(psdInfo);
				}
			}else{
				code = 1;
				mesg = "没有对应的"+st.getName()+"标准数据可供导入!";
			}
		}
		wdm.setStatusCode(code);
		wdm.setStatusMesg(mesg);
		return wdm;
	}


	/**
	 * 功能：项目技术交底
	 * @param projectId
	 * @param userInfo
	 * @param wbsIds
	 */
	public WebDataModel batchSave(String projectId, UserInfo userInfo, String wbsIds,SkillType st) throws SaveException {
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

		String hql = " from SkillItemInfo as si where si.enabled=? and si.skillType=?"
				+ " and si.id in('"+wbsIds.replaceAll(",","','")+"') "
				+ " and not exists(from ProSkillDisclosureInfo as pds where pds.skillItem.id = si.id and pds.project.id=?)";
		List params = new ArrayList();
		params.add(Boolean.TRUE);
		params.add(st);
		params.add(projectId);
		List<SkillItemInfo> datas = findByHQL(hql, params.toArray());
		if(datas!=null&&datas.size()>0){
			code = 0;
			mesg = st.getName()+"标准数据成功导入["+datas.size()+"]个!";
			for(SkillItemInfo siInfo:datas){
				ProSkillDisclosureInfo psdInfo = new ProSkillDisclosureInfo();
				ProjectInfo pinfo = new ProjectInfo();
				pinfo.setId(projectId);
				psdInfo.setProject(pinfo);
				psdInfo.setSkillClass(siInfo.getSkillClass());
				psdInfo.setSkillType(st);
				psdInfo.setSkillItem(siInfo);
				psdInfo.setName(siInfo.getName());
				psdInfo.setNumber(siInfo.getNumber());
				psdInfo.setCreateDate(new Date());
				addNewEntity(psdInfo);
			}
		}else{
			code = 1;
			mesg = "没有对应的"+st.getName()+"标准数据可供导入!";
		}
		wdm.setStatusCode(code);
		wdm.setStatusMesg(mesg);
		return wdm;
	}

	/**
	 * 功能：施工技术交底台帐
	 * @param curPage
	 * @param pageSize
	 * @param params
	 * @return
	 * @throws QueryException
	 */
	public PageModel queryProqmSkilllLedger(Integer curPage, Integer pageSize, Map<String,Object> params)
			throws QueryException {
		List<Object> paramList = new ArrayList<>();
		StringBuffer sql = new StringBuffer();
		sql.append("select b.fname as className,a.fname as name,")
				.append("ds.fname as disclosurer,a.fFinishTime as finishTime,")
				.append("a.fsendee as sendee,a.fid as id,")
				.append("(select count(t.fid) from t_base_attachFile t where t.fsourceBillId = fid) as attach ")
				.append("from t_ec_proskillidisclosure a left join t_ec_skillClass b on a.fskillclass = b.fid ")
				.append(" left join t_pm_user ds on a.fdisclosurerId = ds.fid ")
				.append("where a.fskilltype='QM' and a.fProjectId=? ");
		paramList.add(params.get("projectId"));
		if(!BaseUtil.isEmpty(params.get("sendee"))){
			sql.append("and a.fsendee like ? ");
			paramList.add("%"+params.get("sendee")+"%");
		}
		if(!BaseUtil.isEmpty(params.get("directorName"))){
			sql.append("and ds.fname like ? ");
			paramList.add("%"+params.get("directorName")+"%");
		}
		sql.append(" order by a.fname,a.fFinishTime");
		return toPageSqlQuery(curPage,pageSize,sql.toString(),paramList.toArray());
	}

}
