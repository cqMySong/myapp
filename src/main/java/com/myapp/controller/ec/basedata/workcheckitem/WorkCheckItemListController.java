package com.myapp.controller.ec.basedata.workcheckitem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.AuthorAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseDataListController;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.model.WebDataModel;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.util.EnumUtil;
import com.myapp.enums.ec.WorkCheckGroup;
import com.myapp.enums.ec.WorkCheckType;
import com.myapp.service.ec.basedata.WorkCheckItemService;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2018年1月31日 
 * @system:
 * 现场施工日 周 月检查项目配置 基类
 *-----------MySong---------------
 */
public abstract class WorkCheckItemListController extends BaseDataListController {
	@Resource
	public WorkCheckItemService workCheckItemService;

	public AbstractBaseService getService() {
		return workCheckItemService;
	}
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("workCheckGroup",DataTypeEnum.ENUM,WorkCheckGroup.class));
		cols.add(new ColumnModel("workCheckType",DataTypeEnum.ENUM,WorkCheckType.class));
		cols.add(new ColumnModel("checkRequire"));
		return cols;
	}
	
	public String getRootName(){
		WorkCheckGroup wcg = getWorkCheckGroup();
		return "施工现场"+(wcg!=null?wcg.getName():"")+"检查类别";
	}
	@AuthorAnn(doPermission=false)
	@RequestMapping(value="/treeData")
	@ResponseBody
	public WebDataModel treeData() {
		try{
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
			data = root;
		}catch(Exception e){
			setErrorMesg(e.getMessage());
		}
		return ajaxModel();
	}
	public abstract WorkCheckGroup getWorkCheckGroup();
	public void executeQueryParams(Criteria query) {
		super.executeQueryParams(query);
		WorkCheckGroup wcg = getWorkCheckGroup();
		if(wcg!=null){
			query.add(Restrictions.eq("workCheckGroup",wcg));
		}
		String serach = request.getParameter("search");
		if(!BaseUtil.isEmpty(serach)){
			Map searchMap = JSONObject.parseObject(serach, new HashMap().getClass());
			Object objTree = searchMap.get("tree");
			if(objTree!=null){
				Map treeMap = JSONObject.parseObject(objTree.toString(), new HashMap().getClass());
				Object skIdObj = treeMap.get("id");
				if(!BaseUtil.isEmpty(skIdObj)){
					WorkCheckType uc = EnumUtil.getEnum(WorkCheckType.class.getName(), skIdObj.toString());
					query.add(Restrictions.eq("workCheckType",uc));
				}
			}
		}
	}
	
	public List<Order> getOrders() {
		List<Order> orders = new ArrayList<Order>();
		orders.add(Order.asc("workCheckType"));
		orders.add(Order.asc("number"));
		return orders;
	}
}
