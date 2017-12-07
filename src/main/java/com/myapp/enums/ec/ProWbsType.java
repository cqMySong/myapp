package com.myapp.enums.ec;

import java.util.HashMap;
import java.util.Map;

import com.myapp.core.base.enums.MyEnum;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年12月4日 
 * @system:
 * 工程基础分解结构
 *-----------MySong---------------
 */
public enum ProWbsType implements MyEnum<ProWbsType>{
	FJJG("FJJG","工程分解结构"),FBGC("FBGC","分部工程"),ZFBGC("ZFBGC","子分部工程"),FXGC("FXGC","分项工程"),OTHER("OTHER","其他");

	private String name;
	private String value;
	private static final Map<String, ProWbsType> map = new HashMap<String, ProWbsType>();
	static {
		map.put(FJJG.getValue(), FJJG);
        map.put(FBGC.getValue(), FBGC);
        map.put(ZFBGC.getValue(), ZFBGC);
        map.put(FXGC.getValue(), FXGC);
        map.put(OTHER.getValue(), OTHER);
    }

	ProWbsType(String value,String name){
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return this.name;
	}

	public String getValue() {
		return this.value;
	}

	public ProWbsType getEnum(String value) {
		return map.get(value);
	}
}
