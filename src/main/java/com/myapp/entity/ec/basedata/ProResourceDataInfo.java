package com.myapp.entity.ec.basedata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.myapp.core.annotation.MyEntityAnn;
import com.myapp.core.base.entity.CoreBaseDataInfo;
import com.myapp.core.entity.basedate.DataGroupInfo;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年12月11日 
 * @system: 项目级资料目录（安全资料，技术资料）
 *-----------MySong---------------
 */
@MyEntityAnn(name="项目级资料目录")
@Entity
@Table(name="t_ec_prorecdata")
public class ProResourceDataInfo extends CoreBaseDataInfo {

	private String code;//资料代码
	private DataGroupInfo group; //分类
	private ProjectInfo project;
	
	@Column(name="fcode",nullable=false)
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "fgroupId")
	public DataGroupInfo getGroup() {
		return group;
	}
	public void setGroup(DataGroupInfo group) {
		this.group = group;
	}
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "fprojectId")
	public ProjectInfo getProject() {
		return project;
	}
	public void setProject(ProjectInfo project) {
		this.project = project;
	}
	
}
