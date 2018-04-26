package com.myapp.controller.ec.basedata.ecunit;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseDataEditController;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.entity.ec.basedata.ECUnitInfo;
import com.myapp.enums.UnitType;
import com.myapp.service.ec.basedata.ECUnitService;

@PermissionAnn(name="系统管理.现场管理.基础数据.参建单位",number="app.ec.basedata.ecunit")
@Controller
@RequestMapping("ec/basedata/ecunitEdit")
public class ECUnitEditController extends BaseDataEditController {
	
	@Resource
	public ECUnitService ecUnitService;
	
	public AbstractBaseService getService() {
		return ecUnitService;
	}

	public Object createNewData() {
		ECUnitInfo unitInfo = new ECUnitInfo();
		return unitInfo;
	}

	public CoreBaseInfo getEntityInfo() {
		return new ECUnitInfo();
	}
	
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("unitType",DataTypeEnum.ENUM,UnitType.class));
		return cols;
	}


}
