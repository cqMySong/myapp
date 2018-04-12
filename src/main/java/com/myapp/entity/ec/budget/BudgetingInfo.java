package com.myapp.entity.ec.budget;

import com.myapp.core.annotation.MyEntityAnn;
import com.myapp.core.base.entity.CoreBaseBillInfo;
import com.myapp.entity.ec.basedata.ProjectInfo;

import javax.persistence.*;
import java.util.Set;

/**
 * @path：com.myapp.entity.ec.budget
 * @description：材料设备预算清单
 * @author ： ly
 * @date: 2017-08-25 15:21
 */
@Entity
@MyEntityAnn(name="材料设备预算清单")
@Table(name="t_ec_budgeting")
public class BudgetingInfo extends CoreBaseBillInfo {
    /**
     * 预算所属工程
     */
    private ProjectInfo project;
    /**
     * 预算备注信息
     */
    private String remark;
    /**
     * 预算明细信息
     */
    private Set<BudgetingDetailInfo> budgetingDetailInfos;

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
    public Set<BudgetingDetailInfo> getBudgetingDetailInfos() {
        return budgetingDetailInfos;
    }

    public void setBudgetingDetailInfos(Set<BudgetingDetailInfo> budgetingDetailInfos) {
        this.budgetingDetailInfos = budgetingDetailInfos;
    }
}

