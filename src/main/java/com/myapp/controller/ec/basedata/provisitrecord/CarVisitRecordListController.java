package com.myapp.controller.ec.basedata.provisitrecord;

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

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseListController;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.util.BaseUtil;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.enums.ec.IDType;
import com.myapp.enums.ec.VisitType;
import com.myapp.service.ec.basedata.OnDutyRecordService;
import com.myapp.service.ec.basedata.ProSecurityCaseService;
import com.myapp.service.ec.basedata.ProVisitRecordService;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年10月30日 
 * @system: 项目安保预案
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.基础数据.到访车辆记录",number="app.ec.basedata.carvisitrecord")
@Controller
@RequestMapping("ec/basedata/carvisitrecords")
public class CarVisitRecordListController extends BaseListController {
	
	@Resource
	public ProVisitRecordService proVisitRecordService;
	public AbstractBaseService getService() {
		return proVisitRecordService;
	}

	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("visitDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("cause"));
		
		cols.add(new ColumnModel("carType"));
		cols.add(new ColumnModel("carNo"));
		
		cols.add(new ColumnModel("inDate",DataTypeEnum.DATETIME));
		cols.add(new ColumnModel("outDate",DataTypeEnum.DATETIME));
		cols.add(new ColumnModel("confirm"));
		
		ColumnModel creator = new ColumnModel("creator",DataTypeEnum.F7,"id,name");
		creator.setClaz(UserInfo.class);
		cols.add(creator);
		return cols;
	}
	
	public void executeQueryParams(Criteria query) {
		super.executeQueryParams(query);
		query.add(Restrictions.eq("visitType", VisitType.CAR));
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
		orders.add(Order.asc("inDate"));
		return orders;
	}
	public String getEditUrl() {
		return "ec/basedata/provisitrecord/carVisitRecordEdit";
	}

	public String getListUrl() {
		return "ec/basedata/provisitrecord/carVisitRecordList";
	}

}
