package com.myapp.entity.ec.other;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.myapp.core.base.entity.CoreBaseBillInfo;

/**
 * 包路径：com.myapp.entity.ec.other
 * 功能说明：奖励通知
 * 创建人： 卢松
 * 创建时间: 2017-11-03 14:52
 */
@Entity
@Table(name="t_ec_rewards")
public class RewardsInfo extends CoreBaseBillInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2623994515918891586L;
	private String orgUnit;//单位
	private RewardPunishTypeInfo rewardPunishTypeInfo;//奖惩类型
	private Date grandDate;//授予日期
	private String item;//事项
	private String measures;//奖励办法
	private String grantOrgUnit;//授予单位
	
	@Column(name="forgUnit")
	public String getOrgUnit() {
		return orgUnit;
	}
	public void setOrgUnit(String orgUnit) {
		this.orgUnit = orgUnit;
	}
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "frewardPunishType")
	public RewardPunishTypeInfo getRewardPunishTypeInfo() {
		return rewardPunishTypeInfo;
	}
	public void setRewardPunishTypeInfo(RewardPunishTypeInfo rewardPunishTypeInfo) {
		this.rewardPunishTypeInfo = rewardPunishTypeInfo;
	}
	
	@Column(name="fgrandDate")
	public Date getGrandDate() {
		return grandDate;
	}
	public void setGrandDate(Date grandDate) {
		this.grandDate = grandDate;
	}
	
	@Column(name="fitem",length=500)
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	
	@Column(name="fmeasures",length=500)
	public String getMeasures() {
		return measures;
	}
	public void setMeasures(String measures) {
		this.measures = measures;
	}
	
	@Column(name="fgrantOrgUnit")
	public String getGrantOrgUnit() {
		return grantOrgUnit;
	}
	public void setGrantOrgUnit(String grantOrgUnit) {
		this.grantOrgUnit = grantOrgUnit;
	}
	
}
