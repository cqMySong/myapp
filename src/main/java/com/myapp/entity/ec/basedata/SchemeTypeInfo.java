package com.myapp.entity.ec.basedata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.myapp.core.base.entity.CoreBaseDataInfo;
import com.myapp.enums.ec.WorkSchemeGroup;

/**
 * 施工方案类型
 * @author Phoenix
 * @updator mySong 修改成系统级的施工方案库 包括类别和分类
 */
@Entity
@Table(name="t_ec_schemeType")
public class SchemeTypeInfo extends CoreBaseDataInfo {
	private WorkSchemeGroup workSchemeGroup;

	@Column(name="fworkchecktype",length=10)
	@Type(type="myEnum",parameters={@Parameter(name="enumClass",value="com.myapp.enums.ec.WorkSchemeGroup")})
	public WorkSchemeGroup getWorkSchemeGroup() {
		return workSchemeGroup;
	}

	public void setWorkSchemeGroup(WorkSchemeGroup workSchemeGroup) {
		this.workSchemeGroup = workSchemeGroup;
	}
	
	
}
