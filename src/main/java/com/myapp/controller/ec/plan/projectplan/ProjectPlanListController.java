package com.myapp.controller.ec.plan.projectplan;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.annotation.PermissionItemAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BasePageListController;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.enums.PermissionTypeEnum;
import com.myapp.core.exception.db.QueryException;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.model.PageModel;
import com.myapp.core.model.WebDataModel;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.util.DateUtil;
import com.myapp.service.ec.plan.ProjectTotalPlanService;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年6月7日 
 * @system:
 *
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.计划管理.项目计划分解",number="app.ec.plan.projectplan")
@Controller
@RequestMapping("ec/plan/projectplans")
public class ProjectPlanListController extends BasePageListController {
	
	@Resource
	public ProjectTotalPlanService projectTotalPlanService;
	public AbstractBaseService getService() {
		return projectTotalPlanService;
	}
	
	@PermissionItemAnn(name="月计划查看",number="monthlist",type=PermissionTypeEnum.PAGE)
	@RequestMapping("/monthlist")
	public ModelAndView monthList(){
		Map params = new HashMap();
		return toPage(getMonthListUrl(), params);
	}
	@PermissionItemAnn(name="周计划查看",number="weeklist",type=PermissionTypeEnum.PAGE)
	@RequestMapping("/weeklist")
	public ModelAndView weekList(){
		Map params = new HashMap();
		return toPage(getWeekListUrl(), params);
	}
	
	public String queryHql(){
		StringBuffer hql = new StringBuffer();
		hql.append(" select proStructure.displayName as proStructureName");
		hql.append(" ,proSub.name as proSubName,proSubItem.name as proSubItemName");
		hql.append(" ,a.planBegDate as bd,a.planEndDate as ed,a.planDays as days");
		hql.append(" ,a.content as content,a.proQty as proQty,a.dutyers as dutyers");
		hql.append(" ,proWbs.displayName as proWbsName,a.remark as remark");
		hql.append(" from ProjectTotalPlanItemInfo as a");
		hql.append(" inner join a.parent as parent");
		hql.append(" left outer join parent.project as project");
		hql.append(" left outer join a.proStructure as proStructure");
		hql.append(" left outer join a.projectWbs as proWbs");
		
		hql.append(" left outer join a.proSub as proSub");
		hql.append(" left outer join a.proSubItem as proSubItem");
		
		hql.append(" where project.id=? and (a.planBegDate <=? ");
		hql.append(" and a.planEndDate >=?) ");
		//计划开始日期小于等于统计截止日期  且 计划截止日期大于等于统计开始日期 
		return hql.toString();
	}
	
	@RequestMapping(value="/month")
	@ResponseBody
	public WebDataModel toMonthList() {
		try {
			init();
			PageModel pm = projectTotalPlanService.toPageQuery(getCurPage(), getPageSize(), queryHql(), getQueryParams("month"));
			List datas = pm.getDatas();
			if(datas!=null&&datas.size()>0){
				ColumnModel dutyers = new ColumnModel("dutyers",DataTypeEnum.MUTILF7,"id,name");
				dutyers.setClaz(UserInfo.class);
				List<ColumnModel> cols = new ArrayList<ColumnModel>();
				cols.add(dutyers);
				packageListDataColumns(datas,cols);
				pm.setDatas(datas);
			}
			data = pm;
		} catch (Exception e) {
			e.printStackTrace();
			setExceptionMesg(e.getMessage());
		}
		return ajaxModel();
	}
	
	@RequestMapping(value="/week")
	@ResponseBody
	public WebDataModel toWeekList() {
		try {
			init();
			PageModel pm = projectTotalPlanService.toPageQuery(getCurPage(), getPageSize(), queryHql(), getQueryParams("week"));
			List datas = pm.getDatas();
			if(datas!=null&&datas.size()>0){
				ColumnModel dutyers = new ColumnModel("dutyers",DataTypeEnum.MUTILF7,"id,name");
				dutyers.setClaz(UserInfo.class);
				List<ColumnModel> cols = new ArrayList<ColumnModel>();
				cols.add(dutyers);
				packageListDataColumns(datas,cols);
				pm.setDatas(datas);
			}
			data = pm;
		} catch (Exception e) {
			e.printStackTrace();
			setExceptionMesg(e.getMessage());
		}
		return ajaxModel();
	}
	
	public Object[] getQueryParams(String view){
		String orgId = "xyz";
		Date bd = new Date();
		Date ed = new Date();
		String serach = request.getParameter("search");
		if(!BaseUtil.isEmpty(serach)){
			Map searchMap = JSONObject.parseObject(serach, new HashMap().getClass());
			Object objOrgId = searchMap.get("treeId");
			if(objOrgId!=null) orgId = objOrgId.toString();
			if("month".equals(view)){
				Object objCurPeriod = searchMap.get("curPeriod");
				if(objCurPeriod!=null){
					bd = DateUtil.parseDate(objCurPeriod.toString()+"01 00:00:00", "yyyyMMdd HH:mm:ss");
					ed = DateUtil.parseDate(DateUtil.formatDateByFormat(DateUtil.addDay(DateUtil.addMonth(bd, 1),-1),"yyyyMMdd")+" 23:59:59","yyyyMMdd HH:mm:ss");
				}
			}else if("week".equals(view)){
				Object bdObj = searchMap.get("begData");
				Object edObj = searchMap.get("endData");
				if(bdObj!=null){
					bd = DateUtil.parseDate(bdObj.toString()+" 00:00:00", "yyyyMMdd HH:mm:ss");
				}
				if(edObj!=null){
					ed = DateUtil.parseDate(edObj.toString()+" 23:59:59", "yyyyMMdd HH:mm:ss");
				}
			}
			
		}
		List params = new ArrayList();
		params.add(orgId);
		params.add(ed);
		params.add(bd);
		return params.toArray();
	}
	
	public String getMonthListUrl() {
		return "ec/plan/projectplan/projectMonthPlanList";
	}
	
	public String getWeekListUrl() {
		return "ec/plan/projectplan/projectWeekPlanList";
	}
	
	@RequestMapping("/list")
	public ModelAndView list(){
		Map params = new HashMap();
		return toPage(getListUrl(), params);
	}
	
	public String getListUrl() {
		return "ec/plan/projectplan/projectPlanList";
	}
	@RequestMapping(value="/planRpt")
	@ResponseBody
	public WebDataModel getPlanCompareRpt(){
		String projectId = request.getParameter("projectId");
		init();
		if(BaseUtil.isEmpty(projectId)){
			setErrorMesg("工程项目id为空，不能执行此查询功能!");
		}else{
			try {
				data = projectTotalPlanService.getPlanFinishCompareRpt(projectId);
			} catch (QueryException e) {
				e.printStackTrace();
				setExceptionMesg(e.getMessage());
			}
		}
		return ajaxModel();
	}
}
