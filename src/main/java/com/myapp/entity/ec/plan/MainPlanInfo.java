package com.myapp.entity.ec.plan;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.myapp.core.base.entity.CoreBaseBillInfo;
import com.myapp.enums.ec.WorkFollow;
import com.myapp.enums.ec.WorkType;

@Entity
@Table(name="t_ec_mainPlan")
public class MainPlanInfo extends CoreBaseBillInfo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private WorkType workType;//工作分类
	
	private String content;//工作内容
	
	private Date beginDate;//开始时间
	
	private Date endDate;//完成时间
	
	private String dutyPerson;//责任人
	
	private Boolean isDelivery;//是否交办
	
	private String deliveryPerson;//交办接收人
	
	private WorkFollow workFollow;//工作跟进 是否正常
	
	private Boolean isDone;//是否完成
	
	private String remark;//备注

	@Column(name="fworkType",length=20)
	public WorkType getWorkType() {
		return workType;
	}

	public void setWorkType(WorkType workType) {
		this.workType = workType;
	}

	@Column(name="fcontent",length=500)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name="fbeginDate")
	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	@Column(name="fendDate")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Column(name="fdutyPerson",length=150)
	public String getDutyPerson() {
		return dutyPerson;
	}

	public void setDutyPerson(String dutyPerson) {
		this.dutyPerson = dutyPerson;
	}

	@Column(name="fisDelivery")
	public Boolean getIsDelivery() {
		return isDelivery;
	}

	public void setIsDelivery(Boolean isDelivery) {
		this.isDelivery = isDelivery;
	}

	@Column(name="fdeliveryPerson",length = 150)
	public String getDeliveryPerson() {
		return deliveryPerson;
	}

	public void setDeliveryPerson(String deliveryPerson) {
		this.deliveryPerson = deliveryPerson;
	}

	@Column(name="fworkFollow",length = 20)
	public WorkFollow getWorkFollow() {
		return workFollow;
	}

	public void setWorkFollow(WorkFollow workFollow) {
		this.workFollow = workFollow;
	}

	@Column(name="fisDone")
	public Boolean getIsDone() {
		return isDone;
	}

	public void setIsDone(Boolean isDone) {
		this.isDone = isDone;
	}

	@Column(name="fremark",length=250)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
