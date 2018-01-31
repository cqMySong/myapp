package com.myapp.controller.ec.basedata.workcheckitem;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.enums.ec.WorkCheckGroup;
/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2018年1月31日 
 * @system:
 * 现场施工日检查项目配置 
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.基础数据.施工现场日检查项目",number="app.ec.basedata.workdaycheck")
@Controller
@RequestMapping("ec/basedata/workdaychecks")
public class WorkDayCheckListController extends WorkCheckItemListController {
	
	public WorkCheckGroup getWorkCheckGroup(){
		return WorkCheckGroup.DAY;
	}
	
	public String getEditUrl() {
		return "ec/basedata/workcheckitem/workDayCheckEdit";
	}

	public String getListUrl() {
		return "ec/basedata/workcheckitem/workDayCheckList";
	}

}
