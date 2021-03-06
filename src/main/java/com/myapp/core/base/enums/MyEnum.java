package com.myapp.core.base.enums;


/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年5月3日 
 * @param <T>
 * @system:
 *
 *-----------MySong---------------
 */
public interface MyEnum <E extends Enum<?>> {
    E getEnum(String value);
    public String getName();
    public Object getValue();
}
