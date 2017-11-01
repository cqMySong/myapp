package com.myapp.enums.ec;

import java.util.HashMap;
import java.util.Map;

import com.myapp.core.base.enums.MyEnum;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年10月30日 
 * @system:
 * 证件类别:
 *-----------MySong---------------
 */
public enum IDType implements MyEnum<IDType> {
	SFZ("SFZ","身份证"),JSZ("JSZ","驾驶证"),GZZ("GZZ","工作证"),JGZ("JGZ","军官证"),OTHER("OTHER","其他");

	private String name;
	private String value;
	private static final Map<String, IDType> map = new HashMap<String, IDType>();
	static {
        map.put(SFZ.getValue(), SFZ);
        map.put(JSZ.getValue(), JSZ);
        map.put(GZZ.getValue(), GZZ);
        map.put(JGZ.getValue(), JGZ);
        map.put(OTHER.getValue(), OTHER);
    }

	IDType(String value,String name){
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return this.name;
	}

	public String getValue() {
		return this.value;
	}

	public IDType getEnum(String value) {
		return map.get(value);
	}

}
