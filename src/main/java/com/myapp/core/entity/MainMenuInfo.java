package com.myapp.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.myapp.core.base.entity.CoreBaseTreeInfo;
import com.myapp.core.enums.IconCodeType;
import com.myapp.core.enums.IconType;
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
	private IconCodeType iconCodeType;//图标编码类型
	private IconType iconType;//图标库类型
	private String url;//地址
	private String params;//参数
	private MenuOpenType menuOpenType;//菜单连接打开方式
	private Boolean sysMenu;//是否系统菜单
	
	@Column(name="fonshow",nullable=false)
	public Boolean getOnShow() {
		return onShow;
	}
	public void setOnShow(Boolean onShow) {
		this.onShow = onShow;
	}
	@Column(name="ficon",length=20)
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	@Column(name="furl",length=100)
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Column(name="fparams")
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	@Column(name="fmenuOpenType",length=20)
	@Type(type="myEnum",parameters={@Parameter(name="enumClass",value="com.myapp.core.enums.MenuOpenType")})
	public MenuOpenType getMenuOpenType() {
		return menuOpenType;
	}
	public void setMenuOpenType(MenuOpenType menuOpenType) {
		this.menuOpenType = menuOpenType;
	}
	@Column(name="ficonCodeType",length=20)
	@Type(type="myEnum",parameters={@Parameter(name="enumClass",value="com.myapp.core.enums.IconCodeType")})
	public IconCodeType getIconCodeType() {
		return iconCodeType;
	}
	public void setIconCodeType(IconCodeType iconCodeType) {
		this.iconCodeType = iconCodeType;
	}
	@Column(name="ficonType",length=20)
	@Type(type="myEnum",parameters={@Parameter(name="enumClass",value="com.myapp.core.enums.IconType")})
	public IconType getIconType() {
		return iconType;
	}
	public void setIconType(IconType iconType) {
		this.iconType = iconType;
	}
	@Column(name="fsysMenu",nullable=false)
	public Boolean getSysMenu() {
		return sysMenu;
	}
	public void setSysMenu(Boolean sysMenu) {
		this.sysMenu = sysMenu;
	}
}
