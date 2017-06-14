package com.myapp.core.uuid;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.persister.entity.AbstractEntityPersister;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年3月12日 
 * @system:
 *   自定义主键生成策略
 *-----------MySong---------------
 */
public class GeneratePK implements IdentifierGenerator {

	public Serializable generate(SessionImplementor session, Object obj)
			throws HibernateException {
		Class claz = obj.getClass();
		if(claz!=null){
			String objectType = UuidUtils.getEntityType(claz.getName());
			SysUuid uid = SysUuid.create(SysObjectType.create(objectType));
			System.out.println(uid.toString());
			return uid.toString();
		}
		return null;
	}
}
