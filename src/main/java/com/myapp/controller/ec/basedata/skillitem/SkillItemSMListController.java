package com.myapp.controller.ec.basedata.skillitem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.myapp.core.annotation.AuthorAnn;
import com.myapp.core.model.WebDataModel;
import com.myapp.core.util.EnumUtil;
import com.myapp.enums.ec.WorkSchemeGroup;
import com.myapp.service.ec.basedata.SkillClassService;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseDataListController;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.util.WebUtil;
import com.myapp.entity.ec.basedata.SkillClassInfo;
import com.myapp.enums.ec.SkillType;
import com.myapp.service.ec.basedata.SkillItemService;
import org.springframework.web.bind.annotation.ResponseBody;

@PermissionAnn(name="系统管理.现场管理.基础数据.安全技术交底",number="app.ec.basedata.smskillitem")
@Controller
@RequestMapping("ec/basedata/smskillitems")
public class SkillItemSMListController extends BaseDataListController {
	
	@Resource
	public SkillItemService skillItemService;
	@Resource
	public SkillClassService skillClassService;

	public AbstractBaseService getService() {
		return skillItemService;
	}
	
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("skillClass",DataTypeEnum.F7,SkillClassInfo.class));
		return cols;
	}
	
	public void executeQueryParams(Criteria query) {
		super.executeQueryParams(query);
		query.add(Restrictions.eq("skillType",SkillType.SM));
		String serach = request.getParameter("search");
		if(!BaseUtil.isEmpty(serach)){
			Map searchMap = JSONObject.parseObject(serach, new HashMap().getClass());
			Object objTree = searchMap.get("tree");
			if(objTree!=null){
				Map treeMap = JSONObject.parseObject(objTree.toString(), new HashMap().getClass());
				Object skIdObj = treeMap.get("id");
				if(skIdObj!=null){
					query.add(Restrictions.eq("skillClass.id",WebUtil.UUID_ReplaceID(skIdObj.toString())));
				}
			}
		}
	}
	
	public String getEditUrl() {
		return "ec/basedata/skillitem/smskillItemEdit";
	}

	public String getListUrl() {
		return "ec/basedata/skillitem/smskillItemList";
	}

	@AuthorAnn(doPermission=false)
	@RequestMapping(value="/show/tree")
	@ResponseBody
	public WebDataModel showTree() {
		try{
			List<Map<String,Object>> result = skillItemService.showTree("SM");
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
			List<Map<String,Object>> skillTypeList =
					skillClassService.getSkillItems(EnumUtil.getEnum(SkillType.class.getName(),"SM"));
			for(Map<String,Object> skillType:skillTypeList){
				skillType.put("children",typeResult.get(skillType.get("id")));
			}
			List<Map<String,Object>> rootList = new ArrayList<>();
			Map root = new HashMap();
			root.put("id", "");
			root.put("name", "安全技术交底标准");
			root.put("children", skillTypeList);
			rootList.add(root);
			data = rootList;
		}catch(Exception e){
			e.printStackTrace();
			setErrorMesg(e.getMessage());
		}
		return ajaxModel();
	}

}
