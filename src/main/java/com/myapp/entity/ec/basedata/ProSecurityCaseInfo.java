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
 * @author mySong @date 2017年10月30日 
 * @system:
 *  安保人员 项目安保预案
 *-----------MySong---------------
 */
@Entity
@Table(name="t_ec_prosecuritycase")
public class ProSecurityCaseInfo extends CoreInfo{
	private String part;//重点要点部位
	private String content;//隐患内容
	private String secCase;//安保办法
	private Date createDate;//制单时间
	private UserInfo creator;//制单人
	private ProjectInfo project;//工程项目
	
	@Column(name="fpart",length=100)
	public String getPart() {
		return part;
	}
	public void setPart(String part) {
		this.part = part;
	}
	@Column(name="fseccase",length=200)
	public String getSecCase() {
		return secCase;
	}
	public void setSecCase(String secCase) {
		this.secCase = secCase;
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
