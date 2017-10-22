package com.myapp.controller.base.measureunit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.AuthorAnn;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseDataListController;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.enums.UnitClass;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.model.WebDataModel;
import com.myapp.core.service.MeasureUnitService;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.util.EnumUtil;

@PermissionAnn(name="系统管理.基础资料.计量单位",number="app.basedata.measureunit")
@Controller
@RequestMapping("base/measureunits")
public class MeasureUnitListController extends BaseDataListController {
	@Resource
	public MeasureUnitService measureUnitService;

	public AbstractBaseService getService() {
		return measureUnitService;
	}
	
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("unitClass",DataTypeEnum.ENUM,UnitClass.class));
		return cols;
	}
	
	public void executeQueryParams(Criteria query) {
		super.executeQueryParams(query);
		String serach = request.getParameter("search");
		if(!BaseUtil.isEmpty(serach)){
			Map searchMap = JSONObject.parseObject(serach, new HashMap().getClass());
			Object objTree = searchMap.get("tree");
			if(objTree!=null){
				Map treeMap = JSONObject.parseObject(objTree.toString(), new HashMap().getClass());
				Object skIdObj = treeMap.get("id");
				if(skIdObj!=null){
					UnitClass uc = EnumUtil.getEnum(UnitClass.class.getName(), skIdObj.toString());
					query.add(Restrictions.eq("unitClass",uc));
				}
			}
		}
	}
	
	@AuthorAnn(doPermission=false)
	@RequestMapping(value="/treeData")
	@ResponseBody
	public WebDataModel treeData() {
		Map params = new HashMap();
		List items = new ArrayList();
		for(UnitClass ucs:UnitClass.values()){
			Map item = new HashMap();
			item.put("id", ucs.getValue());
			item.put("name", ucs.getName());
			items.add(item);
		}
		Map root = new HashMap();
		root.put("id", "");
		root.put("name", "单位分类");
		root.put("open", true);
		root.put("children", items);
		data = root;
		return ajaxModel();
	}
	
	public String getEditUrl() {
		return "basedata/measureunit/measureUnitEdit";
	}

	public String getListUrl() {
		return "basedata/measureunit/measureUnitList";
	}

}
