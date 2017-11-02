package com.myapp.controller.base.material;

import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseF7QueryController;
import com.myapp.core.entity.MeasureUnitInfo;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.service.base.BaseService;
import com.myapp.core.entity.MaterialInfo;
import com.myapp.core.enums.MaterialType;
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
@RequestMapping("base/materialF7")
public class MaterialF7QueryController extends BaseF7QueryController {

	@Resource
	public BaseService baseService;
	@Override
	public AbstractBaseService getService() {
		return baseService.newServicInstance(MaterialInfo.class);
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

		col = new ColumnModel("specification");
		col.setAlias_zh("规格");
		cols.add(col);

		col = new ColumnModel("unit",DataTypeEnum.F7,MeasureUnitInfo.class);
		col.setFormat("id,name");
		col.setAlias_zh("id,单位");
		cols.add(col);

		col = new ColumnModel("materialType",DataTypeEnum.ENUM,MaterialType.class);
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
		return "物料信息";
	}

}
