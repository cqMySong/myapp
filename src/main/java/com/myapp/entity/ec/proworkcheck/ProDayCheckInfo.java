package com.myapp.entity.ec.proworkcheck;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.myapp.core.annotation.MyEntityAnn;
import com.myapp.core.entity.UserInfo;
import com.myapp.entity.ec.EcProjectBaseBillInfo;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年11月27日 
 * @system:
 * 项目现场施工 日检查表
 *-----------MySong---------------
 */
@Entity
@MyEntityAnn(name="现场施工 日检查单")
@Table(name="t_ec_prodaycheck")
public class ProDayCheckInfo extends EcProjectBaseBillInfo {
	private UserInfo proSafer;//项目专职安全员
	private String partBChecker;//监理单位检查人员
	private Set<ProDayCheckItemInfo> dayCheckItems;//检查记录
	
	@OneToOne
	@JoinColumn(name = "fproSaferId")
	public UserInfo getProSafer() {
		return proSafer;
	}
	public void setProSafer(UserInfo proSafer) {
		this.proSafer = proSafer;
	}
	@Column(name="fpartbchecker",length=80)
	public String getPartBChecker() {
		return partBChecker;
	}
	public void setPartBChecker(String partBChecker) {
		this.partBChecker = partBChecker;
	}
	
	@OneToMany(cascade={CascadeType.ALL},mappedBy="parent")
	@OrderBy("seq ASC")
	public Set<ProDayCheckItemInfo> getDayCheckItems() {
		return dayCheckItems;
	}
	public void setDayCheckItems(Set<ProDayCheckItemInfo> dayCheckItems) {
		this.dayCheckItems = dayCheckItems;
	}
	
	
	
}
