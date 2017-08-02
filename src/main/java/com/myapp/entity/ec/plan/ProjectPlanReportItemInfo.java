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
@Table(name="t_ec_proWorkPlanItem")
public class ProjectPlanReportItemInfo extends CoreBaseEntryInfo<ProjectPlanReportInfo> {
	private ProStructureInfo proStructure;//项目结构
	private ProSubInfo proSub;//项目分部
	private ProSubItemInfo proSubItem;//工程分项
	private String planContent;//计划内容
	private Date fillDate;//填报日期
	private BigDecimal progress;//当前工程完成进度
	private String finishContent;//工作内容
	private UserInfo filler;//责任人
	private String remark;
	private String planItemId;//计划分录明细id
	
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
	
	@Column(name="fprogress")
	public BigDecimal getProgress() {
		return progress;
	}
	public void setProgress(BigDecimal progress) {
		this.progress = progress;
	}
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "fproSubId")
	public ProSubInfo getProSub() {
		return proSub;
	}
	public void setProSub(ProSubInfo proSub) {
		this.proSub = proSub;
	}
	@Column(name="fPlanContent")
	public String getPlanContent() {
		return planContent;
	}
	public void setPlanContent(String planContent) {
		this.planContent = planContent;
	}
	@Column(name="fFillDate")
	public Date getFillDate() {
		return fillDate;
	}
	public void setFillDate(Date fillDate) {
		this.fillDate = fillDate;
	}
	@Column(name="ffinishContent",length=500)
	public String getFinishContent() {
		return finishContent;
	}
	public void setFinishContent(String finishContent) {
		this.finishContent = finishContent;
	}
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "ffillerId")
	public UserInfo getFiller() {
		return filler;
	}
	public void setFiller(UserInfo filler) {
		this.filler = filler;
	}
	
	@Column(name="fremark",length=200)
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name="fPlanItemId",length=200)
	public String getPlanItemId() {
		return planItemId;
	}
	public void setPlanItemId(String planItemId) {
		this.planItemId = planItemId;
	}
	
	
	
	
}
