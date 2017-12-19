package com.myapp.core.enums;

import com.myapp.core.base.enums.MyEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * @path：com.myapp.core.enums
 * @description：费用类型
 * @author： ly
 * @date: 2017-11-23 23:54
 */
public enum ExpenseType implements MyEnum<ExpenseType> {
    MATERIAL("MATERIAL","材料费"),
    ARTIFICIAL("ARTIFICIAL","人工费"),
    EQUIPMENT("EQUIPMENT","机械费"),
    OTHER("OTHER","其它");

    private String name;
    private String value;

    public static final Map<String, ExpenseType> map = new HashMap<String, ExpenseType>();
    static {
        map.put(MATERIAL.getValue(), MATERIAL);
        map.put(ARTIFICIAL.getValue(), ARTIFICIAL);
        map.put(EQUIPMENT.getValue(), EQUIPMENT);
        map.put(OTHER.getValue(), OTHER);
    }

    ExpenseType(String value, String name){
        this.name = name;
        this.value = value;
    }
    @Override
    public String getName() {
        return this.name;
    }
    @Override
    public String getValue() {
        return this.value;
    }
    @Override
    public ExpenseType getEnum(String value) {
        return map.get(value);
    }
}
