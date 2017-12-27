package com.myapp.controller.ec.proresourcedata;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.core.annotation.PermissionAnn;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年12月27日 
 * @system: 项目级资料目录 -实测实量目录
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.项目资料目录.施工实测实量目录",number="app.ec.proresourcedata.ecmeasuredata")
@Controller
@RequestMapping("ec/proresourcedata/ecmeasuredata")
public class ProEcMeasureDataEditController extends ProResourceDataBaseEditController {
	
	public String getCode(){
		return "scslml";
	}

}
