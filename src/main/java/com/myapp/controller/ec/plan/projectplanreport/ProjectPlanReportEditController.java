package com.myapp.controller.ec.plan.projectplanreport;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.myapp.controller.ec.plan.util.PlanQueryUtil;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseBillEditController;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.BillState;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.util.DateUtil;
import com.myapp.core.util.WebUtil;
import com.myapp.entity.ec.basedata.ProStructureInfo;
import com.myapp.entity.ec.basedata.ProSubInfo;
import com.myapp.entity.ec.basedata.ProSubItemInfo;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.entity.ec.basedata.ProjectWbsInfo;
import com.myapp.entity.ec.plan.ProjectPlanReportInfo;
import com.myapp.entity.ec.plan.ProjectPlanReportItemInfo;
import com.myapp.entity.ec.plan.ProjectTotalPlanInfo;
import com.myapp.enums.ec.CauseType;
import com.myapp.enums.ec.YesNoEnum;
import com.myapp.service.ec.plan.ProjectPlanReportService;
import com.myapp.service.ec.plan.ProjectTotalPlanService;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年5月20日 
 * @system: 
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.计划管理.项目计划汇报",number="app.ec.plan.projectplanreport")
@Controller
@RequestMapping("ec/plan/projectplanreport")
public class ProjectPlanReportEditController extends BaseBillEditController{
	@Resource
	public ProjectPlanReportService projectPlanReportService;
	public AbstractBaseService getService() {
		return projectPlanReportService;
	}
	@Resource
	public ProjectTotalPlanService projectTotalPlanService;

	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("number"));
		cols.add(new ColumnModel("remark"));
		cols.add(new ColumnModel("bizDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("begDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("endDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("billState",DataTypeEnum.ENUM,BillState.class));
		cols.add(new ColumnModel("createDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("lastUpdateDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("auditDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("project",DataTypeEnum.F7,ProjectInfo.class));
		cols.add(new ColumnModel("planInfo",DataTypeEnum.F7,ProjectTotalPlanInfo.class));
		cols.add(new ColumnModel("createUser",DataTypeEnum.F7,UserInfo.class));
		cols.add(new ColumnModel("lastUpdateUser",DataTypeEnum.F7,UserInfo.class));
		cols.add(new ColumnModel("auditor",DataTypeEnum.F7,UserInfo.class));
		
		ColumnModel planItems = new ColumnModel("planReportItems",DataTypeEnum.ENTRY,ProjectPlanReportItemInfo.class);
		planItems.getCols().add(new ColumnModel("id",DataTypeEnum.PK));
		
		ColumnModel psCol = new ColumnModel("proStructure",DataTypeEnum.F7,ProStructureInfo.class);
		psCol.setFormat("id,displayName");
		planItems.getCols().add(psCol);
		
		ColumnModel prowbs = new ColumnModel("projectWbs",DataTypeEnum.F7,ProjectWbsInfo.class);
		prowbs.setFormat("id,name,displayName");
		planItems.getCols().add(prowbs);
		
		ColumnModel proSubItem = new ColumnModel("proSubItem",DataTypeEnum.F7,"id,name");
		proSubItem.setClaz(ProSubItemInfo.class);
		planItems.getCols().add(proSubItem);
		
		ColumnModel proSub = new ColumnModel("proSub",DataTypeEnum.F7,"id,name");
		proSub.setClaz(ProSubInfo.class);
		planItems.getCols().add(proSub);
		
		ColumnModel filler = new ColumnModel("filler",DataTypeEnum.MUTILF7,"id,name");
		filler.setClaz(UserInfo.class);
		planItems.getCols().add(filler);
		
		planItems.getCols().add(new ColumnModel("begDate",DataTypeEnum.DATE));
		planItems.getCols().add(new ColumnModel("endDate",DataTypeEnum.DATE));
		planItems.getCols().add(new ColumnModel("planContent"));
		planItems.getCols().add(new ColumnModel("progress",DataTypeEnum.NUMBER));
		planItems.getCols().add(new ColumnModel("planItemId"));
		planItems.getCols().add(new ColumnModel("delayNature"));
		planItems.getCols().add(new ColumnModel("delayCause",DataTypeEnum.ENUM,CauseType.class));
		planItems.getCols().add(new ColumnModel("meetContent"));
		planItems.getCols().add(new ColumnModel("meetTodo"));
		planItems.getCols().add(new ColumnModel("toDo"));
		planItems.getCols().add(new ColumnModel("doDelay",DataTypeEnum.ENUM,YesNoEnum.class));
		planItems.getCols().add(new ColumnModel("doWorkPay",DataTypeEnum.ENUM,YesNoEnum.class));
		cols.add(planItems);
		return cols;
	}
	
	public Object createNewData() {
		ProjectPlanReportInfo proTotalPlanInfo = new ProjectPlanReportInfo();
		//{tree={"id":"NiEbtFVyTYGc3jn17axYdpn32P4%3D","longNumber":"01","name":"江北项目xx街道施工项目","number":"01","type":"project"}}
		Map uiCtx = getUiCtx();
		String name = "";
		Date curMonDay = DateUtil.getFirstDayOfWeek(new Date());
		Date begDate = DateUtil.addDay(curMonDay, -7);
		Date endDate = DateUtil.addDay(curMonDay, -1);
		if(uiCtx!=null&&uiCtx.containsKey("tree")){
			JSONObject treeMap = (JSONObject) uiCtx.get("tree");
			if(treeMap.containsKey("type")&&"project".equals((String)treeMap.get("type"))){
				if(treeMap.containsKey("id")&&treeMap.containsKey("name")){
					String proId = WebUtil.UUID_ReplaceID((String) treeMap.get("id"));
					String proName = (String) treeMap.get("name");
					ProjectInfo pInfo = new ProjectInfo();
					
					pInfo.setId(proId);
					pInfo.setName(proName);
					proTotalPlanInfo.setProject(pInfo);
					name +=proName;
					String hql = "from ProjectTotalPlanInfo where project.id=? and billState=?";
					List params = new ArrayList();
					params.add("proId");
					params.add(BillState.AUDIT);
					ProjectTotalPlanInfo ptPlInfo = projectTotalPlanService.getEntity(hql,params.toArray());
					if(ptPlInfo!=null){
						proTotalPlanInfo.setPlanInfo(ptPlInfo);
						params = new ArrayList();
						Date bd = DateUtil.parseDate(DateUtil.formatDateByFormat(begDate, "yyyyMMdd")+" 00:00:00", "yyyyMMdd HH:mm:ss");
						Date ed = DateUtil.parseDate(DateUtil.formatDateByFormat(endDate, "yyyyMMdd")+" 23:59:59", "yyyyMMdd HH:mm:ss");
						params.add(ptPlInfo.getId());
						params.add(proId);
						params.add(ed);
						params.add(bd);
						params.add(bd);
						List datas = projectTotalPlanService.findByHQL(PlanQueryUtil.findProjectPlanReport(), params.toArray());
						if(datas!=null&&datas.size()>0){
							Set<ProjectPlanReportItemInfo> items = new HashSet<ProjectPlanReportItemInfo>();
							for(int i=0;i<datas.size();i++){
								Map item = (Map) datas.get(i);
								ProjectPlanReportItemInfo itemInfo = new ProjectPlanReportItemInfo();
								ProStructureInfo pstInfo = new ProStructureInfo();
								pstInfo.setId((String)item.get("proStructureId"));
								pstInfo.setDisplayName((String)item.get("proStructureName"));
								itemInfo.setProStructure(pstInfo);
								ProjectWbsInfo pwInfo = new ProjectWbsInfo();
								pwInfo.setId((String)item.get("proWbsId"));
								pwInfo.setDisplayName((String)item.get("proWbsDisplayName"));
								itemInfo.setProjectWbs(pwInfo);
								
								ProSubInfo psInfo = new ProSubInfo();
								psInfo.setId((String)item.get("proSubId"));
								psInfo.setName((String)item.get("proSubName"));
								itemInfo.setProSub(psInfo);
								ProSubItemInfo psiInfo = new ProSubItemInfo();
								psiInfo.setId((String)item.get("proSubItemId"));
								psiInfo.setName((String)item.get("proSubItemName"));
								itemInfo.setProSubItem(psiInfo);
								itemInfo.setParent(proTotalPlanInfo);
								itemInfo.setPlanContent((String)item.get("content"));
								itemInfo.setPlanItemId((String)item.get("planItemId"));
								itemInfo.setBegDate((Date)item.get("realBegDate"));
								itemInfo.setDelayCause(CauseType.BLANK);
								itemInfo.setDoDelay(YesNoEnum.BLANK);
								itemInfo.setDoWorkPay(YesNoEnum.BLANK);
								items.add(itemInfo);
							}
							proTotalPlanInfo.setPlanReportItems(items);
						}
					}
				}
			}
		}
		name +="施工日志汇报[";
		proTotalPlanInfo.setBegDate(begDate);
		proTotalPlanInfo.setEndDate(endDate);
		name +=DateUtil.formatDateByFormat(begDate,"yyyyMMdd")+"-"+DateUtil.formatDateByFormat(endDate,"yyyyMMdd")+"]";
		proTotalPlanInfo.setName(name);
		return proTotalPlanInfo;
	}

	public CoreBaseInfo getEntityInfo() {
		return new ProjectPlanReportInfo();
	}
}
