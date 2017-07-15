package com.myapp.core.enums;

import java.util.HashMap;
import java.util.Map;

import com.myapp.core.base.enums.MyEnum;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年5月5日 
 * @system:
 *	权限类型 菜单权限和功能权限
 * 菜单，功能，菜单及功能
 *-----------MySong---------------
 */
public enum PermissionTypeEnum implements MyEnum<PermissionTypeEnum>{
	PAGE("PAGE","菜单"),FUNCTION("FUNCTION","功能"),PAGEADDFUNCTION("PAGEADDFUNCTION","菜单&功能");
	private String name;
	private String value;
	private static final Map<String, PermissionTypeEnum> map = new HashMap<String, PermissionTypeEnum>();
	static {
        map.put(PAGE.getValue(), PAGE);
        map.put(FUNCTION.getValue(), FUNCTION);
        map.put(PAGEADDFUNCTION.getValue(), PAGEADDFUNCTION);
    }
	PermissionTypeEnum(String value,String name){
		this.name = name;
		this.value = value;
	}
	public PermissionTypeEnum getEnum(String value) {
		return map.get(value);
	}
	public String getName() {
		return this.name;
	}
	public String getValue() {
		return this.value;
	}
}
