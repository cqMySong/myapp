package com.myapp.entity.ec.basedata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.myapp.core.base.entity.CoreBaseDataInfo;
import com.myapp.enums.UnitType;

@Entity
@Table(name="t_ec_ecUnit")
public class ECUnitInfo extends CoreBaseDataInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6051198973176569650L;
	private UnitType unitType;//结构类型

	@Column(name="unitType",length=20)
	@Type(type="myEnum",parameters={@Parameter(name="enumClass",value="com.myapp.enums.UnitType")})
	public UnitType getUnitType() {
		return unitType;
	}

	public void setUnitType(UnitType unitType) {
		this.unitType = unitType;
	}
	
	
	
}
