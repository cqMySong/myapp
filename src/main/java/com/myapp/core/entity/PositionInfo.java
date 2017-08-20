package com.myapp.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.myapp.core.base.entity.CoreBaseInfo;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年08月16日 
 * @system: 岗位表
 *-----------MySong---------------
 */
@Entity
@Table(name="t_pm_position")
public class PositionInfo extends CoreBaseInfo {

	private BaseOrgInfo org;//所属组织
	private String remark;
	
	@Column(name="fremark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "forgId")
	public BaseOrgInfo getOrg() {
		return org;
	}

	public void setOrg(BaseOrgInfo org) {
		this.org = org;
	}
	
	
	
}
