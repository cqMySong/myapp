package com.myapp.controller.base.role;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseListController;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.service.RoleService;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年6月7日 
 * @system:
 *
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.角色管理",number="app.role")
@Controller
@RequestMapping("base/roles")
public class RoleListController extends BaseListController {
	
	@Resource
	public RoleService roleService;
	
	public AbstractBaseService getService() {
		return roleService;
	}
	
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("number"));
		cols.add(new ColumnModel("enabled"));
		cols.add(new ColumnModel("remark"));
		return cols;
	}

	public String getEditUrl() {
		return "role/roleEdit";
	}

	public String getListUrl() {
		return "role/roleList";
	}
}
