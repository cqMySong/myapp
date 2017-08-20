package com.myapp.controller.ec.plan.worklog;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseBillEditController;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.BillState;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.entity.ec.plan.ProjectTotalPlanInfo;
import com.myapp.entity.ec.plan.WorkLogInfo;
import com.myapp.service.ec.plan.WorkLogService;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年5月20日 
 * @system: 
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.计划管理.项目工作日志",number="app.ec.plan.worklog")
@Controller
@RequestMapping("ec/plan/worklog")
public class WorkLogEditController extends BaseBillEditController{
	@Resource
	public WorkLogService workLogService;
	public AbstractBaseService getService() {
		return workLogService;
	}

	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("number"));
		
		cols.add(new ColumnModel("remark"));
		cols.add(new ColumnModel("week"));
		cols.add(new ColumnModel("workSite"));
		cols.add(new ColumnModel("attendance"));
		cols.add(new ColumnModel("temperature"));
		cols.add(new ColumnModel("amweather"));
		cols.add(new ColumnModel("pmweather"));
		cols.add(new ColumnModel("sjbg"));
		cols.add(new ColumnModel("bgwh"));
		cols.add(new ColumnModel("tzdw"));
		cols.add(new ColumnModel("jsjd"));
		cols.add(new ColumnModel("jsjdr"));
		cols.add(new ColumnModel("ybgcysbw"));
		cols.add(new ColumnModel("sjskzz"));
		cols.add(new ColumnModel("cljcsjqk"));
		cols.add(new ColumnModel("zl"));
		cols.add(new ColumnModel("aq"));
		cols.add(new ColumnModel("gz"));
		cols.add(new ColumnModel("wrokContent"));
		cols.add(new ColumnModel("optDutyer"));
		
		
		
		
		cols.add(new ColumnModel("bizDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("billState",DataTypeEnum.ENUM,BillState.class));
		cols.add(new ColumnModel("createDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("lastUpdateDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("auditDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("project",DataTypeEnum.F7,ProjectInfo.class));
		cols.add(new ColumnModel("createUser",DataTypeEnum.F7,UserInfo.class));
		cols.add(new ColumnModel("lastUpdateUser",DataTypeEnum.F7,UserInfo.class));
		cols.add(new ColumnModel("auditor",DataTypeEnum.F7,UserInfo.class));
		return cols;
	}
	
	public Object createNewData() {
		WorkLogInfo wlInfo = new WorkLogInfo();
		return wlInfo;
	}

	public CoreBaseInfo getEntityInfo() {
		return new WorkLogInfo();
	}
}
