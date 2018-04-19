package com.myapp.controller.ec.basedata.workprocess;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseListController;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.util.BaseUtil;
import com.myapp.enums.ec.WorkType;
import com.myapp.service.ec.basedata.WorkProcessService;
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
@PermissionAnn(name="系统管理.现场管理.基础数据.项目主要问题处理摘要",number="app.ec.basedata.workprocess")
@Controller
@RequestMapping("ec/basedata/workprocesses")
public class WorkProcessListController extends BaseListController {
	
	@Resource
	public WorkProcessService workProcessService;
	@Override
	public AbstractBaseService getService() {
		return workProcessService;
	}
	@Override
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("workType",DataTypeEnum.ENUM, WorkType.class));
		cols.add(new ColumnModel("problemDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("problemContent"));
		cols.add(new ColumnModel("solution"));
		cols.add(new ColumnModel("workFollowUp",DataTypeEnum.BOOLEAN));
		cols.add(new ColumnModel("endSolveDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("solve",DataTypeEnum.BOOLEAN));
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
		orders.add(Order.desc("problemDate"));
		return orders;
	}
	@Override
	public String getEditUrl() {
		return "ec/basedata/workprocess/workProcessEdit";
	}
	@Override
	public String getListUrl() {
		return "ec/basedata/workprocess/workProcessList";
	}
}
