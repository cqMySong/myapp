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
 * @author mySong @date 2017年12月18日 
 * @system: 技术负责人：项目级检验批信息
 *-----------MySong---------------
 */
@MyEntityAnn(name="项目检验批信息")
@Entity
@Table(name="t_ec_probathchtest")
public class ProBatchTestInfo extends CoreBaseInfo {
	private ProBaseWbsInfo proBaseWbs;//检验批分解结构
	private String content;//检验批划分办法
	private String remark; //标准划分办法
	private ProjectInfo project;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "fprowbsId")
	public ProBaseWbsInfo getProBaseWbs() {
		return proBaseWbs;
	}
	public void setProBaseWbs(ProBaseWbsInfo proBaseWbs) {
		this.proBaseWbs = proBaseWbs;
	}
	
	@Column(name="fcontent",length=500)
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@Column(name="fremark",length=500)
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
