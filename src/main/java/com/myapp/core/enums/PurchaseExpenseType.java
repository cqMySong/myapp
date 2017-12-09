package com.myapp.core.enums;

import com.myapp.core.base.enums.MyEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * @path：com.myapp.core.enums
 * @description：采购费用类型
 * @author： ly
 * @date: 2017-11-23 23:54
 */
public enum PurchaseExpenseType implements MyEnum<PurchaseExpenseType> {
    MATERIAL("MATERIAL","材料费"),
    EQUIPMENT("EQUIPMENT","机械费"),
    OTHER("OTHER","其他");

    private String name;
    private String value;

    public static final Map<String, PurchaseExpenseType> map = new HashMap<String, PurchaseExpenseType>();
    static {
        map.put(MATERIAL.getValue(), MATERIAL);
        map.put(EQUIPMENT.getValue(), EQUIPMENT);
        map.put(OTHER.getValue(), OTHER);
    }

    PurchaseExpenseType(String value, String name){
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
    public PurchaseExpenseType getEnum(String value) {
        return map.get(value);
    }
}
