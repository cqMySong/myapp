package com.myapp.entity.ec.plan;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.myapp.core.annotation.MyEntityAnn;
import com.myapp.core.base.entity.CoreBaseBillInfo;
import com.myapp.entity.ec.basedata.ProjectInfo;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年8月14日 
 * @system:
 * 工作日志（施工日志）
 *-----------MySong---------------
 */
@MyEntityAnn(name="工作日志")
@Entity
@Table(name="t_ec_workLog")
public class WorkLogInfo extends CoreBaseBillInfo {
	private ProjectInfo project;//工程项目
	private String remark;//其他
	private String week;//星期
	private String workSite;//施工部位
	private Integer attendance;//出勤人数(人)
	private BigDecimal temperature;//气温
	private String amweather;//上午天气
	private String pmweather;//下午天气
	private String sjbg;//设计变更
	private String bgwh;//文号
	private String tzdw;//通知单位
	private String jsjd;//技术交底
	private String jsjdr;//接受交底人
	private String ybgcysbw;//隐蔽工程验收部位
	private String sjskzz;//砼砂浆试块制作
	private String cljcsjqk;//材料进场送检情况
	private String zl;//质量
	private String aq;//安全
	private String gz;//工长
	private String wrokContent;//施工内容
	private String optDutyer;//操作负责人
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "fprojectId")
	public ProjectInfo getProject() {
		return project;
	}
	public void setProject(ProjectInfo project) {
		this.project = project;
	}
	@Column(name="fremark",length=200)
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name="fweek")
	public String getWeek() {
		return week;
	}
	public void setWeek(String week) {
		this.week = week;
	}
	@Column(name="fworkSite")
	public String getWorkSite() {
		return workSite;
	}
	public void setWorkSite(String workSite) {
		this.workSite = workSite;
	}
	@Column(name="fattendance")
	public Integer getAttendance() {
		return attendance;
	}
	public void setAttendance(Integer attendance) {
		this.attendance = attendance;
	}
	@Column(name="ftemperature")
	public BigDecimal getTemperature() {
		return temperature;
	}
	public void setTemperature(BigDecimal temperature) {
		this.temperature = temperature;
	}
	@Column(name="famweather")
	public String getAmweather() {
		return amweather;
	}
	public void setAmweather(String amweather) {
		this.amweather = amweather;
	}
	@Column(name="fpmweather")
	public String getPmweather() {
		return pmweather;
	}
	public void setPmweather(String pmweather) {
		this.pmweather = pmweather;
	}
	@Column(name="fsjbg")
	public String getSjbg() {
		return sjbg;
	}
	public void setSjbg(String sjbg) {
		this.sjbg = sjbg;
	}
	@Column(name="fbgwh")
	public String getBgwh() {
		return bgwh;
	}
	public void setBgwh(String bgwh) {
		this.bgwh = bgwh;
	}
	@Column(name="ftzdw")
	public String getTzdw() {
		return tzdw;
	}
	public void setTzdw(String tzdw) {
		this.tzdw = tzdw;
	}
	@Column(name="fjsjd")
	public String getJsjd() {
		return jsjd;
	}
	public void setJsjd(String jsjd) {
		this.jsjd = jsjd;
	}
	@Column(name="fjsjdr")
	public String getJsjdr() {
		return jsjdr;
	}
	public void setJsjdr(String jsjdr) {
		this.jsjdr = jsjdr;
	}
	@Column(name="fybgcysbw")
	public String getYbgcysbw() {
		return ybgcysbw;
	}
	public void setYbgcysbw(String ybgcysbw) {
		this.ybgcysbw = ybgcysbw;
	}
	@Column(name="fsjskzz")
	public String getSjskzz() {
		return sjskzz;
	}
	public void setSjskzz(String sjskzz) {
		this.sjskzz = sjskzz;
	}
	@Column(name="fcljcsjqk")
	public String getCljcsjqk() {
		return cljcsjqk;
	}
	public void setCljcsjqk(String cljcsjqk) {
		this.cljcsjqk = cljcsjqk;
	}
	@Column(name="fzl")
	public String getZl() {
		return zl;
	}
	public void setZl(String zl) {
		this.zl = zl;
	}
	@Column(name="faq")
	public String getAq() {
		return aq;
	}
	public void setAq(String aq) {
		this.aq = aq;
	}
	@Column(name="fgz")
	public String getGz() {
		return gz;
	}
	public void setGz(String gz) {
		this.gz = gz;
	}
	@Column(name="fworkContent",length=500)
	public String getWrokContent() {
		return wrokContent;
	}
	public void setWrokContent(String wrokContent) {
		this.wrokContent = wrokContent;
	}
	@Column(name="foptDutyer",length=500)
	public String getOptDutyer() {
		return optDutyer;
	}
	public void setOptDutyer(String optDutyer) {
		this.optDutyer = optDutyer;
	}
	
	
}
