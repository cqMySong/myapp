package com.myapp.entity.ec.maintain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.myapp.core.annotation.MyEntityAnn;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.entity.UserInfo;
import com.myapp.entity.ec.basedata.ProjectInfo;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年12月13日 
 * @system: 维修记录
 *-----------MySong---------------
 */
@Entity
@MyEntityAnn(name="维修记录表")
@Table(name="t_ec_maintainRecord")
public class MaintainRecordInfo extends CoreBaseInfo {
	private ProjectInfo project;//工程项目
	private String remark;//备注
	private Date bizDate;//维修时间
	private String content;//维修内容
	private UserInfo maintainer;//维修人员
	private UserInfo createUser;//制单人
	
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
	@Column(name="fbizdate")
	public Date getBizDate() {
		return bizDate;
	}
	public void setBizDate(Date bizDate) {
		this.bizDate = bizDate;
	}
	@Column(name="fcontent",length=500)
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "fmaintainerId")
	public UserInfo getMaintainer() {
		return maintainer;
	}
	public void setMaintainer(UserInfo maintainer) {
		this.maintainer = maintainer;
	}
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "fcreateUserId")
	public UserInfo getCreateUser() {
		return createUser;
	}
	public void setCreateUser(UserInfo createUser) {
		this.createUser = createUser;
	}
	
}
