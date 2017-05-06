package com.myapp.core.base.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

import com.myapp.core.entity.UserInfo;
/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年3月12日 
 * @system:
 *  基础数据模型
 *-----------MySong---------------
 */
@MappedSuperclass
public class CoreBaseBillInfo extends CoreBaseInfo {
	
	private UserInfo createUser;
	private UserInfo lastUpdateUser;
	
	@OneToOne
	@JoinColumn(name = "fcreateUser")
	public UserInfo getCreateUser() {
		return createUser;
	}
	public void setCreateUser(UserInfo createUser) {
		this.createUser = createUser;
	}
	
	@OneToOne
	@JoinColumn(name = "flastUpdateUser")
	public UserInfo getLastUpdateUser() {
		return lastUpdateUser;
	}
	public void setLastUpdateUser(UserInfo lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}
}
