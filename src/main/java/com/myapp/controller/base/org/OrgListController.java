package com.myapp.controller.base.org;

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
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseListController;
import com.myapp.core.controller.BaseTreeListController;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.service.OrgService;
import com.myapp.core.util.BaseUtil;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年5月27日 
 * @system:
 *
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.组织管理",number="app.org")
@Controller
@RequestMapping("base/orgs")
public class OrgListController extends BaseTreeListController {
	@Resource
	public OrgService orgService;
	public AbstractBaseService getService() {
		return orgService;
	}
	
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("number"));
		cols.add(new ColumnModel("remark"));
		cols.add(new ColumnModel("shortCode"));
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
	public Order getOrder() {
		return Order.asc("longNumber");
	}
	public String getEditUrl() {
		return "org/orgEdit";
	}
	public String getListUrl() {
		return "org/orgList";
	}
	public AbstractBaseService getTreeService() {
		return orgService;
	}
}
