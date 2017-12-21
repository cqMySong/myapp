package com.myapp.enums.ec;

import java.util.HashMap;
import java.util.Map;

import com.myapp.core.base.enums.MyEnum;
/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年12月21日 
 * @system: 函件类别 
 *-----------MySong---------------
 */
public enum LetterType implements MyEnum<LetterType>{
	LXH("LXH","联系函"),TZ("TZ","通知"),BG("BG","变更"),QT("QT","其他");
	private String name;
	private String value;
	private static final Map<String, LetterType> map = new HashMap<String, LetterType>();
	static {
        map.put(LXH.getValue(), LXH);
        map.put(TZ.getValue(), TZ);
        map.put(BG.getValue(), BG);
        map.put(QT.getValue(), QT);
    }

	LetterType(String value,String name){
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return this.name;
	}

	public String getValue() {
		return this.value;
	}

	public LetterType getEnum(String value) {
		return map.get(value);
	}
}
