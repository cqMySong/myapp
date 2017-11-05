package com.myapp.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.myapp.core.base.entity.CoreBaseTreeInfo;
import com.myapp.core.enums.OrgTypeEnum;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年5月1日 
 * @system:
 *
 *-----------MySong---------------
 */
@Entity
@Table(name="t_base_Org")
public class BaseOrgInfo extends CoreBaseTreeInfo<BaseOrgInfo> {
	private String shortCode;
	private String remark;
	private OrgTypeEnum orgType;

	@Column(name="fshortcode",length=50)
	public String getShortCode() {
		return shortCode;
	}
	public void setShortCode(String shortCode) {
		this.shortCode = shortCode;
	}
	@Column(name="fremark",length=1000)
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column(name="forgType",length=20)
	@Type(type="myEnum",parameters={@Parameter(name="enumClass",value="com.myapp.core.enums.OrgTypeEnum")})
	public OrgTypeEnum getOrgType() {
		return orgType;
	}
	public void setOrgType(OrgTypeEnum orgType) {
		this.orgType = orgType;
	}
	
	
}
