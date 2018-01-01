package com.myapp.enums.ec;

import java.util.HashMap;
import java.util.Map;

import com.myapp.core.base.enums.MyEnum;
/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年12月03日 
 * @system: 资料类别
 *-----------MySong---------------
 */
public enum ResourceType implements MyEnum<ResourceType>{
	AQJSYQYSML("AQJSYQYSML","安全技术要求及验收目录"),OTHER("OTHER","其他");

	private String name;
	private String value;
	private static final Map<String, ResourceType> map = new HashMap<String, ResourceType>();
	static {
        map.put(AQJSYQYSML.getValue(), AQJSYQYSML);
        map.put(OTHER.getValue(), OTHER);
    }

	ResourceType(String value,String name){
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return this.name;
	}

	public String getValue() {
		return this.value;
	}

	public ResourceType getEnum(String value) {
		return map.get(value);
	}
}
