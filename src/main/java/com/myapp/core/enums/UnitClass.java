package com.myapp.core.enums;

import java.util.HashMap;
import java.util.Map;

import com.myapp.core.base.enums.MyEnum;
/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年10月22日 
 * @system: 计量单位分类
 *-----------MySong---------------
 */
public enum UnitClass implements MyEnum<UnitClass> {
	CD("CD","长度"),ZL("ZL","重量"),MJ("MJ","面积")
   ,TJ("TJ","体积"),SL("SL","数量"),OTHER("OTHER","其他");
	
	private String name;
	private String value;

	private static final Map<String, UnitClass> map = new HashMap<String, UnitClass>();
	static {
        map.put(CD.getValue(), CD);
        map.put(ZL.getValue(), ZL);
        map.put(MJ.getValue(), MJ);
        map.put(TJ.getValue(), TJ);
        map.put(SL.getValue(), SL);
        map.put(OTHER.getValue(), OTHER);
    }

	UnitClass(String value,String name){
		this.name = name;
		this.value = value;
	}
	@Override
	public String getName() {
		return this.name;
	}
	@Override
	public String getValue() {
		return this.value;
	}
	@Override
	public UnitClass getEnum(String value) {
		return map.get(value);
	}

}
