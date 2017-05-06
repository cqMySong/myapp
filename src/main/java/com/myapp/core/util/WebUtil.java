package com.myapp.core.util;
/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年5月6日 
 * @system:
 *  处理一些页面请求方面的工具类
 *-----------MySong---------------
 */
public class WebUtil {
	
	public static String UUID_ReplaceID(String id){
		if(!BaseUtil.isEmpty(id)){
			boolean go = true;
			while(go){
				if(id.indexOf("%2B")>=0){
					id = id.replaceAll("%2B", "+");
				}
				if(id.indexOf(" ")>=0){
					id = id.replaceAll(" ", "+");
				}
				
				if(id.indexOf("%3D")>=0){
					id = id.replaceAll("%3D", "=");
				}
				if(id.indexOf("%2F")>=0){
					id = id.replaceAll("%2F", "/");
				}
				go = id.indexOf("%2B")>=0||id.indexOf("%3D")>=0||id.indexOf("%2F")>=0;
			}
		}
		return id;
	}
}
