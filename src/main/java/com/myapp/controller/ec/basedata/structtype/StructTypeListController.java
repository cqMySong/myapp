package com.myapp.controller.ec.basedata.structtype;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseDataListController;
import com.myapp.service.ec.basedata.StructTypeService;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年6月7日 
 * @system:
 *
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.基础数据.结构类型",number="app.ec.basedata.structtype")
@Controller
@RequestMapping("ec/basedata/structTypes")
public class StructTypeListController extends BaseDataListController {
	
	@Resource
	public StructTypeService structTypeService;
	
	public AbstractBaseService getService() {
		return structTypeService;
	}

	public String getEditUrl() {
		return "ec/basedata/structtype/structTypeEdit";
	}

	public String getListUrl() {
		return "ec/basedata/structtype/structTypeList";
	}

}
