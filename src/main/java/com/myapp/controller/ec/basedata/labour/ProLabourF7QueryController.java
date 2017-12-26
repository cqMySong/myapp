package com.myapp.controller.ec.basedata.labour;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseF7QueryController;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.util.WebUtil;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.entity.ec.basedata.WorkTypeInfo;
import com.myapp.service.ec.basedata.ProLabourService;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年12月24日 
 * @system:
 *
 *-----------MySong---------------
 */
@Controller
@RequestMapping("ec/basedata/proLabourF7")
public class ProLabourF7QueryController extends BaseF7QueryController {

	@Resource
	public ProLabourService proLabourService;
	public AbstractBaseService getService() {
		return proLabourService;
	}
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		ColumnModel col = new ColumnModel("project",DataTypeEnum.F7,ProjectInfo.class);
		col.setAlias_zh("id,工程项目");
		cols.add(col);
		col = new ColumnModel("number");
		col.setAlias_zh("工号");
		cols.add(col);
		col = new ColumnModel("name");
		col.setAlias_zh("姓名");
		cols.add(col);
		col = new ColumnModel("workType",DataTypeEnum.F7,WorkTypeInfo.class);
		col.setAlias_zh("id,工种");
		cols.add(col);
		col = new ColumnModel("joinDate",DataTypeEnum.DATE);
		col.setAlias_zh("加入日期");
		cols.add(col);
		col = new ColumnModel("idCard");
		col.setAlias_zh("身份证号");
		cols.add(col);
		col = new ColumnModel("bank");
		col.setAlias_zh("开户银行");
		cols.add(col);
		col = new ColumnModel("bankNo");
		col.setAlias_zh("银行账号");
		cols.add(col);
		return cols;
	}
	
	public void executeQueryParams(Criteria query) {
		super.executeQueryParams(query);
		Map ctxMap = getQueryUiCtx();
		if(ctxMap!=null){
			Object proId =ctxMap.get("projectId");
			if(BaseUtil.isNotEmpty(proId)){
				proId = WebUtil.UUID_ReplaceID(proId.toString());
				query.add(Restrictions.eq("project.id",proId));
			}
			Object selIdsObj = ctxMap.get("selIds");
			if(BaseUtil.isNotEmpty(selIdsObj)){
				String[] selIds = (WebUtil.UUID_ReplaceID(selIdsObj.toString())).split(",");
				query.add(Restrictions.not(Restrictions.in("id", selIds)));
			}
		}
	}
	
	public Order getOrder() {
		return Order.asc("number");
	}
	
	public String getUIWinTitle() {
		return "项目劳务人员";
	}
	

}
