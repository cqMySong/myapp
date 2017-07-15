package com.myapp.core.base.service;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;

import com.myapp.core.base.entity.CoreInfo;
import com.myapp.core.exception.db.AddNewException;
import com.myapp.core.exception.db.DeleteException;
import com.myapp.core.exception.db.QueryException;
import com.myapp.core.model.PageModel;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年3月27日 
 * @system:
 *
 *-----------MySong---------------
 */
public interface IAbstractBaseService {
	public Serializable addNewEntity(Object entity) throws AddNewException;
	public <T> T loadEntity(Class<T> c,String id);
	public <T> T getEntity(Class<T> c,String id);
	public <T> T queryEntity(Class<T> c, String hql, Object[] params);
	public List<CoreInfo> getEntityCollection(Class claz);
	public void deleteEntity(Class c,String id) throws DeleteException;
	public void deleteEntity(Object entity) throws DeleteException;
	public List findByHQL(String hql, Object[] params); 
	public PageModel toPageQuery(Integer curPage,Integer pageSize, String hql, Object[] params);
	public Criteria initQueryCriteria(Class claz)throws QueryException;
	public Criteria initQueryCriteria(String entityName)throws QueryException;
	public List executeSQLQuery(String sql,Object[] params)throws QueryException;
}
