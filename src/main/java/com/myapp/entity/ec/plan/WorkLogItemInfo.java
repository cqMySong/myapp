package com.myapp.entity.ec.plan;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.myapp.core.annotation.MyEntityAnn;
import com.myapp.core.base.entity.CoreBaseEntryInfo;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年8月20日 
 * @system:
 * 工作内容明细
 *-----------MySong---------------
 */
@MyEntityAnn(name="工作日志明细")
@Entity
@Table(name="t_ec_workLogItem")
public class WorkLogItemInfo extends CoreBaseEntryInfo<WorkLogInfo> {
	private String workContent;//工作内容
	private String dutyer;//施工负责人
	
	@Column(name="fworkContent",length=200)
	public String getWorkContent() {
		return workContent;
	}
	public void setWorkContent(String workContent) {
		this.workContent = workContent;
	}
	@Column(name="fdutyer",length=80)
	public String getDutyer() {
		return dutyer;
	}
	public void setDutyer(String dutyer) {
		this.dutyer = dutyer;
	}
	
	
}
