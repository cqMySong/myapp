package com.myapp.entity.ec.other;

import java.util.Date;

import javax.persistence.*;

import com.myapp.core.base.entity.CoreBaseBillInfo;
import com.myapp.entity.ec.basedata.ProjectInfo;

/**
 * 包路径：com.myapp.entity.ec.other
 * 功能说明：奖励通知
 * 创建人： 卢松
 * 创建时间: 2017-11-03 14:52
 */
@Entity
@Table(name="t_ec_rectifyFeedBack")
public class RectifyFeedBackInfo extends CoreBaseBillInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2081629247388513689L;
	
	private RectifyNoticeInfo rectifyNotice;//整改通知
	private String requires;//整改要求
	private Boolean isDone;//是否整改完毕
	private Date doneDate;//整改完成日期
	private Date endDate;//整改截至日期
	private Boolean isFeedBack;//是否回复整改
	private ProjectInfo project;

	@OneToOne
	@JoinColumn(name = "fRectifyNoticeId")
	public RectifyNoticeInfo getRectifyNotice() {
		return rectifyNotice;
	}
	public void setRectifyNotice(RectifyNoticeInfo rectifyNotice) {
		this.rectifyNotice = rectifyNotice;
	}
	
	@Column(name="fisDone")
	public Boolean getIsDone() {
		return isDone;
	}
	public void setIsDone(Boolean isDone) {
		this.isDone = isDone;
	}
	
	@Column(name="fdoneDate")
	public Date getDoneDate() {
		return doneDate;
	}
	public void setDoneDate(Date doneDate) {
		this.doneDate = doneDate;
	}
	
	@Column(name="fisFeedBack")
	public Boolean getIsFeedBack() {
		return isFeedBack;
	}
	public void setIsFeedBack(Boolean isFeedBack) {
		this.isFeedBack = isFeedBack;
	}
	
	@Column(name="frequires", length=500)
	public String getRequires() {
		return requires;
	}
	public void setRequires(String requires) {
		this.requires = requires;
	}
	
	@Column(name="fendDate")
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@OneToOne
	@JoinColumn(name = "fProjectId")
	public ProjectInfo getProject() {
		return project;
	}

	public void setProject(ProjectInfo project) {
		this.project = project;
	}
}
