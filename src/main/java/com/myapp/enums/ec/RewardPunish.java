package com.myapp.enums.ec;

import java.util.HashMap;
import java.util.Map;

import com.myapp.core.base.enums.MyEnum;

public enum RewardPunish implements MyEnum<RewardPunish>{

	REWARD("REWARD","奖励"),PUNISH("PUNISH","惩罚");

	private String name;
	private String value;
	private static final Map<String, RewardPunish> map = new HashMap<String, RewardPunish>();
	static {
        map.put(REWARD.getValue(), REWARD);
        map.put(PUNISH.getValue(), PUNISH);
    }

	RewardPunish(String value,String name){
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return this.name;
	}

	public String getValue() {
		return this.value;
	}

	public RewardPunish getEnum(String value) {
		return map.get(value);
	}
}
