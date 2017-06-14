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
 * @author mySong @date 2017年6月7日 
 * @system:
 *
 *-----------MySong---------------
 */
@Entity
@Table(name="t_pm_UserRole")
public class UserRoleInfo extends CoreBaseInfo {
	private UserInfo user;
	private RoleInfo role;
	private String remark;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "fuser")
	public UserInfo getUser() {
		return user;
	}
	public void setUser(UserInfo user) {
		this.user = user;
	}
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "frole")
	public RoleInfo getRole() {
		return role;
	}
	public void setRole(RoleInfo role) {
		this.role = role;
	}
	@Column(name="fremark",length=100)
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
