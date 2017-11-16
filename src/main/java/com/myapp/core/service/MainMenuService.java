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
import com.myapp.core.exception.db.QueryException;
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
		sb.append("select m.fid as id,m.fname as name,m.ficonCodeType as iconCodeType");
		sb.append(",m.flongnumber as fln,m.ficonType as iconType,m.ficon as icon");
		sb.append(" from t_pm_mainmenu as m");
		sb.append(" where m.fid is not null and m.flevel =? and m.fonshow=?");
		sb.append(" order by m.fnumber");
		List params = new ArrayList();
		params.add(2);
		params.add(Boolean.TRUE);
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
	
	
	
	public JSONObject getUserMenuJson(String modelLongNumber,UserInfo uInfo) throws QueryException{
		List<Map<String,Object>> menus = new ArrayList<Map<String,Object>>();
		if(!BaseUtil.isEmpty(modelLongNumber)){
			List<Map> dbmenus = getModelMenu(modelLongNumber,uInfo);
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
	
	public static void main(String[] args){
		String id = "tJBa++D2SG+Qk2m/lk9jo=qYE5FAKZA=";
		id = id.replaceAll("/", "");
		id = id.replaceAll("\\+", "");
		id = id.replaceAll("=", "");
		System.out.println(id);
	}
	
	
	
	
}
