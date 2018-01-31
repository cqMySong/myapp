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
 * 现场施工周月检查项目配置 
 *-----------MySong---------------
 */

@PermissionAnn(name="系统管理.现场管理.基础数据.施工现场周月检查项目",number="app.ec.basedata.workweekmonthcheck")
@Controller
@RequestMapping("ec/basedata/workweekmonthchecks")
public class WorkWeekMonthCheckListController extends WorkCheckItemListController {
	
	public WorkCheckGroup getWorkCheckGroup(){
		return WorkCheckGroup.WEEKMONTH;
	}
	public String getEditUrl() {
		return "ec/basedata/workcheckitem/workWeekMonthCheckEdit";
	}

	public String getListUrl() {
		return "ec/basedata/workcheckitem/workWeekMonthCheckList";
	}

}
