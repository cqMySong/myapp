package com.myapp.test;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;


public class Test {

	public static Map jsonMap = new HashMap();
	public static void main(String[] args) {
		String[] alias = new String[]{"t_id","t_name","t_pro_id","t_pro_name","t_entry_id","t_entry_com_id"};
		Object[] vals = new Object[]{"1","2","3.1","3.2","4.1","4.2.1"};
		for(int i=0;i<alias.length;i++){
			String a = alias[i];
			String[] aa = a.split("_");
			Object obj = jsonMap;
			for(int j=0;j<aa.length;j++){
				boolean isLeaf = j == aa.length - 1 ? true : false;
				if(!isLeaf){
					obj = getMap(obj, aa[j]);
				}else{
					((Map)obj).put(aa[j], vals[i]);
				}
			}
		}
		System.out.println(jsonMap);
		System.out.println(JSONObject.toJSON(jsonMap));
		
	}
	
	public static Object getMap(Object obj, String key) {
		Map temp = (Map)obj;
		if(!temp.containsKey(key)){
			temp.put(key, new HashMap());
		}
		return temp.get(key);
	}


}
