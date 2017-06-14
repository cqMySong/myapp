package com.myapp.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.myapp.core.enums.PermissionTypeEnum;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年6月6日 
 * @system:
 * 权限项控制
 *-----------MySong---------------
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface PermissionItemAnn {
	public String name();
	public String number();
	public PermissionTypeEnum type() default PermissionTypeEnum.FUNCTION;
}
