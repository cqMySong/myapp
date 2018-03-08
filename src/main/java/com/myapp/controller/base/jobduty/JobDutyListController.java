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

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseDataListController;
import com.myapp.core.entity.JobDutyGroupInfo;
import com.myapp.core.entity.PermissionInfo;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.service.JobDutyService;
import com.myapp.core.util.BaseUtil;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年6月7日 
 * @system:
 *
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.工作职责",number="app.jobduty")
@Controller
@RequestMapping("base/jobdutys")
public class JobDutyListController extends BaseDataListController{
	@Resource
	public JobDutyService jobDutyService;
	public AbstractBaseService getService() {
		return jobDutyService;
	}
	
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		ColumnModel shortCutMenu = new ColumnModel("shortCutMenu",DataTypeEnum.F7,"id,name,displayName");
		shortCutMenu.setClaz(PermissionInfo.class);
		cols.add(shortCutMenu);
		cols.add(new ColumnModel("group",DataTypeEnum.F7,JobDutyGroupInfo.class,"id,name,displayName"));
		return cols;
	}

	public void executeQueryParams(Criteria query) {
		super.executeQueryParams(query);
		String serach = request.getParameter("search");
		if(!BaseUtil.isEmpty(serach)){
			Map searchMap = JSONObject.parseObject(serach, new HashMap().getClass());
			Object objTree = searchMap.get("tree");
			if(objTree!=null){
				Map treeMap = JSONObject.parseObject(objTree.toString(), new HashMap().getClass());
				Object lnObj = treeMap.get("longNumber");
				if(lnObj!=null){
					query.add(Restrictions.like("group.longNumber",lnObj.toString(),MatchMode.START));
				}
			}
		}
		
//		{"tree":{"id":"+FzoNhaQT1+awmV7ZYuSPRupCTY=","name":"检查类","number":"01.02","longNumber":"01!01.02"}}
	}
	
	
	public String getEditUrl() {
		return "jobduty/jobdutyEdit";
	}

	public String getListUrl() {
		return "jobduty/jobdutyList";
	}
	
	public List<Order> getOrders(){
		List<Order> orders = new ArrayList<Order>();
		orders.add(Order.asc("group.longNumber"));
		orders.add(Order.asc("number"));
		return orders;
	}
}
