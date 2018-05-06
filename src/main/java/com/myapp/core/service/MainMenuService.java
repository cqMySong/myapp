package com.myapp.core.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.entity.MainMenuInfo;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.MenuOpenType;
import com.myapp.core.enums.PermissionTypeEnum;
import com.myapp.core.exception.db.QueryException;
import com.myapp.core.model.MyWebContext;
import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.util.WebUtil;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年11月13日 
 * @system: 系统菜单
 *-----------MySong---------------
 */
@Service("mainMenuService")
public class MainMenuService extends BaseInterfaceService<MainMenuInfo> {

	/**
	 * 获取功能模块级菜单 第二级菜单 
	 * @throws QueryException
	 */
	public List getTopMainMenu(UserInfo uInfo) throws QueryException{
		StringBuffer sb = new StringBuffer();
		List params = new ArrayList();
		sb.append("select m.fid as id,m.fname as name,m.ficonCodeType as iconCodeType");
		sb.append(",m.flongnumber as fln,m.ficonType as iconType,m.ficon as icon");
		sb.append(" from t_pm_mainmenu as m");
		sb.append(" where m.fid is not null and m.flevel =? and m.fonshow=?");
		params.add(2);
		params.add(Boolean.TRUE);
		//方便测试
		if(!"qKfcTZz2RwWP4irSnx4suVm7LJQ=".equals(uInfo.getId())){
			if(!uInfo.getAdmin()){//非系统管理员用户，只能获取功能性菜单，但是系统管理管理员可以查看功能性菜单
				sb.append(" and m.fsysMenu =?");
				params.add(Boolean.FALSE);
			}
			if(uInfo.getAdmin()&&uInfo.getSysUser()){//是系统管理员且系统用户的 只能查看系统菜单下面的所有功能
				sb.append(" and m.fsysMenu =?");
				params.add(Boolean.TRUE);
			}
		}
		sb.append(" order by m.fnumber");
		return executeSQLQuery(sb.toString(), params.toArray());
	}
	/**
	 * 获取模块级菜单  只能有两级
	 * @param modelLongNumber 功能模块长编码
 	 * @param uInfo 用户  用于显示用户级的模块菜单
 	 * 
	 */
	public List getModelMenu(String modelLongNumber,UserInfo uInfo) throws QueryException{
		StringBuffer sb = new StringBuffer();
		sb.append("select m.fid as id,m.fname as name,m.ficonCodeType as iconCodeType");
		sb.append(",m.ficonType as iconType,m.ficon as icon,m.furl as url");
		sb.append(",m.fparams as params,m.fprentid as pid,m.fmenuOpenType as openType");
		sb.append(" from t_pm_mainmenu as m");
		sb.append(" where (m.flevel =? or m.flevel = ?) and m.fonshow=?");
		List params = new ArrayList();
		params.add(3);
		params.add(4);
		params.add(Boolean.TRUE);
		if(!BaseUtil.isEmpty(modelLongNumber)){
			sb.append(" and m.flongnumber like ? ");
			params.add(modelLongNumber+"%");
		}
		sb.append(" order by m.flongnumber");
		return executeSQLQuery(sb.toString(), params.toArray());
	}
	
	public String _toString(Object obj){
		return obj==null?"":obj.toString();
	}
	
	public JSONObject getUserMenuJson(String modelLongNumber,UserInfo uInfo,MyWebContext webCtx) throws QueryException{
		List<Map<String,Object>> menus = new ArrayList<Map<String,Object>>();
		if(!BaseUtil.isEmpty(modelLongNumber)){
			List<Map> dbmenus = getUserMenusData(modelLongNumber,uInfo,webCtx);
			if(dbmenus!=null&&dbmenus.size()>0){
				Map<String,Map> item = new HashMap<String,Map>();
				for(Map dbmenu:dbmenus){
					Map curMenu = new HashMap();
					curMenu.put("id", WebUtil.UUID2String2(_toString(dbmenu.get("id"))));
					curMenu.put("title", dbmenu.get("name"));
					String url = _toString(dbmenu.get("url"));
					if(!BaseUtil.isEmpty(url)){
						String params = _toString(dbmenu.get("params"));
						if(!BaseUtil.isEmpty(params)){
							if(url.indexOf("?")<0) url +="?";
							url += params;
						}
						curMenu.put("url", url);
					}
					String icon = _toString(dbmenu.get("icon"));
					String iconType = _toString(dbmenu.get("iconType"));
					String iconCodeType = _toString(dbmenu.get("iconCodeType"));
					if(!BaseUtil.isEmpty(icon)&&!BaseUtil.isEmpty(iconType)
							&&!BaseUtil.isEmpty(iconCodeType)){
						Map curIcon = new HashMap();
						curIcon.put("name", icon);
						curIcon.put("type", iconType);
						curIcon.put("codeType", iconCodeType);
						curMenu.put("icon", curIcon);
					}
					String openType = _toString(dbmenu.get("openType"));
					if(BaseUtil.isEmpty(openType)) openType = MenuOpenType.MAINTAB.getValue();
					String target = openType;
					if(openType.equals(MenuOpenType.MAINTAB.getValue())){
						target = "#mainTab";
					}
					curMenu.put("target", target);
					
					String pid = _toString(dbmenu.get("pid"));
					Map objp = item.get(pid);
					if(objp!=null){
						Object objchs = objp.get("child");
						List<Map> childs = null;
						if(objchs!=null&&objchs instanceof List){
							childs = (List<Map>) objchs;
						}else{
							childs = new ArrayList<Map>();
						}
						childs.add(curMenu);
						objp.put("child", childs);
					}else{
						item.put(_toString(dbmenu.get("id")), curMenu);
						menus.add(curMenu);
					}
				}
			}
		}
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("menus", menus);
		jsonObj.put("theme", "white");//默认主题
		jsonObj.put("skin", "");
		return jsonObj;
	}
	
	//根据用户且当前的用户组织去获取对应的菜单范围
	public List getUserMenusData(String mln,UserInfo uInfo,MyWebContext webCtx) throws QueryException{
		if(uInfo.getAdmin()&&uInfo.getSysUser()){
			return getModelMenu(mln,uInfo);
		}
		String curOrgLn = "";
		String curOrgId ="";
		if(webCtx!=null&&webCtx.getCurOrg()!=null){
			curOrgLn = webCtx.getCurOrg().getLongNumber();
			curOrgId = webCtx.getCurOrg().getId();
		}
		String userId = uInfo.getId();
		List params = new ArrayList();
		StringBuffer sb = new StringBuffer();
		sb.append("select m.* from (");
		//权限分配到人的菜单
		sb.append(" select a.fid as id,a.fname as name,a.ficonCodeType as iconCodeType");
		sb.append(" ,a.ficonType as iconType,a.ficon as icon,a.furl as url,a.flongnumber as fln");
		sb.append(" ,a.fparams as params,a.fprentid as pid,a.fmenuOpenType as openType");
		sb.append(" from t_pm_mainmenu as a");
		sb.append(" where a.fonshow =? and exists(");params.add(Boolean.TRUE);
			sb.append(" select b.fid from t_pm_permission as b where b.furl = a.furl and b.ftype=?");params.add(PermissionTypeEnum.PAGE.getValue());
				//用户权限范围内所有权限
			
				//1：组织范围内:通过工作岗位职责分部的权限切通过导入到用户的权限中
				sb.append("	and ( ");
				sb.append("(");
					sb.append("	exists(select c.fid from t_pm_permissionassign as c where c.fpermissionId=b.fid and c.ftargetId=? and c.forgId is null)");params.add(userId);
					sb.append(" and exists(SELECT t5.fid ");//组织范围内:通过工作岗位职责分部的权限
					sb.append("	from t_base_Org AS t1,t_pm_position AS t2");
					sb.append(",t_pm_positionJobDuty AS t3,t_pm_permissionassign AS t4,t_pm_permission AS t5");
					sb.append(" where t1.flongnumber like ?");params.add(curOrgLn+"%");
					sb.append(" and t2.forgId = t1.fid and t3.fprentid = t2.fid and t5.ftype=?");params.add(PermissionTypeEnum.PAGE.getValue());
					sb.append(" and t3.fjobDutyId = t4.ftargetId and t5.fid = t4.fpermissionId and t5.fid = b.fid)");
				sb.append(")");
				sb.append(" or exists(select c.fid from t_pm_permissionassign as c where c.fpermissionId=b.fid and c.ftargetId=? and c.forgId=?)");params.add(userId);params.add(curOrgId);
				sb.append(")");
		sb.append(" )");
		if(!BaseUtil.isEmpty(mln)){
			sb.append(" and a.flongnumber like ? ");
			params.add(mln+"%");
		}
		sb.append(" union all ");
		//权限分配到人对应的上级菜单
		sb.append(" select t.fid as id,t.fname as name,t.ficonCodeType as iconCodeType");
		sb.append(" ,t.ficonType as iconType,t.ficon as icon,t.furl as url,t.flongnumber as fln");
		sb.append(" ,t.fparams as params,t.fprentid as pid,t.fmenuOpenType as openType");
		sb.append(" from t_pm_mainmenu as t");
		sb.append(" where t.flevel=3 and exists(");
		sb.append(" select a.fid from t_pm_mainmenu as a where a.fonshow =? and exists(");params.add(Boolean.TRUE);
			sb.append(" select b.fid from t_pm_permission as b where b.furl = a.furl and b.ftype=?");params.add(PermissionTypeEnum.PAGE.getValue());
			//用户权限范围内
			sb.append("	and exists(select c.fid from t_pm_permissionassign as c where c.fpermissionId=b.fid and c.ftargetId=?)");params.add(userId);
				//组织范围内
				sb.append("	and ( ");
				sb.append("(");
					sb.append("	exists(select c.fid from t_pm_permissionassign as c where c.fpermissionId=b.fid and c.ftargetId=? and c.forgId is null)");params.add(userId);
					sb.append(" and exists(SELECT t5.fid ");//组织范围内:通过工作岗位职责分部的权限
					sb.append("	from t_base_Org AS t1,t_pm_position AS t2");
					sb.append(",t_pm_positionJobDuty AS t3,t_pm_permissionassign AS t4,t_pm_permission AS t5");
					sb.append(" where t1.flongnumber like ?");params.add(curOrgLn+"%");
					sb.append(" and t2.forgId = t1.fid and t3.fprentid = t2.fid and t5.ftype=?");params.add(PermissionTypeEnum.PAGE.getValue());
					sb.append(" and t3.fjobDutyId = t4.ftargetId and t5.fid = t4.fpermissionId and t5.fid = b.fid)");
				sb.append(")");
				sb.append(" or exists(select c.fid from t_pm_permissionassign as c where c.fpermissionId=b.fid and c.ftargetId=? and c.forgId=?)");params.add(userId);params.add(curOrgId);
				sb.append(")");
		sb.append(" ) and t.fid = a.fprentid");
		if(!BaseUtil.isEmpty(mln)){
			sb.append(" and a.flongnumber like ? ");
			params.add(mln+"%");
		}
		sb.append(") and t.fonshow=?");params.add(Boolean.TRUE);
		sb.append(") as m ");
		sb.append(" order by m.fln");
		System.out.println("执行用户查询菜单权限sql：  "+ sb.toString());
		System.out.println("执行用户查询菜单权限参数：  "+ params.toArray());
		return executeSQLQuery(sb.toString(), params.toArray());
	}
	
	public static void main(String[] args){
		String id = "tJBa++D2SG+Qk2m/lk9jo=qYE5FAKZA=";
		id = id.replaceAll("/", "");
		id = id.replaceAll("\\+", "");
		id = id.replaceAll("=", "");
		System.out.println(id);
	}
	
	
	
	
}
