package com.myapp.entity.ec.basedata;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.myapp.core.annotation.MyEntityAnn;
import com.myapp.core.base.entity.CoreBaseDataInfo;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年12月12日 
 * @system:
 *-----------MySong---------------
 */
@MyEntityAnn(name="施工工种")
@Entity
@Table(name="t_ec_worktype")
public class WorkTypeInfo extends CoreBaseDataInfo {
	
}
