package com.myapp.entity.ec.basedata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.myapp.core.annotation.MyEntityAnn;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.entity.UserInfo;
import com.myapp.enums.ec.SkillType;

import java.util.Date;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年10月22日 
 * @system:
 * 技术交底  包括施工技术和安全技术 交底
 *-----------MySong---------------
 */
@MyEntityAnn(name="技术交底记录")
@Entity
@Table(name="t_ec_proskillidisclosure")
public class ProSkillDisclosureInfo extends CoreBaseInfo {
	private ProjectInfo project;
	private SkillType skillType;//技术类别：施工技术；安全技术
	private SkillClassInfo skillClass;//分类
	private UserInfo disclosurer;//交底人
	private SkillItemInfo skillItem;//技术名称
	private Date finishTime;//完成时间
	private String sendee;//交底接收人
	
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
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "fprojectId")
	public ProjectInfo getProject() {
		return project;
	}

	public void setProject(ProjectInfo project) {
		this.project = project;
	}
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "fdisclosurerId")
	public UserInfo getDisclosurer() {
		return disclosurer;
	}

	public void setDisclosurer(UserInfo disclosurer) {
		this.disclosurer = disclosurer;
	}
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "fskillitemId")
	public SkillItemInfo getSkillItem() {
		return skillItem;
	}

	public void setSkillItem(SkillItemInfo skillItem) {
		this.skillItem = skillItem;
	}

	@Column(name="fFinishTime")
	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	@Column(name="fSendee")
	public String getSendee() {
		return sendee;
	}

	public void setSendee(String sendee) {
		this.sendee = sendee;
	}
}
