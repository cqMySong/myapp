package com.myapp.core.base.entity;

import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.springframework.beans.BeanWrapperImpl;

import com.myapp.core.entity.BaseOrgInfo;
import com.myapp.core.entity.UserInfo;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年4月29日 
 * @system:
 * 基础数据模型
 *-----------MySong---------------
 */
@MappedSuperclass
public class CoreBaseInfo extends CoreInfo {
	private String name;
	private String number;
	private Date createDate;
	private Date lastUpdateDate;
	@Column(name="fcreateDate")
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	@Column(name="flastUpdateDate")
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	@Column(name="fname",length=200)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(name="fnumber",length=100)
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	
	public String toString() {
		return getName();
	}
}
