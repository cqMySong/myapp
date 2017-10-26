package com.myapp.entity.ec.basedata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.myapp.core.base.entity.CoreBaseDataInfo;
import com.myapp.enums.ec.SkillType;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年10月22日 
 * @system:
 * 技术分类明显 包括施工技术和安全技术
 *-----------MySong---------------
 */
@Entity
@Table(name="t_ec_skillitem")
public class SkillItemInfo extends CoreBaseDataInfo {
	
	private SkillType skillType;//技术类别：质量；安全
	
	private SkillClassInfo skillClass;
	
	@Column(name="fskilltype",length=10)
	@Type(type="myEnum",parameters={@Parameter(name="enumClass",value="com.myapp.enums.ec.SkillType")})
	public SkillType getSkillType() {
		return skillType;
	}

	public void setSkillType(SkillType skillType) {
		this.skillType = skillType;
	}
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "fskillclass")
	public SkillClassInfo getSkillClass() {
		return skillClass;
	}

	public void setSkillClass(SkillClassInfo skillClass) {
		this.skillClass = skillClass;
	}
	
	
	
}
