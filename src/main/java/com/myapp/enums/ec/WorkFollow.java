package com.myapp.enums.ec;

import java.util.HashMap;
import java.util.Map;

import com.myapp.core.base.enums.MyEnum;

/**
 * 工作跟进
 * @author Phoenix
 *
 */
public enum WorkFollow implements MyEnum<WorkFollow>{

	NORMAL("NORMAL","正常"),ABNORMAL("ABNORMAL","非正常");

	private String name;
	private String value;
	private static final Map<String, WorkFollow> map = new HashMap<String, WorkFollow>();
	static {
        map.put(NORMAL.getValue(), NORMAL);
        map.put(ABNORMAL.getValue(), ABNORMAL);
    }

	WorkFollow(String value,String name){
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return this.name;
	}

	public String getValue() {
		return this.value;
	}

	public WorkFollow getEnum(String value) {
		return map.get(value);
	}
}
