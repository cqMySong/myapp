package com.myapp.controller.base.role;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.ProjectionList;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseEditController;
import com.myapp.core.entity.BaseOrgInfo;
import com.myapp.core.entity.RoleInfo;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.BaseMethodEnum;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.enums.UserState;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.model.EditDataModel;
import com.myapp.core.service.RoleService;
import com.myapp.core.service.UserService;
import com.myapp.core.util.DateUtil;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年5月20日 
 * @system:
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.角色管理",number="app.role")
@Controller
@RequestMapping("base/role")
public class RoleEditController extends BaseEditController{
	@Resource
	public RoleService roleService;
	
	public AbstractBaseService getService() {
		return roleService;
	}

	public Object createNewData() {
		return new RoleInfo();
	}

	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("number"));
		ColumnModel orgCol = new ColumnModel("org",DataTypeEnum.F7,BaseOrgInfo.class);
		orgCol.setFormat("id,name");
		cols.add(orgCol);
		cols.add(new ColumnModel("remark"));
		return cols;
	}

	public CoreBaseInfo getEntityInfo() {
		return new RoleInfo();
	}
}
