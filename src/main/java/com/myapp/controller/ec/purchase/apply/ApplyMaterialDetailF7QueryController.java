package com.myapp.controller.ec.purchase.apply;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseF7QueryController;
import com.myapp.core.entity.MaterialInfo;
import com.myapp.core.entity.MeasureUnitInfo;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.enums.MaterialType;
import com.myapp.core.exception.db.QueryException;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.model.PageModel;
import com.myapp.core.service.base.BaseService;
import com.myapp.core.util.BaseUtil;
import com.myapp.entity.ec.budget.BudgetingDetailInfo;
import com.myapp.entity.ec.purchase.ApplyMaterialDetailInfo;
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
 * @description:申购单明细查询
 * @author :ly
 * @date:2017-10-18
 */
@Controller
@RequestMapping("ec/purchase/applyMaterialDetailF7")
public class ApplyMaterialDetailF7QueryController extends BaseF7QueryController {

	@Resource
	public BaseService baseService;
	@Override
	public AbstractBaseService getService() {
		return baseService.newServicInstance(ApplyMaterialDetailInfo.class);
	}
	@Override
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		ColumnModel col = new ColumnModel("pr.number",DataTypeEnum.STRING);
		col.setAlias_zh("申购单号");
		cols.add(col);

		col = new ColumnModel("createUser.name",DataTypeEnum.STRING);
		col.setAlias_zh("申购人");
		cols.add(col);

		col = new ColumnModel("bdi.materialType",DataTypeEnum.ENUM,MaterialType.class);
		col.setFormat("materialType");
		col.setAlias_zh("材料类型");
		cols.add(col);

		col = new ColumnModel("pr.id",DataTypeEnum.STRING);
		col.setAlias_zh("申购id");
		col.setQueryDisplay(false);
		col.setQueryFilter(false);
		cols.add(col);

		col = new ColumnModel("ma.id",DataTypeEnum.STRING);
		col.setAlias_zh("材料id");
		col.setQueryDisplay(false);
		col.setQueryFilter(false);
		cols.add(col);

		col = new ColumnModel("bdi.materialName",DataTypeEnum.STRING);
		col.setAlias_zh("材料名称");
		cols.add(col);

		col = new ColumnModel("bdi.specification",DataTypeEnum.STRING);
		col.setAlias_zh("规格");
		cols.add(col);

		col = new ColumnModel("mui.name",DataTypeEnum.STRING);
		col.setAlias_zh("计量单位");
		cols.add(col);

		col = new ColumnModel("purchaseNum",DataTypeEnum.NUMBER);
		col.setAlias_zh("申购数量");
		cols.add(col);

		col = new ColumnModel("arrivalTime",DataTypeEnum.DATE);
		col.setAlias_zh("计划到场时间");
		cols.add(col);
		return cols;
	}
	@Override
	public Order getOrder() {
		return Order.asc("budgetingDetailInfo");
	}
	@Override
	public String getUIWinTitle() {
		return "材料申购信息";
	}

	@Override
	public void executeQueryParams(Criteria query) {
		super.executeQueryParams(query);
		query.createAlias("budgetingDetailInfo","bdi", JoinType.INNER_JOIN);
		query.createAlias("budgetingDetailInfo.measureUnitInfo","mui", JoinType.INNER_JOIN);
		query.createAlias("budgetingDetailInfo.material","ma", JoinType.INNER_JOIN);
		query.createAlias("parent","pr",JoinType.INNER_JOIN);
		query.createAlias("parent.createUser","createUser",JoinType.INNER_JOIN);
		query.createAlias("parent.project","pro",JoinType.INNER_JOIN);

		String search = request.getParameter("search");
		String projectId = "-1";
		if(!BaseUtil.isEmpty(search)) {
			Map searchMap = JSONObject.parseObject(search, new HashMap().getClass());
			if(searchMap!=null&&searchMap.get("uiCtx")!=null){
				projectId = ((JSONObject)searchMap.get("uiCtx")).getString("projectId");
				if(!((JSONObject)searchMap.get("uiCtx")).getBooleanValue("purchaseContract")){
					query.createAlias("purchaseContractDetailInfoSet","pcdi",JoinType.LEFT_OUTER_JOIN);
					query.add(Restrictions.isNull("pcdi.id"));
				}else{
					query.createAlias("purchaseStockDetailInfoSet","psdi",JoinType.LEFT_OUTER_JOIN);
					query.add(Restrictions.isNull("psdi.id"));
					//query.add(Restrictions.or(Restrictions.isNull("psdi.id"),
					//		Restrictions.gtProperty("purchaseNum","psdi.count")));
				}
			}
		}
		query.add(Restrictions.eq("pro.id",projectId));

	}
}
