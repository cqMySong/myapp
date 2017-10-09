package com.myapp.controller.base.position;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseF7QueryController;
import com.myapp.core.entity.BaseOrgInfo;
import com.myapp.core.entity.PositionInfo;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.service.PositionService;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年9月10日 
 * @system:
 *
 *-----------MySong---------------
 */
@Controller
@RequestMapping("base/positionf7")
public class PositionF7QueryController extends BaseF7QueryController {

	@Resource
	public PositionService positionService;
	
	public AbstractBaseService getService() {
		return positionService;
	}
	
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		ColumnModel col = new ColumnModel("org",DataTypeEnum.F7,BaseOrgInfo.class);
		col.setFormat("id,name");
		col.setAlias_zh("id,所属组织");
		cols.add(col);
		
		col = new ColumnModel("number");
		col.setAlias_zh("编码");
		cols.add(col);
		col = new ColumnModel("name");
		col.setAlias_zh("名称");
		cols.add(col);
		col = new ColumnModel("respible",DataTypeEnum.BOOLEAN);
		col.setAlias_zh("负责人岗");
		cols.add(col);
		
		col = new ColumnModel("parent",DataTypeEnum.F7,PositionInfo.class);
		col.setFormat("id,name");
		col.setAlias_zh("id,上级岗位");
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
		return "工作岗位查询";
	}

}
