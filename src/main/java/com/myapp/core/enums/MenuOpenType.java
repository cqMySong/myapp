package com.myapp.core.enums;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.myapp.core.base.enums.MyEnum;
import com.myapp.core.util.BaseUtil;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年11月05日 
 * @system:
 * 
 *-----------MySong---------------
 */
public enum MenuOpenType implements MyEnum<MenuOpenType> {
	MAINTAB("MAINTAB","主选项卡"),NEWWIN("NEWWIN","新窗口"),MODEL("MODEL","弹窗口");

	private String name;
	private String value;
	private static final Map<String, MenuOpenType> map = new HashMap<String, MenuOpenType>();
	static {
		map.put(MAINTAB.getValue(), MAINTAB);
        map.put(NEWWIN.getValue(), NEWWIN);
        map.put(MODEL.getValue(), MODEL);
    }

	MenuOpenType(String value,String name){
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return this.name;
	}

	public String getValue() {
		return this.value;
	}

	public MenuOpenType getEnum(String value) {
		return map.get(value);
	}

}
