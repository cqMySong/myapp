package com.myapp.core.entity.basedate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.myapp.core.base.entity.CoreBaseTreeInfo;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年10月29日 
 * @system:
 * 核心数据分组资料库
 *-----------MySong---------------
 */
@Entity
@Table(name="t_bd_datagroup")
public class DataGroupInfo extends CoreBaseTreeInfo<DataGroupInfo> {
	
	private String code;//数据模块编码
	
	@Column(name="fcode",nullable=false)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}
