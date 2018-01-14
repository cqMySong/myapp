package com.myapp.controller.ec.basedata.ecunit;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseDataListController;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.util.EnumUtil;
import com.myapp.enums.UnitType;
import com.myapp.service.ec.basedata.ECUnitService;

@PermissionAnn(name="系统管理.现场管理.基础数据.单位",number="app.ec.basedata.ecunit")
@Controller
@RequestMapping("ec/basedata/ecunitList")
public class ECUnitListController extends BaseDataListController {
	
	@Resource
	public ECUnitService ecUnitService;
	
	public AbstractBaseService getService() {
		return ecUnitService;
	}

	public String getEditUrl() {
		return "ec/basedata/ecunit/ecUnitEdit";
	}

	public String getListUrl() {
		return "ec/basedata/ecunit/ecUnitList";
	}
	
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("unitType",DataTypeEnum.ENUM,UnitType.class));
		return cols;
	}
	public String getHeadTitle() {
		return "计量单位";
	}
	
	public List<ExcelExportEntity> getExportHeader() {
		List<ExcelExportEntity> entity = super.getExportHeader();
		ExcelExportEntity unitType = new ExcelExportEntity("计量单位", "unitType");
		entity.add(2,unitType);
		return entity;
	}


}
