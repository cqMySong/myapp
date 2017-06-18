package com.myapp.enums;

import java.util.HashMap;
import java.util.Map;

import com.myapp.core.base.enums.MyEnum;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年6月15日 
 * @system:
 * 项目状态
 *-----------MySong---------------
 */
public enum ProjectState implements MyEnum<ProjectState,String> {
	SGZB("SGZB","施工准备"),ZZSG("ZZSG","正在施工"),YJG("YJG","已竣工"),YGB("YGB","已关闭"),YDG("YDG","已停工");

	private String name;
	private String value;
	private static final Map<String, ProjectState> map = new HashMap<String, ProjectState>();
	static {
        map.put(SGZB.getValue(), SGZB);
        map.put(ZZSG.getValue(), ZZSG);
        map.put(YJG.getValue(), YJG);
        map.put(YGB.getValue(), YGB);
        map.put(YDG.getValue(), YDG);
    }

	ProjectState(String value,String name){
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return this.name;
	}

	public String getValue() {
		return this.value;
	}

	public ProjectState getEnum(String value) {
		return map.get(value);
	}

}
