package com.myapp.controller.ec.proresourcedata;

import com.myapp.core.annotation.PermissionAnn;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年12月11日 
 * @system: 项目级资料目录 档案资料
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.项目资料目录.档案资料",number="app.ec.proresourcedata.profile")
@Controller
@RequestMapping("ec/proresourcedata/profiles")
public class ProfilesListController extends ProResourceDataBaseListController {
	@Override
	public String getCode(){
		return "profiles";
	}
	@Override
	public String getEditUrl() {
		return "ec/proresourcedata/profilesEdit";
	}
	@Override
	public String getListUrl() {
		return "ec/proresourcedata/profilesList";
	}
	
}
