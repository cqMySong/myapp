package com.myapp.controller.ec.proresourcedata;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.core.annotation.PermissionAnn;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年12月11日 
 * @system: 项目级资料目录 -技术（暂时未看到技术资料）
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.项目资料目录.技术资料",number="app.ec.proresourcedata.skilldata")
@Controller
@RequestMapping("ec/proresourcedata/skilldatas")
public class ProSkillDataListController extends ProResourceDataBaseListController {
	
	public String getCode(){
		return "skilldata";
	}

	public String getEditUrl() {
		return "ec/proresourcedata/skillDataEdit";
	}

	public String getListUrl() {
		return "ec/proresourcedata/skillDataList";
	}
	
}
