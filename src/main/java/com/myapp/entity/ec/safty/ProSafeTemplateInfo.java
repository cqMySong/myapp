package com.myapp.entity.ec.safty;

import com.myapp.core.annotation.MyEntityAnn;
import com.myapp.core.base.entity.CoreBaseBillInfo;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.entity.ec.basedata.ProjectWbsInfo;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@MyEntityAnn(name="项目安全样板")
@Entity
@Table(name = "t_ec_pro_safe_template")
public class ProSafeTemplateInfo extends CoreBaseBillInfo {
	/**
	 * 工程项目
	 */
	private ProjectInfo project;
	/**
	 * 分部
	 */
	private ProjectWbsInfo branchBaseWbs;
	/**
	 * 分项
	 */
	private ProjectWbsInfo subentry;
	/**
	 * 操作要点
	 */
	private String operationPoint;
	/**
	 * 预计实施时间
	 */
	private Date expectStartDate;
	/**
	 * 验收时间
	 */
	private Date acceptanceDate;
	/**
	 * 质量样板职责明细
	 */
	private Set<ProSafeTemplateDetailInfo> proQualityTemplateDetailInfoSet;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fProjectId")
	public ProjectInfo getProject() {
		return project;
	}

	public void setProject(ProjectInfo project) {
		this.project = project;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fBranchBaseWbsId")
	public ProjectWbsInfo getBranchBaseWbs() {
		return branchBaseWbs;
	}

	public void setBranchBaseWbs(ProjectWbsInfo branchBaseWbs) {
		this.branchBaseWbs = branchBaseWbs;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fSubentryId")
	public ProjectWbsInfo getSubentry() {
		return subentry;
	}

	public void setSubentry(ProjectWbsInfo subentry) {
		this.subentry = subentry;
	}

	@Column(name = "fOperationPoint")
	public String getOperationPoint() {
		return operationPoint;
	}

	public void setOperationPoint(String operationPoint) {
		this.operationPoint = operationPoint;
	}
	@Column(name = "fExpectStartDate")
	public Date getExpectStartDate() {
		return expectStartDate;
	}

	public void setExpectStartDate(Date expectStartDate) {
		this.expectStartDate = expectStartDate;
	}
	@Column(name = "fAcceptanceDate")
	public Date getAcceptanceDate() {
		return acceptanceDate;
	}

	public void setAcceptanceDate(Date acceptanceDate) {
		this.acceptanceDate = acceptanceDate;
	}
	@OneToMany(cascade={CascadeType.ALL},mappedBy="parent")
	@OrderBy("seq ASC")
	public Set<ProSafeTemplateDetailInfo> getProQualityTemplateDetailInfoSet() {
		return proQualityTemplateDetailInfoSet;
	}

	public void setProQualityTemplateDetailInfoSet(Set<ProSafeTemplateDetailInfo> proQualityTemplateDetailInfoSet) {
		this.proQualityTemplateDetailInfoSet = proQualityTemplateDetailInfoSet;
	}
}
