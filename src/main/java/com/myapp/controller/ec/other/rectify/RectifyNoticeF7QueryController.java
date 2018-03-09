package com.myapp.controller.ec.other.rectify;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.enums.ChargingBasis;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.util.WebUtil;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseF7QueryController;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.service.ec.other.RectifyNoticeService;

@Controller
@RequestMapping("ec/other/rectifyNoticesF7")
public class RectifyNoticeF7QueryController extends BaseF7QueryController {

	@Resource
	public RectifyNoticeService rectifyNoticeService;
	@Override
	public AbstractBaseService getService() {
		return rectifyNoticeService;
	}
	@Override
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		ColumnModel col =  new ColumnModel("name",DataTypeEnum.STRING);
		col.setAlias_zh("通知名称");
		cols.add(col);

		col =  new ColumnModel("number",DataTypeEnum.STRING);
		col.setAlias_zh("通知编号");
		cols.add(col);

		col = new ColumnModel("checkDate",DataTypeEnum.DATE);
		col.setAlias_zh("检查日期");
		cols.add(col);

		col = new ColumnModel("endDate",DataTypeEnum.DATE);
		col.setAlias_zh("截至日期");
		cols.add(col);

		col = new ColumnModel("orgUnit",DataTypeEnum.STRING);
		col.setAlias_zh("受检单位");
		cols.add(col);

		col = new ColumnModel("requires",DataTypeEnum.STRING);
		col.setAlias_zh("整改要求");
		cols.add(col);

		return cols;
	}
	@Override
	public Order getOrder() {
		return Order.asc("number");
	}
	@Override
	public String getUIWinTitle() {
		return "整改通知单";
	}

	@Override
	public void executeQueryParams(Criteria query) {
		super.executeQueryParams(query);
		query.createAlias("rectifyFeedBack","rfb", JoinType.LEFT_OUTER_JOIN);
		String search = request.getParameter("search");
		String projectId = "-1";
		if(!BaseUtil.isEmpty(search)) {
			Map searchMap = JSONObject.parseObject(search, new HashMap().getClass());
			if(searchMap!=null&&searchMap.get("uiCtx")!=null){
				projectId = ((JSONObject)searchMap.get("uiCtx")).getString("projectId");
			}
		}
		query.add(Restrictions.eq("project.id", WebUtil.UUID_ReplaceID(projectId)));
		query.add(Restrictions.isNull("rfb.id"));
	}
}
