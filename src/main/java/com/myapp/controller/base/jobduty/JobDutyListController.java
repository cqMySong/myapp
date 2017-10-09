package com.myapp.controller.base.jobduty;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseDataListController;
import com.myapp.core.entity.PermissionInfo;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.service.JobDutyService;

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
		return cols;
	}

	public String getEditUrl() {
		return "jobduty/jobdutyEdit";
	}

	public String getListUrl() {
		return "jobduty/jobdutyList";
	}
}
