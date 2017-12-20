package com.myapp.controller.ec.basedata.skillitem;

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
import com.myapp.core.controller.BaseListController;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.exception.db.AddNewException;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.model.WebDataModel;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.util.WebUtil;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.entity.ec.basedata.SkillClassInfo;
import com.myapp.enums.ec.SkillType;
import com.myapp.service.ec.basedata.ProSkillDisclosureService;

@PermissionAnn(name="系统管理.现场管理.技术交底.项目安全技术交底",number="app.ec.skilldisclosure.prosmskill")
@Controller
@RequestMapping("ec/skilldisclosure/prosmskills")
public class ProSkillDisclosureSMListController extends BaseListController {
	
	@Resource
	public ProSkillDisclosureService proSkillDisclosureService;
	public AbstractBaseService getService() {
		return proSkillDisclosureService;
	}
	
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("number"));
		cols.add(new ColumnModel("attachs",DataTypeEnum.INT));
		cols.add(new ColumnModel("createDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("project",DataTypeEnum.F7,ProjectInfo.class));
		cols.add(new ColumnModel("skillClass",DataTypeEnum.F7,SkillClassInfo.class));
		cols.add(new ColumnModel("disclosurer",DataTypeEnum.F7,UserInfo.class));
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
				Object treeIdObj = treeMap.get("id");
				if(treeIdObj!=null){
					query.add(Restrictions.eq("project.id",WebUtil.UUID_ReplaceID(treeIdObj.toString())));
				}
			}
		}
	}
	
	public String getEditUrl() {
		return "ec/skilldisclosure/prosmskillEdit";
	}

	public String getListUrl() {
		return "ec/skilldisclosure/prosmskillList";
	}
	
	@AuthorAnn(doPermission=false)
	@RequestMapping("/batchimp")
	@ResponseBody
	public WebDataModel batchImp(){
		String projectId= request.getParameter("projectId");
		try {
			return proSkillDisclosureService.batchInitProData(projectId,SkillType.SM);
		} catch (AddNewException e) {
			e.printStackTrace();
			setErrorMesg(e.getMessage());
		}
		return ajaxModel();
	}


}
