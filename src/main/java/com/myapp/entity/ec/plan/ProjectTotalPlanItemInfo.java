package com.myapp.entity.ec.plan;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.myapp.core.base.entity.CoreBaseEntryInfo;
import com.myapp.core.entity.UserInfo;
import com.myapp.entity.ec.basedata.ProStructureInfo;
import com.myapp.entity.ec.basedata.ProSubInfo;
import com.myapp.entity.ec.basedata.ProSubItemInfo;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年6月21日 
 * @system:
 *
 *-----------MySong---------------
 */
@Entity
@Table(name="t_ec_projectTotalPlanItem")
public class ProjectTotalPlanItemInfo extends CoreBaseEntryInfo<ProjectTotalPlanInfo> {
	private ProStructureInfo proStructure;//项目结构
	private ProSubInfo proSub;//项目分部
	private ProSubItemInfo proSubItem;//工程分项
	private Date planBegDate;//计划开始日期
	private Date planEndDate;//计划截止日期
	private Integer planDays;//计划工期
	private Date realBegDate;//实际开始日期
	private Date realEndDate;//实际截止日期
	private Integer realDays;//实际工期
	private BigDecimal progress;//当前工程完成进度
	
	private String content;//工作内容
	private String proQty;//工程量
	private String proPersons;//施工人员
	
	private UserInfo dutyer;//责任人
	private String dutyers;//责任人 多人之间用,分隔开
	
	
	private String remark;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "fproStructureId")
	public ProStructureInfo getProStructure() {
		return proStructure;
	}
	public void setProStructure(ProStructureInfo proStructure) {
		this.proStructure = proStructure;
	}
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "fproSubItemId")
	public ProSubItemInfo getProSubItem() {
		return proSubItem;
	}
	public void setProSubItem(ProSubItemInfo proSubItem) {
		this.proSubItem = proSubItem;
	}
	@Column(name="fplanBegDate")
	public Date getPlanBegDate() {
		return planBegDate;
	}
	public void setPlanBegDate(Date planBegDate) {
		this.planBegDate = planBegDate;
	}
	@Column(name="fplanEndDate")
	public Date getPlanEndDate() {
		return planEndDate;
	}
	public void setPlanEndDate(Date planEndDate) {
		this.planEndDate = planEndDate;
	}
	@Column(name="fplanDays")
	public Integer getPlanDays() {
		return planDays;
	}
	public void setPlanDays(Integer planDays) {
		this.planDays = planDays;
	}
	@Column(name="frealBegDate")
	public Date getRealBegDate() {
		return realBegDate;
	}
	public void setRealBegDate(Date realBegDate) {
		this.realBegDate = realBegDate;
	}
	@Column(name="frealEndDate")
	public Date getRealEndDate() {
		return realEndDate;
	}
	public void setRealEndDate(Date realEndDate) {
		this.realEndDate = realEndDate;
	}
	@Column(name="frealDays")
	public Integer getRealDays() {
		return realDays;
	}
	public void setRealDays(Integer realDays) {
		this.realDays = realDays;
	}
	@Column(name="fprogress")
	public BigDecimal getProgress() {
		return progress;
	}
	public void setProgress(BigDecimal progress) {
		this.progress = progress;
	}
	@Column(name="fremark",length=200)
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name="fcontent",length=250)
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Column(name="fproQty",length=100)
	public String getProQty() {
		return proQty;
	}
	public void setProQty(String proQty) {
		this.proQty = proQty;
	}
	@Column(name="fproPersons",length=100)
	public String getProPersons() {
		return proPersons;
	}
	public void setProPersons(String proPersons) {
		this.proPersons = proPersons;
	}
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "fproSubId")
	public ProSubInfo getProSub() {
		return proSub;
	}
	public void setProSub(ProSubInfo proSub) {
		this.proSub = proSub;
	}
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "fdutyerId")
	public UserInfo getDutyer() {
		return dutyer;
	}
	public void setDutyer(UserInfo dutyer) {
		this.dutyer = dutyer;
	}
	
	@Column(name="fdutyers",length=500)
	public String getDutyers() {
		return dutyers;
	}
	public void setDutyers(String dutyers) {
		this.dutyers = dutyers;
	}
	
	
	
}
