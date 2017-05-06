package com.myapp.core.enums;

import java.util.HashMap;
import java.util.Map;

import com.myapp.core.base.enums.MyEnum;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年5月5日 
 * @system:  整行 枚举案例
 *
 *-----------MySong---------------
 */
public enum Sex_copy implements MyEnum<Sex_copy,Integer> {
	MAIL(1,"男"),WOMAN(2,"女"),NO(3,"什么也不是");
	private String name;
	private Integer value;
	private static final Map<Integer, Sex_copy> map = new HashMap<Integer, Sex_copy>();
	static {
        map.put(MAIL.getValue(), MAIL);
        map.put(WOMAN.getValue(), WOMAN);
        map.put(NO.getValue(), NO);
    }

	Sex_copy(Integer value,String name){
		this.name = name;
		this.value = value;
	}
	public Sex_copy getEnum(Integer value) {
		return map.get(value);
	}

	public String getName() {
		return this.name;
	}

	public Integer getValue() {
		return this.value;
	}

	

}
