package com.myapp.core.base.service;

import java.io.Serializable;
import java.util.List;

import com.myapp.core.base.entity.CoreInfo;
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
	public Serializable addNewEntity(Object entity);
	public <T> T loadEntity(Class<T> c,String id);
	public <T> T getEntity(Class<T> c,String id);
	public List<CoreInfo> getEntityCollection(Class claz);
	public Object saveEntity(Object entity);
	public void deleteEntity(Class c,String id);
	public void deleteEntity(Object entity);
	public List findByHQL(String hql, Object[] params); 
	public PageModel toPageQuery(Integer curPage,Integer pageSize, String hql, Object[] params);
}
