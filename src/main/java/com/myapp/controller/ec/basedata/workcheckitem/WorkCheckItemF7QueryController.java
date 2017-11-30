package com.myapp.controller.ec.basedata.workcheckitem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.AuthorAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BasePageListController;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.exception.db.QueryException;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.model.PageModel;
import com.myapp.core.model.WebDataModel;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.util.EnumUtil;
import com.myapp.enums.ec.WorkCheckType;
import com.myapp.service.ec.basedata.WorkCheckItemService;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年11月29日 
 * @system:
 *
 *-----------MySong---------------
 */
@Controller
@RequestMapping("ec/basedata/workcheckitemquery")
public class WorkCheckItemF7QueryController extends BasePageListController {
	
	@Resource
	public WorkCheckItemService workCheckItemService;
	public AbstractBaseService getService() {
		return workCheckItemService;
	}
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("number"));
		cols.add(new ColumnModel("workCheckType",DataTypeEnum.ENUM,WorkCheckType.class));
		cols.add(new ColumnModel("checkRequire"));
		return cols;
	}
	
	public String getF7URL(){
		return "ec/basedata/workcheckitem/workCheckItemF7Query";
	}
	
	@AuthorAnn(doPermission=false)
	@RequestMapping("/f7show")
	public ModelAndView f7show(){
		Map params = new HashMap();
		boolean toMutil = true;
		String isMut_ = request.getParameter("mutil");
		if(!BaseUtil.isEmpty(isMut_)){
			toMutil = "true".equals(isMut_)||"1".equals(isMut_);
		}
		params.put("mutil", isMut_);
		return toPage(getF7URL(),params);
	}
	
	public void executeQueryParams(Criteria query) {
		//table 数据过滤
		String search = request.getParameter("search");//分类
		if(BaseUtil.isNotEmpty(search)){
			Map searchMap = JSONObject.parseObject(search, new HashMap().getClass());
			Object type = searchMap.get("type");
			if(!BaseUtil.isEmpty(type)){
				WorkCheckType uc = EnumUtil.getEnum(WorkCheckType.class.getName(), type);
				query.add(Restrictions.eq("workCheckType",uc));
			}
			Object name = searchMap.get("name");//项目名称
			if(!BaseUtil.isEmpty(name)){
				query.add(Restrictions.like("name",name.toString(),MatchMode.ANYWHERE));
			}
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
			Order order = getOrder();
			if(order!=null){
				query.addOrder(order);
			}
			afterQuery(getService().toPageQuery(query, getProjectionList(), getCurPage(), getPageSize()));
		} catch (Exception e) {
			e.printStackTrace();
			setExceptionMesg(e.getMessage());
		}
		return ajaxModel();
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
			if(DataTypeEnum.ENUM.equals(dte)&&cm.getClaz()!=null){
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
	
	public String getRootName(){
		return "施工现场检查类别";
	}
	@AuthorAnn(doPermission=false)
	@RequestMapping(value="/treeData")
	@ResponseBody
	public WebDataModel tree() {
		try {
			init();
			List<Map> items = new ArrayList<Map>();
			for(WorkCheckType tg:WorkCheckType.values()){
				Map item = new HashMap();
				item.put("id", tg.getValue());
				item.put("name",tg.getName());
				items.add(item);
			}
			Map root = new HashMap();
			root.put("id", "");
			root.put("name", getRootName());
			root.put("open", true);
			root.put("children", items);
			List treeDatas = new ArrayList();
			treeDatas.add(root);
			data = treeDatas;
		} catch (Exception e) {
			e.printStackTrace();
			setExceptionMesg(e.getMessage());
		}
		return ajaxModel();
	}
}
