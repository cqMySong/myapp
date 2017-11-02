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
 * @author mySong @date 2017年11月2日 
 * @system:
 *  安保人员: 安保巡查记录表
 *  处理办法 ：□向主管反应  □及时制止
 *-----------MySong---------------
 */
@Entity
@Table(name="t_ec_safepatrolrecord")
public class SafePatrolRecordInfo extends CoreInfo{
	private Date patrolDate; //时间
	private String range;//线路及范围
	private String attention;//注意事项
	private Boolean hasQuestion;//异常问题
	private String doWay;//处理办法 多选  DoWayType
	private UserInfo creator;//制单人
	private Date createDate;//制单时间
	private ProjectInfo project;//工程项目
	
	@Column(name="fpatroldate")
	public Date getPatrolDate() {
		return patrolDate;
	}
	public void setPatrolDate(Date patrolDate) {
		this.patrolDate = patrolDate;
	}
	@Column(name="frange",length=150)
	public String getRange() {
		return range;
	}
	public void setRange(String range) {
		this.range = range;
	}
	@Column(name="fattention",length=200)
	public String getAttention() {
		return attention;
	}
	public void setAttention(String attention) {
		this.attention = attention;
	}
	@Column(name="fdoway",length=100)
	public String getDoWay() {
		return doWay;
	}
	public void setDoWay(String doWay) {
		this.doWay = doWay;
	}
	@Column(name="fhasquestion")
	public Boolean getHasQuestion() {
		return hasQuestion;
	}
	public void setHasQuestion(Boolean hasQuestion) {
		this.hasQuestion = hasQuestion;
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
	@Column(name="fcreatedate")
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	
}
