package com.myapp.enums.ec;

import java.util.HashMap;
import java.util.Map;

import com.myapp.core.base.enums.MyEnum;

public enum ChangeType implements MyEnum<ChangeType>{
	SJBG("SJBG","设计变更"),JSQS("JSQS","技术洽商");

	private String name;
	private String value;
	private static final Map<String, ChangeType> map = new HashMap<String, ChangeType>();
	static {
        map.put(SJBG.getValue(), SJBG);
        map.put(JSQS.getValue(), JSQS);
    }

	ChangeType(String value,String name){
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return this.name;
	}

	public String getValue() {
		return this.value;
	}

	public ChangeType getEnum(String value) {
		return map.get(value);
	}
}
