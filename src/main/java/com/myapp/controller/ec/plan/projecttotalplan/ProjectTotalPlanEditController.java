package com.myapp.controller.ec.plan.projecttotalplan;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.myapp.core.annotation.AuthorAnn;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseBillEditController;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.BillState;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.model.WebDataModel;
import com.myapp.core.util.BaseUtil;
import com.myapp.entity.ec.basedata.ProStructureInfo;
import com.myapp.entity.ec.basedata.ProSubInfo;
import com.myapp.entity.ec.basedata.ProSubItemInfo;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.entity.ec.basedata.ProjectWbsInfo;
import com.myapp.entity.ec.plan.ProjectTotalPlanInfo;
import com.myapp.entity.ec.plan.ProjectTotalPlanItemInfo;
import com.myapp.service.ec.plan.ProjectTotalPlanService;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年5月20日 
 * @system: 
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.计划管理.项目总体计划",number="app.ec.plan.projecttotalplan")
@Controller
@RequestMapping("ec/plan/projecttotalplan")
public class ProjectTotalPlanEditController extends BaseBillEditController{
	@Resource
	public ProjectTotalPlanService projectTotalPlanService;
	public AbstractBaseService getService() {
		return projectTotalPlanService;
	}

	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("number"));
		cols.add(new ColumnModel("remark"));
		cols.add(new ColumnModel("bizDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("billState",DataTypeEnum.ENUM,BillState.class));
		cols.add(new ColumnModel("createDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("lastUpdateDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("auditDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("project",DataTypeEnum.F7,ProjectInfo.class));
		cols.add(new ColumnModel("createUser",DataTypeEnum.F7,UserInfo.class));
		cols.add(new ColumnModel("lastUpdateUser",DataTypeEnum.F7,UserInfo.class));
		cols.add(new ColumnModel("auditor",DataTypeEnum.F7,UserInfo.class));
		
		ColumnModel planItems = new ColumnModel("planItems",DataTypeEnum.ENTRY,ProjectTotalPlanItemInfo.class);
		planItems.getCols().add(new ColumnModel("id",DataTypeEnum.PK));
		
		ColumnModel psCol = new ColumnModel("proStructure",DataTypeEnum.F7,ProStructureInfo.class);
		psCol.setFormat("id,name,displayName");
		planItems.getCols().add(psCol);
		
		ColumnModel prowbs = new ColumnModel("projectWbs",DataTypeEnum.F7,ProjectWbsInfo.class);
		prowbs.setFormat("id,name,displayName");
		planItems.getCols().add(prowbs);
		
		//作废
		ColumnModel proSubItem = new ColumnModel("proSubItem",DataTypeEnum.F7,"id,name");
		proSubItem.setClaz(ProSubItemInfo.class);
		planItems.getCols().add(proSubItem);
		ColumnModel proSub = new ColumnModel("proSub",DataTypeEnum.F7,"id,name");
		proSub.setClaz(ProSubInfo.class);
		planItems.getCols().add(proSub);
		
		ColumnModel dutyers = new ColumnModel("dutyers",DataTypeEnum.MUTILF7,"id,name");
		dutyers.setClaz(UserInfo.class);
		planItems.getCols().add(dutyers);
		
		planItems.getCols().add(new ColumnModel("planBegDate",DataTypeEnum.DATE));
		planItems.getCols().add(new ColumnModel("planEndDate",DataTypeEnum.DATE));
		planItems.getCols().add(new ColumnModel("planDays",DataTypeEnum.INT));
		planItems.getCols().add(new ColumnModel("realBegDate",DataTypeEnum.DATE));
		planItems.getCols().add(new ColumnModel("realEndDate",DataTypeEnum.DATE));
		planItems.getCols().add(new ColumnModel("realDays",DataTypeEnum.INT));
		planItems.getCols().add(new ColumnModel("progress",DataTypeEnum.NUMBER));
		planItems.getCols().add(new ColumnModel("content"));
		planItems.getCols().add(new ColumnModel("proQty"));
		planItems.getCols().add(new ColumnModel("remark",DataTypeEnum.STRING));
		cols.add(planItems);
		return cols;
	}
	
	protected boolean verifyInput(Object editData) {
		boolean isOk = super.verifyInput(editData);
		if(isOk){
			if(editData!=null&&editData instanceof ProjectTotalPlanInfo){
				ProjectTotalPlanInfo ptInfo = (ProjectTotalPlanInfo) editData;
				ProjectInfo project = ptInfo.getProject();
				if(project==null){
					isOk = false;
					setErrorMesg("项目总进度计划中工程项目不能为空!");
				}else{
					String hql = "from ProjectTotalPlanInfo where project.id=?";
					List params = new ArrayList();
					params.add(project.getId());
					if(!BaseUtil.isEmpty(ptInfo.getId())){
						hql+=" and id!=?";
						params.add(ptInfo.getId());
					}
					try{
						if(projectTotalPlanService.isExist(hql, params.toArray())){
							isOk = false;
							setErrorMesg(project.getName()+"已经存在了项目的总进度计划，不允许重复出现!");
						}
					}catch(Exception e){
						isOk = false;
						e.printStackTrace();
						setExceptionMesg(e.getMessage());
					}
				}
			}
		}
		return isOk;
	}
	
	protected boolean verifyInputSubmit(Object editData) {
		boolean isOk = super.verifyInputSubmit(editData);
		if(isOk){
			if(editData!=null&&editData instanceof ProjectTotalPlanInfo){
				ProjectTotalPlanInfo ptInfo = (ProjectTotalPlanInfo) editData;
				Set<ProjectTotalPlanItemInfo> items = ptInfo.getPlanItems();
				if(items==null||items.size()<=0) {
					isOk = false;
					setErrorMesg("项目总进度计划明细不能为空!");
				}
			}
		}
		return isOk;
	}
	
	@AuthorAnn(doPermission=false)
	@RequestMapping("/checkRemoveItem")
	@ResponseBody
	public WebDataModel checkRemoveItem(){
		return projectTotalPlanService.checkRemoveItemId(request.getParameter("itemId"));
	}
	
	
	public Object createNewData() {
		ProjectTotalPlanInfo proTotalPlanInfo = new ProjectTotalPlanInfo();
		return proTotalPlanInfo;
	}

	public CoreBaseInfo getEntityInfo() {
		return new ProjectTotalPlanInfo();
	}
}
