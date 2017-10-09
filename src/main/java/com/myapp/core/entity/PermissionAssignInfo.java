package com.myapp.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.myapp.core.base.entity.CoreInfo;
import com.myapp.core.enums.PermissionTypeEnum;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年8月20日 
 * @system:
 * 职责权限项
 *-----------MySong---------------
 */
@Entity
@Table(name="t_pm_permissionAssign")
public class PermissionAssignInfo extends CoreInfo {
	
	private String targetId;//目标对象id
	private PermissionInfo permission;//权限对象
	private String targetType;//目标对象的bostype
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "fpermissionId")
	public PermissionInfo getPermission() {
		return permission;
	}
	public void setPermission(PermissionInfo permission) {
		this.permission = permission;
	}
	
	@Column(name="ftargetId",length=200)
	public String getTargetId() {
		return targetId;
	}
	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}
	
	@Column(name="ftargetType",length=50)
	public String getTargetType() {
		return targetType;
	}
	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}
	
}
