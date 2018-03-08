package com.myapp.controller.base.jobduty;

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

import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseTreeListController;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.service.JobJutyGroupService;
import com.myapp.core.util.BaseUtil;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2018年03月08日 
 * @system:
 * 工作职责分组
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.工作职责分组",number="app.jobdutygroup")
@Controller
@RequestMapping("base/jobdutygroups")
public class JobJutyGroupListController extends BaseTreeListController {
	@Resource
	public JobJutyGroupService jobJutyGroupService;
	public AbstractBaseService getService() {
		return this.jobJutyGroupService;
	}
	
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("number"));
		cols.add(new ColumnModel("remark"));
		ColumnModel orgCol = new ColumnModel("parent",DataTypeEnum.F7,"name,number");
		cols.add(orgCol);
		return cols;
	}
	public List<ColumnModel> getTreeDataBinding() {
		List<ColumnModel> cols = super.getTreeDataBinding();
		cols.add(new ColumnModel("displayName"));
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
		return "jobduty/jobdutyGroupEdit";
	}

	public String getListUrl() {
		return "jobduty/jobdutyGroupList";
	}
	
	public AbstractBaseService getTreeService() {
		return jobJutyGroupService;
	}
	
	public String getHeadTitle() {
		return "组织机构";
	}
	
	public List<ExcelExportEntity> getExportHeader() {
		List<ExcelExportEntity> entity = new ArrayList<ExcelExportEntity>();
		entity.add(new ExcelExportEntity("编码", "number"));
		entity.add(new ExcelExportEntity("名称", "name"));
		entity.add(new ExcelExportEntity("上级编码", "parent_number"));
		entity.add(new ExcelExportEntity("上级名称", "parent_name"));
		ExcelExportEntity remark = new ExcelExportEntity("备注", "remark");
		remark.setWidth(80);
		remark.setWrap(true);
		entity.add(remark);
		return entity;
	}
	
}
