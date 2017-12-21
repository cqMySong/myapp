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
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.Sex;
import com.myapp.enums.ec.LetterType;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年12月21日 
 * @system:项目往来单位函件
 *-----------MySong---------------
 */
@MyEntityAnn(name="项目往来单位函件")
@Entity
@Table(name="t_ec_proExchangeLetter")
public class ProExchangeLetterInfo extends CoreBaseInfo {
	private ProjectInfo project;
	private Date dispatchDate;//发文时间
	private Date receivedDate;//收文时间
	private String dispatchUnit;//发文单位
	private String receivedUnit;//签收单位
	private LetterType type;//函件类别
	private String content;//主要内容
	private UserInfo createUser;
	
	@OneToOne
	@JoinColumn(name = "fcreateUserId")
	public UserInfo getCreateUser() {
		return createUser;
	}
	public void setCreateUser(UserInfo createUser) {
		this.createUser = createUser;
	}
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "fprojectId")
	public ProjectInfo getProject() {
		return project;
	}
	public void setProject(ProjectInfo project) {
		this.project = project;
	}
	@Column(name="fdispatchDage")
	public Date getDispatchDate() {
		return dispatchDate;
	}
	public void setDispatchDate(Date dispatchDate) {
		this.dispatchDate = dispatchDate;
	}
	@Column(name="freceivedDate")
	public Date getReceivedDate() {
		return receivedDate;
	}
	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}
	
	@Column(name="fdispatchUnit",length=150)
	public String getDispatchUnit() {
		return dispatchUnit;
	}
	public void setDispatchUnit(String dispatchUnit) {
		this.dispatchUnit = dispatchUnit;
	}
	
	@Column(name="freceivedUnit",length=150)
	public String getReceivedUnit() {
		return receivedUnit;
	}
	public void setReceivedUnit(String receivedUnit) {
		this.receivedUnit = receivedUnit;
	}
	
	@Column(name="ftype",length=20)
	@Type(type="myEnum",parameters={@Parameter(name="enumClass",value="com.myapp.enums.ec.LetterType")})
	public LetterType getType() {
		return type;
	}
	public void setType(LetterType type) {
		this.type = type;
	}
	
	@Column(name="fcontent",length=300)
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
