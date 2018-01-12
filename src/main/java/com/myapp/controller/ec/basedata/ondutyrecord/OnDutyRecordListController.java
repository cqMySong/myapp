package com.myapp.controller.ec.basedata.ondutyrecord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseListController;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.util.EnumUtil;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.enums.ec.LetterType;
import com.myapp.service.ec.basedata.OnDutyRecordService;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年6月7日 
 * @system:
 *
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.基础数据.安保值班记录",number="app.ec.basedata.ondutyrecord")
@Controller
@RequestMapping("ec/basedata/ondutyrecords")
public class OnDutyRecordListController extends BaseListController {
	
	@Resource
	public OnDutyRecordService onDutyRecordService;
	public AbstractBaseService getService() {
		return onDutyRecordService;
	}

	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("dutyDate",DataTypeEnum.DATETIME));
		cols.add(new ColumnModel("dutyPosition"));
		cols.add(new ColumnModel("planDutyor"));
		cols.add(new ColumnModel("realDutyor"));
		cols.add(new ColumnModel("content"));
		ColumnModel creator = new ColumnModel("creator",DataTypeEnum.F7,"id,name");
		creator.setClaz(UserInfo.class);
		cols.add(creator);
		ColumnModel project = new ColumnModel("project",DataTypeEnum.F7,"id,name");
		project.setClaz(ProjectInfo.class);
		cols.add(project);
		return cols;
	}
	public void executeQueryParams(Criteria query) {
		super.executeQueryParams(query);
		String serach = request.getParameter("search");
		SimpleExpression se = Restrictions.eq("id","xyz");
		if(!BaseUtil.isEmpty(serach)){
			Map searchMap = JSONObject.parseObject(serach, new HashMap().getClass());
			Object objTree = searchMap.get("tree");
			if(objTree!=null){
				Map treeMap = JSONObject.parseObject(objTree.toString(), new HashMap().getClass());
				Object idObj = treeMap.get("id");
				Object type = treeMap.get("type");
				if(type!=null&&idObj!=null){
					if("project".equals(type.toString())){
						se = Restrictions.eq("project.id", idObj.toString());
					}
				}
			}
		}
		query.add(se);
	}
	public List<Order> getOrders() {
		List<Order> orders = new ArrayList<Order>();
		orders.add(Order.asc("project.number"));
		orders.add(Order.asc("dutyDate"));
		return orders;
	}
	public String getEditUrl() {
		return "ec/basedata/ondutyrecord/onDutyRecordEdit";
	}

	public String getListUrl() {
		return "ec/basedata/ondutyrecord/onDutyRecordList";
	}
	
	public String getHeadTitle() {
		return "安保值班记录";
	}
	
	public List<ExcelExportEntity> getExportHeader() {
		List<ExcelExportEntity> entitys = new ArrayList<ExcelExportEntity>();
		entitys.add(stringEntity("工程项目", "project_name"));
		entitys.add(dateEntity("值班时间", "dutyDate"));
		entitys.add(dateEntity("计划值班人员", "planDutyor"));
		entitys.add(dateEntity("实际值班人员", "realDutyor"));
		entitys.add(remarkEntity("交接事项", "content"));
		return entitys;
	}
}
