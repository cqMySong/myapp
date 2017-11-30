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

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.myapp.core.annotation.MyEntityAnn;
import com.myapp.core.entity.UserInfo;
import com.myapp.entity.ec.EcProjectBaseBillInfo;
import com.myapp.enums.ec.CheckType;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年11月30日 
 * @system:
 * 项目现场施工 安全检查（周,月）
 *-----------MySong---------------
 */
@Entity
@MyEntityAnn(name="现场施工 周(月)安全检查单")
@Table(name="t_ec_prosafecheck")
public class ProSafeCheckInfo extends EcProjectBaseBillInfo {
	private CheckType checkType;//检查类别
	private UserInfo proManager;//项目经理
	private UserInfo proSkiller;//项目技术负责人
	private String partAChecker;//施工单位检查人员
	private String partBChecker;//监理单位检查人员
	private Set<ProSafeCheckItemInfo> checkItems;//检查记录
	
	@Column(name="fchecktype",length=10)
	@Type(type="myEnum",parameters={@Parameter(name="enumClass",value="com.myapp.enums.ec.CheckType")})
	public CheckType getCheckType() {
		return checkType;
	}
	public void setCheckType(CheckType checkType) {
		this.checkType = checkType;
	}
	@OneToOne
	@JoinColumn(name = "fpromanagerId")
	public UserInfo getProManager() {
		return proManager;
	}
	public void setProManager(UserInfo proManager) {
		this.proManager = proManager;
	}
	@OneToOne
	@JoinColumn(name = "fproSkillerId")
	public UserInfo getProSkiller() {
		return proSkiller;
	}
	public void setProSkiller(UserInfo proSkiller) {
		this.proSkiller = proSkiller;
	}
	@Column(name="fpartacheker",length=80)
	public String getPartAChecker() {
		return partAChecker;
	}
	public void setPartAChecker(String partAChecker) {
		this.partAChecker = partAChecker;
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
	public Set<ProSafeCheckItemInfo> getCheckItems() {
		return checkItems;
	}
	public void setCheckItems(Set<ProSafeCheckItemInfo> checkItems) {
		this.checkItems = checkItems;
	}
	
	
	
	
	
	
	
}
