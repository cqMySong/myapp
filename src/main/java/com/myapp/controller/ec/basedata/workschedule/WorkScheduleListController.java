package com.myapp.controller.ec.basedata.workschedule;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseListController;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.util.BaseUtil;
import com.myapp.enums.ec.WorkType;
import com.myapp.service.ec.basedata.WorkScheduleService;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年10月30日 
 * @system: 项目安保预案
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.基础数据.项目主要工作安排摘要",number="app.ec.basedata.workschedule")
@Controller
@RequestMapping("ec/basedata/workschedules")
public class WorkScheduleListController extends BaseListController {
	
	@Resource
	public WorkScheduleService workScheduleService;
	@Override
	public AbstractBaseService getService() {
		return workScheduleService;
	}
	@Override
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("workType",DataTypeEnum.ENUM, WorkType.class));
		cols.add(new ColumnModel("jobContent"));
		cols.add(new ColumnModel("startDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("finishDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("personLiable"));
		cols.add(new ColumnModel("takeOver",DataTypeEnum.BOOLEAN));
		cols.add(new ColumnModel("sendee"));
		cols.add(new ColumnModel("workFollowUp",DataTypeEnum.BOOLEAN));
		cols.add(new ColumnModel("finish",DataTypeEnum.BOOLEAN));
		cols.add(new ColumnModel("remark"));
		ColumnModel creator = new ColumnModel("creator",DataTypeEnum.F7,"id,name");
		creator.setClaz(UserInfo.class);
		cols.add(creator);
		return cols;
	}
	@Override
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
	@Override
	public List<Order> getOrders() {
		List<Order> orders = new ArrayList<Order>();
		orders.add(Order.desc("startDate"));
		return orders;
	}
	@Override
	public String getEditUrl() {
		return "ec/basedata/workschedule/workScheduleEdit";
	}
	@Override
	public String getListUrl() {
		return "ec/basedata/workschedule/workScheduleList";
	}

}
