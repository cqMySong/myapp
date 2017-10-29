package com.myapp.entity.ec.basedata;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.myapp.core.base.entity.CoreInfo;
import com.myapp.core.entity.UserInfo;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年10月29日 
 * @system:
 *  安保人员 值班记录表
 *-----------MySong---------------
 */
@Entity
@Table(name="t_ec_ondutyrecord")
public class OnDutyRecordInfo extends CoreInfo{
	private Date dutyDate; //值班时间
	private String dutyPosition;//安保岗位
	private String planDutyor;//计划值班人
	private String realDutyor;//实际值班人
	private Date createDate;//制单时间
	private String content;//交班注意事项
	private UserInfo creator;//制单人
	private ProjectInfo project;//工程项目
	
	@Column(name="fdutyDate")
	public Date getDutyDate() {
		return dutyDate;
	}
	public void setDutyDate(Date dutyDate) {
		this.dutyDate = dutyDate;
	}
	@Column(name="fdutyPosition",length=100)
	public String getDutyPosition() {
		return dutyPosition;
	}
	public void setDutyPosition(String dutyPosition) {
		this.dutyPosition = dutyPosition;
	}
	@Column(name="fplandutyor",length=80)
	public String getPlanDutyor() {
		return planDutyor;
	}
	public void setPlanDutyor(String planDutyor) {
		this.planDutyor = planDutyor;
	}
	@Column(name="frealdutyor",length=80)
	public String getRealDutyor() {
		return realDutyor;
	}
	public void setRealDutyor(String realDutyor) {
		this.realDutyor = realDutyor;
	}
	@Column(name="fcreateDate")
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	@Column(name="fcontent",length=200)
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
