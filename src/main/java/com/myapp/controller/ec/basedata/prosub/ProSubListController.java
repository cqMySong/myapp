package com.myapp.controller.ec.basedata.prosub;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.myapp.core.model.WebDataModel;
import com.myapp.service.ec.basedata.ProStructureService;
import com.myapp.service.ec.basedata.ProjectService;
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
import com.myapp.core.service.base.BaseService;
import com.myapp.core.util.BaseUtil;
import com.myapp.entity.ec.basedata.ProStructureInfo;
import com.myapp.entity.ec.basedata.ProSubInfo;
import com.myapp.entity.ec.basedata.ProjectInfo;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年6月7日 
 * @system:
 *
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.基础数据.项目分部",number="app.ec.basedata.prosub")
@Controller
@RequestMapping("ec/basedata/prosubs")
public class ProSubListController extends BaseDataListController {
	
	@Resource
	public BaseService baseService;
	@Resource
	public ProStructureService proStructureService;
	@Resource
	public ProjectService projectService;
	public AbstractBaseService getService() {
		return baseService.newServicInstance(ProSubInfo.class);
	}

	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		ColumnModel proStruct = new ColumnModel("proStruct",DataTypeEnum.F7,"id,name");
		proStruct.setClaz(ProStructureInfo.class);
		cols.add(proStruct);
		ColumnModel project = new ColumnModel("project",DataTypeEnum.F7,"id,name");
		project.setClaz(ProjectInfo.class);
		cols.add(project);
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
		}
		query.add(se);
	}
	public String getEditUrl() {
		return "ec/basedata/prosub/proSubEdit";
	}

	public String getListUrl() {
		return "ec/basedata/prosub/proSubList";
	}

	//项目分部树
	@RequestMapping(value="/proSubTree")
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
				//添加项目分部
				sql = new StringBuffer();
				sql.append(" select a.fid as id,a.fnumber as number,a.fname as name,");
				sql.append("a.fproStructId as parentId,'' as longNumber,'proSub' as type");
				sql.append(" from t_ec_prosub as a");
				List proSubList = baseService.executeSQLQuery(sql.toString(),null);
				if(proSubList!=null&&proSubList.size()!=0){
					orgList.addAll(proSubList);
				}
				data = orgList;
			}
		}catch(Exception e){
			setErrorMesg(e.getMessage());
		}
		return ajaxModel();
	}
}
