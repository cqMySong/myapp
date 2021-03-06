package com.myapp.controller.ec.basedata.prostructure;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.myapp.core.annotation.AuthorAnn;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseDataListController;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.model.WebDataModel;
import com.myapp.core.util.BaseUtil;
import com.myapp.entity.ec.basedata.ProSubItemInfo;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.service.ec.basedata.ProStructureService;
import com.myapp.service.ec.basedata.ProjectService;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年6月7日 
 * @system:
 *
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.基础数据.单位(子单位)工程",number="app.ec.basedata.prostructure")
@Controller
@RequestMapping("ec/basedata/prostructures")
public class ProStructureListController extends BaseDataListController {
	
	@Resource
	public ProStructureService proStructureService;
	@Resource
	public ProjectService projectService;
	
	public AbstractBaseService getService() {
		return proStructureService;
	}

	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		ColumnModel parent = new ColumnModel("parent",DataTypeEnum.F7,"id,name,number");
		parent.setClaz(ProSubItemInfo.class);
		cols.add(parent);
		ColumnModel project = new ColumnModel("project",DataTypeEnum.F7,"id,name");
		project.setClaz(ProjectInfo.class);
		cols.add(project);
		return cols;
	}
	
	public void executeQueryParams(Criteria query) {
		super.executeQueryParams(query);
		String serach = request.getParameter("search");
		String projectId = "xyz";
		if(!BaseUtil.isEmpty(serach)){
			Map searchMap = JSONObject.parseObject(serach, new HashMap().getClass());
			Object objTree = searchMap.get("tree");
			if(objTree!=null){
				Map treeMap = JSONObject.parseObject(objTree.toString(), new HashMap().getClass());
				Object idObj = treeMap.get("id");
				Object type = treeMap.get("type");
				if(type!=null&&idObj!=null){
					if("project".equals(type.toString())){
						projectId = idObj.toString();
					}
				}
			}
		}
		query.add(Restrictions.eq("project.id",projectId));
	}
	
	//项目单位工程结构树
	@AuthorAnn(doLongin=true,doPermission=false)
	@RequestMapping(value="/proStructureTree")
	@ResponseBody
	public WebDataModel treeData() {
		try{
			Map params = new HashMap();
			List orgList = projectService.getProjectTreeData(params);//项目组织树
			if(orgList!=null&&orgList.size()>0){
				//添加项目单位工程
				StringBuffer sql = new StringBuffer();
				sql.append(" select a.fid as id,a.fnumber as number,a.fname as name,case when a.fprentid is null then a.fprojectId else a.fprentid end as parentId,a.flongnumber as longNumber,'proStructure' as type");
				sql.append(" from t_ec_proStructure as a");
				sql.append(" left join t_ec_project as b on b.fid = a.fprojectId");
				sql.append(" order by a.flongnumber asc");
				List orgStruct = projectService.executeSQLQuery(sql.toString(), null);
				if(orgStruct!=null&&orgStruct.size()>0){
					orgList.addAll(orgStruct);
				}
				data = orgList;
			}
		}catch(Exception e){
			setErrorMesg(e.getMessage());
		}
		return ajaxModel();
	}
	
	
	public String getEditUrl() {
		return "ec/basedata/prostructure/proStructureEdit";
	}

	public String getListUrl() {
		return "ec/basedata/prostructure/proStructureList";
	}

}
