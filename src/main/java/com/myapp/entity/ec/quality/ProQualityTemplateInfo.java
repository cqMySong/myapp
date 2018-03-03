package com.myapp.entity.ec.quality;

import com.myapp.core.annotation.MyEntityAnn;
import com.myapp.core.base.entity.CoreBaseBillInfo;
import com.myapp.core.entity.UserInfo;
import com.myapp.entity.ec.basedata.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@MyEntityAnn(name="项目质量样板")
@Entity
@Table(name = "t_ec_pro_quality_template")
public class ProQualityTemplateInfo extends CoreBaseBillInfo {
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
	 * 预计实施时间
	 */
	private Date expectStartDate;
	/**
	 * 验收时间
	 */
	private Date acceptanceDate;
	/**
	 * 质量样板
	 */
	private QualityTemplateInfo qualityTemplateInfo;
	/**
	 * 质量样板职责明细
	 */
	private Set<ProQualityTemplateDetailInfo> proQualityTemplateDetailInfoSet;

	private QualityTemplateInfo oldQualityTemplateInfo;

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
	public Set<ProQualityTemplateDetailInfo> getProQualityTemplateDetailInfoSet() {
		return proQualityTemplateDetailInfoSet;
	}

	public void setProQualityTemplateDetailInfoSet(Set<ProQualityTemplateDetailInfo> proQualityTemplateDetailInfoSet) {
		this.proQualityTemplateDetailInfoSet = proQualityTemplateDetailInfoSet;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fQualityTemplateId")
	public QualityTemplateInfo getQualityTemplateInfo() {
		return qualityTemplateInfo;
	}

	public void setQualityTemplateInfo(QualityTemplateInfo qualityTemplateInfo) {
		this.qualityTemplateInfo = qualityTemplateInfo;
	}

	@Transient
	public QualityTemplateInfo getOldQualityTemplateInfo() {
		return oldQualityTemplateInfo;
	}

	public void setOldQualityTemplateInfo(QualityTemplateInfo oldQualityTemplateInfo) {
		this.oldQualityTemplateInfo = oldQualityTemplateInfo;
	}
}
