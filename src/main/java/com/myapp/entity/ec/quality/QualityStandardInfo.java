package com.myapp.entity.ec.quality;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.myapp.core.base.entity.CoreBaseBillInfo;
import com.myapp.core.entity.UserInfo;
import com.myapp.entity.ec.basedata.ECUnitInfo;
import com.myapp.entity.ec.basedata.ProStructureInfo;
import com.myapp.entity.ec.basedata.ProSubItemInfo;
import com.myapp.entity.ec.basedata.ProjectInfo;

@Entity
@Table(name = "t_ec_qualityStandard")
public class QualityStandardInfo extends CoreBaseBillInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 496731565179641430L;

	private ProjectInfo project;// 工程项目

	private ProSubItemInfo proSubItem;// 工程分项

	private ECUnitInfo unit;// 施工单位
	
	private ProStructureInfo proStructure;//交底部位
	
	private Date finishDate;//计划完成日期

	private Date qualityDate;// 交底日期
	
	private String constructionClass;//施工班组
	
	private String participants;//主要参加人员

	private UserInfo techUser;// 技术负责人

	private UserInfo qualityUser;// 交底人

	private UserInfo accepter;// 接交人
	
	private String remark;//备注

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fprojectId")
	public ProjectInfo getProject() {
		return project;
	}

	public void setProject(ProjectInfo project) {
		this.project = project;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fproSubItemId")
	public ProSubItemInfo getProSubItem() {
		return proSubItem;
	}

	public void setProSubItem(ProSubItemInfo proSubItem) {
		this.proSubItem = proSubItem;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fecUnitId")
	public ECUnitInfo getUnit() {
		return unit;
	}

	public void setUnit(ECUnitInfo unit) {
		this.unit = unit;
	}

	@Column(name="fQualityDate")
	public Date getQualityDate() {
		return qualityDate;
	}

	public void setQualityDate(Date qualityDate) {
		this.qualityDate = qualityDate;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ftechUserId")
	public UserInfo getTechUser() {
		return techUser;
	}

	public void setTechUser(UserInfo techUser) {
		this.techUser = techUser;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fqualityUserId")
	public UserInfo getQualityUser() {
		return qualityUser;
	}

	public void setQualityUser(UserInfo qualityUser) {
		this.qualityUser = qualityUser;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "faccepterId")
	public UserInfo getAccepter() {
		return accepter;
	}

	public void setAccepter(UserInfo accepter) {
		this.accepter = accepter;
	}
	
	@Column(name="fremark",length=250)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fproStructureId")
	public ProStructureInfo getProStructure() {
		return proStructure;
	}

	public void setProStructure(ProStructureInfo proStructure) {
		this.proStructure = proStructure;
	}

	@Column(name="ffinishDate")
	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	@Column(name="fconstructionClass",length=100)
	public String getConstructionClass() {
		return constructionClass;
	}

	public void setConstructionClass(String constructionClass) {
		this.constructionClass = constructionClass;
	}

	@Column(name="fparticipants",length=200)
	public String getParticipants() {
		return participants;
	}

	public void setParticipants(String participants) {
		this.participants = participants;
	}
}
