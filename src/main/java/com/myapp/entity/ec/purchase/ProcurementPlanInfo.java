package com.myapp.entity.ec.purchase;

import com.myapp.core.annotation.MyEntityAnn;
import com.myapp.core.base.entity.CoreBaseBillInfo;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.entity.ec.budget.BudgetingDetailInfo;

import javax.persistence.*;
import java.util.Set;

/**
 * 包路径：com.myapp.entity.ec.purchase
 * 功能说明：采购计划
 * 创建人： ly
 * 创建时间: 2017-08-28 21:08
 */
@Entity
@MyEntityAnn(name="采购计划")
@Table(name="t_ec_procurement_plan")
public class ProcurementPlanInfo extends CoreBaseBillInfo{
    private ProjectInfo project;//项目单位工程
    private String remark;//备注
    private Set<ProcurementPlanDetailInfo> procurementPlanDetails;//采购计划信息
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
    public Set<ProcurementPlanDetailInfo> getProcurementPlanDetails() {
        return procurementPlanDetails;
    }

    public void setProcurementPlanDetails(Set<ProcurementPlanDetailInfo> procurementPlanDetails) {
        this.procurementPlanDetails = procurementPlanDetails;
    }
}
