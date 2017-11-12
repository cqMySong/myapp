package com.myapp.controller.ec.purchase.contract;

import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseF7QueryController;
import com.myapp.core.entity.MaterialInfo;
import com.myapp.core.enums.ContractType;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.enums.MaterialType;
import com.myapp.core.exception.db.QueryException;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.model.PageModel;
import com.myapp.core.service.base.BaseService;
import com.myapp.entity.ec.purchase.PurchaseContractDetailInfo;
import com.myapp.entity.ec.purchase.PurchaseContractInfo;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * @path:com.myapp.controller.ec.budget
 * @description:采购合同查询
 * @author :ly
 * @date:2017-10-18
 */
@Controller
@RequestMapping("ec/purchase/purchaseContractF7")
public class PurchaseContractF7QueryController extends BaseF7QueryController {

	@Resource
	public BaseService baseService;
	@Override
	public AbstractBaseService getService() {
		return baseService.newServicInstance(PurchaseContractInfo.class);
	}
	@Override
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		ColumnModel col = new ColumnModel("contractType",DataTypeEnum.ENUM,ContractType.class);
		col.setAlias_zh("合同类型");
		cols.add(col);

		col = new ColumnModel("number",DataTypeEnum.STRING);
		col.setAlias_zh("合同编号");
		cols.add(col);

		col = new ColumnModel("name",DataTypeEnum.STRING);
		col.setAlias_zh("合同名称");
		cols.add(col);

		col = new ColumnModel("supplyCompany",DataTypeEnum.STRING);
		col.setAlias_zh("单位名称");
		cols.add(col);

		col = new ColumnModel("amount",DataTypeEnum.NUMBER);
		col.setAlias_zh("合同金额");
		cols.add(col);

		col = new ColumnModel("balanceSettleAmount",DataTypeEnum.NUMBER);
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
		return "合同信息";
	}

}
