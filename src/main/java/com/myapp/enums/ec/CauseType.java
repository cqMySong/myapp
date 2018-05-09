package com.myapp.enums.ec;

import java.util.HashMap;
import java.util.Map;

import com.myapp.core.base.enums.MyEnum;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年10月30日 
 * @system:
 * 原因类别
 *-----------MySong---------------
 */
public enum CauseType implements MyEnum<CauseType> {
	BLANK("BLANK"," "),ZS("ZS","自身原因"),FZS("FZS","非自身原因");

	private String name;
	private String value;
	private static final Map<String, CauseType> map = new HashMap<String, CauseType>();
	static {
        map.put(BLANK.getValue(), BLANK);
        map.put(ZS.getValue(), ZS);
        map.put(FZS.getValue(), FZS);
    }

	CauseType(String value,String name){
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return this.name;
	}

	public String getValue() {
		return this.value;
	}

	public CauseType getEnum(String value) {
		return map.get(value);
	}

}
