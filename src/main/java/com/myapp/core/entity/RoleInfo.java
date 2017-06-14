package com.myapp.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.myapp.core.base.entity.CoreBaseInfo;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年6月6日 
 * @system:
 *角色表
 *-----------MySong---------------
 */
@Entity
@Table(name="t_pm_Role")
public class RoleInfo extends CoreBaseInfo {

	private String remark;
	
	@Column(name="fremark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
