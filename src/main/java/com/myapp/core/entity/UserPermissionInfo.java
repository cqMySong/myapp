package com.myapp.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.myapp.core.base.entity.CoreInfo;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年6月7日 
 * @system:
 *
 *-----------MySong---------------
 */
@Entity
@Table(name="t_pm_UserPermission")
public class UserPermissionInfo extends CoreInfo {
	private UserInfo user;
	private PermissionInfo permission;
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
	@JoinColumn(name = "fpermission")
	public PermissionInfo getPermission() {
		return permission;
	}
	public void setPermission(PermissionInfo permission) {
		this.permission = permission;
	}
	@Column(name="fremark",length=100)
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
