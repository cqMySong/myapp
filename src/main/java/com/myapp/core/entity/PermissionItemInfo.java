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
@Table(name="t_pm_permissionItem")
public class PermissionItemInfo extends CoreInfo {
	private String sourceId;//原始对象id
	private PermissionInfo permission;//权限
	
	@Column(name="fsourceId",length=200)
	public String getSourceId() {
		return sourceId;
	}
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "fpermissionId")
	public PermissionInfo getPermission() {
		return permission;
	}
	public void setPermission(PermissionInfo permission) {
		this.permission = permission;
	}
	
}
