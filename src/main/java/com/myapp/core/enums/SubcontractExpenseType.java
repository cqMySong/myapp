package com.myapp.core.enums;

import com.myapp.core.base.enums.MyEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * @path：com.myapp.core.enums
 * @description：分包合同费用类型
 * @author： ly
 * @date: 2017-11-23 23:54
 */
public enum SubcontractExpenseType implements MyEnum<SubcontractExpenseType> {
    ARTIFICIAL("ARTIFICIAL","人工费"),
    OTHER("OTHER","其它");

    private String name;
    private String value;

    public static final Map<String, SubcontractExpenseType> map = new HashMap<String, SubcontractExpenseType>();
    static {
        map.put(ARTIFICIAL.getValue(), ARTIFICIAL);
        map.put(OTHER.getValue(), OTHER);
    }

    SubcontractExpenseType(String value, String name){
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
    public SubcontractExpenseType getEnum(String value) {
        return map.get(value);
    }
}
