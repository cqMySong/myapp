package com.myapp.controller.ec.basedata.skillitem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

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

@PermissionAnn(name="系统管理.现场管理.基础数据.施工技术交底",number="app.ec.basedata.qmskillitem")
@Controller
@RequestMapping("ec/basedata/qmskillitems")
public class SkillClassQMListController extends BaseDataListController {
	@Resource
	public SkillItemService skillItemService;

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
		query.add(Restrictions.eq("skillType",SkillType.QM));
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
		return "ec/basedata/skillitem/qmskillItemEdit";
	}

	public String getListUrl() {
		return "ec/basedata/skillitem/qmskillItemList";
	}

}
