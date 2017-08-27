package com.myapp.entity.ec.plan;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.myapp.core.base.entity.CoreBaseBillInfo;
import com.myapp.entity.ec.basedata.ProjectInfo;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年6月21日 
 * @system:
 * 项目工作计划汇报
 *-----------MySong---------------
 */
@Entity
@Table(name="t_ec_proWorkPlanReport")
public class ProjectPlanReportInfo extends CoreBaseBillInfo {
	private ProjectInfo project;//工程项目
	
	private ProjectTotalPlanInfo planInfo;//总进度计划
	private Date begDate;//开始日期
	private Date endDate;//截止日期
	
	private String remark;//备注
	private Set<ProjectPlanReportItemInfo> planReportItems;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "fprojectId")
	public ProjectInfo getProject() {
		return project;
	}
	public void setProject(ProjectInfo project) {
		this.project = project;
	}
	@Column(name="fremark",length=200)
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@OneToMany(cascade={CascadeType.ALL},mappedBy="parent")
	@OrderBy("seq ASC")
	public Set<ProjectPlanReportItemInfo> getPlanReportItems() {
		return planReportItems;
	}
	public void setPlanReportItems(Set<ProjectPlanReportItemInfo> planReportItems) {
		this.planReportItems = planReportItems;
	}
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "ftotalplanId")
	public ProjectTotalPlanInfo getPlanInfo() {
		return planInfo;
	}
	public void setPlanInfo(ProjectTotalPlanInfo planInfo) {
		this.planInfo = planInfo;
	}
	@Column(name="fbegDate")
	public Date getBegDate() {
		return begDate;
	}
	public void setBegDate(Date begDate) {
		this.begDate = begDate;
	}
	@Column(name="fendDate")
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
}
