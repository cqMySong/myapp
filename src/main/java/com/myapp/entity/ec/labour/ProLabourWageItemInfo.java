package com.myapp.entity.ec.labour;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.myapp.core.base.entity.CoreBaseEntryInfo;
import com.myapp.entity.ec.basedata.ProLabourInfo;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年12月24日 
 * @system: 民工工资明细
 *-----------MySong---------------
 */
@Entity
@Table(name="t_ec_prolabourwageitem")
public class ProLabourWageItemInfo extends CoreBaseEntryInfo<ProLabourWageInfo> {
	private ProLabourInfo labour;
	private String bankNo;//银行账号
	private String phone;//联系电话
	private BigDecimal workDays;//用工时间
	private BigDecimal wage;//应发工资
	private Date signDate;//签收时间
	private String idCard;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "flabourId")
	public ProLabourInfo getLabour() {
		return labour;
	}
	public void setLabour(ProLabourInfo labour) {
		this.labour = labour;
	}
	
	@Column(name="fbankno",length=100)
	public String getBankNo() {
		return bankNo;
	}
	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}
	
	@Column(name="fphone",length=50)
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Column(name="fworkdays")
	public BigDecimal getWorkDays() {
		return workDays;
	}
	public void setWorkDays(BigDecimal workDays) {
		this.workDays = workDays;
	}
	
	@Column(name="fwage")
	public BigDecimal getWage() {
		return wage;
	}
	public void setWage(BigDecimal wage) {
		this.wage = wage;
	}
	
	@Column(name="fsigndate")
	public Date getSignDate() {
		return signDate;
	}
	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}
	@Column(name="fidCard",length=50)
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
}
