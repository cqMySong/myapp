package com.myapp.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.myapp.core.base.entity.CoreBaseEntryInfo;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年9月10日 
 * @system:
 *-----------MySong---------------
 */
@Entity
@Table(name="t_pm_userPosition")
public class UserPositionInfo extends CoreBaseEntryInfo<UserInfo> {
	private PositionInfo position;//岗位
	private Boolean main;//主要职位
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "fpositionId")
	public PositionInfo getPosition() {
		return position;
	}
	public void setPosition(PositionInfo position) {
		this.position = position;
	}
	
	@Column(name="fisAdmin")
	public Boolean getMain() {
		return main;
	}
	public void setMain(Boolean main) {
		this.main = main;
	}
	
}
