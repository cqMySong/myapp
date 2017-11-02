package com.myapp.controller.ec.purchase.apply;

import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseF7QueryController;
import com.myapp.core.entity.MaterialInfo;
import com.myapp.core.entity.MeasureUnitInfo;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.enums.MaterialType;
import com.myapp.core.exception.db.QueryException;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.model.PageModel;
import com.myapp.core.service.base.BaseService;
import com.myapp.entity.ec.budget.BudgetingDetailInfo;
import com.myapp.entity.ec.purchase.ApplyMaterialDetailInfo;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

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
		ColumnModel col = new ColumnModel("budgetingDetailInfo",DataTypeEnum.F7,BudgetingDetailInfo.class);
		col.setFormat("materialType,material,specification,measureUnitInfo");
		col.setAlias_zh("材料类型,材料名称,规格,计量单位,物料");
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
	public void afterQuery(PageModel pm) throws QueryException {
		super.afterQuery(pm);
		List<HashMap> datas = pm.getDatas();
		if(datas!=null&&datas.size()>0){
			for(HashMap hashMap : datas){
				hashMap.put("budgetingDetailInfo_materialType_id",
						((MaterialType)hashMap.get("budgetingDetailInfo_materialType")).getValue());
				hashMap.put("budgetingDetailInfo_materialType",
						((MaterialType)hashMap.get("budgetingDetailInfo_materialType")).getName());
				hashMap.put("budgetingDetailInfo_measureUnitInfo_id",
						((MeasureUnitInfo)hashMap.get("budgetingDetailInfo_measureUnitInfo")).getId());
				hashMap.put("budgetingDetailInfo_measureUnitInfo",
						((MeasureUnitInfo)hashMap.get("budgetingDetailInfo_measureUnitInfo")).getName());
				hashMap.put("applyMaterialDetailInfo_id",hashMap.get("id"));
				hashMap.put("id",((MaterialInfo)hashMap.get("budgetingDetailInfo_material")).getId());
				hashMap.put("budgetingDetailInfo_material",
						((MaterialInfo)hashMap.get("budgetingDetailInfo_material")).getName());
				hashMap.put("name",hashMap.get("budgetingDetailInfo_material"));

			}
		}
		this.data = pm;
	}
}
