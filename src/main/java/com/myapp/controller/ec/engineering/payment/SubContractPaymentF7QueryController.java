package com.myapp.controller.ec.engineering.payment;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseF7QueryController;
import com.myapp.core.entity.MeasureUnitInfo;
import com.myapp.core.enums.ChargingBasis;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.enums.SettleType;
import com.myapp.core.enums.SubcontractExpenseType;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.service.base.BaseService;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.util.WebUtil;
import com.myapp.entity.ec.basedata.ECUnitInfo;
import com.myapp.entity.ec.engineering.SiteVisaOutInfo;
import com.myapp.entity.ec.engineering.SubContractPaymentInfo;
import com.myapp.entity.ec.engineering.SubcontractInfo;
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
 * @path:com.myapp.controller.ec.engineering.sitevisaout
 * @description:分包结算F7
 * @author :ly
 * @date:2017-10-18
 */
@Controller
@RequestMapping("ec/engineering/subContractPaymentF7")
public class SubContractPaymentF7QueryController extends BaseF7QueryController {

	@Resource
	public BaseService baseService;
	@Override
	public AbstractBaseService getService() {
		return baseService.newServicInstance(SubContractPaymentInfo.class);
	}
	@Override
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		ColumnModel col =  new ColumnModel("subcontractInfo",DataTypeEnum.F7,SubcontractInfo.class);
		col.setFormat("id,number,name,amount");
		col.setAlias_zh("id,合同编号,合同名称,合同金额");
		cols.add(col);

		col =  new ColumnModel("ecUnitInfo",DataTypeEnum.F7,ECUnitInfo.class);
		col.setFormat("id,name");
		col.setAlias_zh("id,合同单位");
		cols.add(col);

		col = new ColumnModel("number",DataTypeEnum.STRING);
		col.setAlias_zh("结算单号");
		cols.add(col);

		col = new ColumnModel("settleType",DataTypeEnum.ENUM, SettleType.class);
		col.setAlias_zh("结算类型");
		cols.add(col);

		col = new ColumnModel("settleAmount",DataTypeEnum.NUMBER);
		col.setAlias_zh("结算金额");
		cols.add(col);
		return cols;
	}
	@Override
	public Order getOrder() {
		return Order.asc("number");
	}
	@Override
	public String getUIWinTitle() {
		return "劳务结算信息";
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
		query.add(Restrictions.eq("project.id", WebUtil.UUID_ReplaceID(projectId)));
		query.add(Restrictions.eq("subcontractInfo.subcontractExpenseType", SubcontractExpenseType.ARTIFICIAL));
	}
}
