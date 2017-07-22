package com.myapp.core.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.base.controller.BaseController;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.exception.db.QueryException;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.util.WebUtil;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年5月25日 
 * @system:
 *
 *-----------MySong---------------
 */
public abstract class CoreBaseController extends BaseController{
	public abstract AbstractBaseService getService();
	private String billId;
	ProjectionList projectList;
	
	public String getReuestBillId() {
		return WebUtil.UUID_ReplaceID(request.getParameter("id"));
	}
	public String getBillId(){
		return this.billId;
	}
	public void setBillId(String billId) {
		this.billId = WebUtil.UUID_ReplaceID(billId);
	}
	public Order getOrder(){
		return Order.desc("id");
	}
	protected Projection createBaseField(String field){
		return createBaseField(field,field);
	}
	
	protected Projection createBaseField(String key,String alias){
		return Projections.property(key).as(alias);
	}
	
	public void packageCriteria(Criteria criter,String p_alias,ProjectionList projectList,ColumnModel cm){
		String key = cm.getName();
		String thisKey = cm.getName();
		String thisAlias = cm.getAlias();
		if(!BaseUtil.isEmpty(p_alias)){
			thisKey = p_alias+"."+key;
			thisAlias = p_alias+"_"+thisAlias;
		}
		DataTypeEnum dte = cm.getDataType();
		if(DataTypeEnum.F7.equals(dte)){
			criter.createAlias(thisKey, thisAlias, cm.getJoinType());
			String f7_format = cm.getFormat();
			if(!BaseUtil.isEmpty(f7_format)){
				String[] _cols = f7_format.split(",");
				for(String colItem:_cols){
					projectList.add(createBaseField(thisKey+"."+colItem,thisAlias+"_"+colItem));
				}
			}
		}else if(DataTypeEnum.ENTRY.equals(dte)){
			List<ColumnModel> entrys = cm.getCols();
			if(entrys.size()>0){
				criter.createAlias(thisKey, thisAlias, cm.getJoinType());
				for(ColumnModel _entry:entrys){
					packageCriteria(criter,thisAlias,projectList,_entry);
				}
			}
		}else{
			projectList.add(createBaseField(thisKey,thisAlias));
		}
	}
	
	protected Criteria initQueryCriteria() throws QueryException{
		Criteria criter = getService().initQueryCriteria();
		projectList = Projections.projectionList();
		List<ColumnModel> cols = getDataBinding();
		for(ColumnModel cm:cols){
			packageCriteria(criter,"",projectList,cm);
		}
		return criter;
	}
	
	private void setProjections(ProjectionList projectList){
		this.projectList = projectList;
	}
	public ProjectionList getProjectionList(){
		return this.projectList;
	}
	
	public List<ColumnModel> getDataBinding(){
		List<ColumnModel> cols = new ArrayList<ColumnModel>();
		cols.add(new ColumnModel("id",DataTypeEnum.PK));
		return cols;
	}
	
	public Map getUiCtx(){
		Map uiCtx = new HashMap();
		String uiCtxStr = request.getParameter("uiCtx");
		if(!BaseUtil.isEmpty(uiCtxStr)){
			uiCtx = JSONObject.parseObject(uiCtxStr, new HashMap().getClass());
		}
		return uiCtx;
	}
}
