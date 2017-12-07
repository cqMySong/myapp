package com.myapp.entity.ec.rectify;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.myapp.core.entity.UserInfo;
import com.myapp.entity.ec.EcProjectBaseBillInfo;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年12月7日 
 * @system:
 * 隐患整改通知单
 *-----------MySong---------------
 */
@Entity
@Table(name="t_ec_hiddenDangerNotice")
public class HiddenDangerNoticeInfo extends EcProjectBaseBillInfo {
	// 通知批次号 用number
	//单位名称（分包单位班组） 用name
	private Date checkDate;//检查日期
	private String danger;//问题描述
	private Date endDate;//整改期限
	private String sendUnit;//发出单位
	private UserInfo checker;//检查员
	
	@Column(name="fcheckDate")
	public Date getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	@Column(name="fdanger",length=500)
	public String getDanger() {
		return danger;
	}
	public void setDanger(String danger) {
		this.danger = danger;
	}
	@Column(name="fendDate")
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	@Column(name="fsendUnit",length=100)
	public String getSendUnit() {
		return sendUnit;
	}
	public void setSendUnit(String sendUnit) {
		this.sendUnit = sendUnit;
	}
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "fcheckerId")
	public UserInfo getChecker() {
		return checker;
	}
	public void setChecker(UserInfo checker) {
		this.checker = checker;
	}
}
