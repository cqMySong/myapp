package com.myapp.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.myapp.core.base.entity.CoreBaseDataInfo;
import com.myapp.core.enums.UnitClass;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年10月22日 
 * @system: 计量单位 
 *-----------MySong---------------
 */
@Entity
@Table(name="t_base_measureunit")
public class MeasureUnitInfo extends CoreBaseDataInfo {
	
	private UnitClass unitClass;
	
	@Column(name="funitClass",length=10)
	@Type(type="myEnum",parameters={@Parameter(name="enumClass",value="com.myapp.core.enums.UnitClass")})
	public UnitClass getUnitClass() {
		return unitClass;
	}

	public void setUnitClass(UnitClass unitClass) {
		this.unitClass = unitClass;
	}
	
}
