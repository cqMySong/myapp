package com.myapp.entity.ec.other;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.myapp.core.base.entity.CoreBaseBillInfo;
import com.myapp.enums.ec.WorkFollow;
import com.myapp.enums.ec.WorkType;

/**
 * 包路径：com.myapp.entity.ec.other
 * 功能说明：项目主要问题处理摘要
 * 创建人： 卢松
 * 创建时间: 2017-11-03 14:52
 */
@Entity
@Table(name="t_ec_proDisposal")
public class ProDisposalInfo extends CoreBaseBillInfo{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Date bizDate;//发现问题时间
	
	private WorkType workType;//问题分类
	
	private String proDescription;//问题描述
	
	private String disposal;//解决方法
	
	private WorkFollow workFollow;//工作跟进 是否正常
	
	private Date lastestDate;//最迟解决时间
	
	private Boolean isDone;//是否解决
	
	private String remark;//备注

	
	@Column(name="fbizDate")
	public Date getBizDate() {
		return bizDate;
	}

	public void setBizDate(Date bizDate) {
		this.bizDate = bizDate;
	}

	@Column(name="fworkType",length=20)
	public WorkType getWorkType() {
		return workType;
	}

	public void setWorkType(WorkType workType) {
		this.workType = workType;
	}

	@Column(name="fproDescription",length=250)
	public String getProDescription() {
		return proDescription;
	}

	public void setProDescription(String proDescription) {
		this.proDescription = proDescription;
	}

	@Column(name="fdisposal",length=250)
	public String getDisposal() {
		return disposal;
	}

	public void setDisposal(String disposal) {
		this.disposal = disposal;
	}

	@Column(name="fworkFollow",length=20)
	public WorkFollow getWorkFollow() {
		return workFollow;
	}

	public void setWorkFollow(WorkFollow workFollow) {
		this.workFollow = workFollow;
	}

	@Column(name="flastestDate")
	public Date getLastestDate() {
		return lastestDate;
	}

	public void setLastestDate(Date lastestDate) {
		this.lastestDate = lastestDate;
	}

	@Column(name="fisDone")
	public Boolean getIsDone() {
		return isDone;
	}

	public void setIsDone(Boolean isDone) {
		this.isDone = isDone;
	}

	@Column(name="fremark",length=200)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
}
