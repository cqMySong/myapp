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
import com.myapp.core.base.setting.SystemConstant;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
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
public abstract class BaseF7QueryController extends CoreBaseController {
	private Integer curPage;
	private Integer pageSize;
	private boolean mutil = false;
	public String getF7URL(){
		return "base/commonF7";
	}
	
	public List getF7DisplayCols(){
		return null;
	}
	
	private void pacageDisplayCols(List displayCols,ColumnModel cm){
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
						colMap.put("field", alias+"_"+f7Item);
						colMap.put("type", WebUtil.Web_DataType_text);
						colMap.put("name", alias_zhs[i]);
						colMap.put("filter", cm.isQueryFilter());
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
				pacageDisplayCols(displayCols,entryCol);
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
			}
			Map colMap = new HashMap();
			colMap.put("field",alias);
			colMap.put("type",type );
			colMap.put("name", cm.getAlias_zh());
			colMap.put("filter", cm.isQueryFilter());
			displayCols.add(colMap);
		}
	}
	
	public String getUIWinTitle(){
		return "信息查询!";
	}
	
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
				pacageDisplayCols(displayCols,cm);
			}
		}
		String path = request.getServletPath();
		path = path.substring(0, path.lastIndexOf("/"))+"/f7Data";
		params.put("cols", JSONObject.toJSON(displayCols));
		params.put("mutil", isMutil());
		params.put("dataUrl", path);
		return toPage(getF7URL(), params);
	}
	
	@RequestMapping(value="/f7Data")
	@ResponseBody
	public WebDataModel toList() {
		try {
			init();
			Criteria query = initQueryCriteria();
			executeQueryParams(query);
			Order order = getOrder();
			if(order!=null){
				query.addOrder(order);
			}
			data = getService().toPageQuery(query, getProjectionList(), getCurPage(), getPageSize());
		} catch (Exception e) {
			e.printStackTrace();
			setExceptionMesg(e.getMessage());
		}
		return ajaxModel();
	}
	
	public void executeQueryParams(Criteria query) {
		String serach = request.getParameter("search");
		if(!BaseUtil.isEmpty(serach)){
			Map searchMap = JSONObject.parseObject(serach, new HashMap().getClass());
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
					query.add(Restrictions.eq(obj_field.toString(),obj_val));
				}
			}
		}
	}
	
	public boolean isMutil() {
		return mutil;
	}
	public void setMutil(boolean mutil) {
		this.mutil = mutil;
	}	
	
	public Integer getCurPage() {
		String cpage = request.getParameter("curPage");
		if(!BaseUtil.isEmpty(cpage)){
			curPage = Integer.valueOf(cpage);
		}
		if (curPage == null)
			curPage = SystemConstant.DEF_PAGE_BEG;
		return curPage;
	}

	public void setCurPage(Integer curPage) {
		this.curPage = curPage;
	}

	public Integer getPageSize() {
		String pgsize = request.getParameter("pageSize");
		if(!BaseUtil.isEmpty(pgsize)){
			pageSize = Integer.valueOf(pgsize);
		}
		if (pageSize == null)
			pageSize = SystemConstant.DEF_PAGE_SIZE;
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
}
