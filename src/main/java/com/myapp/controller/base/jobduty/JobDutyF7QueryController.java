package com.myapp.controller.base.jobduty;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseF7QueryController;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.enums.PermissionTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.service.JobDutyService;
import com.myapp.core.service.PermissionService;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.util.EnumUtil;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年9月10日 
 * @system:
 *
 *-----------MySong---------------
 */
@Controller
@RequestMapping("base/jobDutyf7")
public class JobDutyF7QueryController extends BaseF7QueryController {

	@Resource
	public JobDutyService jobDutyService;
	public AbstractBaseService getService() {
		return jobDutyService;
	}
	
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		ColumnModel col = new ColumnModel("number");
		col.setAlias_zh("编码");
		cols.add(col);
		col = new ColumnModel("name");
		col.setAlias_zh("名称");
		cols.add(col);
		col = new ColumnModel("remark");
		col.setAlias_zh("描述");
		cols.add(col);
		return cols;
	}
	
	public void executeQueryParams(Criteria query) {
		super.executeQueryParams(query);
		
	}
	
	public Order getOrder() {
		return Order.asc("number");
	}
	public String getUIWinTitle() {
		return "工作职责查询";
	}

}
