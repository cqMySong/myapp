package com.myapp.entity.ec.usepower;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.myapp.core.annotation.MyEntityAnn;
import com.myapp.core.base.entity.CoreBaseEntryInfo;
import com.myapp.entity.ec.basedata.SafeUsePowerInfo;
import com.myapp.entity.ec.basedata.WorkCheckItemInfo;
import com.myapp.enums.ec.WorkCheckType;
import com.myapp.enums.ec.YesNoEnum;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年12月13日 
 * @system:
 * 施工现场安全用电检查表
 *-----------MySong---------------
 */
@Entity
@MyEntityAnn(name="施工现场安全用电检查表")
@Table(name="t_ec_prousepowercheckitem")
public class ProUsePowerCheckItemInfo extends CoreBaseEntryInfo<ProUsePowerCheckInfo> {
	private SafeUsePowerInfo usePower;//用电项
	private String standard;//用电标准
	private String checkResult;//检查结论
	private String remark;//备注
	
	@OneToOne
	@JoinColumn(name = "fusePowerId")
	public SafeUsePowerInfo getUsePower() {
		return usePower;
	}
	public void setUsePower(SafeUsePowerInfo usePower) {
		this.usePower = usePower;
	}
	
	@Column(name="fstandard",length=250)
	public String getStandard() {
		return standard;
	}
	public void setStandard(String standard) {
		this.standard = standard;
	}
	@Column(name="fcheckResult",length=250)
	public String getCheckResult() {
		return checkResult;
	}
	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}
	@Column(name="fremark",length=250)
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
	
	
	
}
