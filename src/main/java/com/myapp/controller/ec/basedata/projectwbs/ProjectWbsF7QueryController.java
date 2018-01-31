package com.myapp.controller.ec.basedata.projectwbs;

import java.util.ArrayList;
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
import com.myapp.core.model.ColumnModel;
import com.myapp.entity.ec.basedata.ProBaseWbsInfo;
import com.myapp.entity.ec.basedata.ProStructureInfo;
import com.myapp.enums.ec.ProWbsType;
import com.myapp.service.ec.basedata.ProjectWbsService;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年12月18日 
 * @system: 工程项目分解结构
 *-----------MySong---------------
 */
@Controller
@RequestMapping("ec/basedata/proWbsF7")
public class ProjectWbsF7QueryController extends BaseF7QueryController {
	
	@Resource
	public ProjectWbsService projectWbsService;
	public AbstractBaseService getService() {
		return projectWbsService;
	}
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		ColumnModel col = new ColumnModel("proStruct",DataTypeEnum.F7,ProStructureInfo.class);
		col.setFormat("id,name,displayName");
		col.setAlias_zh("id,单位工程,单位工程");
		cols.add(col);
		
		col = new ColumnModel("wbsType",DataTypeEnum.ENUM,ProWbsType.class);
		col.setAlias_zh("结构类型");
		cols.add(col);
		
		col = new ColumnModel("parent",DataTypeEnum.F7,ProBaseWbsInfo.class);
		col.setFormat("id,number,wbsType");
		col.setAlias_zh("id,上级编码,wbsType");
		cols.add(col);
		
		
		col = new ColumnModel("number");
		col.setAlias_zh("编码");
		cols.add(col);
		
		col = new ColumnModel("name");
		col.setAlias_zh("名称");
		cols.add(col);
		
		col = new ColumnModel("displayName");
		col.setAlias_zh("全名");
		cols.add(col);
		
		col = new ColumnModel("remark");
		col.setAlias_zh("备注");
		cols.add(col);
		
		return cols;
	}
	
	public boolean showCol(String colName) {
		if(colName.equals("parent_wbsType")
				||colName.equals("proStruct_name")){
			return false;
		}
		return super.showCol(colName);
	}
	
	public void executeQueryParams(Criteria query) {
		super.executeQueryParams(query);
		query.add(Restrictions.eq("enabled",Boolean.TRUE));
		Map uiCtx = getQueryUiCtx();
		System.out.println(uiCtx);
		if(uiCtx!=null){
			Object projectIdObj = uiCtx.get("projectId");
			if(projectIdObj!=null){
				query.add(Restrictions.eq("project.id",projectIdObj.toString()));
			}
			Object proStructIdObj = uiCtx.get("structId");
			if(proStructIdObj!=null){
				query.add(Restrictions.eq("proStruct.id",proStructIdObj.toString()));
			}
		}
	}
	
	public List<Order> getOrders(){
		List<Order> orders = new ArrayList<Order>();
		orders.add(Order.asc("proStruct.longNumber"));
		orders.add(Order.asc("longNumber"));
		return orders;
	}
}
