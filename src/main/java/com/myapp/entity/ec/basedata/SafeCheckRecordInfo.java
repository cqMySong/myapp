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
 * @author mySong @date 2017年11月1日 
 * @system:
 *  安保人员: 安保监督检查记录表记录表
 *  检查内容 1、当班人员是否在岗；2、当班人员是否随意调岗或换岗；
 *  		3、当班人员是否脱岗；4、当班人员是否瞌睡；
 *  		5、当班人员是否认真值岗；6、当班人员是否及时反映突发、异常情况；
 *  		7、当班人员是否认真、及时记录值班情况等。
 *-----------MySong---------------
 */
@Entity
@Table(name="t_ec_safecheckrecord")
public class SafeCheckRecordInfo extends CoreInfo{
	private Date checkDate; //时间
	private String position;//检查岗位
	private String question;//存在问题
	private String content;//检查内容  下拉多选
	private UserInfo creator;//制单人
	private Date createDate;//制单时间
	private ProjectInfo project;//工程项目
	
	@Column(name="fcheckDate")
	public Date getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	@Column(name="fposition",length=100)
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	
	@Column(name="fquestion",length=150)
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	@Column(name="fcontent",length=100)
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
	@Column(name="fcreatedate")
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	
}
