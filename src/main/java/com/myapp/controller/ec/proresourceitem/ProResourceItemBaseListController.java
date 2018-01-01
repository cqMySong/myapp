package com.myapp.controller.ec.proresourceitem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.AuthorAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseListController;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.exception.db.AddNewException;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.model.WebDataModel;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.util.WebUtil;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.enums.ec.ResourceType;
import com.myapp.service.ec.basedata.ProResourceItemService;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2018年1月1日 
 * @system: 项目级资料目录（一级）
 *-----------MySong---------------
 */
public abstract class ProResourceItemBaseListController extends BaseListController {
	@Resource
	public ProResourceItemService proResourceItemService;
	public AbstractBaseService getService() {
		return proResourceItemService;
	}
	
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("project",DataTypeEnum.F7,ProjectInfo.class));
		cols.add(new ColumnModel("attachs",DataTypeEnum.INT));
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("number"));
		cols.add(new ColumnModel("remark"));
		return cols;
	}
	
	public abstract ResourceType getResourceType();
	
	public void executeQueryParams(Criteria query) {
		super.executeQueryParams(query);
		ResourceType type = getResourceType();
		if(BaseUtil.isNotEmpty(type)){
			query.add(Restrictions.eq("resourceType",type));
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
	
	@AuthorAnn(doPermission=false)
	@RequestMapping("/batchimp")
	@ResponseBody
	public WebDataModel batchImp(){
		String projectId = request.getParameter("projectId");
		try {
			return proResourceItemService.batchInitProData(projectId,getResourceType());
		} catch (AddNewException e) {
			e.printStackTrace();
			setErrorMesg(e.getMessage());
		}
		return ajaxModel();
	}
}
