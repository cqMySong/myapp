package com.myapp.entity.ec.proworkcheck;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.myapp.core.annotation.MyEntityAnn;
import com.myapp.core.base.entity.CoreBaseEntryInfo;
import com.myapp.entity.ec.basedata.WorkCheckItemInfo;
import com.myapp.enums.ec.WorkCheckType;
import com.myapp.enums.ec.YesNoEnum;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年11月27日 
 * @system:
 * 项目现场施工 日检查项目表
 *-----------MySong---------------
 */
@Entity
@MyEntityAnn(name="现场施工 周(月)安全检查记录")
@Table(name="t_ec_prosafecheckitem")
public class ProSafeCheckItemInfo extends CoreBaseEntryInfo<ProSafeCheckInfo> {
	private WorkCheckType workCheckType;//检验分类
	private String checkItem;//检查项目
	private String checkRequire;//检查要求
	private WorkCheckItemInfo workCheckItem;//系统级坚持项目
	
	private String checkResult;//检查结果
	private String rectifyIdea;//整改意见
	private String rectifyResult;//整改结果
	
	private String remark;//备注
	
	@Column(name="fworkchecktype",length=10)
	@Type(type="myEnum",parameters={@Parameter(name="enumClass",value="com.myapp.enums.ec.WorkCheckType")})
	public WorkCheckType getWorkCheckType() {
		return workCheckType;
	}
	public void setWorkCheckType(WorkCheckType workCheckType) {
		this.workCheckType = workCheckType;
	}
	@Column(name="fcheckitem",length=200)
	public String getCheckItem() {
		return checkItem;
	}
	public void setCheckItem(String checkItem) {
		this.checkItem = checkItem;
	}
	@Column(name="fcheckRequire",length=200)
	public String getCheckRequire() {
		return checkRequire;
	}
	public void setCheckRequire(String checkRequire) {
		this.checkRequire = checkRequire;
	}
	@OneToOne
	@JoinColumn(name = "fworkcheckitemId")
	public WorkCheckItemInfo getWorkCheckItem() {
		return workCheckItem;
	}
	public void setWorkCheckItem(WorkCheckItemInfo workCheckItem) {
		this.workCheckItem = workCheckItem;
	}
	@Column(name="fcheckresult",length=200)
	public String getCheckResult() {
		return checkResult;
	}
	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}
	@Column(name="frectifyidea",length=200)
	public String getRectifyIdea() {
		return rectifyIdea;
	}
	public void setRectifyIdea(String rectifyIdea) {
		this.rectifyIdea = rectifyIdea;
	}
	@Column(name="frectifyresult",length=200)
	public String getRectifyResult() {
		return rectifyResult;
	}
	public void setRectifyResult(String rectifyResult) {
		this.rectifyResult = rectifyResult;
	}
	@Column(name="fremark",length=200)
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
