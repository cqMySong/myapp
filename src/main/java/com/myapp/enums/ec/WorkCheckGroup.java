package com.myapp.enums.ec;

import java.util.HashMap;
import java.util.Map;

import com.myapp.core.base.enums.MyEnum;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年10月23日 
 * @system: 施工检查分组：日检查，周月检查
 * 安全员：施工现场日、周、月检表
 *-----------MySong---------------
 */
public enum WorkCheckGroup implements MyEnum<WorkCheckGroup> {
	DAY("DAY","日检") ,WEEKMONTH("WEEKMONTH","周(月)检");
	private String name;
	private String value;
	private static final Map<String, WorkCheckGroup> map = new HashMap<String, WorkCheckGroup>();
	static {
		map.put(DAY.getValue(), DAY);
        map.put(WEEKMONTH.getValue(), WEEKMONTH);
    }

	WorkCheckGroup(String value,String name){
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return this.name;
	}

	public String getValue() {
		return this.value;
	}

	public WorkCheckGroup getEnum(String value) {
		return map.get(value);
	}

}
