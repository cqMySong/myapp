package com.myapp.entity.ec.basedata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.myapp.core.annotation.MyEntityAnn;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.enums.ec.ResourceType;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2018年1月1日 
 * @system: 项目级资料目录（一级的形式）
 *
 *-----------MySong---------------
 */
@MyEntityAnn(name="项目级资料目录")
@Entity
@Table(name="t_ec_prorecitemdata")
public class ProResourceItemInfo extends CoreBaseInfo {
	private ResourceItemInfo resourceItem;
	private ResourceType resourceType;
	private ProjectInfo project;
	private String remark;
	
	@Column(name="ftype",length=20)
	@Type(type="myEnum",parameters={@Parameter(name="enumClass",value="com.myapp.enums.ec.ResourceType")})
	public ResourceType getResourceType() {
		return resourceType;
	}

	public void setResourceType(ResourceType resourceType) {
		this.resourceType = resourceType;
	}
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "frecItemId")
	public ResourceItemInfo getResourceItem() {
		return resourceItem;
	}

	public void setResourceItem(ResourceItemInfo resourceItem) {
		this.resourceItem = resourceItem;
	}
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "fprojectId")
	public ProjectInfo getProject() {
		return project;
	}

	public void setProject(ProjectInfo project) {
		this.project = project;
	}

	@Column(name="fremark",length=500)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
}
