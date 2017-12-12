package com.myapp.controller.ec.basedata.worktype;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseDataListController;
import com.myapp.service.ec.basedata.StructTypeService;
import com.myapp.service.ec.basedata.WorkTypeService;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年12月12日 
 * @system: 施工工种
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.基础数据.施工工种",number="app.ec.basedata.worktype")
@Controller
@RequestMapping("ec/basedata/worktypes")
public class WorkTypeListController extends BaseDataListController {
	
	@Resource
	public WorkTypeService workTypeService;
	public AbstractBaseService getService() {
		return workTypeService;
	}

	public String getEditUrl() {
		return "ec/basedata/worktype/workTypeEdit";
	}

	public String getListUrl() {
		return "ec/basedata/worktype/workTypeList";
	}

}
