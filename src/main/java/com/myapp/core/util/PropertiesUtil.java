package com.myapp.core.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import com.alibaba.fastjson.JSONObject;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年10月29日 
 * @system: 属性Properties 文件的读写操作工作类
 *
 *-----------MySong---------------
 */
public abstract class PropertiesUtil {
	
	public static ResourceBundle getResourceBundle(String fileName){
		if(BaseUtil.isEmpty(fileName)) return null;
		return ResourceBundle.getBundle(fileName,Locale.CHINESE);
	}
	
	public static String getProperty(String fileName,String key){
		return getProperty(getResourceBundle(fileName),key);
	}
	
	public static String getProperty(ResourceBundle rb ,String key){
		if(rb==null||BaseUtil.isEmpty(key)) return "";
		if(rb!=null) return BaseUtil.strTransUtfCode(rb.getString(key),"ISO-8859-1");
		return "";
	}
	
	public static Map<String,String> getProperties(String fileName){
		return getProperties(getResourceBundle(fileName));
	}
	
	public static Map<String,String> getProperties(ResourceBundle rb){
		if(rb==null) return null;
		Set<String> keys = rb.keySet();
		Map<String,String> pps= new HashMap<String,String>();
		for(String key:keys){
			pps.put(key, getProperty(rb,key));
		}
		return pps;
	}
	
	public static void main(String[] args){
		System.out.println(getProperty("dataCode", "safedata"));
		System.out.println(JSONObject.toJSONString(getProperties("dataCode")));
	}
	
	
}
