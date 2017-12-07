package com.myapp.entity.ec.basedata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.myapp.core.base.entity.CoreBaseTreeInfo;
import com.myapp.enums.ec.ProWbsType;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年12月4日 
 * @system:
 *
 *-----------MySong---------------
 */
@Entity
@Table(name="t_ec_probasewbs")
public class ProBaseWbsInfo extends CoreBaseTreeInfo<ProBaseWbsInfo> {
	private ProWbsType wbsType;

	@Column(name="fwbstype",length=20)
	@Type(type="myEnum",parameters={@Parameter(name="enumClass",value="com.myapp.enums.ec.ProWbsType")})
	public ProWbsType getWbsType() {
		return wbsType;
	}

	public void setWbsType(ProWbsType wbsType) {
		this.wbsType = wbsType;
	}
	
}
