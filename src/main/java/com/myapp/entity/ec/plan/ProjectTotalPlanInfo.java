package com.myapp.entity.ec.plan;

import java.util.HashSet;
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
 * 项目总体计划
 *-----------MySong---------------
 */
@Entity
@Table(name="t_ec_projectTotalPlan")
public class ProjectTotalPlanInfo extends CoreBaseBillInfo {
	private ProjectInfo project;//工程项目
	private String remark;//备注
	private Set<ProjectTotalPlanItemInfo> planItems;
	
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
	public Set<ProjectTotalPlanItemInfo> getPlanItems() {
		if(planItems==null) planItems = new HashSet<ProjectTotalPlanItemInfo>();
		return planItems;
	}
	public void setPlanItems(Set<ProjectTotalPlanItemInfo> planItems) {
		this.planItems = planItems;
	}
	
	
	public static void main(String[] args){
		ProjectTotalPlanInfo p = new ProjectTotalPlanInfo();
		Object obj = p.get("planItems");
		Set ids =  new HashSet();
		ids.add("1");
		ids.add("3");
		p.put("remarkex", ids);
		System.out.println(p.get("remarkex").toString());
		
	}
	
}
