package com.myapp.controller.base.measureunit;

import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseF7QueryController;
import com.myapp.core.entity.MeasureUnitInfo;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.enums.MaterialType;
import com.myapp.core.enums.UnitClass;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.service.base.BaseService;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author :ly
 * @date:2017-10-18
 */
@Controller
@RequestMapping("base/measureUnitF7")
public class MeasureUnitF7QueryController extends BaseF7QueryController {

	@Resource
	public BaseService baseService;
	@Override
	public AbstractBaseService getService() {
		return baseService.newServicInstance(MeasureUnitInfo.class);
	}
	@Override
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		ColumnModel  col = new ColumnModel("number");
		col.setAlias_zh("编码");
		cols.add(col);
		col = new ColumnModel("name");
		col.setAlias_zh("名称");
		cols.add(col);

		col = new ColumnModel("unitClass",DataTypeEnum.ENUM,UnitClass.class);
		col.setAlias_zh("类型");
		cols.add(col);
		
		col = new ColumnModel("enabled",DataTypeEnum.BOOLEAN);
		col.setAlias_zh("启用");
		cols.add(col);
		return cols;
	}
	@Override
	public Order getOrder() {
		return Order.asc("number");
	}
	@Override
	public String getUIWinTitle() {
		return "计量单位";
	}

}
