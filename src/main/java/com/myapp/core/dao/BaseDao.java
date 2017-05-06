package com.myapp.core.dao;
import java.io.Serializable;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.myapp.core.base.dao.IAbstractBaseDao;
import com.myapp.core.base.dao.impl.AbstractBaseDao;
@Repository
public class BaseDao extends AbstractBaseDao {
	@Autowired
	private SessionFactory sessionFactory;
	
	public Session getCurrentSession() {
		return getSessionFactory().getCurrentSession();
	}

	public Class getEntryClaz() {
		return null;
	}

	public SessionFactory getSessionFactory() {
		return this.sessionFactory;
	}

	
}