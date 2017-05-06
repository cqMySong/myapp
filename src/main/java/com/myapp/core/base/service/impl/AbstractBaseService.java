package com.myapp.core.base.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.myapp.core.base.dao.IAbstractBaseDao;
import com.myapp.core.base.dao.impl.AbstractBaseDao;
import com.myapp.core.base.service.IAbstractBaseService;
import com.myapp.core.exception.db.AddNewException;
import com.myapp.core.exception.db.DeleteException;
import com.myapp.core.exception.db.QueryException;
import com.myapp.core.exception.db.ReadException;
import com.myapp.core.exception.db.SaveException;
import com.myapp.core.model.PageModel;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年3月27日 
 * @system:
 *-----------MySong---------------
 */
@SuppressWarnings("unchecked")
public abstract class AbstractBaseService implements IAbstractBaseService {
	private static final Logger log = LogManager.getLogger(AbstractBaseDao.class);
	
	protected abstract Class getEntityClass();
	
	@Resource
	public IAbstractBaseDao baseDao;
	
	protected IAbstractBaseDao getBaseDao() {
		return this.baseDao;
	}
	
	public <T> T loadEntity(String id) {
		return (T) loadEntity(getEntityClass(), id);
	}
	
	public <T> T loadEntity(Class<T> c, String id) {
		try {
			return (T) getBaseDao().loadEntity(c, id);
		} catch (ReadException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return null;
	}
	
	public Object getEntity(String id) {
		return getEntity(getEntityClass(),id);
	}
	
	public <T> T getEntity(Class<T> c, String id) {
		try {
			return (T) getBaseDao().getEntity(c, id);
		} catch (ReadException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return null;
	}
	
	public <T> T getEntity(Class<T> c,String hql,Object[] params){
		List datas = findByHQL(hql, params);
		if(datas!=null&&datas.size()>0) {
			return (T) datas.get(0);
		}
		return null;
	}
	 
	
	public <T> T getEntity(String hql,Object[] params){
		return (T) getEntity(getEntityClass(),hql,params);
	}
	
	public <E> List<E> getEntityCollection() {
		return getEntityCollection(getEntityClass());
	}
	
	public List getEntityCollection(Class claz) {
		try {
			return getBaseDao().getEntityCollection(claz);
		} catch (ReadException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return null;
	}
	
	public Object saveEntity(Object entity) {
		try {
			return getBaseDao().saveEntity(entity);
		} catch (SaveException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return null;
	}
	public void deleteEntity(String id) {
		deleteEntity(getEntityClass(), id);
	}
	public void deleteEntity(Class claz, String id) {
		try {
			getBaseDao().deleteEntity(claz, id);
		} catch (DeleteException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
	}
	public void deleteEntity(Object entity) {
		 try {
			getBaseDao().deleteEntity(entity);
		} catch (DeleteException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
	}
	
	public List findByHQL(String hql, Object[] params) {
		try {
			return getBaseDao().findByHQL(hql, params);
		} catch (QueryException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return null;
	}
	public Serializable addNewEntity(Object entity) {
		try {
			return getBaseDao().addNewEntity(entity);
		} catch (AddNewException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return null;
	}
	public PageModel toPageQuery(Integer curPage, Integer pageSize, String hql,
			Object[] params) {
		try {
			return getBaseDao().toPageQuery(curPage, pageSize, hql, params);
		} catch (QueryException e) {
			e.printStackTrace();
		}
		return null;
	}
}
