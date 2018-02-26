package com.myapp.controller.ec.basedata.skillitem;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.myapp.core.annotation.PermissionItemAnn;
import com.myapp.core.enums.PermissionTypeEnum;
import com.myapp.core.exception.db.QueryException;
import com.myapp.core.model.PageModel;
import com.myapp.service.ec.plan.ProjectPlanReportService;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.AuthorAnn;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseListController;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.exception.db.SaveException;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.model.WebDataModel;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.util.WebUtil;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.entity.ec.basedata.SkillClassInfo;
import com.myapp.enums.ec.SkillType;
import com.myapp.service.ec.basedata.ProSkillDisclosureService;
import org.springframework.web.servlet.ModelAndView;

@PermissionAnn(name="系统管理.现场管理.技术交底.项目施工技术交底",number="app.ec.skilldisclosure.proqmskill")
@Controller
@RequestMapping("ec/skilldisclosure/proqmskills")
public class ProSkillDisclosureQMListController extends BaseListController {
	
	@Resource
	public ProSkillDisclosureService proSkillDisclosureService;
	@Resource
	private ProjectPlanReportService projectPlanReportService;
	@Override
	public AbstractBaseService getService() {
		return proSkillDisclosureService;
	}
	@Override
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("number"));
		cols.add(new ColumnModel("attachs",DataTypeEnum.INT));
		cols.add(new ColumnModel("finishTime",DataTypeEnum.DATE));
		cols.add(new ColumnModel("createDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("project",DataTypeEnum.F7,ProjectInfo.class));
		cols.add(new ColumnModel("skillClass",DataTypeEnum.F7,SkillClassInfo.class));
		cols.add(new ColumnModel("disclosurer",DataTypeEnum.F7,UserInfo.class));
		cols.add(new ColumnModel("lastUpdateDate",DataTypeEnum.DATE));
		return cols;
	}
	@Override
	public void executeQueryParams(Criteria query) {
		super.executeQueryParams(query);
		query.add(Restrictions.eq("skillType",SkillType.QM));
		String serach = request.getParameter("search");
		String projectId = "xyz";
		if(!BaseUtil.isEmpty(serach)){
			Map searchMap = JSONObject.parseObject(serach, new HashMap().getClass());
			Object objTree = searchMap.get("tree");
			if(objTree!=null){
				Map treeMap = JSONObject.parseObject(objTree.toString(), new HashMap().getClass());
				Object treeIdObj = treeMap.get("id");
				if(treeIdObj!=null){
					projectId = WebUtil.UUID_ReplaceID(treeIdObj.toString());
				}
			}
		}
		query.add(Restrictions.eq("project.id",projectId));
	}
	@Override
	public String getEditUrl() {
		return "ec/skilldisclosure/proqmskillEdit";
	}
	@Override
	public String getListUrl() {
		return "ec/skilldisclosure/proqmskillList";
	}
	@AuthorAnn(doPermission=false)
	@RequestMapping("/batchimp")
	@ResponseBody
	public WebDataModel batchImp(){
		String projectId= request.getParameter("projectId");
		try {
			return proSkillDisclosureService.batchInitProData(projectId,SkillType.QM);
		} catch (SaveException e) {
			e.printStackTrace();
			setErrorMesg(e.getMessage());
		}
		return ajaxModel();
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

	@PermissionItemAnn(name="项目施工技术交底导入",number="onload",type= PermissionTypeEnum.FUNCTION)
	@RequestMapping("/batch/import")
	public ModelAndView forwardBatchImport(){
		Map params = new HashMap();
		toListUIParams(params);
		params.put("uiCtx",WebUtil.UUID_ReplaceID(params.get("uiCtx").toString()));
		return toPage("ec/skilldisclosure/proQmSkillBatchImport", params);
	}
}
