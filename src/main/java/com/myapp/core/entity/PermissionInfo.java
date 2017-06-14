package com.myapp.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.myapp.core.base.entity.CoreBaseTreeInfo;
import com.myapp.core.enums.PermissionTypeEnum;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年6月7日 
 * @system:
 *
 *-----------MySong---------------
 */
@Entity
@Table(name="t_pm_permission")
public class PermissionInfo extends CoreBaseTreeInfo<PermissionInfo> {
	private String remark;
	private String url;
	private PermissionTypeEnum type;
	private Boolean enabled;
	
	@Column(name="fremark",length=100)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column(name="furl",length=100)
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	@Column(name="ftype",length=20)
	@Type(type="myEnum",parameters={@Parameter(name="enumClass",value="com.myapp.core.enums.PermissionTypeEnum")})
	public PermissionTypeEnum getType() {
		return type;
	}

	public void setType(PermissionTypeEnum type) {
		this.type = type;
	}
	
	@Column(name="fenabled",length=5)
	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
}
