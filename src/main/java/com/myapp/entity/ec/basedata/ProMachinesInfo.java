package com.myapp.entity.ec.basedata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.myapp.core.annotation.MyEntityAnn;
import com.myapp.core.base.entity.CoreBaseInfo;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年12月12日 
 * @system:
 * 项目现场施工机械清单
 *-----------MySong---------------
 */
@MyEntityAnn(name="现场施工机械清单")
@Entity
@Table(name="t_ec_proMachines")
public class ProMachinesInfo extends CoreBaseInfo {
	private ProjectInfo project;
	private String operator;//操作人员
	private String maintenancer;//维修人员
	private String checkContent;//验收情况
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "fprojectId")
	public ProjectInfo getProject() {
		return project;
	}
	public void setProject(ProjectInfo project) {
		this.project = project;
	}
	@Column(name="foperator",length=50)
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	@Column(name="fmaintenancer",length=50)
	public String getMaintenancer() {
		return maintenancer;
	}
	public void setMaintenancer(String maintenancer) {
		this.maintenancer = maintenancer;
	}
	@Column(name="fcheckcontent",length=200)
	public String getCheckContent() {
		return checkContent;
	}
	public void setCheckContent(String checkContent) {
		this.checkContent = checkContent;
	}
	
	
}
