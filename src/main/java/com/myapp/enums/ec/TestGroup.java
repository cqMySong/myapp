package com.myapp.enums.ec;

import java.util.HashMap;
import java.util.Map;

import com.myapp.core.base.enums.MyEnum;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年10月23日 
 * @system: 检验批分部项目
 *-----------MySong---------------
 */
public enum TestGroup implements MyEnum<TestGroup> {
//	地基与基础
//	主体结构
//	建筑装饰装修
//	屋面
//	建筑给水排水及供暖
//	通风与空调
//	建筑电气
//	智能建筑
//	建筑节能
//	电梯
//	室外设施
//	附属建筑及室外环境

	DJJC("DJJC","地基与基础"),ZTJG("ZTJG","主体结构"),ZSZX("ZSZX","建筑装饰装修")
	,WM("WM","屋面"),PSGN("PSGN","建筑给水排水及供暖"),TFKT("TFKT","通风与空调")
	,JZDQ("JZDQ","建筑电气") ,ZNJZ("ZNJZ","智能建筑"),JZJN("JZJN","建筑节能")
	,DT("DT","电梯"),SWSS("SWSS","室外设施"),SWHJ("SWHJ","附属建筑及室外环境");

	private String name;
	private String value;
	private static final Map<String, TestGroup> map = new HashMap<String, TestGroup>();
	static {
        map.put(DJJC.getValue(), DJJC);
        map.put(ZTJG.getValue(), ZTJG);
        map.put(ZSZX.getValue(), ZSZX);
        map.put(WM.getValue(), WM);
        map.put(PSGN.getValue(), PSGN);
        map.put(TFKT.getValue(), TFKT);
        map.put(JZDQ.getValue(), JZDQ);
        map.put(ZNJZ.getValue(), ZNJZ);
        map.put(JZJN.getValue(), JZJN);
        map.put(DT.getValue(), DT);
        map.put(SWSS.getValue(), SWSS);
        map.put(SWHJ.getValue(), SWHJ);
    }

	TestGroup(String value,String name){
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return this.name;
	}

	public String getValue() {
		return this.value;
	}

	public TestGroup getEnum(String value) {
		return map.get(value);
	}

}
