package com.myapp.core.model;

import com.myapp.core.enums.PermissionTypeEnum;
import com.myapp.core.util.BaseUtil;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年6月7日 
 * @system:
 * controller 权限配置的model
 *-----------MySong---------------
 */
public class PermissionModel {
	private String name;
	private String number;
	private PermissionTypeEnum type;
	private String url;
	
	public PermissionModel(String number,String name,String url,PermissionTypeEnum type){
		this.name = name;
		this.number = number;
		this.url = url;
		this.type = type;
	}
	public String getName() {
		if(BaseUtil.isEmpty(name)) this.name = "系统管理";
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNumber() {
		if(BaseUtil.isEmpty(number)) this.number = "app";
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public PermissionTypeEnum getType() {
		return type;
	}
	public void setType(PermissionTypeEnum type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		if(BaseUtil.isEmpty(url)) return;
		if(!BaseUtil.isEmpty(this.url)){
			this.url +=","+url;
		}else{
			this.url = url;
		}
		
	}
}
