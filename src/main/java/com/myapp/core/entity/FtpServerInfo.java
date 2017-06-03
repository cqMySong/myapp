package com.myapp.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.myapp.core.base.entity.CoreBaseInfo;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年5月6日 
 * @system:
 * 暂时说不用考虑组织类型表
 *-----------MySong---------------
 */
@Entity
@Table(name="t_base_Ftp")
public class FtpServerInfo extends CoreBaseInfo {
	private String ftpUrl;
	private String root;//
	private String userName;//webApp
	private String password;//myApp
	private Integer port = 21;
	private Boolean enabled = Boolean.TRUE;
	
	@Column(name="fFtpUrl")
	public String getFtpUrl() {
		return ftpUrl;
	}
	public void setFtpUrl(String ftpUrl) {
		this.ftpUrl = ftpUrl;
	}
	@Column(name="fRoot")
	public String getRoot() {
		return root;
	}
	public void setRoot(String root) {
		this.root = root;
	}
	@Column(name="fUserName")
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column(name="fPassword")
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Column(name="fPort")
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	@Column(name="fEnabled")
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
}
