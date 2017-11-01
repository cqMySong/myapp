package com.myapp.enums.ec;

import java.util.HashMap;
import java.util.Map;

import com.myapp.core.base.enums.MyEnum;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年10月30日 
 * @system:
 * 到访类别:人员和车辆
 *-----------MySong---------------
 */
public enum VisitType implements MyEnum<VisitType> {
	PERSON("PERSON","人员"),CAR("CAR","车辆");

	private String name;
	private String value;
	private static final Map<String, VisitType> map = new HashMap<String, VisitType>();
	static {
        map.put(PERSON.getValue(), PERSON);
        map.put(CAR.getValue(), CAR);
    }

	VisitType(String value,String name){
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return this.name;
	}

	public String getValue() {
		return this.value;
	}

	public VisitType getEnum(String value) {
		return map.get(value);
	}

}
