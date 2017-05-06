package com.myapp.core.util;

import java.lang.reflect.Type;
import java.sql.Types;
import java.util.Date;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年5月5日 
 * @system:
 *
 *-----------MySong---------------
 */
public class HibernateHelpper {
	
	public static int javaType2SqlType(Type type){
		int sqlType =0;
		if(type!=null){
			String typeName = type.getTypeName();
			if(typeName.equals(String.class.getName())){
				sqlType = Types.NVARCHAR;
			}else if(typeName.equals(Integer.class.getName())){
				sqlType = Types.INTEGER;
			}else if(typeName.equals(Float.class.getName())){
				sqlType = Types.FLOAT;
			}else if(typeName.equals(Double.class.getName())){
				sqlType = Types.DOUBLE;
			}else if(typeName.equals(Date.class.getName())){
				sqlType = Types.DATE;
			}
		}
		return sqlType;
	}
	public static Class javaType2Class(Type type){
		Class claz = null;
		if(type!=null){
			String typeName = type.getTypeName();
			if(typeName.equals(String.class.getName())){
				claz = String.class;
			}else if(typeName.equals(Integer.class.getName())){
				claz = Integer.class;
			}else if(typeName.equals(Float.class.getName())){
				claz = Float.class;
			}else if(typeName.equals(Double.class.getName())){
				claz = Double.class;
			}else if(typeName.equals(Date.class.getName())){
				claz = Date.class;
			}
		}
		return claz;
	}
}
