package com.myapp.entity.ec.basedata;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.myapp.core.base.entity.CoreBaseDataInfo;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年7月6日 
 * @system:
 * 项目分部工程
 *-----------MySong---------------
 */
@Entity
@Table(name="t_ec_proSub")
public class ProSubInfo extends CoreBaseDataInfo {
	private ProjectInfo project;//工程项目
	private ProStructureInfo proStruct;//项目单位工程

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "fprojectId")
	public ProjectInfo getProject() {
		return project;
	}
	public void setProject(ProjectInfo project) {
		this.project = project;
	}
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "fproStructId")
	public ProStructureInfo getProStruct() {
		return proStruct;
	}

	public void setProStruct(ProStructureInfo proStruct) {
		this.proStruct = proStruct;
	}
	
}
