package com.myapp.enums.ec;

import java.util.HashMap;
import java.util.Map;

import com.myapp.core.base.enums.MyEnum;

/**
 * 
 * @author Phoenix
 *
 */
public enum DisclosureType implements MyEnum<DisclosureType> {

	YJD("YJD", "已交底"), WJD("WJD", "未交底"), WXJD("WXJD", "无需交底");

	private String name;
	private String value;
	private static final Map<String, DisclosureType> map = new HashMap<String, DisclosureType>();

	static {
		map.put(YJD.getValue(), YJD);
		map.put(WJD.getValue(), WJD);
		map.put(WXJD.getValue(), WXJD);
	}

	DisclosureType(String value, String name) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return this.name;
	}

	public String getValue() {
		return this.value;
	}

	public DisclosureType getEnum(String value) {
		return map.get(value);
	}

}
