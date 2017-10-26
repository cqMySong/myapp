package com.myapp.entity.ec.basedata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.myapp.core.base.entity.CoreBaseDataInfo;
import com.myapp.enums.ec.TestGroup;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年10月23日 
 * @system: 技术负责人：检验批信息
 *-----------MySong---------------
 */
@Entity
@Table(name="t_ec_bathchtest")
public class BatchTestInfo extends CoreBaseDataInfo {
	private TestGroup testGroup;//检验批分组
	private String content;//分项工程
	
	@Column(name="fskilltype",length=10)
	@Type(type="myEnum",parameters={@Parameter(name="enumClass",value="com.myapp.enums.ec.TestGroup")})
	public TestGroup getTestGroup() {
		return testGroup;
	}
	public void setTestGroup(TestGroup testGroup) {
		this.testGroup = testGroup;
	}
	@Column(name="fcontent",length=500)
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
	
	
}
