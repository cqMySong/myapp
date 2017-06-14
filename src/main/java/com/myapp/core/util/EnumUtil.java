package com.myapp.core.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.myapp.core.base.enums.MyEnum;
import com.myapp.core.enums.Sex;
import com.myapp.core.model.KeyValueModel;

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
	
	public static List<KeyValueModel> getEnumKvs(String enum_str){
		List<KeyValueModel> items = new ArrayList<KeyValueModel>();
		Object[] enum_obj = getEnums(enum_str);
		if(enum_obj!=null&&enum_obj.length>0){
			for(Object item:enum_obj){
				if(item instanceof MyEnum){
					MyEnum me = (MyEnum) item;
					items.add(new KeyValueModel(me.getValue().toString(),me.getName()));
				}
			}
		}
		return items;
	}
	
	public static void main(String[] args){
		Object[] objs = getEnums(Sex.class.getName());
		for(Object sx:objs){
			if(sx instanceof Enum){
				Enum sse  = (Enum) sx;
				System.out.println(sse.name()+" = "+sse.valueOf(Sex.class, sse.name()));
			}
		}
		Object obj = getEnum(Sex.class.getName(),"WOMAN");
		if(obj instanceof MyEnum){
			MyEnum me = (MyEnum)obj;
			System.out.println(me.getName()+" == "+me.getValue());
		}
	}
}
