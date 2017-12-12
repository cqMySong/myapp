package com.myapp.controller.ec.basedata.machine;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseEditController;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.entity.ec.basedata.ProMachinesInfo;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.service.ec.basedata.ProMachinesService;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年12月12日 
 * @system:
 * 现场施工机械一览表
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.施工机械.现场施工机械一览表",number="app.ec.machine.promachines")
@Controller
@RequestMapping("ec/machine/promachines")
public class ProMachinesEditController extends BaseEditController{
	@Resource
	public ProMachinesService proMachinesService;
	public AbstractBaseService getService() {
		return proMachinesService;
	}

	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("number"));
		cols.add(new ColumnModel("operator"));
		cols.add(new ColumnModel("maintenancer"));
		cols.add(new ColumnModel("checkContent"));
		cols.add(new ColumnModel("project",DataTypeEnum.F7,ProjectInfo.class));
		return cols;
	}
	
	public Object createNewData() {
		return new ProMachinesInfo();
	}

	public CoreBaseInfo getEntityInfo() {
		return new ProMachinesInfo();
	}
}
