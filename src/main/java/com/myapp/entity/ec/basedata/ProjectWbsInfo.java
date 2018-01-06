package com.myapp.entity.ec.basedata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.myapp.core.base.entity.CoreBaseTreeInfo;
import com.myapp.enums.ec.ProWbsType;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年7月6日 
 * @system:
 * 工程项目分解结构
 *-----------MySong---------------
 */
@Entity
@Table(name="t_ec_projectwbs")
public class ProjectWbsInfo extends CoreBaseTreeInfo<ProjectWbsInfo> {
	private ProjectInfo project;//工程项目
	private ProStructureInfo proStruct;//项目单位工程
	private ProWbsType wbsType;
	private ProBaseWbsInfo baseWbs;
	
	@Column(name="fwbstype",length=20)
	@Type(type="myEnum",parameters={@Parameter(name="enumClass",value="com.myapp.enums.ec.ProWbsType")})
	public ProWbsType getWbsType() {
		return wbsType;
	}

	public void setWbsType(ProWbsType wbsType) {
		this.wbsType = wbsType;
	}
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

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "fbasewWbsId")
	public ProBaseWbsInfo getBaseWbs() {
		return baseWbs;
	}

	public void setBaseWbs(ProBaseWbsInfo baseWbs) {
		this.baseWbs = baseWbs;
	}
}
