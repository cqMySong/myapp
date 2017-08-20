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
 * @author mySong @date 2017年6月12日 
 * @system:
 * 基础资料数据结构模型
 *-----------MySong---------------
 */
@MappedSuperclass
public class CoreBaseDataInfo extends CoreBaseInfo {
	private String remark;
	private Boolean enabled;
	
	@Column(name="fremark",length=250)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column(name="fenabled",length=2)
	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
	
}
