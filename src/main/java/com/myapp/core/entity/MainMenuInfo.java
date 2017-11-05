package com.myapp.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.myapp.core.base.entity.CoreBaseTreeInfo;
import com.myapp.core.enums.MenuOpenType;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年11月05日 
 * @system: 首页菜单配置
 *-----------MySong---------------
 */
@Entity
@Table(name="t_pm_mainmenu")
public class MainMenuInfo extends CoreBaseTreeInfo<MainMenuInfo> {
	private Boolean onShow;//显示
	private String icon;//图标
	private String url;//地址
	private String params;//参数
	private MenuOpenType menuOpenType;//菜单连接打开方式
	private String funCode;//功能代码
	
	@Column(name="fisOnshow",nullable=false)
	public Boolean getOnShow() {
		return onShow;
	}
	public void setOnShow(Boolean onShow) {
		this.onShow = onShow;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	public MenuOpenType getMenuOpenType() {
		return menuOpenType;
	}
	public void setMenuOpenType(MenuOpenType menuOpenType) {
		this.menuOpenType = menuOpenType;
	}
	public String getFunCode() {
		return funCode;
	}
	public void setFunCode(String funCode) {
		this.funCode = funCode;
	}
	
	

	
	
	
}
