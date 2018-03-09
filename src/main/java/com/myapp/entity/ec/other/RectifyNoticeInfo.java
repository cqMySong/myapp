package com.myapp.entity.ec.other;

import java.util.Date;

import javax.persistence.*;

import com.myapp.core.base.entity.CoreBaseBillInfo;
import com.myapp.entity.ec.basedata.ProjectInfo;

/**
 * 包路径：com.myapp.entity.ec.other
 * 功能说明：整改回复
 * 创建人： 卢松
 * 创建时间: 2017-11-03 14:52
 */
@Entity
@Table(name="t_ec_rectifyNotice")
public class RectifyNoticeInfo extends CoreBaseBillInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2081629247388513689L;
	
	private String orgUnit;//受检单位
	private String orgUnitPerson;//受检人
	private String type;//类别
	private String problem;//存在的问题
	private String requires;//整改要求
	private Date endDate;//整改截至时间
	private String checkOrgUnit;//检查单位
	private String checkOrgPerson;//检查人
	private Date checkDate;//检查日期
	private ProjectInfo project;//工程项目
	private RectifyFeedBackInfo rectifyFeedBack;

	
	@Column(name="forgUnit")
	public String getOrgUnit() {
		return orgUnit;
	}
	public void setOrgUnit(String orgUnit) {
		this.orgUnit = orgUnit;
	}
	
	@Column(name="ftype")
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	@Column(name="fproblem", length=500)
	public String getProblem() {
		return problem;
	}
	public void setProblem(String problem) {
		this.problem = problem;
	}
	
	@Column(name="frequires", length=500)
	public String getRequires() {
		return requires;
	}
	public void setRequires(String requires) {
		this.requires = requires;
	}
	
	@Column(name="fendDate")
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	@Column(name="fcheckOrgUnit")
	public String getCheckOrgUnit() {
		return checkOrgUnit;
	}
	public void setCheckOrgUnit(String checkOrgUnit) {
		this.checkOrgUnit = checkOrgUnit;
	}

	@OneToOne
	@JoinColumn(name = "fProjectId")
	public ProjectInfo getProject() {
		return project;
	}

	public void setProject(ProjectInfo project) {
		this.project = project;
	}

	@Column(name="fOrgUnitPerson", length=30)
	public String getOrgUnitPerson() {
		return orgUnitPerson;
	}

	public void setOrgUnitPerson(String orgUnitPerson) {
		this.orgUnitPerson = orgUnitPerson;
	}

	@Column(name="fCheckOrgPerson", length=30)
	public String getCheckOrgPerson() {
		return checkOrgPerson;
	}

	public void setCheckOrgPerson(String checkOrgPerson) {
		this.checkOrgPerson = checkOrgPerson;
	}
	@Column(name="fCheckDate")
	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	@OneToOne(cascade={CascadeType.ALL},mappedBy="rectifyNotice")
	public RectifyFeedBackInfo getRectifyFeedBack() {
		return rectifyFeedBack;
	}

	public void setRectifyFeedBack(RectifyFeedBackInfo rectifyFeedBack) {
		this.rectifyFeedBack = rectifyFeedBack;
	}
}
