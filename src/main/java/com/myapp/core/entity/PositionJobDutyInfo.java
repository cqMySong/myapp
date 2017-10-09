package com.myapp.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.myapp.core.base.entity.CoreBaseEntryInfo;
import com.myapp.core.base.entity.CoreInfo;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年09月06日 
 * @system: 岗位职责明细表
 *-----------MySong---------------
 */
@Entity
@Table(name="t_pm_positionJobDuty")
public class PositionJobDutyInfo extends CoreBaseEntryInfo<PositionInfo> {

	private JobDutyInfo jobDuty;
	private String remark;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "fjobDutyId")
	public JobDutyInfo getJobDuty() {
		return jobDuty;
		
		
	}
	public void setJobDuty(JobDutyInfo jobDuty) {
		this.jobDuty = jobDuty;
	}
	
	@Column(name="fremark")
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
