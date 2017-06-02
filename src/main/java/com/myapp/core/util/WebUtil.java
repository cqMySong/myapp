package com.myapp.core.util;

import com.myapp.core.enums.DataTypeEnum;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年5月6日 
 * @system:
 *  处理一些页面请求方面的工具类
 *-----------MySong---------------
 */
public class WebUtil {
	
	public static String Web_DataType_text  = "text";
	public static String Web_DataType_date  = "date";
	public static String Web_DataType_datetime  = "datetime";
	public static String Web_DataType_select  = "select";
	public static String Web_DataType_number  = "number";
	public static String Web_DataType_checekbox  = "checekbox";
	public static String Web_DataType_radio  = "radio";
	public static String Web_DataType_f7  = "f7";
	
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
	
	//网页端传递到服务器端的数据类型对应关系
	public static DataTypeEnum getWebServerDataType(Object type){
		DataTypeEnum dte = DataTypeEnum.STRING;
		if(!BaseUtil.isEmpty(type)){
			type = type.toString().toLowerCase();
			if(type.equals(Web_DataType_number)){
				dte = DataTypeEnum.NUMBER;
			}else if(type.equals(Web_DataType_date)){
				dte = DataTypeEnum.DATE;
			}else if(type.equals(Web_DataType_datetime)){
				dte = DataTypeEnum.DATETIME;
			}else if(type.equals(Web_DataType_text)){
				dte = DataTypeEnum.STRING;
			}else if(type.equals(Web_DataType_checekbox)||type.equals(Web_DataType_radio)){
				dte = DataTypeEnum.BOOLEAN;
			}else if(type.equals(Web_DataType_f7)){
				dte = DataTypeEnum.F7;
			}
		}
		return dte;
	}
	
	
}
