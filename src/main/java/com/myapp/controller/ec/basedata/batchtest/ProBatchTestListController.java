package com.myapp.controller.ec.basedata.batchtest;

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
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.exception.db.SaveException;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.model.WebDataModel;
import com.myapp.core.util.BaseUtil;
import com.myapp.entity.ec.basedata.ProBaseWbsInfo;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.service.ec.basedata.ProBatchTestService;
/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年12月18日 
 * @system:
 * 项目检验批划分
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.基础数据.项目检验批划分",number="app.ec.basedata.probatchtest")
@Controller
@RequestMapping("ec/basedata/probatchtests")
public class ProBatchTestListController extends BaseListController {
	
	@Resource
	public ProBatchTestService proBatchTestService;
	public AbstractBaseService getService() {
		return proBatchTestService;
	}
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("number"));
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("remark"));
		cols.add(new ColumnModel("proBaseWbs",DataTypeEnum.F7,ProBaseWbsInfo.class,"id,name,displayName"));
		cols.add(new ColumnModel("project",DataTypeEnum.F7,ProjectInfo.class));
		cols.add(new ColumnModel("content"));
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
		return "ec/basedata/batchtest/proBatchTestEdit";
	}

	public String getListUrl() {
		return "ec/basedata/batchtest/proBatchTestList";
	}
	
	@AuthorAnn(doPermission=false)
	@RequestMapping("/batchimp")
	@ResponseBody
	public WebDataModel batchImp(){
		String projectId= request.getParameter("projectId");
		try {
			return proBatchTestService.batchInitProData(projectId);
		} catch (SaveException e) {
			e.printStackTrace();
			setErrorMesg(e.getMessage());
		}
		return ajaxModel();
	}
	
	public String getHeadTitle() {
		return "项目检验批划分";
	}
	public List<ExcelExportEntity> getExportHeader() {
		List<ExcelExportEntity> entitys = new ArrayList<ExcelExportEntity>();
		entitys.add(new ExcelExportEntity("工程项目", "project_name"));
		ExcelExportEntity proBaseWbs = new ExcelExportEntity("分部分项工程", "proBaseWbs_displayName");
		entitys.add(proBaseWbs);
		entitys.add(new ExcelExportEntity("编码", "number"));
		entitys.add(new ExcelExportEntity("名称", "name"));
		ExcelExportEntity content = new ExcelExportEntity("划分标准", "content");
		content.setWrap(true);
		content.setWidth(60);
		entitys.add(content);
		ExcelExportEntity remark = new ExcelExportEntity("备注", "remark");
		remark.setWidth(80);
		remark.setWrap(true);
		entitys.add(remark);
		return entitys;
	}

}
