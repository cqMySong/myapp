package com.myapp.entity.ec.basedata;

import com.myapp.core.annotation.MyEntityAnn;
import com.myapp.core.base.entity.CoreBaseDataInfo;

import javax.persistence.*;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年10月23日 
 * @system: 技术负责人：检验批信息
 *-----------MySong---------------
 */
@MyEntityAnn(name="施工样板清单")
@Entity
@Table(name="t_ec_construction_template")
public class ConstructionTemplateInfo extends CoreBaseDataInfo {

	private String engineeringProject;

	private String templateType;

	private String content;

	@Column(name="fEngineeringProject",length = 100)
	public String getEngineeringProject() {
		return engineeringProject;
	}

	public void setEngineeringProject(String engineeringProject) {
		this.engineeringProject = engineeringProject;
	}
	@Column(name="fTemplateType",length = 100)
	public String getTemplateType() {
		return templateType;
	}

	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}
	@Column(name="fContent",length = 500)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
