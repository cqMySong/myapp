package com.myapp.controller.ec.plan.util;
/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年8月23日 
 * @system:
 * 进度计划管理中的一些常用的查询query
 *-----------MySong---------------
 */
public class PlanQueryUtil {

	
	public static String findProjectPlanReport(){
		StringBuffer hql = new StringBuffer();
		hql.append(" select proStructure.displayName as proStructureName,proStructure.id as proStructureId");
		hql.append(" ,proSub.id as proSubId,proSub.name as proSubName,proSubItem.id as proSubItemId,proSubItem.name as proSubItemName");
		hql.append(" ,a.content as content,a.realBegDate as realBegDate,a.id as planItemId");
		hql.append(" from ProjectTotalPlanItemInfo as a");
		hql.append(" inner join a.parent as parent");
		hql.append(" left outer join parent.project as project");
		hql.append(" left outer join a.proStructure as proStructure");
		hql.append(" left outer join a.proSub as proSub");
		hql.append(" left outer join a.proSubItem as proSubItem");
		hql.append(" where parent.id=? and project.id=? and a.realEndDate is null ");
		hql.append(" and ((a.planBegDate <=? and a.planEndDate >=?) or (a.planBegDate<=?)) ");
		//计划开始日期小于等于统计截止日期  且 计划截止日期大于等于统计开始日期 
		return hql.toString();
	}
}
