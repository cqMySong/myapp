package com.myapp.controller.base.datagroup;

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
import com.myapp.core.entity.basedate.DataGroupInfo;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.service.basedata.DataGroupService;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.util.PropertiesUtil;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年5月27日 
 * @system:
 *
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.数据分类",number="app.datagroup")
@Controller
@RequestMapping("base/datagroups")
public class DataGroupListController extends BaseTreeListController {
	
	@Resource
	public DataGroupService dataGroupService;
	public AbstractBaseService getService() {
		return dataGroupService;
	}
	
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("number"));
		cols.add(new ColumnModel("enabled",DataTypeEnum.BOOLEAN));
		ColumnModel parent = new ColumnModel("parent",DataTypeEnum.F7,"id,name,number");
		parent.setClaz(DataGroupInfo.class);
		cols.add(parent);
		cols.add(new ColumnModel("remark"));
		return cols;
	}
	
	public void toListUIParams(Map params) {
		super.packageUIParams(params);
		String code = request.getParameter("code");
		if(!BaseUtil.isEmpty(code)){
			params.put("code", code);
		}
	}
	
	
	public void toEditUIParams(Map params) {
		super.toEditUIParams(params);
		Map uiCtx = getUiCtx();
		if(uiCtx!=null&&uiCtx.containsKey("code")){
			params.put("code", uiCtx.get("code"));
			params.put("title", getCodeName(uiCtx.get("code")));
		}
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
			
			Object codeObj = searchMap.get("code");
			if(!BaseUtil.isEmpty(codeObj)){
				query.add(Restrictions.eq("code", codeObj.toString()));
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
		String code = request.getParameter("code");
		if(!BaseUtil.isEmpty(code)){
			query.add(Restrictions.eq("code",code.toString()));
		}
	}
	
	public String getCodeName(Object code){
		String codeName = "基础数据";
		if(!BaseUtil.isEmpty(code)){
			codeName = PropertiesUtil.getProperty("dataCode", code.toString());
		}
		if(!BaseUtil.isEmpty(codeName)) codeName +="分类";
		return codeName;
	}
	
	public List<Order> getTreeOrders() {
		List<Order> treeOrds = new ArrayList<Order>();
		treeOrds.add(Order.asc("code"));
		treeOrds.add(Order.asc("longNumber"));
		return treeOrds;
	}
	
	public List<Order> getOrders() {
		return getTreeOrders();
	}
	
	public String getTitle() {
		return getCodeName(request.getParameter("code"));
	}
	
	public String getTreeTitle() {
		return getCodeName(request.getParameter("code"));
	}

	public String getEditUrl() {
		return "basedata/datagroup/dataGroupEdit";
	}
	
	public String getListUrl() {
		return "basedata/datagroup/dataGroupList";
	}
	
	public AbstractBaseService getTreeService() {
		return dataGroupService;
	}
}
