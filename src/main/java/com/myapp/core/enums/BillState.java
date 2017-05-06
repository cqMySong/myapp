package com.myapp.core.enums;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.myapp.core.base.enums.MyEnum;
import com.myapp.core.util.BaseUtil;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年5月5日 
 * @system:
 *
 *-----------MySong---------------
 */
public enum BillState implements MyEnum<BillState,String> {
	SAVE("SAVE","保存"),SUBMIT("SUBMIT","提交"),AUDIT("AUDIT","已审核"),NOPASS("NOPASS","审核不通过");

	private String name;
	private String value;
	private static final Map<String, BillState> map = new HashMap<String, BillState>();
	static {
        map.put(SAVE.getValue(), SAVE);
        map.put(SUBMIT.getValue(), SUBMIT);
        map.put(AUDIT.getValue(), AUDIT);
        map.put(NOPASS.getValue(), NOPASS);
    }

	BillState(String value,String name){
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return this.name;
	}

	public String getValue() {
		return this.value;
	}

	public BillState getEnum(String value) {
		return map.get(value);
	}

}
