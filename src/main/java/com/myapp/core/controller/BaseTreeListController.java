package com.myapp.core.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.myapp.core.annotation.AuthorAnn;
import com.myapp.core.annotation.PermissionItemAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.exception.db.QueryException;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.model.WebDataModel;
import com.myapp.core.util.BaseUtil;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年5月28日 
 * @system:
 *
 *-----------MySong---------------
 */
public abstract class BaseTreeListController extends BaseListController {
	public abstract AbstractBaseService getTreeService();
	private ProjectionList treeProjections;
	
	@AuthorAnn(doLongin=true,doPermission=false)
	@RequestMapping(value="/tree")
	@ResponseBody
	public WebDataModel toTree() {
		try {
			init();
			Criteria query = initTreeQueryCriteria();
			executeTreeQueryParams(query);
			List<Order> orders = getTreeOrders();
			if(query!=null&&orders!=null&&orders.size()>0){
				for(Order order:orders){
					query.addOrder(order);
				}
			}
			data = excueteTreeQuery(query);
		} catch (Exception e) {
			e.printStackTrace();
			setExceptionMesg(e.getMessage());
		}
		return ajaxModel();
	}
	public void toListUIParams(Map params) {
		super.toListUIParams(params);
		String treeTitle = getTreeTitle();
		if(BaseUtil.isEmpty(treeTitle)){
			params.put("treeTitle", treeTitle);
		}
	}
	
	public String getTreeTitle(){
		return "";
	}
	
	
	public List<Order> getTreeOrders(){
		List<Order> treeOrds = new ArrayList<Order>();
		treeOrds.add(Order.asc("longNumber"));
		return treeOrds;
	}
	
	public List<Order> getOrders() {
		return getTreeOrders();
	}
	
	protected Criteria initTreeQueryCriteria() throws QueryException{
		Criteria criter = getTreeService().initQueryCriteria();
		treeProjections = Projections.projectionList();
		List<ColumnModel> cols = getTreeDataBinding();
		for(ColumnModel cm:cols){
			packageCriteria(criter,"",treeProjections,cm);
		}
		return criter;
	}
	public List excueteTreeQuery(Criteria query){
		if(treeProjections!=null){
			query.setProjection(treeProjections);
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}
	public List<ColumnModel> getTreeDataBinding(){
		List<ColumnModel> cols = new ArrayList<ColumnModel>();
		cols.add(new ColumnModel("id",DataTypeEnum.PK));
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("number"));
		cols.add(new ColumnModel("longNumber"));
		ColumnModel orgCol = new ColumnModel("parent",DataTypeEnum.F7,"id,name");
		cols.add(orgCol);
		return cols;
	}
	
	public void executeTreeQueryParams(Criteria query) {
		
	}
}
