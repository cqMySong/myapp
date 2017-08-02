package com.myapp.enums;

import com.myapp.core.base.enums.MyEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 包路径：com.myapp.enums
 * 功能说明：
 * 创建人： ly
 * 创建时间: 2017-07-26 21:17
 */
public enum FlowCategory implements MyEnum<FlowCategory> {
    DRAWING("DRAWING","图纸会审","ec/discussiondrawing/addnew"),BUDGET("BUDGET","预算审核","");
    private String name;
    private String value;
    private String path;
    private static final Map<String, FlowCategory> map = new HashMap<String, FlowCategory>();
    static {
        map.put(DRAWING.getValue(), DRAWING);
        map.put(BUDGET.getValue(), BUDGET);
    }

    FlowCategory(String value,String name,String path){
        this.name = name;
        this.value = value;
        this.path = path;
    }

    @Override
    public FlowCategory getEnum(String value) {
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

    public String getPath() { return this.path;}
}
