package com.myapp.controller.ec.basedata.safepatrolrecord;

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
import com.myapp.enums.ec.DoWayType;
import com.myapp.service.ec.basedata.SafePatrolRecordService;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年11月02日 
 * @system:
 * 安保巡查记录
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.基础数据.安保巡查记录",number="app.ec.basedata.safepatrolrecord")
@Controller
@RequestMapping("ec/basedata/safepatrolrecords")
public class SafePatrolRecordListController extends BaseListController {
	
	@Resource
	public SafePatrolRecordService safePatrolRecordService;
	public AbstractBaseService getService() {
		return safePatrolRecordService;
	}

	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("patrolDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("range"));
		cols.add(new ColumnModel("attention"));
		cols.add(new ColumnModel("hasQuestion",DataTypeEnum.BOOLEAN));
		cols.add(new ColumnModel("doWay",DataTypeEnum.MUTILENUM,DoWayType.class));
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
		orders.add(Order.asc("patrolDate"));
		return orders;
	}
	
	public String getEditUrl() {
		return "ec/basedata/safepatrolrecord/safePatrolRecordEdit";
	}

	public String getListUrl() {
		return "ec/basedata/safepatrolrecord/safePatrolRecordList";
	}

}
