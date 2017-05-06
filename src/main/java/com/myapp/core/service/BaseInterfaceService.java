package com.myapp.core.service;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.model.PageModel;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年5月1日 
 * @system:
 *
 *-----------MySong---------------
 */
@Service("baseInterfaceService")
@Transactional
public class BaseInterfaceService<T> extends AbstractBaseService {
	@Resource
	private BaseService baseService;
	
	private Class claz;
	public BaseInterfaceService(){
		Type type = this.getClass().getGenericSuperclass();
		if(type!=null&&type instanceof ParameterizedType){
			ParameterizedType pt = (ParameterizedType) type;
			Type[] types = pt.getActualTypeArguments();
			if(types!=null&&types.length>0){
				this.claz = (Class)types[0];
			}
		}
		
	}
	
	protected Class getEntityClass() {
		return this.claz;
	}
	
	public BaseService getBaseService(){
		return this.baseService;
	}
	
	public PageModel toQueryPage(Integer curPage, Integer pageSize, String hql,
			Object[] params){
		return toPageQuery(curPage, pageSize, hql, params);
	}

}
