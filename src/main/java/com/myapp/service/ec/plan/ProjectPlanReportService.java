package com.myapp.service.ec.plan;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.myapp.core.exception.db.SaveException;
import com.myapp.core.model.MyWebContext;
import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.core.service.base.BaseService;
import com.myapp.core.util.BaseUtil;
import com.myapp.entity.ec.plan.ProjectPlanReportInfo;
import com.myapp.entity.ec.plan.ProjectPlanReportItemInfo;
import com.myapp.entity.ec.plan.ProjectTotalPlanItemInfo;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年07月30日 
 * @system:
 *  工程项目计划汇报
 *-----------MySong---------------
 */
@Service("projectPlanReportService")
public class ProjectPlanReportService extends BaseInterfaceService<ProjectPlanReportInfo> {
	
	public Object auditEntity(Object entity, MyWebContext webCtx)
			throws SaveException {
		if(entity!=null&&entity instanceof ProjectPlanReportInfo){
			ProjectPlanReportInfo plrInfo = (ProjectPlanReportInfo) entity;
			Set<ProjectPlanReportItemInfo> items = plrInfo.getPlanReportItems();
			if(items!=null&&items.size()>0){
				BaseService bsService =	getBaseService().newServicInstance(ProjectTotalPlanItemInfo.class);
				for(ProjectPlanReportItemInfo itemInfo:items){
					Date bd = itemInfo.getBegDate();
					Date ed = itemInfo.getEndDate();
					String pIid = itemInfo.getPlanItemId();
					if(!BaseUtil.isEmpty(pIid)&&(bd!=null||ed!=null)){
						ProjectTotalPlanItemInfo ptiInfo = (ProjectTotalPlanItemInfo) bsService.getEntity(pIid);
						if(ptiInfo.getRealBegDate()==null){
							ptiInfo.setRealBegDate(bd);
						}
						if(ed!=null){
							ptiInfo.setRealEndDate(ed);
						}
						ptiInfo.setProgress(itemInfo.getProgress());
						bsService.saveEntity(ptiInfo);
					}
				}
			}
		}
		return super.auditEntity(entity, webCtx);
	}
	
	public Object unauditEntity(Object entity, MyWebContext webCtx)
			throws SaveException {
		return super.unauditEntity(entity, webCtx);
	}
}
