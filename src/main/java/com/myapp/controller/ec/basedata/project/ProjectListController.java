package com.myapp.controller.ec.basedata.project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.AuthorAnn;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseListController;
import com.myapp.core.entity.BaseOrgInfo;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.enums.OrgTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.model.MyWebContext;
import com.myapp.core.model.WebDataModel;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.util.EnumUtil;
import com.myapp.entity.ec.basedata.StructTypeInfo;
import com.myapp.enums.IndustryType;
import com.myapp.enums.ProjectState;
import com.myapp.enums.ec.ProWbsType;
import com.myapp.service.ec.basedata.ProjectService;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年5月27日 
 * @system:
 *
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.基础数据.工程项目",number="app.ec.basedata.project")
@Controller
@RequestMapping("ec/basedata/projects")
public class ProjectListController extends BaseListController {
	@Resource
	public ProjectService projectService;
	public AbstractBaseService getService() {
		return projectService;
	}
	
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("number"));
		cols.add(new ColumnModel("remark"));
		cols.add(new ColumnModel("address"));
		cols.add(new ColumnModel("scale"));
		cols.add(new ColumnModel("eavesHeight"));
		cols.add(new ColumnModel("floorHeight"));
		cols.add(new ColumnModel("area"));
		cols.add(new ColumnModel("proState",DataTypeEnum.ENUM,ProjectState.class));
		cols.add(new ColumnModel("industryType",DataTypeEnum.ENUM,IndustryType.class));
		cols.add(new ColumnModel("aseismicLevel"));
		ColumnModel structCols = new ColumnModel("structTypes",DataTypeEnum.MUTILF7,"name,number");
		structCols.setClaz(StructTypeInfo.class);
		cols.add(structCols);
		ColumnModel orgCol = new ColumnModel("org",DataTypeEnum.F7,"name");
		cols.add(orgCol);
		return cols;
	}
	public void executeQueryParams(Criteria query) {
		super.executeQueryParams(query);
		String serach = request.getParameter("search");
		if(!BaseUtil.isEmpty(serach)){
			Map searchMap = JSONObject.parseObject(serach, new HashMap().getClass());
			Object includeChildObj = searchMap.get("includeChild");
			boolean include = true;
			if(includeChildObj!=null&&includeChildObj instanceof Boolean){
				include = (Boolean)includeChildObj;
			}
			Object objTree = searchMap.get("tree");
			if(objTree!=null){
				Map treeMap = JSONObject.parseObject(objTree.toString(), new HashMap().getClass());
				Object lnObj = treeMap.get("longNumber");
				if(lnObj!=null){
					DetachedCriteria orgCriteria = DetachedCriteria.forClass(BaseOrgInfo.class, "torg");
					orgCriteria.add(Restrictions.like("longNumber",lnObj.toString(),include?MatchMode.START:MatchMode.EXACT));
					orgCriteria.add(Property.forName("torg.id").eqProperty("org.id"));
					orgCriteria.setProjection(Projections.property("torg.id"));
					query.add(Subqueries.exists(orgCriteria));
				}
			}
		}
		MyWebContext webCtx = getCurWebContext();
		if(webCtx!=null){
			BaseOrgInfo curOrg = webCtx.getCurOrg();
			if(curOrg!=null&&BaseUtil.isNotEmpty(curOrg.getId())){
				query.add(Restrictions.eq("org.id",curOrg.getId()));
			}
		}
	}
	
	@AuthorAnn(doPermission=false)
	@RequestMapping(value="/projectTree")
	@ResponseBody
	public WebDataModel treeData() {
		try{
			Map params = new HashMap();
			String orgType = request.getParameter("orgType");
			if(!BaseUtil.isEmpty(orgType)){
				orgType = "'"+orgType.replaceAll(",", "','")+"'";
				params.put("orgType", orgType);
			}
			boolean includeProOrg = false;
			String inPro = request.getParameter("includeProOrg");
			if(!BaseUtil.isEmpty(inPro)){
				includeProOrg = inPro.toLowerCase().equals("true")||inPro.equals("1");
			}
			params.put("includeProOrg", includeProOrg);
			data = projectService.getProjectTreeData(params,getCurWebContext());
		}catch(Exception e){
			setErrorMesg(e.getMessage());
		}
		return ajaxModel();
	}
	
	public Order getOrder() {
		return Order.asc("number");
	}
	public String getEditUrl() {
		return "ec/basedata/project/projectEdit";
	}
	public String getListUrl() {
		return "ec/basedata/project/projectList";
	}
	
	public String getHeadTitle() {
		return "工程项目";
	}
	
	public List<ExcelExportEntity> getExportHeader() {
		List<ExcelExportEntity> entitys = new ArrayList<ExcelExportEntity>();
		entitys.add(stringEntity("项目编码", "number"));
		entitys.add(stringEntity("项目名称", "name"));
		entitys.add(stringEntity("工程分类", "industryType"));
		entitys.add(stringEntity("项目状态", "proState"));
		entitys.add(stringEntity("所属组织", "org_name"));
		entitys.add(stringEntity("项目地址", "address"));
		entitys.add(stringEntity("项目规模", "scale"));
		entitys.add(stringEntity("建筑高度(m)", "eavesHeight"));
		entitys.add(stringEntity("层高(m)", "floorHeight"));
		entitys.add(stringEntity("结构类型", "structTypes"));
		entitys.add(stringEntity("占地面积", "area"));
		entitys.add(stringEntity("抗震等级", "aseismicLevel"));
		entitys.add(remarkEntity("备注", "remark"));
		return entitys;
	}
}
