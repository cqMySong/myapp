package com.myapp.controller.ec.basedata.schemetype;

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
import com.myapp.core.model.ColumnModel;
import com.myapp.core.model.WebDataModel;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.util.EnumUtil;
import com.myapp.enums.ec.WorkSchemeGroup;
import com.myapp.service.ec.basedata.SchemeTypeService;

@PermissionAnn(name="系统管理.现场管理.基础数据.施工方案分类",number="app.ec.basedata.schemetype")
@Controller
@RequestMapping("ec/basedata/schemeTypeList")
public class SchemeTypeListController extends BaseDataListController {
	
	@Resource
	public SchemeTypeService schemeTypeService;

	public AbstractBaseService getService() {
		return schemeTypeService;
	}
	
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("workSchemeGroup",DataTypeEnum.ENUM,WorkSchemeGroup.class));
		return cols;
	}
	
	public void setSchemeTypeService(SchemeTypeService schemeTypeService) {
		this.schemeTypeService = schemeTypeService;
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
				if(!BaseUtil.isEmpty(skIdObj)){
					WorkSchemeGroup uc = EnumUtil.getEnum(WorkSchemeGroup.class.getName(), skIdObj.toString());
					query.add(Restrictions.eq("workSchemeGroup",uc));
				}
			}
		}
	}
	public String getRootName(){
		return "施工方案类别";
	}
	@AuthorAnn(doPermission=false)
	@RequestMapping(value="/treeData")
	@ResponseBody
	public WebDataModel treeData() {
		try{
			List<Map> items = new ArrayList<Map>();
			for(WorkSchemeGroup tg:WorkSchemeGroup.values()){
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
		return "ec/basedata/schemetype/schemeTypeEdit";
	}

	public String getListUrl() {
		return "ec/basedata/schemetype/schemeTypeList";
	}

	@AuthorAnn(doPermission=false)
	@RequestMapping(value="/show/tree")
	@ResponseBody
	public WebDataModel showTree() {
		try{
			List<Map> items = new ArrayList<Map>();
			List<Map<String,Object>> result = schemeTypeService.showTree();
			Map<String,List<Map<String,Object>>> typeResult = new HashMap<>();
			List<Map<String,Object>> children = null;
			for (Map<String,Object> schemeType:result){
				if(typeResult.get(schemeType.get("parent"))==null){
					children = new ArrayList<>();
					typeResult.put(schemeType.get("parent").toString(),children);
				}else{
					children = typeResult.get(schemeType.get("parent").toString());
				}
				children.add(schemeType);
			}
			for(WorkSchemeGroup tg:WorkSchemeGroup.values()){
				Map item = new HashMap();
				item.put("id", tg.getValue());
				item.put("name",tg.getName());
				item.put("children",typeResult.get(tg.getValue()));
				items.add(item);
			}
			List<Map<String,Object>> rootList = new ArrayList<>();
			Map root = new HashMap();
			root.put("id", "");
			root.put("name", "施工方案标准");
			root.put("children", items);
			rootList.add(root);
			data = rootList;
		}catch(Exception e){
			e.printStackTrace();
			setErrorMesg(e.getMessage());
		}
		return ajaxModel();
	}

}
