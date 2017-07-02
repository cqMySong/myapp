package com.myapp.controller.ec.plan.projectplan;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseListController;
import com.myapp.service.ec.plan.ProjectTotalPlanService;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年6月7日 
 * @system:
 *
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.计划管理.项目计划",number="app.ec.plan.projectplan")
@Controller
@RequestMapping("ec/plan/projectplans")
public class ProjectPlanListController extends BaseListController {
	
	@Resource
	public ProjectTotalPlanService projectTotalPlanService;
	public AbstractBaseService getService() {
		return projectTotalPlanService;
	}
	
	@RequestMapping("/monthlist")
	public ModelAndView monthList(){
		Map params = new HashMap();
		packageUIParams(params);
		return toPage(getMonthListUrl(), params);
	}
	
	
	
	
	public String getMonthListUrl() {
		return "ec/plan/projectplan/projectMonthPlanList";
	}
	
	public String getEditUrl() {
		return "";
	}

	public String getListUrl() {
		return "ec/plan/projectplan/projectPlanList";
	}

}
