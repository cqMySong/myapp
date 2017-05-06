package com.myapp.core.util;

import java.lang.reflect.Method;

import org.aspectj.apache.bcel.classfile.Constant;

import com.myapp.core.base.enums.MyEnum;
import com.myapp.core.enums.Sex;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年5月6日 
 * @system:
 *  enum的一些常用操作工具
 *  ** 特殊说明 
 *-----------MySong---------------
 */
public class EnumUtil {
	public static <T extends MyEnum>  T getEnum(String enum_str,Object value){
		try{
			Class<Enum<?>> enumClass = (Class<Enum<?>>) Class.forName(enum_str);
			Object enumObject = enumClass.getEnumConstants()[0];
			
			Method method = enumClass.getMethod("getEnum",new Class[] {value.getClass()});
			return (T) method.invoke(enumObject, value);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Object[] getEnums(String enum_str){
		try {
			Class<Enum<?>> enumClass = (Class<Enum<?>>) Class.forName(enum_str);
			Object enumObject = enumClass.getEnumConstants()[0];
			return (Object[]) enumClass.getMethod("values").invoke(enumObject);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args){
		Object[] objs = getEnums(Sex.class.getName());
		for(Object sx:objs){
			System.out.println(((Sex)sx).getName());
		}
		Object obj = getEnum(Sex.class.getName(),"WOMAN");
		System.out.println(obj.toString());
	}
}
