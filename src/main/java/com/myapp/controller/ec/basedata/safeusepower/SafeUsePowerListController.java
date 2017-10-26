package com.myapp.controller.ec.basedata.safeusepower;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseDataListController;
import com.myapp.core.model.ColumnModel;
import com.myapp.service.ec.basedata.SafeUsePowerService;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年6月7日 
 * @system:
 *
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.基础数据.安全用电",number="app.ec.basedata.safeusepower")
@Controller
@RequestMapping("ec/basedata/safeusepowers")
public class SafeUsePowerListController extends BaseDataListController {
	
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

	public String getEditUrl() {
		return "ec/basedata/safeusepower/safeUsePowerEdit";
	}

	public String getListUrl() {
		return "ec/basedata/safeusepower/safeUsePowerList";
	}

}
