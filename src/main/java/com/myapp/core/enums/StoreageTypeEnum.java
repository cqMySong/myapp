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
public enum StoreageTypeEnum implements MyEnum<StoreageTypeEnum,String> {
	FTP("FTP","FTP服务器"),DATABASE("DATABASE","数据库服务器"),APP("APP","应用服务器");

	private String name;
	private String value;
	private static final Map<String, StoreageTypeEnum> map = new HashMap<String, StoreageTypeEnum>();
	static {
        map.put(FTP.getValue(), FTP);
        map.put(DATABASE.getValue(), DATABASE);
        map.put(APP.getValue(), APP);
    }

	StoreageTypeEnum(String value,String name){
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return this.name;
	}

	public String getValue() {
		return this.value;
	}

	public StoreageTypeEnum getEnum(String value) {
		return map.get(value);
	}

}
