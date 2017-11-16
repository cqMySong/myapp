package com.myapp.controller.base.mainmenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseTreeListController;
import com.myapp.core.entity.BaseOrgInfo;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.enums.IconCodeType;
import com.myapp.core.enums.IconType;
import com.myapp.core.enums.MenuOpenType;
import com.myapp.core.enums.OrgTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.service.MainMenuService;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.util.EnumUtil;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年5月27日 
 * @system:
 *
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.门户管理.菜单管理",number="app.home.menu")
@Controller
@RequestMapping("base/home/menus")
public class MainMenuListController extends BaseTreeListController {
	@Resource
	public MainMenuService mainMenuService;
	public AbstractBaseService getService() {
		return mainMenuService;
	}
	
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("number"));
		cols.add(new ColumnModel("icon"));
		cols.add(new ColumnModel("url"));
		cols.add(new ColumnModel("params"));
		cols.add(new ColumnModel("remark"));
		cols.add(new ColumnModel("sysMenu",DataTypeEnum.BOOLEAN));
		cols.add(new ColumnModel("onShow",DataTypeEnum.BOOLEAN));
		cols.add(new ColumnModel("iconCodeType",DataTypeEnum.ENUM,IconCodeType.class));
		cols.add(new ColumnModel("iconType",DataTypeEnum.ENUM,IconType.class));
		cols.add(new ColumnModel("menuOpenType",DataTypeEnum.ENUM,MenuOpenType.class));
		ColumnModel orgCol = new ColumnModel("parent",DataTypeEnum.F7,"name,number");
		cols.add(orgCol);
		return cols;
	}
	public void executeQueryParams(Criteria query) {
		super.executeQueryParams(query);
		String serach = request.getParameter("search");
		if(!BaseUtil.isEmpty(serach)){
			Map searchMap = JSONObject.parseObject(serach, new HashMap().getClass());
			Object includeChildObj = searchMap.get("includeChild");
			boolean include = true;
			if(includeChildObj!=null&&includeChildObj instanceof Boolean){
				include = (Boolean)includeChildObj;
			}
			Object objTree = searchMap.get("tree");
			if(objTree!=null){
				Map treeMap = JSONObject.parseObject(objTree.toString(), new HashMap().getClass());
				Object lnObj = treeMap.get("longNumber");
				if(lnObj!=null){
					query.add(Restrictions.like("longNumber",lnObj.toString(),include?MatchMode.START:MatchMode.EXACT));
				}
			}
		}
	}
	
	public void executeTreeQueryParams(Criteria query) {
		super.executeQueryParams(query);
	}

	public Order getOrder() {
		return Order.asc("longNumber");
	}
	public String getEditUrl() {
		return "menu/mainMenuEdit";
	}
	public String getListUrl() {
		return "menu/mainMenuList";
	}
	public AbstractBaseService getTreeService() {
		return mainMenuService;
	}
}
