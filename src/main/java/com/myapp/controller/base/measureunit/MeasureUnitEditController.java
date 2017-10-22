package com.myapp.controller.base.measureunit;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseDataEditController;
import com.myapp.core.entity.MeasureUnitInfo;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.enums.UnitClass;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.service.MeasureUnitService;

@PermissionAnn(name="系统管理.基础资料.计量单位",number="app.basedata.measureunit")
@Controller
@RequestMapping("base/measureunit")
public class MeasureUnitEditController extends BaseDataEditController {
	
	@Resource
	public MeasureUnitService measureUnitService;

	public AbstractBaseService getService() {
		return measureUnitService;
	}
	
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("unitClass",DataTypeEnum.ENUM,UnitClass.class));
		return cols;
	}

	public Object createNewData() {
		MeasureUnitInfo info = new MeasureUnitInfo();
		info.setEnabled(true);
		return info;
	}

	public CoreBaseInfo getEntityInfo() {
		return new MeasureUnitInfo();
	}

}
