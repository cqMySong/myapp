package com.myapp.controller.ec.basedata.structtype;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseF7QueryController;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.service.OrgService;
import com.myapp.service.ec.basedata.StructTypeService;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年5月30日 
 * @system:
 *
 *-----------MySong---------------
 */
@Controller
@RequestMapping("ec/basedata/structTypeF7")
public class StructTypeF7QueryController extends BaseF7QueryController {
	
	@Resource
	public StructTypeService structTypeService;
	public AbstractBaseService getService() {
		return structTypeService;
	}
	
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		ColumnModel col = new ColumnModel("number");
		col.setAlias_zh("编码");
		cols.add(col);
		col = new ColumnModel("name");
		col.setAlias_zh("名称");
		cols.add(col);
		col = new ColumnModel("enabled",DataTypeEnum.BOOLEAN);
		col.setAlias_zh("启用");
		cols.add(col);
		col = new ColumnModel("remark");
		col.setAlias_zh("备注");
		cols.add(col);
		return cols;
	}
	
	public void executeQueryParams(Criteria query) {
		super.executeQueryParams(query);
		query.add(Restrictions.eq("enabled",Boolean.TRUE));
	}
	
	public Order getOrder() {
		return Order.asc("number");
	}
	
	public String getUIWinTitle() {
		return "结构类型查询";
	}
}
