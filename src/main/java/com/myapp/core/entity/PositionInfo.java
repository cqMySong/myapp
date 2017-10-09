package com.myapp.core.entity;

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

import com.myapp.core.base.entity.CoreBaseDataInfo;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.entity.ec.plan.ProjectTotalPlanItemInfo;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年08月16日 
 * @system: 岗位表
 *-----------MySong---------------
 */
@Entity
@Table(name="t_pm_position")
public class PositionInfo extends CoreBaseDataInfo {

	private BaseOrgInfo org;//所属组织
	private Set<PositionJobDutyInfo> jobDutyItems;//岗位职责
	private PositionInfo parent;//上级岗位
	private Boolean respible;//负责人岗位
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "forgId")
	public BaseOrgInfo getOrg() {
		return org;
	}

	public void setOrg(BaseOrgInfo org) {
		this.org = org;
	}
	
	@OneToMany(cascade={CascadeType.ALL},mappedBy="parent")
	@OrderBy("seq ASC")
	public Set<PositionJobDutyInfo> getJobDutyItems() {
		return jobDutyItems;
	}

	public void setJobDutyItems(Set<PositionJobDutyInfo> jobDutyItems) {
		this.jobDutyItems = jobDutyItems;
	}
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "fparentId")
	public PositionInfo getParent() {
		return parent;
	}

	public void setParent(PositionInfo parent) {
		this.parent = parent;
	}
	@Column(name="frespible")
	public Boolean getRespible() {
		return respible;
	}

	public void setRespible(Boolean respible) {
		this.respible = respible;
	}
	
	
	
}
