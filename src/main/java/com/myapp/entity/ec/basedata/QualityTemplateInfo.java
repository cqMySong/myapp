package com.myapp.entity.ec.basedata;

import com.myapp.core.annotation.MyEntityAnn;
import com.myapp.core.base.entity.CoreBaseDataInfo;
import com.myapp.core.entity.PositionInfo;

import javax.persistence.*;

/**
 * @path：com.myapp.entity.ec.basedata
 * @description：
 * @author： ly
 * @date: 2018-01-14 20:21
 */
@MyEntityAnn(name="质量样板")
@Entity
@Table(name="t_ec_quality_template")
public class QualityTemplateInfo extends CoreBaseDataInfo {
    /**
     * 岗位信息
     */
    private PositionInfo position;
    /**
     * 分部
     */
    private ProBaseWbsInfo branchBaseWbs;
    /**
     * 分项
     */
    private ProBaseWbsInfo subentry;
    /**
     * 工作要求
     */
    private String jobRequirement;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fPositionId")
    public PositionInfo getPosition() {
        return position;
    }

    public void setPosition(PositionInfo position) {
        this.position = position;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fBranchBaseWbsId")
    public ProBaseWbsInfo getBranchBaseWbs() {
        return branchBaseWbs;
    }

    public void setBranchBaseWbs(ProBaseWbsInfo branchBaseWbs) {
        this.branchBaseWbs = branchBaseWbs;
    }
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fSubentryId")
    public ProBaseWbsInfo getSubentry() {
        return subentry;
    }

    public void setSubentry(ProBaseWbsInfo subentry) {
        this.subentry = subentry;
    }
    @Column(name = "fJobRequirement",length=500)
    public String getJobRequirement() {
        return jobRequirement;
    }

    public void setJobRequirement(String jobRequirement) {
        this.jobRequirement = jobRequirement;
    }
}
