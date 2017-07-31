package com.myapp.controller.ec.basedata.schemetype;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseDataEditController;
import com.myapp.entity.ec.basedata.SchemeTypeInfo;
import com.myapp.service.ec.basedata.SchemeTypeService;

@PermissionAnn(name="系统管理.现场管理.基础数据.建筑方案类型",number="app.ec.basedata.schemetype")
@Controller
@RequestMapping("ec/basedata/schemeTypeEdit")
public class SchemeTypeEditController extends BaseDataEditController {
	
	@Resource
	public SchemeTypeService schemeTypeService;

	public AbstractBaseService getService() {
		return schemeTypeService;
	}

	public void setSchemeTypeService(SchemeTypeService schemeTypeService) {
		this.schemeTypeService = schemeTypeService;
	}

	public Object createNewData() {
		SchemeTypeInfo info = new SchemeTypeInfo();
		return info;
	}

	public CoreBaseInfo getEntityInfo() {
		return new SchemeTypeInfo();
	}

}
