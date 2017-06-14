package com.myapp.controller.ec.basedata.project;

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
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.util.BaseUtil;
import com.myapp.service.ec.basedata.ProjectService;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年5月27日 
 * @system:
 *
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.基础数据.工程项目",number="app.ec.basedata.project")
@Controller
@RequestMapping("ec/basedata/projects")
public class ProjectListController extends BaseTreeListController {
	@Resource
	public ProjectService projectService;
	public AbstractBaseService getService() {
		return projectService;
	}
	
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("number"));
		cols.add(new ColumnModel("remark"));
		cols.add(new ColumnModel("address"));
		cols.add(new ColumnModel("scale"));
		cols.add(new ColumnModel("eavesHeight"));
		cols.add(new ColumnModel("floorHeight"));
		cols.add(new ColumnModel("area"));
		cols.add(new ColumnModel("aseismicLevel"));
		ColumnModel parentCol = new ColumnModel("parent",DataTypeEnum.F7,"name,number");
		cols.add(parentCol);
		ColumnModel structCol = new ColumnModel("structType",DataTypeEnum.F7,"name,number");
		cols.add(structCol);
		ColumnModel orgCol = new ColumnModel("org",DataTypeEnum.F7,"name");
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
		return "ec/basedata/project/projectEdit";
	}
	public String getListUrl() {
		return "ec/basedata/project/projectList";
	}
	public AbstractBaseService getTreeService() {
		return projectService;
	}
}
