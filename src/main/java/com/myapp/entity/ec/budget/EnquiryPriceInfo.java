package com.myapp.entity.ec.budget;

import com.myapp.core.annotation.MyEntityAnn;
import com.myapp.core.base.entity.CoreBaseBillInfo;
import com.myapp.entity.ec.basedata.ProjectInfo;

import javax.persistence.*;
import java.util.Set;

/**
 * @path：com.myapp.entity.ec.purchase
 * @description：采购计划
 * @author ： ly
 * @date: 2017-08-28 21:08
 */
@Entity
@MyEntityAnn(name="预算询价")
@Table(name="t_ec_enquiry_price")
public class EnquiryPriceInfo extends CoreBaseBillInfo{
    /**
     * 项目工程
     */
    private ProjectInfo project;
    /**
     * 备注信息
     */
    private String remark;
    /**
     * 询价明细信息
     */
    private Set<EnquiryPriceDetailInfo> enquiryPriceDetailInfos;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fProjectId")
    public ProjectInfo getProject() {
        return project;
    }

    public void setProject(ProjectInfo project) {
        this.project = project;
    }

    @Column(name="fRemark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @OneToMany(cascade={CascadeType.ALL},mappedBy="parent")
    @OrderBy("seq ASC")
    public Set<EnquiryPriceDetailInfo> getEnquiryPriceDetailInfos() {
        return enquiryPriceDetailInfos;
    }

    public void setEnquiryPriceDetailInfos(Set<EnquiryPriceDetailInfo> enquiryPriceDetailInfos) {
        this.enquiryPriceDetailInfos = enquiryPriceDetailInfos;
    }
}
