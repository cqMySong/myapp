package com.myapp.controller.ec.basedata.ecdraw;

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

import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.AuthorAnn;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseListController;
import com.myapp.core.entity.basedate.DataGroupInfo;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.exception.db.SaveException;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.model.WebDataModel;
import com.myapp.core.util.BaseUtil;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.service.ec.basedata.ProEcDrawService;
/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年12月21日 
 * @system: 项目施工图资料
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.施工图.项目施工图",number="app.ec.ecdraw.proecdraw")
@Controller
@RequestMapping("ec/ecdraw/proecdraws")
public class ProEcDrawListController extends BaseListController {
	
	@Resource
	public ProEcDrawService proEcDrawService;
	public AbstractBaseService getService() {
		return proEcDrawService;
	}
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("number"));
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("group",DataTypeEnum.F7,DataGroupInfo.class,"id,name,displayName"));
		cols.add(new ColumnModel("project",DataTypeEnum.F7,ProjectInfo.class));
		cols.add(new ColumnModel("attachs",DataTypeEnum.INT));
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
				Object treeId = treeMap.get("id");
				if(!BaseUtil.isEmpty(treeId)){
					query.add(Restrictions.eq("project.id",treeId.toString()));
				}
			}
		}
	}
	
	public String getEditUrl() {
		return "ec/basedata/ecdraw/proEcDrawEdit";
	}

	public String getListUrl() {
		return "ec/basedata/ecdraw/proEcDrawList";
	}
	
	@AuthorAnn(doPermission=false)
	@RequestMapping("/batchimp")
	@ResponseBody
	public WebDataModel batchImp(){
		String projectId = request.getParameter("projectId");
		try {
			return proEcDrawService.batchInitProData(projectId);
		} catch (SaveException e) {
			e.printStackTrace();
			setErrorMesg(e.getMessage());
		}
		return ajaxModel();
	}
	public String getHeadTitle() {
		return "项目施工图";
	}
	public List<ExcelExportEntity> getExportHeader() {
		List<ExcelExportEntity> entitys = new ArrayList<ExcelExportEntity>();
		entitys.add(new ExcelExportEntity("工程项目", "project_name"));
		ExcelExportEntity group = new ExcelExportEntity("资料目录", "group_displayName");
		group.setWidth(80);
		entitys.add(group);
		entitys.add(new ExcelExportEntity("编码", "number"));
		entitys.add(new ExcelExportEntity("名称", "name"));
		return entitys;
	}
}
