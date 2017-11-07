package com.myapp.controller.ec.purchase.contract;

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
import com.myapp.entity.ec.purchase.PurchaseContractDetailInfo;
import com.myapp.entity.ec.purchase.PurchaseContractInfo;
import com.myapp.entity.ec.purchase.PurchaseStockInfo;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * @path:com.myapp.controller.ec.budget
 * @description:采购合同明细查询
 * @author :ly
 * @date:2017-10-18
 */
@Controller
@RequestMapping("ec/purchase/purchaseContractDetailF7")
public class PurchaseContractDetailF7QueryController extends BaseF7QueryController {

	@Resource
	public BaseService baseService;
	@Override
	public AbstractBaseService getService() {
		return baseService.newServicInstance(PurchaseContractDetailInfo.class);
	}
	@Override
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		ColumnModel col = new ColumnModel("parent",DataTypeEnum.F7,PurchaseContractInfo.class);
		col.setFormat("id,name");
		col.setAlias_zh("id,合同名称");
		cols.add(col);

		col = new ColumnModel("materialType",DataTypeEnum.ENUM,MaterialType.class);
		col.setAlias_zh("类型");
		cols.add(col);

		col = new ColumnModel("material",DataTypeEnum.F7,MaterialInfo.class);
		col.setFormat("id,name");
		col.setAlias_zh("id,物料名称");
		cols.add(col);

		col = new ColumnModel("specification",DataTypeEnum.STRING);
		col.setAlias_zh("规格");
		cols.add(col);

		col = new ColumnModel("measureUnitName",DataTypeEnum.STRING);
		col.setAlias_zh("计量单位");
		cols.add(col);

		col = new ColumnModel("quantity",DataTypeEnum.NUMBER);
		col.setAlias_zh("数量");
		cols.add(col);

		col = new ColumnModel("purchasePrice",DataTypeEnum.NUMBER);
		col.setAlias_zh("单价");
		cols.add(col);

		return cols;
	}
	@Override
	public Order getOrder() {
		return Order.asc("materialName");
	}
	@Override
	public String getUIWinTitle() {
		return "合同明细";
	}

	@Override
	public void afterQuery(PageModel pm) throws QueryException {
		super.afterQuery(pm);
		List<HashMap> datas = pm.getDatas();
		if(datas!=null&&datas.size()>0){
			for(HashMap hashMap : datas){
				hashMap.put("name",hashMap.get("material_name"));
			}
		}
		this.data = pm;
	}
}
