package com.myapp.entity.ec.basedata;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.myapp.core.annotation.MyEntityAnn;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.entity.basedate.DataGroupInfo;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年12月12日 
 * @system:项目施工图资料
 *-----------MySong---------------
 */
@MyEntityAnn(name="项目施工图资料")
@Entity
@Table(name="t_ec_proecdraw")
public class ProEcDrawInfo extends CoreBaseInfo {
	private ProjectInfo project;
	private DataGroupInfo group;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "fprojectId")
	public ProjectInfo getProject() {
		return project;
	}
	public void setProject(ProjectInfo project) {
		this.project = project;
	}
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "fgroupId")
	public DataGroupInfo getGroup() {
		return group;
	}
	public void setGroup(DataGroupInfo group) {
		this.group = group;
	}
	
	
}
