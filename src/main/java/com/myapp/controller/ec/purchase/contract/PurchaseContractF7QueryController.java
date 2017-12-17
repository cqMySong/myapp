package com.myapp.controller.ec.purchase.contract;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseF7QueryController;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.enums.ExpenseType;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.service.base.BaseService;
import com.myapp.core.util.BaseUtil;
import com.myapp.entity.ec.purchase.PurchaseContractInfo;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		ColumnModel col = new ColumnModel("expenseType", DataTypeEnum.ENUM,ExpenseType.class);
		col.setAlias_zh("费用类型");
		cols.add(col);

		col = new ColumnModel("number", DataTypeEnum.STRING);
		col.setAlias_zh("合同编号");
		cols.add(col);

		col = new ColumnModel("name", DataTypeEnum.STRING);
		col.setAlias_zh("合同名称");
		cols.add(col);

		col = new ColumnModel("supplyCompany", DataTypeEnum.STRING);
		col.setAlias_zh("单位名称");
		cols.add(col);

		col = new ColumnModel("amount", DataTypeEnum.NUMBER);
		col.setAlias_zh("合同金额");
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

	@Override
	public void executeQueryParams(Criteria query) {
		super.executeQueryParams(query);
		String search = request.getParameter("search");
		String projectId = "-1";
		if(!BaseUtil.isEmpty(search)) {
			Map searchMap = JSONObject.parseObject(search, new HashMap().getClass());
			if(searchMap!=null&&searchMap.get("uiCtx")!=null){
				projectId = ((JSONObject)searchMap.get("uiCtx")).getString("projectId");
			}
		}
		query.add(Restrictions.eq("project.id",projectId));
	}
}
