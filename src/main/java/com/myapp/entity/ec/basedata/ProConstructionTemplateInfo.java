package com.myapp.entity.ec.basedata;

import com.myapp.core.annotation.MyEntityAnn;
import com.myapp.core.base.entity.CoreBaseInfo;

import javax.persistence.*;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年10月23日 
 * @system: 技术负责人：检验批信息
 *-----------MySong---------------
 */
@MyEntityAnn(name="项目施工样板清单")
@Entity
@Table(name="t_ec_proconstruction_template")
public class ProConstructionTemplateInfo extends CoreBaseInfo {
	/**
	 * 工程项目
	 */
	private ProjectInfo project;
	/**
	 * 施工样板清单
	 */
	private ConstructionTemplateInfo constructionTemplateInfo;
	/**
	 * 清单内容
	 */
	private String content;
	/**
	 * 备注
	 */
	private String remark;

	@OneToOne(fetch= FetchType.LAZY)
	@JoinColumn(name = "fProjectId")
	public ProjectInfo getProject() {
		return project;
	}

	public void setProject(ProjectInfo project) {
		this.project = project;
	}

	@OneToOne(fetch= FetchType.LAZY)
	@JoinColumn(name = "fConstructionTemplateId")
	public ConstructionTemplateInfo getConstructionTemplateInfo() {
		return constructionTemplateInfo;
	}

	public void setConstructionTemplateInfo(ConstructionTemplateInfo constructionTemplateInfo) {
		this.constructionTemplateInfo = constructionTemplateInfo;
	}
	@Column(name="fContent",length=500)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	@Column(name="fRemark",length=500)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
