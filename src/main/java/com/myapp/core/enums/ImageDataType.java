package com.myapp.core.enums;

import com.myapp.core.base.enums.MyEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * @path：com.myapp.core.enums
 * @description：
 * @author： ly
 * @date: 2018-01-10 23:11
 */
public enum ImageDataType implements MyEnum<ImageDataType> {
    IMAGE("IMAGE","图片"),
    VIDEO("VIDEO","视频");

    private String name;
    private String value;

    ImageDataType(String value,String name){
        this.name = name;
        this.value = value;
    }
    private static final Map<String, ImageDataType> map = new HashMap<String, ImageDataType>();

    static {
        map.put(IMAGE.getValue(), IMAGE);
        map.put(VIDEO.getValue(), VIDEO);
    }

    @Override
    public ImageDataType getEnum(String value) {
        return map.get(value);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getValue() {
        return this.value;
    }
}
