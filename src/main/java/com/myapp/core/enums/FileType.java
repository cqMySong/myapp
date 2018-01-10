package com.myapp.core.enums;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.myapp.core.base.enums.MyEnum;
import com.myapp.core.util.BaseUtil;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2018年1月8日 
 * @system:
 *
 *-----------MySong---------------
 */
public enum FileType implements MyEnum<FileType> {
	DOC("DOC","word"),EXCEL("EXCEL","excel"),PPT("PPT","ppt"),ZIP("ZIP","压缩文件"),IMG("IMG","图片");

	private String name;
	private String value;
	private static final Map<String, FileType> map = new HashMap<String, FileType>();
	static {
        map.put(DOC.getValue(), DOC);
        map.put(EXCEL.getValue(), EXCEL);
        map.put(PPT.getValue(), PPT);
        map.put(ZIP.getValue(), ZIP);
        map.put(IMG.getValue(), IMG);
    }

	FileType(String value,String name){
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return this.name;
	}

	public String getValue() {
		return this.value;
	}

	public FileType getEnum(String value) {
		return map.get(value);
	}

}
