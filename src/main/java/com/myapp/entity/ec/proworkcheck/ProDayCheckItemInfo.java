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
@MyEntityAnn(name="现场施工 日检查记录")
@Table(name="t_ec_prodaycheckitem")
public class ProDayCheckItemInfo extends CoreBaseEntryInfo<ProDayCheckInfo> {
	private WorkCheckType workCheckType;//检验分类
	private String checkItem;//检查项目
	private String checkRequire;//检查要求
	private WorkCheckItemInfo workCheckItem;//系统级坚持项目
	private YesNoEnum safePeril;//是否有安全隐患
	private String perilContent;//隐患情况
	private YesNoEnum removePeril;//排除隐患：整改结果：已排除，未排除
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
	@Column(name="fsafeperil",length=10)
	@Type(type="myEnum",parameters={@Parameter(name="enumClass",value="com.myapp.enums.ec.YesNoEnum")})
	public YesNoEnum getSafePeril() {
		return safePeril;
	}
	public void setSafePeril(YesNoEnum safePeril) {
		this.safePeril = safePeril;
	}
	@Column(name="fperilcontent",length=200)
	public String getPerilContent() {
		return perilContent;
	}
	public void setPerilContent(String perilContent) {
		this.perilContent = perilContent;
	}
	@Column(name="fremoveperil",length=10)
	@Type(type="myEnum",parameters={@Parameter(name="enumClass",value="com.myapp.enums.ec.YesNoEnum")})
	public YesNoEnum getRemovePeril() {
		return removePeril;
	}
	public void setRemovePeril(YesNoEnum removePeril) {
		this.removePeril = removePeril;
	}
	
	@Column(name="fremark",length=200)
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
