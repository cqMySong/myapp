package com.myapp.controller.ec.basedata.schemetype;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseListController;
import com.myapp.service.ec.basedata.SchemeTypeService;

@PermissionAnn(name="系统管理.现场管理.基础数据.建筑方案类型",number="app.ec.basedata.schemetype")
@Controller
@RequestMapping("ec/basedata/schemeTypeList")
public class SchemeTypeListController extends BaseListController {
	
	@Resource
	public SchemeTypeService schemeTypeService;

	public AbstractBaseService getService() {
		return schemeTypeService;
	}

	public void setSchemeTypeService(SchemeTypeService schemeTypeService) {
		this.schemeTypeService = schemeTypeService;
	}


	public String getEditUrl() {
		return "ec/basedata/schemetype/schemeTypeEdit";
	}

	public String getListUrl() {
		return "ec/basedata/schemetype/schemeTypeList";
	}

}
