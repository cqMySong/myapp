package com.myapp.entity.ec.basedata;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.myapp.core.annotation.MyEntityAnn;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.enums.Sex;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年12月12日 
 * @system:劳务人员备案表
 *-----------MySong---------------
 */
@MyEntityAnn(name="项目劳务人员信息")
@Entity
@Table(name="t_ec_prolabour")
public class ProLabourInfo extends CoreBaseInfo {
	private ProjectInfo project;
	private Sex sex;
	private Integer age;
	private WorkTypeInfo workType;
	private String addr;
	private Date joinDate;
	private String idCard;
	private String remark;
	private String bank;//开户行
	private String bankNo;//银行账户
	
	@Column(name="fsex",length=20)
	@Type(type="myEnum",parameters={@Parameter(name="enumClass",value="com.myapp.core.enums.Sex")})
	public Sex getSex() {
		return sex;
	}
	public void setSex(Sex sex) {
		this.sex = sex;
	}
	
	@Column(name="fage",length=4)
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	@Column(name="faddr",length=100)
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	@Column(name="fjoindate")
	public Date getJoinDate() {
		return joinDate;
	}
	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}
	@Column(name="fidcard",length=50)
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "fprojectId")
	public ProjectInfo getProject() {
		return project;
	}
	public void setProject(ProjectInfo project) {
		this.project = project;
	}
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "fworkTypeId")
	public WorkTypeInfo getWorkType() {
		return workType;
	}
	public void setWorkType(WorkTypeInfo workType) {
		this.workType = workType;
	}
	@Column(name="fremark",length=200)
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name="fbank",length=100)
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	@Column(name="fbankNo",length=100)
	public String getBankNo() {
		return bankNo;
	}
	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}
	
}
