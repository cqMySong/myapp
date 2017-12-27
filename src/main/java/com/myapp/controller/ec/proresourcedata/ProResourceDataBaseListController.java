package com.myapp.controller.ec.proresourcedata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseListController;
import com.myapp.core.entity.basedate.DataGroupInfo;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.service.basedata.DataGroupService;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.util.WebUtil;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.service.ec.basedata.ProResourceDataService;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年12月11日 
 * @system: 项目级资料目录(安全 ，技术,.....)
 *-----------MySong---------------
 */
public abstract class ProResourceDataBaseListController extends BaseListController {
	@Resource
	public ProResourceDataService proResourceDataService;
	public AbstractBaseService getService() {
		return proResourceDataService;
	}
	
	@Resource
	public DataGroupService dataGroupService;
	public AbstractBaseService getTreeService() {
		return dataGroupService;
	}
	
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		ColumnModel col = new ColumnModel("group",DataTypeEnum.F7,DataGroupInfo.class);
		col.setFormat("id,name,displayName");
		cols.add(col);
		cols.add(new ColumnModel("project",DataTypeEnum.F7,ProjectInfo.class));
		cols.add(new ColumnModel("attachs",DataTypeEnum.INT));
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("number"));
		return cols;
	}
	
	public abstract String getCode();
	
	public void executeQueryParams(Criteria query) {
		super.executeQueryParams(query);
		String code = getCode();
		if(BaseUtil.isNotEmpty(code)){
			query.add(Restrictions.eq("code",code));
		}
		String serach = request.getParameter("search");
		if(!BaseUtil.isEmpty(serach)){
			Map searchMap = JSONObject.parseObject(serach, new HashMap().getClass());
			Object objTree = searchMap.get("tree");
			if(objTree!=null){
				Map treeMap = JSONObject.parseObject(objTree.toString(), new HashMap().getClass());
				Object skIdObj = treeMap.get("id");
				if(skIdObj!=null){
					query.add(Restrictions.eq("project.id",WebUtil.UUID_ReplaceID(skIdObj.toString())));
				}
			}
		}
	}
}
