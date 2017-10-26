package com.myapp.entity.ec.basedata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.myapp.core.base.entity.CoreBaseDataInfo;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年10月26日 
 * @system:
 * 施工现场安全用电
 *-----------MySong---------------
 */
@Entity
@Table(name="t_ec_safeusepower")
public class SafeUsePowerInfo extends CoreBaseDataInfo {
	private String standard;//用电标准

	@Column(name="fstandard",length=200)
	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}
	
}
