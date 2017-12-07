package com.myapp.entity.ec.basedata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.myapp.core.base.entity.CoreBaseDataInfo;
import com.myapp.enums.ec.ResourceType;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年11月30日 
 * @system: 资料目录
 *-----------MySong---------------
 */
@Entity
@Table(name="t_ec_resourceitem")
public class ResourceItemInfo extends CoreBaseDataInfo {
	
	private ResourceType resourceType;
	
	@Column(name="ftype",length=20)
	@Type(type="myEnum",parameters={@Parameter(name="enumClass",value="com.myapp.enums.ec.ResourceType")})
	public ResourceType getResourceType() {
		return resourceType;
	}

	public void setResourceType(ResourceType resourceType) {
		this.resourceType = resourceType;
	}
	
	
}
