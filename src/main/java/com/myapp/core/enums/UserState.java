package com.myapp.core.enums;

import java.util.HashMap;
import java.util.Map;

import com.myapp.core.base.enums.MyEnum;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年5月6日 
 * @system:
 * DISABLE 禁止登录等其他操作，FREEZE 只是禁止一些操作
 *-----------MySong---------------
 */
public enum UserState implements MyEnum<UserState> {
	ENABLE("ENABLE","正常"),DISABLE("DISABLE","失效"),FREEZE("FREEZE","冻结");

	private String name;
	private String value;
	private static final Map<String, UserState> map = new HashMap<String, UserState>();
	static {
        map.put(ENABLE.getValue(), ENABLE);
        map.put(DISABLE.getValue(), DISABLE);
        map.put(FREEZE.getValue(), FREEZE);
    }

	UserState(String value,String name){
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return this.name;
	}

	public String getValue() {
		return this.value;
	}

	public UserState getEnum(String value) {
		return map.get(value);
	}

}
