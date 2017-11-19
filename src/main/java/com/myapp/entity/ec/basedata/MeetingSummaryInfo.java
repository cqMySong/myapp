package com.myapp.entity.ec.basedata;

import com.myapp.core.base.entity.CoreBaseBillInfo;
import com.myapp.core.enums.MeetingSummaryType;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

/**
 * @path：com.myapp.entity.ec.basedata
 * @description：会议纪要
 * @author： ly
 * @date: 2017-11-19 20:59
 */
@Entity
@Table(name="t_ec_meeting_summary")
public class MeetingSummaryInfo extends CoreBaseBillInfo {
    /**
     * 所属项目
     */
    private ProjectInfo project;
    /**
     * 会议纪要类型
     */
    private MeetingSummaryType meetingSummaryType;
    /**
     * 会议时间
     */
    private Date meetingDate;

    /**
     * 会议内容
     */
    private String content;
    /**
     * 备注
     */
    private String remark;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fProjectId")
    public ProjectInfo getProject() {
        return project;
    }

    public void setProject(ProjectInfo project) {
        this.project = project;
    }

    @Column(name="fMeetingSummaryType",length = 20)
    @Type(type="myEnum",parameters={@org.hibernate.annotations.Parameter(name="enumClass",value="com.myapp.core.enums.MeetingSummaryType")})
    public MeetingSummaryType getMeetingSummaryType() {
        return meetingSummaryType;
    }

    public void setMeetingSummaryType(MeetingSummaryType meetingSummaryType) {
        this.meetingSummaryType = meetingSummaryType;
    }

    @Column(name="fMeetingDate")
    public Date getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(Date meetingDate) {
        this.meetingDate = meetingDate;
    }

    @Column(name="fContent",length = 500)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Column(name="fRemark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
