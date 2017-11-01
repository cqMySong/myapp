package com.myapp.entity.ec.basedata;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.myapp.core.base.entity.CoreInfo;
import com.myapp.core.entity.UserInfo;
import com.myapp.enums.ec.IDType;
import com.myapp.enums.ec.VisitType;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年10月30日 
 * @system:
 *  安保人员 值班记录表
 *-----------MySong---------------
 */
@Entity
@Table(name="t_ec_provisitrecord")
public class ProVisitRecordInfo extends CoreInfo{
	private Date createDate;//制单时间
	private UserInfo creator;//制单人
	private ProjectInfo project;//工程项目
	private VisitType visitType;//到访类型
	private Date visitDate;//到访日期
	private String cause;//到访事由
	
	private String visitor;//到访人员
	private IDType idType;//证件类型
	private String idNo;//证件号码
	
	private String carType;//车辆类别
	private String carNo;//车牌号
	
	private Date inDate;//进场时间
	private Date outDate;//出场时间
	private String confirm;//确认
	
	@Column(name="fvisittype",length=10)
	@Type(type="myEnum",parameters={@Parameter(name="enumClass",value="com.myapp.enums.ec.VisitType")})
	public VisitType getVisitType() {
		return visitType;
	}
	public void setVisitType(VisitType visitType) {
		this.visitType = visitType;
	}
	@Column(name="fvisitdate")
	public Date getVisitDate() {
		return visitDate;
	}
	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}
	@Column(name="fcause",length=150)
	public String getCause() {
		return cause;
	}
	public void setCause(String cause) {
		this.cause = cause;
	}
	@Column(name="fvisitor",length=50)
	public String getVisitor() {
		return visitor;
	}
	public void setVisitor(String visitor) {
		this.visitor = visitor;
	}
	@Column(name="fidtype",length=10)
	@Type(type="myEnum",parameters={@Parameter(name="enumClass",value="com.myapp.enums.ec.IDType")})
	public IDType getIdType() {
		return idType;
	}
	public void setIdType(IDType idType) {
		this.idType = idType;
	}
	@Column(name="fidno",length=80)
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	@Column(name="fcartype",length=50)
	public String getCarType() {
		return carType;
	}
	public void setCarType(String carType) {
		this.carType = carType;
	}
	@Column(name="fcarno",length=50)
	public String getCarNo() {
		return carNo;
	}
	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}
	@Column(name="findate")
	public Date getInDate() {
		return inDate;
	}
	public void setInDate(Date inDate) {
		this.inDate = inDate;
	}
	@Column(name="foutdate")
	public Date getOutDate() {
		return outDate;
	}
	public void setOutDate(Date outDate) {
		this.outDate = outDate;
	}
	public String getConfirm() {
		return confirm;
	}
	@Column(name="fconfirm",length=50)
	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}
	@Column(name="fcreateDate")
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	@OneToOne
	@JoinColumn(name = "fcreator")
	public UserInfo getCreator() {
		return creator;
	}
	public void setCreator(UserInfo creator) {
		this.creator = creator;
	}
	@OneToOne
	@JoinColumn(name = "fproject")
	public ProjectInfo getProject() {
		return project;
	}
	public void setProject(ProjectInfo project) {
		this.project = project;
	}
	
	
}
