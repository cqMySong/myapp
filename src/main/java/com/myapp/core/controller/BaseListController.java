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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.PermissionItemAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.base.setting.SystemConstant;
import com.myapp.core.enums.BaseMethodEnum;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.enums.PermissionTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.model.WebDataModel;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.util.DateUtil;
import com.myapp.core.util.WebUtil;

/**
 * 
 * -----------MySong---------------
 *  ©MySong基础框架搭建
 * @author mySong @date 2017年3月12日
 * @system:
 *
 *-----------MySong---------------
 */
public abstract class BaseListController extends CoreBaseController {
	private Integer curPage;
	private Integer pageSize;
	
	
	
	//特殊情况 在处理
	public String querySQL(){
		return "";
	}
	
	public void init() {
		super.init();
	}
	
	@PermissionItemAnn(name="查看",number="onload",type=PermissionTypeEnum.PAGE)
	@RequestMapping("/list")
	public ModelAndView list(){
		Map params = new HashMap();
		packageUIParams(params);
		return toPage(getListUrl(), params);
	}
	
	@PermissionItemAnn(name="查询",number="query")
	@RequestMapping(value="/query")
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

	@PermissionItemAnn(name="删除",number="remove")
	@ResponseBody
	@RequestMapping(value="/remove",method=RequestMethod.POST)
	public WebDataModel toRemove() {
		try {
			init();
			String billId = getReuestBillId();
			if(!BaseUtil.isEmpty(billId)){
				if(billId.indexOf(",")<0){
					getService().deleteEntity(billId);
				}else{
					String[] billIds = billId.split(",");
					for(int i=0;i<billIds.length;i++){
						getService().deleteEntity(billIds[i]);
					}
				}
				setInfoMesg("数据删除成功!");
			}else{
				setErrorMesg("单据id为空，无法完成删除操作!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			setExceptionMesg(e.getMessage());
		}
		return ajaxModel();
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
	
	
	@PermissionItemAnn(name="编辑",number="edit",type=PermissionTypeEnum.PAGEADDFUNCTION)
	@RequestMapping("/edit")
	public ModelAndView edit(){
		System.out.println(this.getClass().getName());
		Map params = new HashMap();
		init();
		try {
			setBaseMethod(BaseMethodEnum.EDIT);
			String billId = getReuestBillId();
			packageUIParams(params);
			if(!BaseUtil.isEmpty(billId)){
				params.put(getEntityPk(),billId);
			}else{
				setErrorMesg("单据id为空，无法进入数据编辑界面!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			setExceptionMesg(e.getMessage());
		}
		return toPage(getEditUrl(), params);
	}
	@PermissionItemAnn(name="查看",number="view",type=PermissionTypeEnum.PAGEADDFUNCTION)
	@RequestMapping("/view")
	public ModelAndView view(){
		Map params = new HashMap();
		init();
		try {
			setBaseMethod(BaseMethodEnum.VIEW);
			String billId = getReuestBillId();
			packageUIParams(params);
			if(!BaseUtil.isEmpty(billId)){
				params.put(getEntityPk(), billId);
			}else{
				setErrorMesg("单据id为空，无法进入数据查看界面!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			setExceptionMesg(e.getMessage());
		}
		return toPage(getEditUrl(), params);
	}
	//统一处理 uiCtx的参数类型传递
	public void packageUIParams(Map params){
		String ciCtx = request.getParameter("uiCtx");
		if(!BaseUtil.isEmpty(ciCtx)){
			Map uiCtx = JSONObject.parseObject(ciCtx, new HashMap().getClass());
			params.put("uiCtx", ciCtx);
		}
	}
	@PermissionItemAnn(name="新增",number="addnew",type=PermissionTypeEnum.PAGEADDFUNCTION)
	@RequestMapping("/addnew")
	public ModelAndView addnew(){
		init();
		setBaseMethod(BaseMethodEnum.ADDNEW);
		Map params = new HashMap();
		packageUIParams(params);
		return toPage(getEditUrl(), params);
	}
	
	public void initAddNewParams(HashMap params){
		
	}
	
	public Order getOrder() {
		return Order.asc("number");
	}
	
	public abstract String getEditUrl();
	public abstract String getListUrl();
}
