package com.myapp.core.enums;

import com.myapp.core.base.enums.MyEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * @path：com.myapp.core.enums
 * @description：业主合同类型
 * @author： ly
 * @date: 2017-11-23 23:54
 */
public enum ProprietorContractType implements MyEnum<ProprietorContractType> {
    BUDGET("BUDGET","预算"),
    SETTLE("SETTLE","结算");

    private String name;
    private String value;

    public static final Map<String, ProprietorContractType> map = new HashMap<String, ProprietorContractType>();
    static {
        map.put(BUDGET.getValue(), BUDGET);
        map.put(SETTLE.getValue(), SETTLE);
    }

    ProprietorContractType(String value, String name){
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
    public ProprietorContractType getEnum(String value) {
        return map.get(value);
    }
}
