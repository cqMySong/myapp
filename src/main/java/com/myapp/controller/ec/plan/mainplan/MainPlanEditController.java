package com.myapp.controller.ec.plan.mainplan;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.entity.CoreInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseBillEditController;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.entity.ec.plan.MainPlanInfo;
import com.myapp.enums.ec.WorkFollow;
import com.myapp.enums.ec.WorkType;
import com.myapp.service.ec.plan.MainPlanService;

@PermissionAnn(name="系统管理.现场管理.计划管理.项目主要工作安排",number="app.ec.plan.mainplan")
@Controller
@RequestMapping("ec/plan/mainplan")
public class MainPlanEditController extends BaseBillEditController{
	
	@Resource
	public MainPlanService mainPlanService;
	public AbstractBaseService getService() {
		return mainPlanService;
	}
	
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("workType",DataTypeEnum.ENUM,WorkType.class));
		cols.add(new ColumnModel("content"));
		cols.add(new ColumnModel("beginDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("endDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("dutyPerson"));
		cols.add(new ColumnModel("isDelivery",DataTypeEnum.BOOLEAN));
		cols.add(new ColumnModel("deliveryPerson"));
		cols.add(new ColumnModel("workFollow",DataTypeEnum.ENUM,WorkFollow.class));
		cols.add(new ColumnModel("isDone",DataTypeEnum.BOOLEAN));
		cols.add(new ColumnModel("remark"));
		return cols;
	}

	public Object createNewData() {
		MainPlanInfo info = new MainPlanInfo();
		info.setWorkType(WorkType.QUALITY);
		info.setWorkFollow(WorkFollow.NORMAL);
		return info;
	}

	public CoreInfo getEntityInfo() {
		return new MainPlanInfo();
	}
	
	protected boolean verifyInput(Object editData) {
		return true;
	}

}
