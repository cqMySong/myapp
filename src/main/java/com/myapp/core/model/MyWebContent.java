package com.myapp.core.model;

import java.util.List;
import java.util.Map;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年6月10日 
 * @system:
 * web的通用上下文
 *-----------MySong---------------
 */
public class MyWebContent {
	private String userName;//用户名
	private String userId;//用户id
	private String userNumber;//用户编码
	private String orgId;//默认组织id
	private String orgName;//默认组织名
	private List<Map<String,String>> orgs;//用户组织范围
	private List<Map<String,String>> permission;//用户权限范围
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserNumber() {
		return userNumber;
	}
	public void setUserNumber(String userNumber) {
		this.userNumber = userNumber;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public List<Map<String, String>> getOrgs() {
		return orgs;
	}
	public void setOrgs(List<Map<String, String>> orgs) {
		this.orgs = orgs;
	}
	public List<Map<String, String>> getPermission() {
		return permission;
	}
	public void setPermission(List<Map<String, String>> permission) {
		this.permission = permission;
	}
	
	
}
