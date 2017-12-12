package com.myapp.controller.ec.proresourcedata;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.core.annotation.PermissionAnn;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年12月11日 
 * @system: 项目级资料目录 -安全
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.项目资料目录.安全资料",number="app.ec.proresourcedata.safedata")
@Controller
@RequestMapping("ec/proresourcedata/safedatas")
public class ProSafeDataListController extends ProResourceDataBaseListController {
	
	public String getCode(){
		return "safedata";
	}

	public String getEditUrl() {
		return "ec/proresourcedata/safeDataEdit";
	}

	public String getListUrl() {
		return "ec/proresourcedata/safeDataList";
	}
	
}
