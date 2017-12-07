package com.myapp.controller.ec.stock.stockout;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseF7QueryController;
import com.myapp.core.entity.MaterialInfo;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.enums.MaterialType;
import com.myapp.core.exception.db.QueryException;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.model.PageModel;
import com.myapp.core.service.base.BaseService;
import com.myapp.core.util.BaseUtil;
import com.myapp.entity.ec.purchase.PurchaseContractDetailInfo;
import com.myapp.entity.ec.purchase.PurchaseContractInfo;
import com.myapp.entity.ec.stock.StockOutDetailInfo;
import com.myapp.entity.ec.stock.StockOutInfo;
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
 * @path:com.myapp.controller.ec.stock.stockout
 * @description:出库明细F7查询
 * @author :ly
 * @date:2017-10-18
 */
@Controller
@RequestMapping("ec/stock/stockOutDetailF7")
public class StockOutDetailF7QueryController extends BaseF7QueryController {

	@Resource
	public BaseService baseService;
	@Override
	public AbstractBaseService getService() {
		return baseService.newServicInstance(StockOutDetailInfo.class);
	}
	@Override
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		ColumnModel col = new ColumnModel("pr.number",DataTypeEnum.STRING);
		col.setAlias_zh("出库单号");
		cols.add(col);

		col = new ColumnModel("pr.id",DataTypeEnum.STRING);
		col.setQueryFilter(false);
		col.setQueryDisplay(false);
		col.setAlias_zh("出库id");
		cols.add(col);

		col = new ColumnModel("mater.materialType",DataTypeEnum.ENUM,MaterialType.class);
		col.setAlias_zh("物料类型");
		cols.add(col);

		col = new ColumnModel("mater.name",DataTypeEnum.STRING);
		col.setAlias_zh("物料名称");
		cols.add(col);

		col = new ColumnModel("mater.id",DataTypeEnum.STRING);
		col.setQueryFilter(false);
		col.setQueryDisplay(false);
		col.setAlias_zh("物料id");
		cols.add(col);

		col = new ColumnModel("specification",DataTypeEnum.STRING);
		col.setAlias_zh("规格");
		cols.add(col);

		col = new ColumnModel("measureUnit",DataTypeEnum.STRING);
		col.setAlias_zh("计量单位");
		cols.add(col);

		col = new ColumnModel("count",DataTypeEnum.NUMBER);
		col.setAlias_zh("领取数量");
		cols.add(col);
		return cols;
	}
	@Override
	public Order getOrder() {
		return Order.asc("material");
	}
	@Override
	public String getUIWinTitle() {
		return "出库详细";
	}

	@Override
	public void executeQueryParams(Criteria query) {
		query.createAlias("parent","pr", JoinType.INNER_JOIN);
		query.createAlias("material","mater", JoinType.INNER_JOIN);
		query.createAlias("parent.createUser","createUser",JoinType.INNER_JOIN);
		query.createAlias("parent.project","pro",JoinType.INNER_JOIN);

		String search = request.getParameter("search");
		String projectId = "-1";
		if(!BaseUtil.isEmpty(search)) {
			Map searchMap = JSONObject.parseObject(search, new HashMap().getClass());
			if(searchMap!=null&&searchMap.get("uiCtx")!=null){
				projectId = ((JSONObject)searchMap.get("uiCtx")).getString("projectId");
			}
		}
		query.add(Restrictions.eq("pro.id",projectId));
	}
}
