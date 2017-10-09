package com.myapp.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年6月6日 
 * @system:
 * 权限项控制 包括登陆验证和权限验证
 *-----------MySong---------------
 */
@Inherited  //定义可以被子类继承
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface AuthorAnn {
	public String dese() default "";
	public boolean doLongin() default true;
	public boolean doPermission() default true;
}
