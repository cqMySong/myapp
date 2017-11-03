package com.myapp.entity.ec.safty;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.myapp.core.base.entity.CoreBaseBillInfo;
import com.myapp.core.enums.Sex;

@Entity
@Table(name = "t_ec_saftyEduBook")
public class SaftyEduBookInfo extends CoreBaseBillInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Sex sex;//性别
	
	private Integer age;//年龄
	
	private String workType;//工种
	
	private String homeAddress;//家庭住址
	
	private String idCardNo;//身份证号
	
	private Date companyDate;//进工地日期

	@Column(name="fsex",length=20)
	public Sex getSex() {
		return sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	@Column(name="fage")
	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Column(name="fworkType",length=150)
	public String getWorkType() {
		return workType;
	}

	public void setWorkType(String workType) {
		this.workType = workType;
	}

	@Column(name="fhomeAddress",length=200)
	public String getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}

	@Column(name="fidCardNo",length=20)
	public String getIdCardNo() {
		return idCardNo;
	}

	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}

	@Column(name="fcompanyDate")
	public Date getCompanyDate() {
		return companyDate;
	}

	public void setCompanyDate(Date companyDate) {
		this.companyDate = companyDate;
	}
	
	
}
