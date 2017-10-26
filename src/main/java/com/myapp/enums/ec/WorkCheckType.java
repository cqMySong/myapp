package com.myapp.enums.ec;

import java.util.HashMap;
import java.util.Map;

import com.myapp.core.base.enums.MyEnum;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年10月23日 
 * @system: 施工检查分类
 * 安全员：施工现场日、周、月检表
 *-----------MySong---------------
 */
public enum WorkCheckType implements MyEnum<WorkCheckType> {

	JSJ("JSJ","脚手架") ,JKZH("JKZH","基坑支护"),MBGC("MBGC","模板工程")
	,SBSK("SBSK","三宝四口防护"),SGYD("SGYD","施工用电"),SJDT("SJDT","施工升降机(人货两用电梯)")
	,TD("TD","塔吊"),SGJJ("SGJJ","施工机具"),WMSG("WMSG","文明施工")
	,QT("QT","其他");

	private String name;
	private String value;
	private static final Map<String, WorkCheckType> map = new HashMap<String, WorkCheckType>();
	static {
		map.put(JSJ.getValue(), JSJ);
        map.put(JKZH.getValue(), JKZH);
        map.put(MBGC.getValue(), MBGC);
        map.put(SBSK.getValue(), SBSK);
        map.put(SGYD.getValue(), SGYD);
        map.put(SJDT.getValue(), SJDT);
        map.put(TD.getValue(), TD);
        map.put(SGJJ.getValue(), SGJJ);
        map.put(WMSG.getValue(), WMSG);
        map.put(QT.getValue(), QT);
    }

	WorkCheckType(String value,String name){
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return this.name;
	}

	public String getValue() {
		return this.value;
	}

	public WorkCheckType getEnum(String value) {
		return map.get(value);
	}

}
