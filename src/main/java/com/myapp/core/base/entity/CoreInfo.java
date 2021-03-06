package com.myapp.core.base.entity;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.TypeDef;

import com.myapp.core.base.dao.impl.AbstractBaseDao;
import com.myapp.core.base.usertype.MyEnumType;
import com.myapp.core.uuid.UuidUtils;


/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年3月12日 
 * @system:
 *  核心数据模型
 *-----------MySong---------------
 */
@MappedSuperclass
@TypeDef(name = "myEnum", typeClass = MyEnumType.class)
public class CoreInfo implements Serializable{
	private Object beanObj = null;
	private String id;
	private Map fieldEx = new HashMap();
	private static Logger log = null;
	
	@Id
	@Column(name="fid",length=50,unique=true)
	@GenericGenerator(name = "generatePK", strategy = "com.myapp.core.uuid.GeneratePK")
	@GeneratedValue(generator ="generatePK")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public CoreInfo(){
		newInstanceBeanObj();
	}
	
	public void newInstanceBeanObj(){
		beanObj = this;
		log = LogManager.getLogger(beanObj.getClass());
	}
	
	public void put(String property,Object val){
		try {
			if(beanObj!=null)
				PropertyUtils.setProperty(beanObj, property, val);
		} catch ( Exception e) {
//			e.printStackTrace();
			log.error(beanObj.getClass().getSimpleName()+" has no property "+property +" has set fieldEx");
			fieldEx.put(property, val);
		}
	}
	
	public Object get(String property){
		try {
			if(beanObj!=null)
				return PropertyUtils.getProperty(beanObj, property);
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
//			e.printStackTrace();
			log.error(" has no property "+property +" getValue from fieldEx");
			return fieldEx.get(property);
		}
		return null;
	}
	
	public String genEntityType(){
		return UuidUtils.getEntityType(beanObj.getClass().getName());
	}
}
