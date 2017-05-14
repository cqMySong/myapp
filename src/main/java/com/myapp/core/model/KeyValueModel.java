package com.myapp.core.model;
/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年5月14日 
 * @system:
 *  通用的keyvalue 的数据模型
 *-----------MySong---------------
 */
public class KeyValueModel {
	private String key;
	private Object val;
	KeyValueModel(){
		
	}
	public KeyValueModel(String key,Object val){
		this.key = key;
		this.val = val;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public Object getVal() {
		if(val==null) val = "";
		return val;
	}
	public void setVal(Object val) {
		this.val = val;
	}
	
	public String toString() {
		return getKey()+" "+getVal().toString();
	}
	
	
}
