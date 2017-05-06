package com.myapp.core.base.dao;

import java.io.Serializable;
import java.util.List;

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
 *
 *-----------MySong---------------
 */
public interface IAbstractBaseDao {
	public Serializable addNewEntity(Object entity) throws AddNewException;
	public Object loadEntity(Class claz,String id) throws ReadException;
	public Object getEntity(Class claz,String id) throws ReadException;
	public List getEntityCollection(Class claz) throws ReadException;
	public Object saveEntity(Object entity) throws SaveException;
	public void deleteEntity(Class claz,String id) throws DeleteException;
	public void deleteEntity(Object entity) throws DeleteException;
	public List findByHQL(String hql, Object[] params) throws QueryException; 
	public PageModel toPageQuery(Integer curPage,Integer pageSize, String hql, Object[] params) throws QueryException;
}
