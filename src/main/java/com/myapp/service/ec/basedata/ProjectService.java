package com.myapp.service.ec.basedata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.myapp.core.enums.OrgTypeEnum;
import com.myapp.core.exception.db.QueryException;
import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.entity.ec.basedata.ProjectInfo;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年6月12日 
 * @system:
 *
 *-----------MySong---------------
 */
@Service("projectService")
public class ProjectService extends BaseInterfaceService<ProjectInfo> {
	
//	public List getProjectTreeData(Map params) throws QueryException{//项目组织树 
//		StringBuffer sql = new StringBuffer();
//		List orgType = new ArrayList();
//		String otpe = "";
//		if(params!=null&&params.containsKey("orgType")){
//			otpe = (String)params.get("orgType");
//		}else{
//			otpe = "'"+OrgTypeEnum.COMPANYORG.getValue()+"','"+OrgTypeEnum.PROJECTORG.getValue()+"'";
//		}
//		sql.append(" select fid as id,fnumber as number,fname as name,fprentid as parentId,flongnumber as longNumber,'baseOrg' as type");
//		sql.append(" from t_base_Org ");
//		sql.append(" where forgType in("+otpe+")");
//		sql.append(" union all ");
//		sql.append(" select fid as id,fnumber as number,fname as name,forgId as parentId,'01' as longNumber,'project' as type");
//		sql.append(" from t_ec_project ");
//		return executeSQLQuery(sql.toString(), orgType.toArray());
//	}
	
	public List getProjectTreeData(Map params) throws QueryException{//项目组织树 ;是否包含项目部
		StringBuffer sql = new StringBuffer();
		List queryParams = new ArrayList();
		String otpe = "";
		if(params!=null&&params.containsKey("orgType")){
			otpe = (String)params.get("orgType");
		}else{
			otpe = "'"+OrgTypeEnum.COMPANYORG.getValue()+"','"+OrgTypeEnum.PROJECTORG.getValue()+"'";
		}
		boolean includeProOrg = false;
		if(params!=null&&params.containsKey("includeProOrg")){
			Object objIn = params.get("includeProOrg");
			if(objIn!=null&&objIn instanceof Boolean){
				includeProOrg = (Boolean)objIn;
			}
		}
		sql.append(" select fid as id,fnumber as number,fname as name,fprentid as parentId,flongnumber as longNumber,'baseOrg' as type");
		sql.append(" from t_base_Org ");
		sql.append(" where forgType in("+otpe+")");
		if(!includeProOrg){
			sql.append(" and forgType != ?");
			queryParams.add(OrgTypeEnum.PROJECTORG.getValue());
		}
		sql.append(" union all ");
		sql.append(" select t.fid as id,t.fnumber as number,t.fname as name");
		if(!includeProOrg){
			sql.append(" ,t2.fid as parentId");
		}else{
			sql.append(" ,t.forgId as parentId");
		}
		sql.append(" ,'01' as longNumber,'project' as type");
		sql.append(" from t_ec_project as t ");
		if(!includeProOrg){
			sql.append(" left join t_base_Org as t1 on t1.fid = t.forgId");
			sql.append(" left join t_base_Org as t2 on t2.fid = t1.fprentid");
		}
		return executeSQLQuery(sql.toString(), queryParams.toArray());
	}
}
