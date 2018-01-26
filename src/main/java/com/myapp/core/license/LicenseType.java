package com.myapp.core.license;

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
public enum LicenseType implements MyEnum<LicenseType> {
	RELEASE("RELEASE","正式许可"),BETA("BETA","试用版");

	private String name;
	private String value;
	private static final Map<String, LicenseType> map = new HashMap<String, LicenseType>();
	static {
        map.put(RELEASE.getValue(), RELEASE);
        map.put(BETA.getValue(), BETA);
    }

	LicenseType(String value,String name){
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return this.name;
	}

	public String getValue() {
		return this.value;
	}

	public LicenseType getEnum(String value) {
		return map.get(value);
	}

}
