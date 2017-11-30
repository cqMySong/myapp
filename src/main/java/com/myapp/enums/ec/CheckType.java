package com.myapp.enums.ec;

import java.util.HashMap;
import java.util.Map;

import com.myapp.core.base.enums.MyEnum;
/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年11月30日 
 * @system:
 *  是否类型的boolean的枚举
 *-----------MySong---------------
 */
public enum CheckType implements MyEnum<CheckType>{
	WEEK("WEEK","周检"),MONTH("MONTH","月检");

	private String name;
	private String value;
	private static final Map<String, CheckType> map = new HashMap<String, CheckType>();
	static {
        map.put(WEEK.getValue(), WEEK);
        map.put(MONTH.getValue(), MONTH);
    }

	CheckType(String value,String name){
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return this.name;
	}

	public String getValue() {
		return this.value;
	}

	public CheckType getEnum(String value) {
		return map.get(value);
	}
}
