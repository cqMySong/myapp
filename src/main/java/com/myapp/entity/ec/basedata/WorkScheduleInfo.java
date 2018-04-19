package com.myapp.entity.ec.basedata;

import com.myapp.core.annotation.MyEntityAnn;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.entity.UserInfo;
import com.myapp.enums.ec.WorkType;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年12月12日 
 * @system:
 *-----------MySong---------------
 */
@MyEntityAnn(name="工作安排摘要")
@Entity
@Table(name="t_ec_work_schedule")
public class WorkScheduleInfo extends CoreBaseInfo {
    /**
     * 工程项目
     */
    private ProjectInfo project;
    /**
     * 工作分类
     */
    private WorkType workType;
    /**
     * 工作内容
     */
    private String jobContent;
    /**
     * 工作开始时间
     */
    private Date startDate;
    /**
     * 完成时间
     */
    private Date finishDate;
    /**
     * 责任人
     */
    private String personLiable;
    /**
     * 是否交接
     */
    private Boolean takeOver;
    /**
     * 接收人
     */
    private String sendee;
    /**
     * 工作跟进
     */
    private Boolean workFollowUp;
    /**
     * 是否完成
     */
    private Boolean finish;
    /**
     * 备注
     */
    private String remark;
    /**
     * 创建人
     */
    private UserInfo creator;

    @OneToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "fProjectId")
    public ProjectInfo getProject() {
        return project;
    }

    public void setProject(ProjectInfo project) {
        this.project = project;
    }
    @Column(name="fWorkType",length = 20)
    @Type(type="myEnum",parameters={@org.hibernate.annotations.Parameter(name="enumClass",value="com.myapp.enums.ec.WorkType")})
    public WorkType getWorkType() {
        return workType;
    }

    public void setWorkType(WorkType workType) {
        this.workType = workType;
    }
    @Column(name="fJobContent",length=500)
    public String getJobContent() {
        return jobContent;
    }

    public void setJobContent(String jobContent) {
        this.jobContent = jobContent;
    }
    @Column(name="fStartDate")
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    @Column(name="fFinishDate")
    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }
    @Column(name="fPersonLiable",length = 50)
    public String getPersonLiable() {
        return personLiable;
    }

    public void setPersonLiable(String personLiable) {
        this.personLiable = personLiable;
    }
    @Column(name="fTakeOver")
    public Boolean getTakeOver() {
        return takeOver;
    }

    public void setTakeOver(Boolean takeOver) {
        this.takeOver = takeOver;
    }
    @Column(name="fSendee")
    public String getSendee() {
        return sendee;
    }

    public void setSendee(String sendee) {
        this.sendee = sendee;
    }
    @Column(name="fWorkFollowUp")
    public Boolean getWorkFollowUp() {
        return workFollowUp;
    }

    public void setWorkFollowUp(Boolean workFollowUp) {
        this.workFollowUp = workFollowUp;
    }
    @Column(name="fFinish")
    public Boolean getFinish() {
        return finish;
    }

    public void setFinish(Boolean finish) {
        this.finish = finish;
    }
    @Column(name="fRemark",length = 500)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    @OneToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "fCreatorId")
    public UserInfo getCreator() {
        return creator;
    }

    public void setCreator(UserInfo creator) {
        this.creator = creator;
    }
}
