package com.myapp.core.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.enums.UserState;


@Entity
@Table(name="t_pm_user")
public class UserInfo extends CoreBaseInfo{
	private String passWord;
	private Boolean admin = Boolean.FALSE;
	private UserState userState;
	private String remark;
	private BaseOrgInfo defOrg;
	private Boolean sysUser = Boolean.FALSE;
	private String linkers;//联系方式
	
	private Set<UserPositionInfo> positionItems;
	
	@Column(name="fpassword")
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	@Column(name="fisAdmin")
	public Boolean getAdmin() {
		return admin;
	}
	public void setAdmin(Boolean isAdmin) {
		this.admin = isAdmin;
	}
	@Column(name="fuserState",length=20)
	@Type(type="myEnum",parameters={@Parameter(name="enumClass",value="com.myapp.core.enums.UserState")})
	public UserState getUserState() {
		return userState;
	}
	public void setUserState(UserState userState) {
		this.userState = userState;
	}
	@Column(name="fremark",length=200)
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "fdefOrg")
	public BaseOrgInfo getDefOrg() {
		return defOrg;
	}
	public void setDefOrg(BaseOrgInfo defOrg) {
		this.defOrg = defOrg;
	}
	@Column(name="fisSysUser",length=2)
	public Boolean getSysUser() {
		return sysUser;
	}
	public void setSysUser(Boolean isSysUser) {
		this.sysUser = isSysUser;
	}
	@Column(name="flinkers",length=100)
	public String getLinkers() {
		return linkers;
	}
	public void setLinkers(String linkers) {
		this.linkers = linkers;
	}
	@OneToMany(cascade={CascadeType.ALL},mappedBy="parent")
	@OrderBy("seq ASC")
	public Set<UserPositionInfo> getPositionItems() {
		return positionItems;
	}
	public void setPositionItems(Set<UserPositionInfo> positionItems) {
		this.positionItems = positionItems;
	}
	
	
	
}
