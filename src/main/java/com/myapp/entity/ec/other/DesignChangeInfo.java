package com.myapp.entity.ec.other;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.myapp.core.annotation.MyEntityAnn;
import com.myapp.core.base.entity.CoreBaseBillInfo;
import com.myapp.enums.ec.ChangeType;
import com.myapp.enums.ec.DisclosureType;

/**
 * 包路径：com.myapp.entity.ec.drawing
 * 功能说明：
 * 创建人： 卢松
 * 创建时间: 2017-11-03 14:52
 */
@Entity
@Table(name="t_ec_designChange")
public class DesignChangeInfo extends CoreBaseBillInfo{

	private ChangeType changeType;//变更类型
	
	private String changePlace;//变更部位
	
	private String changeContent;//变更内容
	
	private Date changeDate;//变更日期
	
	private DisclosureType disclosureType;//交底情况
	
	private String remark;//备注

	@Column(name="fchangeType",length = 20)
	public ChangeType getChangeType() {
		return changeType;
	}

	public void setChangeType(ChangeType changeType) {
		this.changeType = changeType;
	}
	
	@Column(name="fchangePlace",length = 200)
	public String getChangePlace() {
		return changePlace;
	}

	public void setChangePlace(String changePlace) {
		this.changePlace = changePlace;
	}

	@Column(name="fchangeContent",length = 250)
	public String getChangeContent() {
		return changeContent;
	}

	public void setChangeContent(String changeContent) {
		this.changeContent = changeContent;
	}

	@Column(name="fchangeDate")
	public Date getChangeDate() {
		return changeDate;
	}

	public void setChangeDate(Date changeDate) {
		this.changeDate = changeDate;
	}

	@Column(name="fdisclosureType",length = 20)
	public DisclosureType getDisclosureType() {
		return disclosureType;
	}

	public void setDisclosureType(DisclosureType disclosureType) {
		this.disclosureType = disclosureType;
	}

	@Column(name="fremark",length = 200)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
