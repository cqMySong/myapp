package com.myapp.controller.ec.stock;

import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseF7QueryController;
import com.myapp.core.entity.MaterialInfo;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.enums.MaterialType;
import com.myapp.core.exception.db.QueryException;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.model.PageModel;
import com.myapp.core.service.base.BaseService;
import com.myapp.entity.ec.purchase.PurchaseContractDetailInfo;
import com.myapp.entity.ec.purchase.PurchaseContractInfo;
import com.myapp.entity.ec.purchase.PurchaseStockDetailInfo;
import com.myapp.entity.ec.purchase.PurchaseStockInfo;
import com.myapp.entity.ec.stock.StockInfo;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * @path:com.myapp.controller.ec.stock
 * @description:库存信息F7
 * @author :ly
 * @date:2017-10-18
 */
@Controller
@RequestMapping("ec/stock/stockF7")
public class StockF7QueryController extends BaseF7QueryController {

	@Resource
	public BaseService baseService;
	@Override
	public AbstractBaseService getService() {
		return baseService.newServicInstance(StockInfo.class);
	}
	@Override
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		ColumnModel col =  new ColumnModel("inStockNumber",DataTypeEnum.STRING);
		col.setAlias_zh("入库单号");
		cols.add(col);

		col =  new ColumnModel("materialInfo",DataTypeEnum.F7,MaterialInfo.class);
		col.setFormat("id,materialType,name");
		col.setAlias_zh("id,物料类型,物料名称");
		cols.add(col);

		col = new ColumnModel("specification",DataTypeEnum.STRING);
		col.setAlias_zh("规格");
		cols.add(col);

		col = new ColumnModel("measureUnit",DataTypeEnum.STRING);
		col.setAlias_zh("计量单位");
		cols.add(col);

		col = new ColumnModel("count",DataTypeEnum.NUMBER);
		col.setAlias_zh("库存数量");
		cols.add(col);

		return cols;
	}
	@Override
	public Order getOrder() {
		return Order.asc("materialInfo");
	}
	@Override
	public String getUIWinTitle() {
		return "库存信息";
	}

	@Override
	public void afterQuery(PageModel pm) throws QueryException {
		super.afterQuery(pm);
		List<HashMap> datas = pm.getDatas();
		if(datas!=null&&datas.size()>0){
			for(HashMap hashMap : datas){
				hashMap.put("materialInfo_materialType_id",
						((MaterialType)hashMap.get("materialInfo_materialType")).getValue());
				hashMap.put("materialInfo_materialType",
						((MaterialType)hashMap.get("materialInfo_materialType")).getName());
				hashMap.put("name",hashMap.get("materialInfo_name"));
				hashMap.put("stock_id",hashMap.get("id"));
				hashMap.put("id",hashMap.get("materialInfo_id"));
			}
		}
		this.data = pm;
	}
}
