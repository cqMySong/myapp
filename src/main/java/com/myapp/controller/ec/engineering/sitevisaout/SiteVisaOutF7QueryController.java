package com.myapp.controller.ec.engineering.sitevisaout;

import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseF7QueryController;
import com.myapp.core.enums.ChargingBasis;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.service.base.BaseService;
import com.myapp.entity.ec.engineering.SiteVisaOutInfo;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * @path:com.myapp.controller.ec.engineering.sitevisaout
 * @description:现场签证(支出)
 * @author :ly
 * @date:2017-10-18
 */
@Controller
@RequestMapping("ec/engineering/siteVisaOutF7")
public class SiteVisaOutF7QueryController extends BaseF7QueryController {

	@Resource
	public BaseService baseService;
	@Override
	public AbstractBaseService getService() {
		return baseService.newServicInstance(SiteVisaOutInfo.class);
	}
	@Override
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		ColumnModel col =  new ColumnModel("name",DataTypeEnum.STRING);
		col.setAlias_zh("签证名称");
		cols.add(col);

		col =  new ColumnModel("number",DataTypeEnum.STRING);
		col.setAlias_zh("签证编号");
		cols.add(col);

		col = new ColumnModel("visaDate",DataTypeEnum.DATE);
		col.setAlias_zh("签证日期");
		cols.add(col);

		col = new ColumnModel("visaUnit",DataTypeEnum.STRING);
		col.setAlias_zh("签证单位");
		cols.add(col);

		col =  new ColumnModel("chargingBasis",DataTypeEnum.ENUM,ChargingBasis.class);
		col.setAlias_zh("计费依据");
		cols.add(col);

		col = new ColumnModel("amount",DataTypeEnum.NUMBER);
		col.setAlias_zh("签证金额");
		cols.add(col);

		return cols;
	}
	@Override
	public Order getOrder() {
		return Order.asc("number");
	}
	@Override
	public String getUIWinTitle() {
		return "现场签证(支出)";
	}

	@Override
	public void executeQueryParams(Criteria query) {
		super.executeQueryParams(query);
		query.createAlias("siteVisaInInfo","a", JoinType.LEFT_OUTER_JOIN);
		query.add(Restrictions.isNull("a.id"));
	}
}
