package com.myapp.enums.ec;

import java.util.HashMap;
import java.util.Map;

import com.myapp.core.base.enums.MyEnum;
/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年11月27日 
 * @system:
 *  是否类型的boolean的枚举
 *-----------MySong---------------
 */
public enum YesNoEnum implements MyEnum<YesNoEnum>{
	BLANK("BLANK"," "),YES("YES","是"),NO("NO","否");

	private String name;
	private String value;
	private static final Map<String, YesNoEnum> map = new HashMap<String, YesNoEnum>();
	static {
        map.put(BLANK.getValue(), BLANK);
        map.put(YES.getValue(), YES);
        map.put(NO.getValue(), NO);
    }

	YesNoEnum(String value,String name){
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return this.name;
	}

	public String getValue() {
		return this.value;
	}

	public YesNoEnum getEnum(String value) {
		return map.get(value);
	}
}
