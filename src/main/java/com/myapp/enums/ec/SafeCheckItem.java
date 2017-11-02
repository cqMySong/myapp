package com.myapp.enums.ec;

import java.util.HashMap;
import java.util.Map;

import com.myapp.core.base.enums.MyEnum;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年10月23日 
 * @system: 安保监督检查内容清单
 * 安全员：安保监督检查内容清单
 *  检查内容 1、当班人员是否在岗；2、当班人员是否随意调岗或换岗；
 *  		3、当班人员是否脱岗；4、当班人员是否瞌睡；
 *  		5、当班人员是否认真值岗；6、当班人员是否及时反映突发、异常情况；
 *  		7、当班人员是否认真、及时记录值班情况等。
 *-----------MySong---------------
 */
public enum SafeCheckItem implements MyEnum<SafeCheckItem> {

	NO1("NO1","当班人员是否在岗") ,NO2("NO2","当班人员是否随意调岗或换岗"),NO3("NO3","当班人员是否脱岗")
	,NO4("NO4","当班人员是否瞌睡"),NO5("NO5","当班人员是否认真值岗")
	,NO6("NO6","当班人员是否及时反映突发、异常情况")
	,NO7("NO7","当班人员是否认真、及时记录值班情况");

	private String name;
	private String value;
	private static final Map<String, SafeCheckItem> map = new HashMap<String, SafeCheckItem>();
	static {
		map.put(NO1.getValue(), NO1);
        map.put(NO2.getValue(), NO2);
        map.put(NO3.getValue(), NO3);
        map.put(NO4.getValue(), NO4);
        map.put(NO5.getValue(), NO5);
        map.put(NO6.getValue(), NO6);
        map.put(NO7.getValue(), NO7);
    }

	SafeCheckItem(String value,String name){
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return this.name;
	}

	public String getValue() {
		return this.value;
	}

	public SafeCheckItem getEnum(String value) {
		return map.get(value);
	}

}
