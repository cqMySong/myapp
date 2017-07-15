package com.myapp.entity.ec.basedata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.myapp.core.base.entity.CoreBaseDataInfo;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年6月19日 
 * @system:
 * 项目工程分部分项
 *-----------MySong---------------
 */
@Entity
@Table(name="t_ec_proSubItem")
public class ProSubItemInfo extends CoreBaseDataInfo {
	private ProjectInfo project;//工程项目
	private ProStructureInfo proStruct;//项目工程结构
	private ProSubInfo proSub;//项目分部工程

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "fprojectId")
	public ProjectInfo getProject() {
		return project;
	}
	public void setProject(ProjectInfo project) {
		this.project = project;
	}
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "fproSubId")
	public ProSubInfo getProSub() {
		return proSub;
	}

	public void setProSub(ProSubInfo proSub) {
		this.proSub = proSub;
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
