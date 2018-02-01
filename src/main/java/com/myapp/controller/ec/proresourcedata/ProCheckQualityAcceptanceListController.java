package com.myapp.controller.ec.proresourcedata;

import com.myapp.core.annotation.PermissionAnn;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年12月11日 
 * @system:项目检验批质量验收
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.项目资料目录.项目检验批质量验收",number="app.ec.proresourcedata.procheckqualityacceptance")
@Controller
@RequestMapping("ec/proresourcedata/procheckqualityacceptances")
public class ProCheckQualityAcceptanceListController extends ProResourceDataBaseListController {
	@Override
	public String getCode(){
		return "jypzlysb";
	}
	@Override
	public String getEditUrl() {
		return "ec/proresourcedata/proCheckQualityAcceptanceEdit";
	}
	@Override
	public String getListUrl() {
		return "ec/proresourcedata/proCheckQualityAcceptanceList";
	}
	
}
