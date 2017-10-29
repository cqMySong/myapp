package com.myapp.core.controller;

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
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.AuthorAnn;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.exception.db.QueryException;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.model.WebDataModel;
import com.myapp.core.util.BaseUtil;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年6月20日 
 * @system:
 *
 *-----------MySong---------------
 */
public abstract class BaseTreeF7QueryController extends CoreBaseController {
	private ProjectionList treeProjections;
	public String getF7URL(){
		return "base/baseTreeF7";
	}
	public String getTreeTitle(){
		return "信息查询!";
	}
	
	public List<ColumnModel> getTreeDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("number"));
		cols.add(new ColumnModel("longNumber"));
		ColumnModel orgCol = new ColumnModel("parent",DataTypeEnum.F7,"id,name");
		cols.add(orgCol);
		return cols;
	}
	
	@AuthorAnn(doPermission=false)
	@RequestMapping("/f7show")
	public ModelAndView f7show(){
		String path = request.getServletPath();
		path = path.substring(0, path.lastIndexOf("/"))+"/treeData";
		Map params = new HashMap();
		params.put("dataUrl", path);
		params.put("treeTitle", getTreeTitle());
		packageUIParams(params);
		return toPage(getF7URL(), params);
	}
	
	public void packageUIParams(Map params){
		String ciCtx = request.getParameter("uiCtx");
		if(!BaseUtil.isEmpty(ciCtx)){
			Map uiCtx = JSONObject.parseObject(ciCtx, new HashMap().getClass());
			params.put("uiCtx", ciCtx);
		}
	}
	
	protected Criteria initTreeQueryCriteria() throws QueryException{
		Criteria criter = getService().initQueryCriteria();
		treeProjections = Projections.projectionList();
		List<ColumnModel> cols = getTreeDataBinding();
		for(ColumnModel cm:cols){
			packageCriteria(criter,"",treeProjections,cm);
		}
		return criter;
	}
	
	@AuthorAnn(doPermission=false)
	@RequestMapping(value="/treeData")
	@ResponseBody
	public WebDataModel treeData() {
		try {
			init();
			Criteria query = initTreeQueryCriteria();
			executeTreeQueryParams(query);
			Order order = getTreeOrder();
			if(order!=null){
				query.addOrder(order);
			}
			data = excueteTreeQuery(query);
		} catch (Exception e) {
			e.printStackTrace();
			setExceptionMesg(e.getMessage());
		}
		return ajaxModel();
	}
	
	public List excueteTreeQuery(Criteria query){
		if(treeProjections!=null){
			query.setProjection(treeProjections);
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}
	
	public void executeTreeQueryParams(Criteria query) {
		
	}
	
	public Order getTreeOrder(){
		return Order.asc("longNumber");
	}

}
