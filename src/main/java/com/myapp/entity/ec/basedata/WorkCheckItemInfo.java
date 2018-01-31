package com.myapp.entity.ec.basedata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.myapp.core.base.entity.CoreBaseDataInfo;
import com.myapp.enums.ec.WorkCheckGroup;
import com.myapp.enums.ec.WorkCheckType;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年10月23日 
 * @system: 安全员：施工现场日、周、月检表
 *-----------MySong---------------
 */
@Entity
@Table(name="t_ec_workcheckItem")
public class WorkCheckItemInfo extends CoreBaseDataInfo {
	private WorkCheckGroup workCheckGroup;//检验分组
	private WorkCheckType workCheckType;//检验分类
	private String checkRequire;//检查要求
	
	@Column(name="fworkchecktype",length=10)
	@Type(type="myEnum",parameters={@Parameter(name="enumClass",value="com.myapp.enums.ec.WorkCheckType")})
	public WorkCheckType getWorkCheckType() {
		return workCheckType;
	}
	public void setWorkCheckType(WorkCheckType workCheckType) {
		this.workCheckType = workCheckType;
	}
	
	@Column(name="fcheckRequire",length=200)
	public String getCheckRequire() {
		return checkRequire;
	}
	public void setCheckRequire(String checkRequire) {
		this.checkRequire = checkRequire;
	}
	@Column(name="fworkcheckgroup",length=10)
	@Type(type="myEnum",parameters={@Parameter(name="enumClass",value="com.myapp.enums.ec.WorkCheckGroup")})
	public WorkCheckGroup getWorkCheckGroup() {
		return workCheckGroup;
	}
	public void setWorkCheckGroup(WorkCheckGroup workCheckGroup) {
		this.workCheckGroup = workCheckGroup;
	}
	
	
}
