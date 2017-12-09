package com.myapp.controller.ec.stock.inventory;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseF7QueryController;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.enums.MaterialType;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.service.base.BaseService;
import com.myapp.core.util.BaseUtil;
import com.myapp.entity.ec.stock.StockInventoryInfo;
import com.myapp.entity.ec.stock.StockOutDetailInfo;
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
 * @description:盘存信息f7查询
 * @author :ly
 * @date:2017-10-18
 */
@Controller
@RequestMapping("ec/stock/inventoryF7")
public class InventoryF7QueryController extends BaseF7QueryController {

	@Resource
	public BaseService baseService;
	@Override
	public AbstractBaseService getService() {
		return baseService.newServicInstance(StockInventoryInfo.class);
	}
	@Override
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		ColumnModel col = new ColumnModel("number",DataTypeEnum.STRING);
		col.setAlias_zh("盘存单号");
		cols.add(col);

		col = new ColumnModel("name",DataTypeEnum.DATE);
		col.setAlias_zh("盘存名称");
		cols.add(col);

		col = new ColumnModel("startDate",DataTypeEnum.DATE);
		col.setAlias_zh("开始时间");
		cols.add(col);

		col = new ColumnModel("endDate",DataTypeEnum.DATE);
		col.setAlias_zh("结束时间");
		cols.add(col);

		col = new ColumnModel("createUser",DataTypeEnum.F7);
		col.setClaz(UserInfo.class);
		col.setFormat("id,name");
		col.setAlias_zh("id,盘存人");
		cols.add(col);
		return cols;
	}
	@Override
	public Order getOrder() {
		return Order.asc("startDate");
	}
	@Override
	public String getUIWinTitle() {
		return "盘存信息";
	}

	@Override
	public void executeQueryParams(Criteria query) {
		query.createAlias("stockCalculationInfo","sci",JoinType.LEFT_OUTER_JOIN);
		String search = request.getParameter("search");
		String projectId = "-1";
		if(!BaseUtil.isEmpty(search)) {
			Map searchMap = JSONObject.parseObject(search, new HashMap().getClass());
			if(searchMap!=null&&searchMap.get("uiCtx")!=null){
				projectId = ((JSONObject)searchMap.get("uiCtx")).getString("projectId");
			}
		}
		query.add(Restrictions.eq("project.id",projectId));
		query.add(Restrictions.isNull("sci.id"));
	}
}
