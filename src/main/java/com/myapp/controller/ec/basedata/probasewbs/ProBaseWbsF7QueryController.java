package com.myapp.controller.ec.basedata.probasewbs;

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
import com.myapp.entity.ec.basedata.ProBaseWbsInfo;
import com.myapp.enums.ec.ProWbsType;
import com.myapp.service.ec.basedata.ProBaseWbsService;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年12月18日 
 * @system: 系统级检验批
 *-----------MySong---------------
 */
@Controller
@RequestMapping("ec/basedata/proBaseWbsF7")
public class ProBaseWbsF7QueryController extends BaseF7QueryController {
	
	@Resource
	public ProBaseWbsService proBaseWbsService;
	public AbstractBaseService getService() {
		return proBaseWbsService;
	}
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		ColumnModel col = new ColumnModel("parent",DataTypeEnum.F7,ProBaseWbsInfo.class);
		col.setFormat("id,displayName");
		col.setAlias_zh("id,全名");
		cols.add(col);
		
		col = new ColumnModel("wbsType",DataTypeEnum.ENUM,ProWbsType.class);
		col.setAlias_zh("结构类型");
		cols.add(col);
		
		col = new ColumnModel("number");
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
	
	public Order getOrder(){
		return Order.asc("longNumber");
	}
	
}
