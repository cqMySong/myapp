package com.myapp.entity.ec.other;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.myapp.core.base.entity.CoreBaseDataInfo;
import com.myapp.enums.ec.RewardPunish;

/**
 * 奖惩类型
 * @author Phoenix
 * @updator mySong
 */
@Entity
@Table(name="t_ec_rewardPunishType")
public class RewardPunishTypeInfo extends CoreBaseDataInfo{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5203069010857500204L;
	
	private RewardPunish rewardPunish;

	@Column(name="frewardPunish",length=20)
	@Type(type="myEnum",parameters={@Parameter(name="enumClass",value="com.myapp.enums.ec.RewardPunish")})
	public RewardPunish getRewardPunish() {
		return rewardPunish;
	}

	public void setRewardPunish(RewardPunish rewardPunish) {
		this.rewardPunish = rewardPunish;
	}

}
