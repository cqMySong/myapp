package com.myapp.controller.ec.basedata.ecunit;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.Order;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseF7QueryController;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.service.base.BaseService;
import com.myapp.entity.ec.basedata.ECUnitInfo;
import com.myapp.enums.UnitType;

@Controller
@RequestMapping("ec/basedata/ecunitF7")
public class ECUnitF7QueryController extends BaseF7QueryController {

	@Resource
	public BaseService baseService;
	public AbstractBaseService getService() {
		return baseService.newServicInstance(ECUnitInfo.class);
	}
	
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		ColumnModel col = new ColumnModel("number");
		col.setAlias_zh("编码");
		cols.add(col);
		col = new ColumnModel("name");
		col.setAlias_zh("名称");
		cols.add(col);
		
		col = new ColumnModel("unitType",DataTypeEnum.ENUM,UnitType.class);
		col.setAlias_zh("单位类型");
		cols.add(col);
		
		col = new ColumnModel("enabled",DataTypeEnum.BOOLEAN);
		col.setAlias_zh("启用");
		cols.add(col);
		return cols;
	}
	
	public Order getOrder() {
		return Order.asc("number");
	}
	
	public String getUIWinTitle() {
		return "参建单位";
	}

}
