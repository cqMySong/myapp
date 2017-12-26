package com.myapp.entity.ec.labour;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.myapp.core.annotation.MyEntityAnn;
import com.myapp.entity.ec.EcProjectBaseBillInfo;
import com.myapp.entity.ec.plan.ProjectTotalPlanItemInfo;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年12月23日 
 * @system:民工工资表
 *-----------MySong---------------
 */
@MyEntityAnn(name="民工工资表")
@Entity
@Table(name="t_ec_prolabourwage")
public class ProLabourWageInfo extends EcProjectBaseBillInfo {
	private String labourGroup;//班组名
	private Set<ProLabourWageItemInfo> labourwageItem;
	
	@Column(name="flabourgroup",length=100)
	public String getLabourGroup() {
		return labourGroup;
	}
	public void setLabourGroup(String labourGroup) {
		this.labourGroup = labourGroup;
	}
	@OneToMany(cascade={CascadeType.ALL},mappedBy="parent")
	@OrderBy("seq ASC")
	public Set<ProLabourWageItemInfo> getLabourwageItem() {
		if(labourwageItem==null) labourwageItem = new HashSet<ProLabourWageItemInfo>();
		return labourwageItem;
	}
	public void setLabourwageItem(Set<ProLabourWageItemInfo> labourwageItem) {
		this.labourwageItem = labourwageItem;
	}
	
}
