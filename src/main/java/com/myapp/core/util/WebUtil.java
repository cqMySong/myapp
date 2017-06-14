package com.myapp.core.util;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletOutputStream;

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
	public static String Web_DataType_checekbox  = "checkbox";
	public static String Web_DataType_radio  = "radio";
	public static String Web_DataType_f7  = "f7";
	
	private final static int BUFFER_SIZE = 1024;  
	
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
	
	public static String UUID2String(String fn){
		if(!BaseUtil.isEmpty(fn)){
			boolean go = true;
			while(go){
				if(fn.indexOf("+")>=0){
					fn = fn.replaceAll("\\+", "%2B");
				}
				if(fn.indexOf("=")>=0){
					fn = fn.replaceAll("=", "%3D");
				}
				if(fn.indexOf("/")>=0){
					fn = fn.replaceAll("/", "%2F");
				}
				go = fn.indexOf("+")>=0||fn.indexOf("=")>=0||fn.indexOf("/")>=0;
			}
		}
		return fn;
	}
	
	public static void inputStreamWrite2Web(InputStream in,ServletOutputStream out) throws IOException {
		if(in==null||out==null) return;
		OutputStream toClient = new BufferedOutputStream(out);
		byte[] data = new byte[BUFFER_SIZE];
		int count = -1;
		while ((count = in.read(data, 0, BUFFER_SIZE)) != -1)
			toClient.write(data, 0, count);
		
		toClient.flush();
		toClient.close();
		in.close();
		
	}
	
}
