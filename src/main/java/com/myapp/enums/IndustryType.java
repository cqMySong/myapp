package com.myapp.enums;

import java.util.HashMap;
import java.util.Map;

import com.myapp.core.base.enums.MyEnum;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年6月15日 
 * @system:
 * 工程行业类别
 *-----------MySong---------------
 */
public enum IndustryType implements MyEnum<IndustryType,String> {
	FWJZ("FWJZ","房屋建筑工程"),SLSD("SLSD","水利水电工程"),SZGC("SZGC","市政工程"),GLGC("GLGC","公路工程"),NLGC("NLGC","农林工程");

	private String name;
	private String value;
	private static final Map<String, IndustryType> map = new HashMap<String, IndustryType>();
	static {
        map.put(FWJZ.getValue(), FWJZ);
        map.put(SLSD.getValue(), SLSD);
        map.put(SZGC.getValue(), SZGC);
        map.put(GLGC.getValue(), GLGC);
        map.put(NLGC.getValue(), NLGC);
    }

	IndustryType(String value,String name){
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return this.name;
	}

	public String getValue() {
		return this.value;
	}

	public IndustryType getEnum(String value) {
		return map.get(value);
	}

}
