package com.myapp.enums.ec;

import java.util.HashMap;
import java.util.Map;

import com.myapp.core.base.enums.MyEnum;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年11月02日 
 * @system: 安保巡查 问题处理办法：□向主管反应  □及时制止
 *-----------MySong---------------
 */
public enum DoWayType implements MyEnum<DoWayType> {
	ZGFY("ZGFY","向主管反应"),JSZZ("JSZZ","及时制止");

	private String name;
	private String value;
	private static final Map<String, DoWayType> map = new HashMap<String, DoWayType>();
	static {
        map.put(ZGFY.getValue(), ZGFY);
        map.put(JSZZ.getValue(), JSZZ);
    }

	DoWayType(String value,String name){
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return this.name;
	}

	public String getValue() {
		return this.value;
	}

	public DoWayType getEnum(String value) {
		return map.get(value);
	}

}
