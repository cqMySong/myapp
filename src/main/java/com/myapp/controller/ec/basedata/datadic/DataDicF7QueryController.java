package com.myapp.controller.ec.basedata.datadic;

import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseF7QueryController;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.service.base.BaseService;
import com.myapp.entity.ec.basedata.DataDictionaryInfo;
import com.myapp.entity.ec.basedata.ECUnitInfo;
import com.myapp.enums.DataDicType;
import com.myapp.enums.UnitType;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("ec/basedata/dataDicF7")
public class DataDicF7QueryController extends BaseF7QueryController {

	@Resource
	public BaseService baseService;
	public AbstractBaseService getService() {
		return baseService.newServicInstance(DataDictionaryInfo.class);
	}
	
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		ColumnModel col = new ColumnModel("number");
		col.setAlias_zh("编码");
		cols.add(col);
		col = new ColumnModel("name");
		col.setAlias_zh("名称");
		cols.add(col);
		
		col = new ColumnModel("dataDicType",DataTypeEnum.ENUM,DataDicType.class);
		col.setAlias_zh("材料类型");
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
		return "物料信息";
	}

}
