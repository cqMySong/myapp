package com.myapp.entity.ec.plan;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.myapp.core.base.entity.CoreBaseEntryInfo;
import com.myapp.core.entity.UserInfo;
import com.myapp.entity.ec.basedata.ProStructureInfo;
import com.myapp.entity.ec.basedata.ProSubInfo;
import com.myapp.entity.ec.basedata.ProSubItemInfo;
import com.myapp.entity.ec.basedata.ProjectWbsInfo;
import com.myapp.enums.ec.CauseType;
import com.myapp.enums.ec.YesNoEnum;

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
	private ProjectWbsInfo projectWbs;//工程分解结构
	
	private ProSubInfo proSub;//项目分部 @作废
	private ProSubItemInfo proSubItem;//工程分项 @作废
	
	private String planContent;//计划内容
	private Date fillDate;//填报日期
	private BigDecimal progress;//当前工程完成进度
	private String finishContent;//工作内容
	private UserInfo filler;//责任人
	private String remark;
	private String planItemId;//计划分录明细id
	
	private Date begDate;//开始日期
	private Date endDate;//截止日期
	
	//处理办法的几个字段
	private String delayNature;//延误性质
	private CauseType delayCause;//延误原因
	private String meetContent;//专题会议纪要
	private String meetTodo;//会议纪要执行情况
	private String toDo;//赶工情况
	private YesNoEnum doDelay;//办理工期延误
	private YesNoEnum doWorkPay;//办理工期费用索赔
	
	
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
	@Column(name="fbegDate")
	public Date getBegDate() {
		return begDate;
	}
	public void setBegDate(Date begDate) {
		this.begDate = begDate;
	}
	@Column(name="fendDate")
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "fprojectWbsId")
	public ProjectWbsInfo getProjectWbs() {
		return projectWbs;
	}
	public void setProjectWbs(ProjectWbsInfo projectWbs) {
		this.projectWbs = projectWbs;
	}
	@Column(name="fdelayNature")
	public String getDelayNature() {
		return delayNature;
	}
	public void setDelayNature(String delayNature) {
		this.delayNature = delayNature;
	}
	@Column(name="fdelayCuse",length=20)
	@Type(type="myEnum",parameters={@Parameter(name="enumClass",value="com.myapp.enums.ec.CauseType")})
	public CauseType getDelayCause() {
		return delayCause;
	}
	public void setDelayCause(CauseType delayCause) {
		this.delayCause = delayCause;
	}
	@Column(name="fmeetContent")
	public String getMeetContent() {
		return meetContent;
	}
	public void setMeetContent(String meetContent) {
		this.meetContent = meetContent;
	}
	@Column(name="fmeetTodo")
	public String getMeetTodo() {
		return meetTodo;
	}
	public void setMeetTodo(String meetTodo) {
		this.meetTodo = meetTodo;
	}
	@Column(name="ftoDo")
	public String getToDo() {
		return toDo;
	}
	public void setToDo(String toDo) {
		this.toDo = toDo;
	}
	@Column(name="fdoDelay",length=20)
	@Type(type="myEnum",parameters={@Parameter(name="enumClass",value="com.myapp.enums.ec.YesNoEnum")})
	public YesNoEnum getDoDelay() {
		return doDelay;
	}
	public void setDoDelay(YesNoEnum doDelay) {
		this.doDelay = doDelay;
	}
	@Column(name="fdoWorkPay",length=20)
	@Type(type="myEnum",parameters={@Parameter(name="enumClass",value="com.myapp.enums.ec.YesNoEnum")})
	public YesNoEnum getDoWorkPay() {
		return doWorkPay;
	}
	public void setDoWorkPay(YesNoEnum doWorkPay) {
		this.doWorkPay = doWorkPay;
	}
	
	
	
}
