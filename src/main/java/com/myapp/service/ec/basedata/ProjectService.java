package com.myapp.service.ec.basedata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.myapp.core.entity.BaseOrgInfo;
import com.myapp.core.enums.OrgTypeEnum;
import com.myapp.core.exception.db.QueryException;
import com.myapp.core.model.MyWebContext;
import com.myapp.core.service.OrgService;
import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.core.util.BaseUtil;
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
	@Resource
	public OrgService orgService;
	
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
		return getProjectTreeData(params,null);
	}
	
	public static void main(String[] args){
		String str[] = "01!01!011!01107".split("!");
		String st = "";
		String ss = "";
		for(String s:str){
			if(!BaseUtil.isEmpty(st)) st +="!";
			st +=s;
			if(!BaseUtil.isEmpty(ss)) ss +=",";
			ss+="'"+st+"'";
			System.out.println(st);
		}
		System.out.println(ss);
	}
	
	public List getProjectTreeData(Map params,MyWebContext webCtx) throws QueryException{//项目组织树 ;是否包含项目部
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
		String flns = "";
		//修正 读取当前登录的默认组织
		if(webCtx!=null&&webCtx.getCurOrg()!=null){
			BaseOrgInfo curOrg = webCtx.getCurOrg();
			if(!OrgTypeEnum.PROJECTORG.equals(curOrg.getOrgType()))
				curOrg = orgService.getCurOrg(curOrg.getId(), OrgTypeEnum.PROJECTORG);
			if(curOrg!=null){
				String curFln = curOrg.getLongNumber();
				if(BaseUtil.isNotEmpty(curFln)){
					String[] curFlns = curFln.split("!");
					String curfn = "";
					for(String s:curFlns){
						if(!BaseUtil.isEmpty(curfn)) curfn +="!";
						curfn +=s;
						if(!BaseUtil.isEmpty(flns)) flns +=",";
						flns +="'"+curfn+"'";
					}
				}
			}
		}
		
		
//		if(webCtx!=null&&!BaseUtil.isEmpty(webCtx.getUserId())){
//			StringBuffer sb = new StringBuffer();
//			sb.append(" select g.flongnumber as fn from t_pm_userPosition as p,t_base_Org as g");
//			sb.append(" where g.fid=p.forgid and p.fprentid=?");
//			List pams = new ArrayList();
//			pams.add(webCtx.getUserId());
//			List<Map> orgLns = executeSQLQuery(sb.toString(),pams.toArray());
//			if(orgLns!=null&&orgLns.size()>0){
//				Map<String, String> flnMap = new HashMap<String, String>();
//				for(Map rowMap:orgLns){
//					String curFln_rd = (String)rowMap.get("fn");
//					String curFln = "";
//					if(!BaseUtil.isEmpty(curFln_rd)){
//						String[] curFln_rds = curFln_rd.split("!");
//						for(String fln:curFln_rds){
//							if(!BaseUtil.isEmpty(curFln)) curFln +="!";
//							curFln +=fln;
//							if(!flnMap.containsKey(curFln)){
//								flnMap.put(curFln, curFln);
//								if(!BaseUtil.isEmpty(flns)) flns +=",";
//								flns +="'"+curFln+"'";
//							}
//						}
//					}
//				}
//			}
//		}
		
		sql.append(" select fid as id,fnumber as number,fname as name,fprentid as parentId,flongnumber as longNumber,'baseOrg' as type");
		sql.append(" from t_base_Org ");
		sql.append(" where forgType in("+otpe+")");
		if(!includeProOrg){
			sql.append(" and forgType != ?");
			queryParams.add(OrgTypeEnum.PROJECTORG.getValue());
		}
		if(!BaseUtil.isEmpty(flns)){
			sql.append(" and flongnumber in("+flns+")");
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
		sql.append(" left join t_base_Org as t1 on t1.fid = t.forgId");
		if(!includeProOrg){
			sql.append(" left join t_base_Org as t2 on t2.fid = t1.fprentid");
		}
		sql.append(" where t.fid is not null");
		if(!BaseUtil.isEmpty(flns)){
			sql.append(" and "+(includeProOrg?"t1":"t2")+".flongnumber in("+flns+")");
		}
		
		return executeSQLQuery(sql.toString(), queryParams.toArray());
	}
	
	
}
