package com.myapp.controller.ec.basedata.safeusepower;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseDataEditController;
import com.myapp.core.model.ColumnModel;
import com.myapp.entity.ec.basedata.SafeUsePowerInfo;
import com.myapp.service.ec.basedata.SafeUsePowerService;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年10月26日 
 * @system:施工现场安全用电
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.基础数据.安全用电",number="app.ec.basedata.safeusepower")
@Controller
@RequestMapping("ec/basedata/safeusepower")
public class SafeUsePowerEditController extends BaseDataEditController{
	@Resource
	public SafeUsePowerService safeUsePowerService;
	
	public AbstractBaseService getService() {
		return safeUsePowerService;
	}
	
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("standard"));
		return cols;
	}

	public Object createNewData() {
		return new SafeUsePowerInfo();
	}

	public CoreBaseInfo getEntityInfo() {
		return new SafeUsePowerInfo();
	}
}
