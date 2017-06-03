package com.myapp.core.service.base;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myapp.core.base.service.impl.AbstractBaseService;
/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年5月1日 
 * @system:
 *  最基础通用都Service 方法，根据实际情况自己初始化
 *  	对应的Entity方法newServicInstance
 *-----------MySong---------------
 */
@Transactional
@Service
public class BaseService extends AbstractBaseService{
	private Class claz;
	protected Class getEntityClass() {
		return this.claz;
	}
	/*
	 * 调用此方法服务器方法 必须初始化当前Service都Entity
	 */
	public BaseService newServicInstance(Class clazz){
		this.claz = clazz;
		return this;
	}
}
