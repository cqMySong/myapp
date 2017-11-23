package com.myapp.controller.ec.engineering.contract;

import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseF7QueryController;
import com.myapp.core.entity.MaterialInfo;
import com.myapp.core.enums.ContractType;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.enums.ExpenseType;
import com.myapp.core.enums.MaterialType;
import com.myapp.core.exception.db.QueryException;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.model.PageModel;
import com.myapp.core.service.base.BaseService;
import com.myapp.entity.ec.basedata.ECUnitInfo;
import com.myapp.entity.ec.engineering.EngineeringContractInfo;
import com.myapp.entity.ec.stock.StockInfo;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * @path:com.myapp.controller.ec.stock
 * @description:工程合同
 * @author :ly
 * @date:2017-10-18
 */
@Controller
@RequestMapping("ec/engineering/contractF7")
public class EngineeringContractF7QueryController extends BaseF7QueryController {

	@Resource
	public BaseService baseService;
	@Override
	public AbstractBaseService getService() {
		return baseService.newServicInstance(EngineeringContractInfo.class);
	}
	@Override
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		ColumnModel col =  new ColumnModel("contractType",DataTypeEnum.ENUM,ContractType.class);
		col.setAlias_zh("合同类型");
		cols.add(col);

		col = new ColumnModel("expenseType",DataTypeEnum.ENUM,ExpenseType.class);
		col.setAlias_zh("费用类型");
		cols.add(col);

		col = new ColumnModel("number",DataTypeEnum.STRING);
		col.setAlias_zh("合同编号");
		cols.add(col);

		col = new ColumnModel("name",DataTypeEnum.STRING);
		col.setAlias_zh("合同名称");
		cols.add(col);

		col =  new ColumnModel("ecUnitInfo",DataTypeEnum.F7,ECUnitInfo.class);
		col.setFormat("id,name");
		col.setAlias_zh("id,合同单位");
		cols.add(col);

		col = new ColumnModel("amount",DataTypeEnum.NUMBER);
		col.setAlias_zh("合同金额");
		cols.add(col);

		col = new ColumnModel("balanceAmount",DataTypeEnum.NUMBER);
		col.setAlias_zh("未结算金额");
		cols.add(col);
		return cols;
	}
	@Override
	public Order getOrder() {
		return Order.asc("number");
	}
	@Override
	public String getUIWinTitle() {
		return "工程合同信息";
	}
}
