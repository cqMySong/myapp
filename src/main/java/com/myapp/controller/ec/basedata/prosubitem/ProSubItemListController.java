package com.myapp.controller.ec.basedata.prosubitem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
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
import com.myapp.entity.ec.basedata.ProStructureInfo;
import com.myapp.entity.ec.basedata.ProSubInfo;
import com.myapp.entity.ec.basedata.ProSubItemInfo;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.service.ec.basedata.ProSubItemService;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年6月7日 
 * @system:
 *
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.基础数据.项目分部分项",number="app.ec.basedata.prosubitem")
@Controller
@RequestMapping("ec/basedata/prosubitems")
public class ProSubItemListController extends BaseDataListController {
	
	@Resource
	public ProSubItemService proSubItemService;
	
	public AbstractBaseService getService() {
		return proSubItemService;
	}

	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		ColumnModel proSub = new ColumnModel("proSub",DataTypeEnum.F7,"id,name");
		proSub.setClaz(ProSubInfo.class);
		cols.add(proSub);
		ColumnModel project = new ColumnModel("project",DataTypeEnum.F7,"id,name");
		project.setClaz(ProjectInfo.class);
		cols.add(project);
		ColumnModel proStruct = new ColumnModel("proStruct",DataTypeEnum.F7,"id,name");
		proStruct.setClaz(ProStructureInfo.class);
		cols.add(proStruct);
		return cols;
	}
	public void executeQueryParams(Criteria query) {
		super.executeQueryParams(query);
		String serach = request.getParameter("search");
		SimpleExpression se = Restrictions.eq("id","xyz");
		if(!BaseUtil.isEmpty(serach)){
			Map searchMap = JSONObject.parseObject(serach, new HashMap().getClass());
			Object objTree = searchMap.get("tree");
			if(objTree!=null){
				Map treeMap = JSONObject.parseObject(objTree.toString(), new HashMap().getClass());
				Object idObj = treeMap.get("id");
				Object type = treeMap.get("type");
				if(type!=null&&idObj!=null){
					if("project".equals(type.toString())){
						se = Restrictions.eq("project.id", idObj.toString());
					}else if("proStructure".equals(type.toString())){
						Object lnObj = treeMap.get("longNumber");
						se = Restrictions.like("proStruct.longNumber", lnObj.toString()+"%");
					}
				}
			}
			Object proSubObj = searchMap.get("proSubId");
			if(proSubObj!=null){
				query.add(Restrictions.eq("proSub.id", WebUtil.UUID_ReplaceID(proSubObj.toString())));
			}
		}
		query.add(se);
	}
	public String getEditUrl() {
		return "ec/basedata/prosubitem/proSubItemEdit";
	}

	public String getListUrl() {
		return "ec/basedata/prosubitem/proSubItemList";
	}

}
