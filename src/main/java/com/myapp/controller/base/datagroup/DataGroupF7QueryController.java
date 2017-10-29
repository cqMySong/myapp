package com.myapp.controller.base.datagroup;

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
import com.myapp.core.service.basedata.DataGroupService;
import com.myapp.core.util.BaseUtil;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年10月29日 
 * @system: 
 *-----------MySong---------------
 */
@Controller
@RequestMapping("base/datagroupF7")
public class DataGroupF7QueryController extends BaseF7QueryController {
	
	@Resource
	public DataGroupService dataGroupService;
	public AbstractBaseService getService() {
		return dataGroupService;
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
	
	public void executeQueryParams(Criteria query) {
		super.executeQueryParams(query);
		String code = request.getParameter("code");
		if(!BaseUtil.isEmpty(code)){
			query.add(Restrictions.eq("code",code));
		}
	}

}
