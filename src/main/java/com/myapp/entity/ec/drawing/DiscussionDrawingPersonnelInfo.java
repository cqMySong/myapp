package com.myapp.entity.ec.drawing;

import com.myapp.core.base.entity.CoreInfo;

import javax.persistence.*;

/**
 * 包路径：com.myapp.entity.ec.drawing
 * 功能说明：图纸会审人员
 * 创建人： ly
 * 创建时间: 2017-07-30 15:11
 */

public class DiscussionDrawingPersonnelInfo extends CoreInfo{
    private DiscussionDrawingInfo discussionDrawingInfo;//图纸会审信息
    private String personnelId;//人员id
    private String personnelName;//人员名称
    private String companyId;//单位id
    private String companyName;//单位名称
    private String type;//人员类型1、参会人员 2、主持人员
    @ManyToOne
    @JoinColumn(name = "fDiscussionDrawingId")
    public DiscussionDrawingInfo getDiscussionDrawingInfo() {
        return discussionDrawingInfo;
    }

    public void setDiscussionDrawingInfo(DiscussionDrawingInfo discussionDrawingInfo) {
        this.discussionDrawingInfo = discussionDrawingInfo;
    }
    @Column(name="fPersonnelId")
    public String getPersonnelId() {
        return personnelId;
    }

    public void setPersonnelId(String personnelId) {
        this.personnelId = personnelId;
    }
    @Column(name="fPersonnelName")
    public String getPersonnelName() {
        return personnelName;
    }

    public void setPersonnelName(String personnelName) {
        this.personnelName = personnelName;
    }
    @Column(name="fCompanyId")
    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
    @Column(name="fCompanyName")
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    @Column(name="fType")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
