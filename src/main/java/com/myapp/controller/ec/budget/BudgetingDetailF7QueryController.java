package com.myapp.controller.ec.budget;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseF7QueryController;
import com.myapp.core.entity.MaterialInfo;
import com.myapp.core.entity.MeasureUnitInfo;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.enums.MaterialType;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.service.base.BaseService;
import com.myapp.core.util.BaseUtil;
import com.myapp.entity.ec.budget.BudgetingDetailInfo;
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
 * @description:预算明细选择
 * @author :ly
 * @date:2017-10-18
 */
@Controller
@RequestMapping("ec/budget/budgetingDetailF7")
public class BudgetingDetailF7QueryController extends BaseF7QueryController {

	@Resource
	public BaseService baseService;
	@Override
	public AbstractBaseService getService() {
		return baseService.newServicInstance(BudgetingDetailInfo.class);
	}
	@Override
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		ColumnModel col = new ColumnModel("pr.number",DataTypeEnum.STRING);
		col.setAlias_zh("预算编号");
		cols.add(col);

		col = new ColumnModel("materialType",DataTypeEnum.ENUM,MaterialType.class);
		col.setAlias_zh("类型");
		cols.add(col);

		col = new ColumnModel("material",DataTypeEnum.F7,MaterialInfo.class);
		col.setFormat("id,number,name");
		col.setAlias_zh("id,物料编码,物料名称");
		cols.add(col);

		col = new ColumnModel("specification");
		col.setAlias_zh("规格");
		cols.add(col);

		col = new ColumnModel("measureUnitInfo",DataTypeEnum.F7,MeasureUnitInfo.class);
		col.setFormat("id,name");
		col.setAlias_zh("id,单位");
		cols.add(col);

		col = new ColumnModel("quantity",DataTypeEnum.NUMBER);
		col.setAlias_zh("数量");
		cols.add(col);

		col = new ColumnModel("budgetaryPrice",DataTypeEnum.NUMBER);
		col.setAlias_zh("预算价");
		cols.add(col);
		return cols;
	}
	@Override
	public Order getOrder() {
		return Order.asc("material");
	}
	@Override
	public String getUIWinTitle() {
		return "预算详细信息";
	}

	@Override
	public void executeQueryParams(Criteria query) {
		super.executeQueryParams(query);
		query.createAlias("parent","pr", JoinType.INNER_JOIN);
		query.createAlias("parent.project","pro", JoinType.INNER_JOIN);
		query.createAlias("enquiryPriceDetailInfos","epdi",JoinType.LEFT_OUTER_JOIN);
		String search = request.getParameter("search");
		String projectId = "-1";
		if(!BaseUtil.isEmpty(search)) {
			Map searchMap = JSONObject.parseObject(search, new HashMap().getClass());
			if(searchMap!=null&&searchMap.get("uiCtx")!=null){
				projectId = ((JSONObject)searchMap.get("uiCtx")).getString("projectId");
			}
		}
		query.add(Restrictions.eq("pro.id",projectId));
		query.add(Restrictions.isNull("epdi.id"));
	}
}
