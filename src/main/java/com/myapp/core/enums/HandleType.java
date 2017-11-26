package com.myapp.core.enums;

import com.myapp.core.base.enums.MyEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 办理类型
 * @author :ly
 * @date:2017-10-18
 */
public enum HandleType implements MyEnum<HandleType> {
	NOT_HANDLE("NOT_HANDLE","未办理"),
	IN_PROCESS("IN_PROCESS","办理中"),
	ALREADY_HANDLE("ALREADY_HANDLE","已办理");

	private String name;
	private String value;

	public static final Map<String, HandleType> map = new HashMap<String, HandleType>();
	static {
        map.put(NOT_HANDLE.getValue(), NOT_HANDLE);
		map.put(IN_PROCESS.getValue(), IN_PROCESS);
		map.put(ALREADY_HANDLE.getValue(), ALREADY_HANDLE);
    }

	HandleType(String value, String name){
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
	public HandleType getEnum(String value) {
		return map.get(value);
	}

}
