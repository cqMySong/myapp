package com.myapp.controller.base.org;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.Order;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseF7QueryController;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.service.OrgService;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年5月30日 
 * @system:
 *
 *-----------MySong---------------
 */
@Controller
@RequestMapping("base/orgf7")
public class OrgF7QueryController extends BaseF7QueryController {
	
	@Resource
	public OrgService orgService;
	public AbstractBaseService getService() {
		return orgService;
	}
	
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		ColumnModel col = new ColumnModel("name");
		col.setAlias_zh("组织名称");
		cols.add(col);
		col = new ColumnModel("number");
		col.setAlias_zh("组织编码");
		cols.add(col);
		ColumnModel orgCol = new ColumnModel("parent",DataTypeEnum.F7,"name,number");
		orgCol.setAlias_zh("上级名称,上级编码");
		cols.add(orgCol);
		return cols;
	}
	public Order getOrder() {
		return Order.asc("longNumber");
	}

}
