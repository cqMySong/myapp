package com.myapp.controller.ec.proresourceitem;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.enums.ec.ResourceType;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2018年1月1日 
 * @system: 项目级资料目录 -安全技术要求及验收目录
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.项目资料目录.安全技术要求及验收目录",number="app.ec.proresourcedata.skillresourceitem")
@Controller
@RequestMapping("ec/proresourceitem/skillresourceitems")
public class ProSkillResourceItemListController extends ProResourceItemBaseListController {
	

	public String getEditUrl() {
		return "ec/proresourceitem/skillResourceItemEdit";
	}

	public String getListUrl() {
		return "ec/proresourceitem/skillResourceItemList";
	}

	public ResourceType getResourceType() {
		return ResourceType.AQJSYQYSML;
	}
	
	
	
}
