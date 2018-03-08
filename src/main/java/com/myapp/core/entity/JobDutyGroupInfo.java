package com.myapp.core.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.myapp.core.base.entity.CoreBaseTreeInfo;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2018年03月08日 
 * @system:
 * 工作职责分组
 *-----------MySong---------------
 */
@Entity
@Table(name="t_pm_jobDutyGroup")
public class JobDutyGroupInfo extends CoreBaseTreeInfo<JobDutyGroupInfo> {
	
}
