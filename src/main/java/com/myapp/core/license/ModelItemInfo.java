package com.myapp.core.license;

import java.io.Serializable;

/**
 * -----------MySong--------------- 
 * ©MySong基础框架搭建
 * @author mySong @date 2018年1月23日
 * @system:
 * license 功能模块
 *-----------MySong---------------
 */
public class ModelItemInfo implements Serializable{
	private String entityClaz;//实体class路径
	private String name;// 模块名
	private String remark;// 描述说明
	private int count = 1;// 个数
	ModelItemInfo(){
		
	}
	
	ModelItemInfo(String entityClaz,String name){
		this.name = name;
		this.entityClaz = entityClaz;
	}
	ModelItemInfo(String entityClaz,String name,String remark){
		this.name = name;
		this.entityClaz = entityClaz;
		this.remark = remark;
	}
	ModelItemInfo(String entityClaz,String name,int count){
		this.name = name;
		this.entityClaz = entityClaz;
		this.count = count;
		this.remark = "";
	}
	ModelItemInfo(String entityClaz,String name,int count,String remark){
		this.name = name;
		this.entityClaz = entityClaz;
		this.count = count;
		this.remark = remark;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}

	public String getEntityClaz() {
		return entityClaz;
	}

	public void setEntityClaz(String entityClaz) {
		this.entityClaz = entityClaz;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
