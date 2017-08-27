package com.myapp.core.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.myapp.core.base.entity.CoreBaseDataInfo;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年8月20日 
 * @system:
 * 工作职责项
 *-----------MySong---------------
 */
@Entity
@Table(name="t_pm_jobDuty")
public class JobDutyInfo extends CoreBaseDataInfo {
	
	private PermissionInfo shortCutMenu;//快捷菜单

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "fshortCutMenu")
	public PermissionInfo getShortCutMenu() {
		return shortCutMenu;
	}

	public void setShortCutMenu(PermissionInfo shortCutMenu) {
		this.shortCutMenu = shortCutMenu;
	}
	
}
