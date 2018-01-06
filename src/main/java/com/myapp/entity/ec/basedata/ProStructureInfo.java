package com.myapp.entity.ec.basedata;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.myapp.core.base.entity.CoreBaseTreeInfo;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年6月19日 
 * @system:
 * 项目工程结构
 *-----------MySong---------------
 */
@Entity
@Table(name="t_ec_proStructure")
public class ProStructureInfo extends CoreBaseTreeInfo<ProStructureInfo> {
	private ProjectInfo project;//工程项目
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "fprojectId")
	public ProjectInfo getProject() {
		return project;
	}
	public void setProject(ProjectInfo project) {
		this.project = project;
	}
}
