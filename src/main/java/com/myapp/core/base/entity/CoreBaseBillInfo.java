package com.myapp.core.base.entity;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.BillState;
/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年3月12日 
 * @system:
 *  基础业务数据模型
 *-----------MySong---------------
 */
@MappedSuperclass
public class CoreBaseBillInfo extends CoreBaseInfo {
	
	private UserInfo createUser;
	private UserInfo lastUpdateUser;
	private BillState billState;
	
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
	@Column(name="fBillState",length=20)
	@Type(type="myEnum",parameters={@Parameter(name="enumClass",value="com.myapp.core.enums.BillState")})
	public BillState getBillState() {
		return billState;
	}
	public void setBillState(BillState billState) {
		this.billState = billState;
	}
	
	
}
