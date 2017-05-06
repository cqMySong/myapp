package com.myapp.core.base.usertype;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;
import org.springframework.util.ObjectUtils;

import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.util.HibernateHelpper;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年5月4日 
 * @system:
 *
 *-----------MySong---------------
 */
public class MyEnumType implements UserType, ParameterizedType {
	private int[] TYPES = null;
	private Class<Enum<?>> enumClass;
	private Object enumObject;
	private Method _getValue;
	private Method _getEnum;
	public void setParameterValues(Properties parameters) {
		if (parameters != null) {
            try {
            	Object obj = parameters.get("enumClass");
            	if(obj!=null){
            		enumClass = (Class<Enum<?>>) Class.forName(obj.toString());
                    enumObject = enumClass.getEnumConstants()[0];
                    _getValue = enumClass.getMethod("getValue");
                    Type returnType = _getValue.getGenericReturnType();// 
                    TYPES = new int[]{HibernateHelpper.javaType2SqlType(returnType)};
                    _getEnum = enumClass.getMethod("getEnum",new Class[] {HibernateHelpper.javaType2Class(returnType)});
            	}
                
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

	}

	public int[] sqlTypes() {
		return TYPES;
	}

	public Class<?> returnedClass() {
		return enumClass;
	}

	public boolean equals(Object x, Object y) throws HibernateException {
		return ObjectUtils.nullSafeEquals(x, y);
	}

	public int hashCode(Object x) throws HibernateException {
		return x.hashCode();
	}

	public Object nullSafeGet(ResultSet rs, String[] names,
			SessionImplementor session, Object owner)
			throws HibernateException, SQLException {
		Object value = rs.getObject(names[0]);
        Object returnVal = null;
        if(!BaseUtil.isEmpty(value)){
        	try {
                returnVal = _getEnum.invoke(enumObject, new Object[] { value });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return returnVal;
	}

	public void nullSafeSet(PreparedStatement st, Object value, int index,
			SessionImplementor session) throws HibernateException, SQLException {
        if (value == null) {
            st.setObject(index, null);
        } else {
            try {
            	st.setObject(index, _getValue.invoke(value));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
	}

	public Object deepCopy(Object value) throws HibernateException {
		return value;
	}

	public boolean isMutable() {
		return false;
	}

	public Serializable disassemble(Object value) throws HibernateException {
		Object deepCopy = deepCopy(value);
        if (!(deepCopy instanceof Serializable)) return (Serializable) deepCopy;
        return null;
	}

	public Object assemble(Serializable cached, Object owner)
			throws HibernateException {
		return deepCopy(cached);
	}

	public Object replace(Object original, Object target, Object owner)
			throws HibernateException {
		return deepCopy(original);
	}
}
