package com.myapp.controller.ec.basedata.batchtest;

import java.util.*;

import javax.annotation.Resource;

import com.myapp.core.annotation.PermissionItemAnn;
import com.myapp.core.enums.PermissionTypeEnum;
import com.myapp.core.exception.db.QueryException;
import com.myapp.core.model.PageModel;
import com.myapp.core.util.WebUtil;
import com.myapp.service.ec.plan.ProjectPlanReportService;
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
import org.springframework.web.servlet.ModelAndView;

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
	@Resource
	private ProjectPlanReportService projectPlanReportService;
	@Override
	public AbstractBaseService getService() {
		return proBatchTestService;
	}
	@Override
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("number"));
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("remark"));
		cols.add(new ColumnModel("proBaseWbs",DataTypeEnum.F7,ProBaseWbsInfo.class,"id,name,displayName"));
		cols.add(new ColumnModel("project",DataTypeEnum.F7,ProjectInfo.class));
		cols.add(new ColumnModel("content"));
		cols.add(new ColumnModel("attachs"));
		return cols;
	}
	@Override
	public void executeQueryParams(Criteria query) {
		super.executeQueryParams(query);
		String serach = request.getParameter("search");
		String projectId = "xyz";
		if(!BaseUtil.isEmpty(serach)){
			Map searchMap = JSONObject.parseObject(serach, new HashMap().getClass());
			Object objTree = searchMap.get("tree");
			if(objTree!=null){
				Map treeMap = JSONObject.parseObject(objTree.toString(), new HashMap().getClass());
				Object treeId = treeMap.get("id");
				if(!BaseUtil.isEmpty(treeId)){
					projectId = treeId.toString();
				}
			}
		}
		query.add(Restrictions.eq("project.id",projectId));
	}
	@Override
	public String getEditUrl() {
		return "ec/basedata/batchtest/proBatchTestEdit";
	}
	@Override
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
	@Override
	public String getHeadTitle() {
		return "项目检验批划分";
	}
	@Override
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
	@Override
	public void afterQuery(PageModel pm) throws QueryException {
		super.afterQuery(pm);
		String serach = request.getParameter("search");
		if(!BaseUtil.isEmpty(serach)){
			Map searchMap = JSONObject.parseObject(serach, new HashMap().getClass());
			Object objTree = searchMap.get("tree");
			if(objTree!=null){
				Map treeMap = JSONObject.parseObject(objTree.toString(), new HashMap().getClass());
				Object treeIdObj = treeMap.get("id");
				if(treeIdObj!=null){
					Date begDate = projectPlanReportService.getReportBeginTime(WebUtil.UUID_ReplaceID(treeIdObj.toString()));
					List<Map<String,Object>> datas = pm.getDatas();
					if(datas!=null){
						for (Map<String,Object> map:datas){
							map.put("projectStartTime",begDate);
						}
					}
				}
			}
		}

	}

	@PermissionItemAnn(name="项目检验批划分导入",number="import",type= PermissionTypeEnum.FUNCTION)
	@RequestMapping("/batch/import")
	public ModelAndView forwardBatchImport(){
		Map params = new HashMap();
		params = getUiCtx();
		if(params!=null&&params.get("project")!=null){
			Map project = (Map) params.get("project");
			params.put("targetId",project.get("id"));
		}
		toListUIParams(params);
		params.put("uiCtx",WebUtil.UUID_ReplaceID(params.get("uiCtx").toString()));
		return toPage("ec/basedata/batchtest/proBatchTestImport", params);
	}
}
