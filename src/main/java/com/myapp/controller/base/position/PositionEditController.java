package com.myapp.controller.base.position;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseDataEditController;
import com.myapp.core.entity.BaseOrgInfo;
import com.myapp.core.entity.JobDutyInfo;
import com.myapp.core.entity.PositionInfo;
import com.myapp.core.entity.PositionJobDutyInfo;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.service.PositionService;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年8月16日 
 * @system:
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.岗位管理",number="app.position")
@Controller
@RequestMapping("base/position")
public class PositionEditController extends BaseDataEditController{
	@Resource
	public PositionService positionService;
	
	public AbstractBaseService getService() {
		return positionService;
	}

	public Object createNewData() {
		return new PositionInfo();
	}

	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("org",DataTypeEnum.F7,BaseOrgInfo.class));
		cols.add(new ColumnModel("parent",DataTypeEnum.F7,PositionInfo.class));
		cols.add(new ColumnModel("respible", DataTypeEnum.BOOLEAN));
		
		ColumnModel jobDutyItems = new ColumnModel("jobDutyItems",DataTypeEnum.ENTRY,PositionJobDutyInfo.class);
		jobDutyItems.getCols().add(new ColumnModel("id",DataTypeEnum.PK));
		
		ColumnModel jobDuty = new ColumnModel("jobDuty",DataTypeEnum.F7,"id,name");
		jobDuty.setClaz(JobDutyInfo.class);
		jobDutyItems.getCols().add(jobDuty);
		jobDutyItems.getCols().add(new ColumnModel("remark"));
		
		cols.add(jobDutyItems);
		return cols;
	}
	

	public CoreBaseInfo getEntityInfo() {
		return new PositionInfo();
	}
}
