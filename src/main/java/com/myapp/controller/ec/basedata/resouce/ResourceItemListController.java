package com.myapp.controller.ec.basedata.resouce;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.EnumType;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.sql.dialect.mysql.ast.expr.MySqlIntervalUnit;
import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.AuthorAnn;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseDataListController;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.model.WebDataModel;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.util.EnumUtil;
import com.myapp.core.util.WebUtil;
import com.myapp.enums.ec.ResourceType;
import com.myapp.service.ec.basedata.ResourceItemService;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年11月30日 
 * @system:
 *
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.基础数据.资料项目录",number="app.ec.basedata.resourceitem")
@Controller
@RequestMapping("ec/basedata/resourceitems")
public class ResourceItemListController extends BaseDataListController {
	@Resource
	public ResourceItemService resourceItemService;
	public AbstractBaseService getService() {
		return resourceItemService;
	}
	
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("resourceType",DataTypeEnum.ENUM,ResourceType.class));
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
				Object rtObjValue = treeMap.get("id");
				if(rtObjValue!=null){
					ResourceType rt = EnumUtil.getEnum(ResourceType.class.getName(), rtObjValue);
					if(rt!=null){
						query.add(Restrictions.eq("resourceType",rt));
					}
				}
			}
		}
	}
	
	public String getRootName(){
		return "资料目录";
	}
	@AuthorAnn(doPermission=false)
	@RequestMapping(value="/treeData")
	@ResponseBody
	public WebDataModel treeData() {
		try{
			List<Map> items = new ArrayList<Map>();
			for(ResourceType tg:ResourceType.values()){
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
	
	public String getEditUrl() {
		return "ec/basedata/resource/resourceItemEdit";
	}

	public String getListUrl() {
		return "ec/basedata/resource/resourceItemList";
	}

	@AuthorAnn(doPermission=false)
	@RequestMapping(value="/showitemtree/{resourceType}")
	@ResponseBody
	public WebDataModel showItemTree(@PathVariable String resourceType) {
		try{
			List<Map<String,Object>> rootList = new ArrayList<>();
			Map root = new HashMap();
			root.put("id", "");
			root.put("name", ResourceType.valueOf(ResourceType.class,resourceType).getName());
			root.put("open", true);
			root.put("children", resourceItemService.queryByResourceType(resourceType));
			rootList.add(root);
			data = rootList;
		}catch(Exception e){
			e.printStackTrace();
			setErrorMesg(e.getMessage());
		}
		return ajaxModel();
	}

}
