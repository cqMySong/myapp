package com.myapp.core.base.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.transform.ResultTransformer;
import org.springframework.beans.BeanWrapperImpl;

import com.myapp.core.base.entity.CoreBaseInfo;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年5月25日 
 * @system:
 *
 *-----------MySong---------------
 */
public class MyResultTransFormer implements ResultTransformer {
	private Class entityClaz;
	private List data;
	private Object entityObj;
	private BeanWrapperImpl beanWrapper;
	private Map dataMap = new HashMap();
	public MyResultTransFormer(Class claz){
		super();
		this.entityClaz = claz;
		this.beanWrapper = new BeanWrapperImpl(claz);
	}
	// alias 规则 t_id, t_pro_id,t_pro_name,t_entry_seq,t_entry_pro_id
	//t:{id:"",pro:{id:""}}
	
	private Map createDataModel(Map dtMap,Object val,String alias){
		String strf = alias.substring(alias.lastIndexOf("_")+1,alias.length());
		alias = alias.substring(0, alias.lastIndexOf("_"));
		Map map = (Map) dtMap.get("alias");
		if(map==null){
			 map = createDataModel(dtMap,new HashMap(),alias);
		}
		map.put(alias, val);
		return map;
	}
	
	public Object transformTuple(Object[] vals, String[] alias) {
		Map rowMap = new HashMap();
		for(int i=0;i<vals.length;i++){
			rowMap.put(alias[i], vals[i]);
		}
		if(data==null) data = new ArrayList();
		data.add(rowMap);
		return rowMap;
	}

	public List transformList(List collection) {
		if(collection==null||collection.size()==0){
			return new ArrayList();
		}else{
			return this.data;
		}
	}

}
