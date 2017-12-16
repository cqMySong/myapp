package com.myapp.core.base.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;

import com.myapp.core.exception.db.AddNewException;
import com.myapp.core.exception.db.DeleteException;
import com.myapp.core.exception.db.QueryException;
import com.myapp.core.exception.db.ReadException;
import com.myapp.core.exception.db.SaveException;
import com.myapp.core.exception.db.UpdateException;
import com.myapp.core.model.PageModel;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年3月27日 
 * @system:
 *
 *-----------MySong---------------
 */
public interface IAbstractBaseDao {
	public Serializable addNewEntity(Object entity) throws AddNewException;
	public Object loadEntity(Class claz,String id) throws ReadException;
	public Object getEntity(Class claz,String id) throws ReadException;
	public Object queryEntity(Class claz,String hql,Object[] params) throws ReadException;
	public List getEntityCollection(Class claz) throws ReadException;
	public Object saveEntity(Object entity) throws SaveException;
	public void deleteEntity(Class claz,String id) throws DeleteException;
	public void deleteEntity(Object entity) throws DeleteException;
	public List findByHQL(String hql, Object[] params) throws QueryException; 
	public Criteria findByDetachedCriteria(Class claz,DetachedCriteria dca) throws QueryException;
	public PageModel toPageQuery(Integer curPage,Integer pageSize, String hql, Object[] params) throws QueryException;
	public PageModel toPageDetachedCriteria(Class claz,DetachedCriteria dca,ProjectionList pList,Integer curPage, Integer pageSize) throws QueryException;
	public Criteria initQueryCriteria(Class claz)throws QueryException;
	public Criteria initQueryCriteria(String entityName)throws QueryException;
	public void executeUpdata(String hql,Object[] params) throws UpdateException;
	public List executeSQLQuery(String sql,Object[] params) throws QueryException;
	public PageModel toPageSqlQuery(Integer curPage,Integer pageSize, String sql, Object[] params) throws QueryException;
}
