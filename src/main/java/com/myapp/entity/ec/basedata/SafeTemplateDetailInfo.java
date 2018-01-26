package com.myapp.entity.ec.basedata;

import com.myapp.core.annotation.MyEntityAnn;
import com.myapp.core.base.entity.CoreBaseEntryInfo;
import com.myapp.core.entity.PositionInfo;

import javax.persistence.*;

/**
 * @path：com.myapp.entity.ec.basedata
 * @description：
 * @author： ly
 * @date: 2018-01-14 20:21
 */
@MyEntityAnn(name="安全样板明细")
@Entity
@Table(name="t_ec_safe_template_detail")
public class SafeTemplateDetailInfo extends CoreBaseEntryInfo<SafeTemplateInfo> {
    /**
     * 岗位信息
     */
    private PositionInfo position;

    /**
     * 工作要求
     */
    private String jobRequirement;
    /***
     * 启用禁用
     */
    private boolean enable;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fPositionId")
    public PositionInfo getPosition() {
        return position;
    }

    public void setPosition(PositionInfo position) {
        this.position = position;
    }

    @Column(name = "fJobRequirement",length=500)
    public String getJobRequirement() {
        return jobRequirement;
    }

    public void setJobRequirement(String jobRequirement) {
        this.jobRequirement = jobRequirement;
    }
    @Column(name = "fEnable")
    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
