package com.myapp.entity.ec.drawing;

import com.myapp.core.base.entity.CoreBaseBillInfo;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 包路径：com.myapp.entity.ec.drawing
 * 功能说明：
 * 创建人： ly
 * 创建时间: 2017-07-30 14:52
 */
@Entity
@Table(name="t_ec_discussion_drawing")
public class DiscussionDrawingInfo extends CoreBaseBillInfo {
    private Date conferenceDate;//会议时间
    private String conferencePlace;//会议地点
    private String subject;//主题
    private Boolean completeSignature;//是否完成签章
    //private List<DiscussionDrawingPersonnelInfo> personnelInfos;//参会人员
    private String participants;//参会人员
    private String participantUnits;//参会单位
    private String moderator;//主持人
    private String type;//1、单位工程 2、分部
    private String belongId;//图纸属于对象id
    @Column(name="fConferenceDate")
    public Date getConferenceDate() {
        return conferenceDate;
    }

    public void setConferenceDate(Date conferenceDate) {
        this.conferenceDate = conferenceDate;
    }
    @Column(name="fConferencePlace",length = 200)
    public String getConferencePlace() {
        return conferencePlace;
    }

    public void setConferencePlace(String conferencePlace) {
        this.conferencePlace = conferencePlace;
    }
    @Column(name="fSubject",length = 1000)
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Column(name="fCompleteSignature")
    public Boolean getCompleteSignature() {
        return completeSignature;
    }

    public void setCompleteSignature(Boolean completeSignature) {
        this.completeSignature = completeSignature;
    }
   /*@OneToMany                                          //指定一对多关系
   // @Cascade(value={CascadeType.SAVE_UPDATE})
   // @JoinColumn(name="fDiscussionDrawingId")
    //public List<DiscussionDrawingPersonnelInfo> getPersonnelInfos() {
        return personnelInfos;
    }

    public void setPersonnelInfos(List<DiscussionDrawingPersonnelInfo> personnelInfos) {
        this.personnelInfos = personnelInfos;
    }*/
    @Column(name="fType",length = 1)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    @Column(name="fBelongId",length = 50,nullable = false)
    public String getBelongId() {
        return belongId;
    }

    public void setBelongId(String belongId) {
        this.belongId = belongId;
    }
    @Column(name="fParticipants",length = 500,nullable = false)
    public String getParticipants() {
        return participants;
    }

    public void setParticipants(String participants) {
        this.participants = participants;
    }
    @Column(name="fParticipantUnits",length = 500,nullable = false)
    public String getParticipantUnits() {
        return participantUnits;
    }

    public void setParticipantUnits(String participantUnits) {
        this.participantUnits = participantUnits;
    }
    @Column(name="fModerator",length = 100,nullable = false)
    public String getModerator() {
        return moderator;
    }

    public void setModerator(String moderator) {
        this.moderator = moderator;
    }
}
