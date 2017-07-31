package com.myapp.controller.ec.basedata.scheme;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseBillEditController;
import com.myapp.entity.ec.basedata.ConstructionSchemeInfo;
import com.myapp.service.ec.basedata.ConstructionSchemeService;

@PermissionAnn(name="系统管理.现场管理.基础数据.施工方案",number="app.ec.basedata.scheme")
@Controller
@RequestMapping("ec/basedata/schemeedit")
public class ConstructionSchemeEditController extends BaseBillEditController {
	
	@Resource
	public ConstructionSchemeService constructionSchemeService;
	
	public AbstractBaseService getService() {
		return constructionSchemeService;
	}

	public Object createNewData() {
		ConstructionSchemeInfo info = new ConstructionSchemeInfo();
		return info;
	}

	public CoreBaseInfo getEntityInfo() {
		return new ConstructionSchemeInfo();
	}

}
