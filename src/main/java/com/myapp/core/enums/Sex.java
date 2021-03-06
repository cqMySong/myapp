package com.myapp.core.enums;

import java.util.HashMap;
import java.util.Map;

import com.myapp.core.base.enums.MyEnum;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年5月5日 
 * @system:
 *
 *-----------MySong---------------
 */
public enum Sex implements MyEnum<Sex> {
	MALE("MALE","男"),FEMALE("FEMALE","女"),SECRET("SECRET","保密");
	private String name;
	private String value;
	private static final Map<String, Sex> map = new HashMap<String, Sex>();
	static {
        map.put(MALE.getValue(), MALE);
        map.put(FEMALE.getValue(), FEMALE);
        map.put(SECRET.getValue(), SECRET);
    }
	Sex(String value,String name){
		this.name = name;
		this.value = value;
	}
	public Sex getEnum(String value) {
		return map.get(value);
	}
	public String getName() {
		return this.name;
	}
	public String getValue() {
		return this.value;
	}
}
