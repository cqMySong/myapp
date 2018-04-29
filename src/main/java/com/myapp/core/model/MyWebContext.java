package com.myapp.core.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.myapp.core.entity.BaseOrgInfo;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.util.BaseUtil;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年6月10日 
 * @system:
 * web的通用上下文
 *-----------MySong---------------
 */
public class MyWebContext {
	private String userName;//用户名
	private String userId;//用户id
	private String userNumber;//用户编码
	private String orgId;//默认组织id 废弃
	private String orgName;//默认组织名  废弃
	private BaseOrgInfo curOrg;//当前登录的默认组织
	private String mainPosition;
	private String linker;
	private List<Map<String,Object>> positions;//用户岗位范围
	private List<BaseOrgInfo> orgs;//用户组织范围
	private Map<String,Map<String,String>> permission;//用户权限范围 :<url,<其他属性值>>
	private Boolean admin = Boolean.FALSE;
	private Boolean sysUser = Boolean.FALSE;
	private List<Map<String,Object>> mainMenu;//主功能模块 一级菜单
	
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
	public List<BaseOrgInfo> getOrgs() {
		if(orgs==null) orgs = new ArrayList<BaseOrgInfo>();
		return orgs;
	}
	public void setOrgs(List<BaseOrgInfo> orgs) {
		this.orgs = orgs;
	}
	
	public Map<String, Map<String, String>> getPermission() {
		return permission;
	}
	public void setPermission(Map<String, Map<String, String>> permission) {
		this.permission = permission;
	}
	public UserInfo getCurUserInfo(){
		UserInfo u = new UserInfo();
		u.setId(getUserId());
		u.setName(getUserName());
		u.setNumber(getUserNumber());
		return u;
	}
	public List<Map<String, Object>> getPositions() {
		return positions;
	}
	public void setPositions(List<Map<String, Object>> positions) {
		this.positions = positions;
	}
	public String getMainPosition() {
		return mainPosition;
	}
	public void setMainPosition(String mainPosition) {
		this.mainPosition = mainPosition;
	}
	
	public String getMainPositionStr(){
		return getOrgName()+(BaseUtil.isEmpty(getMainPosition())?"":("("+getMainPosition()+")"));
	}
	public String getLinker() {
		return linker;
	}
	public void setLinker(String linker) {
		this.linker = linker;
	}
	public Boolean getAdmin() {
		return admin;
	}
	public void setAdmin(Boolean admin) {
		if(admin!=null) this.admin = admin;
	}
	public Boolean getSysUser() {
		return sysUser;
	}
	public void setSysUser(Boolean sysUser) {
		if(sysUser!=null) this.sysUser = sysUser;
	}
	public List<Map<String, Object>> getMainMenu() {
		if(mainMenu==null) mainMenu = new ArrayList<Map<String, Object>>();
		return mainMenu;
	}
	public void setMainMenu(List<Map<String, Object>> mainMenu) {
		this.mainMenu = mainMenu;
	}
	public BaseOrgInfo getCurOrg() {
		return curOrg;
	}
	public void setCurOrg(BaseOrgInfo curOrg) {
		this.curOrg = curOrg;
	}
	
}
