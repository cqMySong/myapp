package com.myapp.entity.ec.usepower;

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
 * @author mySong @date 2017年12月13日 
 * @system:
 * 施工现场安全用电检查表
 *-----------MySong---------------
 */
@Entity
@MyEntityAnn(name="施工现场安全用电检查表")
@Table(name="t_ec_prousepowercheck")
public class ProUsePowerCheckInfo extends EcProjectBaseBillInfo {
	//检查单位用name
	private UserInfo ecWorker;//项目施工人员
	private UserInfo ecChecker;//项目检查人员
	private String rectifyIdea;//整改意见
	//检查时间就用业务时间
	private Set<ProUsePowerCheckItemInfo> checkItems;//检查记录
	
	@OneToOne
	@JoinColumn(name = "fecworker")
	public UserInfo getEcWorker() {
		return ecWorker;
	}
	public void setEcWorker(UserInfo ecWorker) {
		this.ecWorker = ecWorker;
	}
	
	@OneToOne
	@JoinColumn(name = "fecchecker")
	public UserInfo getEcChecker() {
		return ecChecker;
	}
	public void setEcChecker(UserInfo ecChecker) {
		this.ecChecker = ecChecker;
	}
	
	@Column(name="frectifyIdea",length=100)
	public String getRectifyIdea() {
		return rectifyIdea;
	}
	public void setRectifyIdea(String rectifyIdea) {
		this.rectifyIdea = rectifyIdea;
	}
	
	@OneToMany(cascade={CascadeType.ALL},mappedBy="parent")
	@OrderBy("seq ASC")
	public Set<ProUsePowerCheckItemInfo> getCheckItems() {
		return checkItems;
	}
	public void setCheckItems(Set<ProUsePowerCheckItemInfo> checkItems) {
		this.checkItems = checkItems;
	}
}
