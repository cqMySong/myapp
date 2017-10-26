package com.myapp.enums.ec;

import java.util.HashMap;
import java.util.Map;

import com.myapp.core.base.enums.MyEnum;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年10月22日 
 * @system:
 * 技术分类:安全技术交底和施工技术交底
 *-----------MySong---------------
 */
public enum SkillType implements MyEnum<SkillType> {
	SM("SM","安全技术"),QM("QM","施工技术");

	private String name;
	private String value;
	private static final Map<String, SkillType> map = new HashMap<String, SkillType>();
	static {
        map.put(SM.getValue(), SM);
        map.put(QM.getValue(), QM);
    }

	SkillType(String value,String name){
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return this.name;
	}

	public String getValue() {
		return this.value;
	}

	public SkillType getEnum(String value) {
		return map.get(value);
	}

}
