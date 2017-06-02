package com.myapp.core.base.entity;

import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.springframework.beans.BeanWrapperImpl;

import com.myapp.core.entity.BaseOrgInfo;
import com.myapp.core.entity.UserInfo;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年4月29日 
 * @system:
 *
 *-----------MySong---------------
 */
@MappedSuperclass
public class CoreBaseInfo extends CoreInfo {
	private String name;
	private String number;
	private Date createDate;
	private Date lastUpdateDate;
	@Column(name="fcreateDate")
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	@Column(name="flastUpdateDate")
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	@Column(name="fname",length=200)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(name="fnumber",length=100,unique=true)
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	
	public String toString() {
		return getName();
	}
	// alias 规则 t_id, t_pro_id,t_pro_name,t_entry_seq,t_entry_pro_id
		//t:{id:"",pro:{id:""}}
		
		private static Map createDataModel(Map dtMap,Object val,String alias){
			Map map = (Map) dtMap.get("alias");
			if(alias.indexOf("_")>0) {
				String strf = alias.substring(alias.lastIndexOf("_")+1,alias.length());
				alias = alias.substring(0, alias.lastIndexOf("_"));
				if(map==null){
					 map = createDataModel(dtMap,new HashMap(),alias);
				}
			}
			if(map==null) map = new HashMap();
			map.put(alias, val);
			return map;
		}
	public static void main(String[] args){
		String[] ailas = new String[]{"t_id","t_pro_id","t_pro_name","t_entry_seq","t_entry_pro_id"};
		String[] vals = new String[]{"1","2","3","4","5"};
		Map mt = new HashMap();
		for(int i=0;i<vals.length;i++){
			Map mp = createDataModel(mt,vals[i],ailas[i]);
		}
	
//		String str = "t_entry_pro_id";
//		while(str.indexOf("_")>=0){
//			String strf = str.substring(str.lastIndexOf("_")+1,str.length());
//			str = str.substring(0, str.lastIndexOf("_"));
//			System.out.println(str + " "+ strf);
//		}
	}
}
