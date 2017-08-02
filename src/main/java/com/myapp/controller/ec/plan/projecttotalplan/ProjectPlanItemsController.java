package com.myapp.controller.ec.plan.projecttotalplan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.base.dao.MyResultTransFormer;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BasePageListController;
import com.myapp.core.enums.BillState;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.exception.db.QueryException;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.model.PageModel;
import com.myapp.core.model.WebDataModel;
import com.myapp.core.service.base.BaseService;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.util.WebUtil;
import com.myapp.entity.ec.basedata.ProStructureInfo;
import com.myapp.entity.ec.basedata.ProSubInfo;
import com.myapp.entity.ec.basedata.ProSubItemInfo;
import com.myapp.entity.ec.plan.ProjectTotalPlanItemInfo;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年8月2日 
 * @system:
 * 项目进度计划分录查询
 *-----------MySong---------------
 */
@Controller
@RequestMapping("ec/plan/projectplanitems")
public class ProjectPlanItemsController extends BasePageListController {
	@Resource
	public BaseService baseService;
	public AbstractBaseService getService() {
		return baseService.newServicInstance(ProjectTotalPlanItemInfo.class);
	}
	
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		ColumnModel proStructure = new ColumnModel("proStructure",DataTypeEnum.F7,"id,name,displayName");
		proStructure.setClaz(ProStructureInfo.class);
		cols.add(proStructure);
		
		ColumnModel proSub = new ColumnModel("proSub",DataTypeEnum.F7,"id,name");
		proSub.setClaz(ProSubInfo.class);
		cols.add(proSub);
		
		ColumnModel proSubItem = new ColumnModel("proSubItem",DataTypeEnum.F7,"id,name");
		proSubItem.setClaz(ProSubItemInfo.class);
		cols.add(proSubItem);
		cols.add(new ColumnModel("content"));
		cols.add(new ColumnModel("planBegDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("planEndDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("planDays"));
		cols.add(new ColumnModel("proQty"));
		return cols;
	}
	
	public String getProjectId(){
		String projectId = request.getParameter("projectId");
		if(!BaseUtil.isEmpty(projectId)){
			projectId = WebUtil.UUID_ReplaceID(projectId);
		}
		return projectId;
	}
	
	
	@RequestMapping("/show")
	public ModelAndView show(){
		init();
		Map params = new HashMap();
		params.put("projectId", getProjectId());
		return toPage("ec/plan/projecttotalplan/planItems",params);
	}
	
	public List<ColumnModel> getPackageDataCol(){
		List<ColumnModel> cols = getDataBinding();
		List<ColumnModel> toDoCols = new ArrayList<ColumnModel>(); 
		for(ColumnModel cm:cols){
			DataTypeEnum dte = cm.getDataType();
			if(dte.equals(DataTypeEnum.F7)&&cm.getClaz()!=null){
				toDoCols.add(cm);
			}
		}
		return toDoCols;
	}
	
	public void packageDatas(List datas) throws QueryException{
		if(datas==null||datas.size()<=0) return;
		List<ColumnModel> cms = getPackageDataCol();
		if(cms!=null&&cms.size()>0){
			packageListDataColumns(datas, cms);
		}
	}
	@RequestMapping(value="/datas")
	@ResponseBody
	public WebDataModel toList() {
		init();
		try {
			Criteria query = initQueryCriteria();
			query.createAlias("parent", "parent", JoinType.INNER_JOIN);
			query.createAlias("parent.project", "parentProject", JoinType.INNER_JOIN);
			query.add(Restrictions.eq("parentProject.id", getProjectId()));
			query.add(Restrictions.eq("parent.billState", BillState.AUDIT.getValue()));
			query.addOrder(Order.asc("seq"));
			PageModel pm = getService().toPageQuery(query, getProjectionList(), getCurPage(), getPageSize());
			List datas = pm.getDatas();
			if(datas!=null&&datas.size()>0){
				packageDatas(datas);
				pm.setDatas(datas);
			}
			this.data = pm;
			
			
		} catch (Exception e) {
			e.printStackTrace();
			setExceptionMesg(e.getMessage());
		}
		return ajaxModel();
	}
	
	public void packageRowCellData(Map row, ColumnModel cm)
			throws QueryException {
		super.packageRowCellData(row, cm);
		if(row!=null&&row.size()>0&&cm!=null){
			DataTypeEnum dte = cm.getDataType();
			String key = cm.getName();
			Object objval = row.get(key);
			if(!BaseUtil.isEmpty(key)&&!BaseUtil.isEmpty(objval)){
				if(DataTypeEnum.F7.equals(dte)&&cm.getClaz()!=null){
					
//					row.put(key, query.list());
				}
			}
		}
	}
	
	
	public void executeQueryParams(Criteria query) {
		
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
}
