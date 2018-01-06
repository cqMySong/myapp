package com.myapp.core.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.AuthorAnn;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.exception.db.QueryException;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.model.PageModel;
import com.myapp.core.model.WebDataModel;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.util.DateUtil;
import com.myapp.core.util.WebUtil;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年5月30日 
 * @system:
 *
 *-----------MySong---------------
 */
public abstract class BaseF7QueryController extends BasePageListController {
	private boolean mutil = false;
	public String getF7URL(){
		return "base/commonF7";
	}
	
	public List getF7DisplayCols(){
		return null;
	}
	
	private void packageDisplayCols(List displayCols,ColumnModel cm){
		DataTypeEnum dte = cm.getDataType();
		String alias =  cm.getAlias();
		if(DataTypeEnum.F7.equals(dte)){
			String[] formts = cm.getFormat().split(",");
			String[] alias_zhs = cm.getAlias_zh().split(",");
			if(formts!=null&&formts.length>0){
				for(int i=0;i<formts.length;i++){
					String f7Item = formts[i];
					if(!f7Item.equals("id")){
						Map colMap = new HashMap();
						boolean isVisible = showCol(alias+"_"+f7Item);
						colMap.put("field", alias+"_"+f7Item);
						colMap.put("type", WebUtil.Web_DataType_text);
						colMap.put("name", alias_zhs[i]);
						colMap.put("filter", cm.isQueryFilter()&&isVisible);
						colMap.put("visible", isVisible);
						displayCols.add(colMap);
					}
				}
			}
		}else if(DataTypeEnum.ENTRY.equals(dte)){
			List<ColumnModel> cols = cm.getCols();
			for(ColumnModel entryCol:cols){
				DataTypeEnum entrydte = entryCol.getDataType();
				if(!entryCol.isQueryDisplay()) continue;
				if(DataTypeEnum.PK.equals(entrydte)) continue;
				packageDisplayCols(displayCols,entryCol);
			}
		}else{
			String type = WebUtil.Web_DataType_text;
			if(DataTypeEnum.DATE.equals(dte)){
				type = WebUtil.Web_DataType_date;
			}else if(DataTypeEnum.DATETIME.equals(dte)){
				type = WebUtil.Web_DataType_datetime;
			}else if(DataTypeEnum.NUMBER.equals(dte)){
				type = WebUtil.Web_DataType_number;
			}else if(DataTypeEnum.BOOLEAN.equals(dte)){
				type = WebUtil.Web_DataType_checekbox;
			}else if(DataTypeEnum.ENUM.equals(dte)){
				type = WebUtil.Web_DataType_select;
			}
			boolean isVisible = showCol(alias);
			Map colMap = new HashMap();
			colMap.put("field",alias);
			colMap.put("type",type);
			colMap.put("name", cm.getAlias_zh());
			colMap.put("visible", isVisible);
			colMap.put("filter", cm.isQueryFilter()&&isVisible);
			displayCols.add(colMap);
		}
	}
	
	public boolean showCol(String colName){
		return true;
	}
	
	public String getUIWinTitle(){
		return "信息查询!";
	}
	
	@AuthorAnn(doPermission=false)
	@RequestMapping("/f7show")
	public ModelAndView f7show(){
		init();
		Map params = new HashMap();
		List displayCols = getF7DisplayCols();
		if(displayCols==null){
			displayCols = new ArrayList();
			List<ColumnModel> cols = getDataBinding();
			for(ColumnModel cm:cols){
				DataTypeEnum dte = cm.getDataType();
				if(!cm.isQueryDisplay()) continue;
				if(DataTypeEnum.PK.equals(dte)) continue;
				packageDisplayCols(displayCols,cm);
			}
		}
		String path = request.getServletPath();
		path = path.substring(0, path.lastIndexOf("/"))+"/f7Data";
		params.put("cols", JSONObject.toJSON(displayCols));
		params.put("dataUrl", path);
		boolean toMutil = isMutil();
		String isMut_ = request.getParameter("mutil");
		if(!BaseUtil.isEmpty(isMut_)){
			toMutil = "true".equals(isMut_)||"1".equals(isMut_);
		}
		params.put("mutil",toMutil);
		packageUIParams(params);
		return toPage(getF7URL(), params);
	}
	
	public void packageUIParams(Map params){
		String ciCtx = request.getParameter("uiCtx");
		if(!BaseUtil.isEmpty(ciCtx)){
			ciCtx = ciCtx.replaceAll("\'", "\"");
			Map uiCtx = JSONObject.parseObject(ciCtx, new HashMap().getClass());
			params.put("uiCtx", ciCtx);
		}
	}
	
	@AuthorAnn(doPermission=false)
	@RequestMapping(value="/f7Data")
	@ResponseBody
	public WebDataModel toList() {
		try {
			init();
			Criteria query = initQueryCriteria();
			executeQueryParams(query);
			List<Order> orders = getOrders();
			for(Order order:orders){
				if(order!=null) query.addOrder(order);
			}
			afterQuery(getService().toPageQuery(query, getProjectionList(), getCurPage(), getPageSize()));
//			data = getService().toPageQuery(query, getProjectionList(), getCurPage(), getPageSize());
		} catch (Exception e) {
			e.printStackTrace();
			setExceptionMesg(e.getMessage());
		}
		return ajaxModel();
	}
	
	public List<Order> getOrders(){
		List<Order> orders = new ArrayList<Order>();
		Order order = getOrder();
		if(order!=null){
			orders.add(order);
		}
		return orders;
	}
	
	public void afterQuery(PageModel pm) throws QueryException{
		List datas = pm.getDatas();
		if(datas!=null&&datas.size()>0){
			packageDatas(datas);
			pm.setDatas(datas);
		}
		this.data = pm;
	}
	public List<ColumnModel> getPackageDataCol(){
		List<ColumnModel> cols = getDataBinding();
		List<ColumnModel> toDoCols = new ArrayList<ColumnModel>(); 
		for(ColumnModel cm:cols){
			DataTypeEnum dte = cm.getDataType();
			if(dte.equals(DataTypeEnum.MUTILF7)&&cm.getClaz()!=null){
				toDoCols.add(cm);
			}else if(DataTypeEnum.ENUM.equals(dte)&&cm.getClaz()!=null){
				toDoCols.add(cm);
			}
		}
		return toDoCols;
	}
	public void packageDatas(List datas) throws QueryException{
		if(datas==null||datas.size()<=0) return;
		List<ColumnModel> cms = getPackageDataCol();
		if(cms!=null&&cms.size()>0){
			packageListDataColumns(datas, cms);
		}
	}
	
	protected Map getSearchPrams() {
		String serach = request.getParameter("search");
		if(!BaseUtil.isEmpty(serach)){
			return JSONObject.parseObject(serach, new HashMap().getClass());
		}
		return null;
	}
	
	public void executeQueryParams(Criteria query) {
		Map searchMap = getSearchPrams();
		if(!BaseUtil.isEmpty(searchMap)&&searchMap.size()>0){
			Object obj_field = searchMap.get("key");
			Object obj_val = searchMap.get("value");
			Object obj_type = searchMap.get("type");
			if(obj_field!=null){
				obj_field = obj_field.toString().replaceAll("_", ".");
				if(obj_val==null){
					query.add(Restrictions.isNull(obj_field.toString()));
				}else{
					obj_val = obj_val.toString();
					DataTypeEnum dte = WebUtil.getWebServerDataType(obj_type.toString());
					if(DataTypeEnum.BOOLEAN.equals(dte)){
						obj_val = obj_val.toString().toLowerCase().equals("true")||obj_val.equals("1")?1:0;
					}else if(DataTypeEnum.DATE.equals(dte)){
						obj_val = DateUtil.parseDate(obj_val.toString());
					}else if(DataTypeEnum.DATETIME.equals(dte)){
						obj_val = DateUtil.parseDate(obj_val.toString(),DateUtil.DATEFORMT_YMDHMS);
					}else if(DataTypeEnum.NUMBER.equals(dte)){
						obj_val = new BigDecimal(obj_val.toString());
					}
					if(DataTypeEnum.STRING.equals(dte)){
						query.add(Restrictions.like(obj_field.toString(), "%"+obj_val+"%"));
					}else{
						query.add(Restrictions.eq(obj_field.toString(),obj_val));
					}
				}
			}
		}
	}
	public Map getQueryUiCtx() {
		Map searchMap = getSearchPrams();
		if(!BaseUtil.isEmpty(searchMap)&&searchMap.size()>0){
			if(searchMap!=null&&searchMap.containsKey("uiCtx")){
				Object uiCtxObj = searchMap.get("uiCtx");
				if(uiCtxObj!=null) return JSONObject.parseObject(uiCtxObj.toString(), new HashMap().getClass());
			}
		}
		return null;
	}
	public boolean isMutil() {
		return mutil;
	}
	public void setMutil(boolean mutil) {
		this.mutil = mutil;
	}	
	
}
