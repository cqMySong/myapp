package com.myapp.entity.ec.budget;

import com.myapp.core.annotation.MyEntityAnn;
import com.myapp.core.base.entity.CoreBaseBillInfo;
import com.myapp.entity.ec.basedata.ProjectInfo;

import javax.persistence.*;
import java.util.Set;

/**
 * 包路径：com.myapp.entity.ec.budget
 * 功能说明：预算编制
 * 创建人： ly
 * 创建时间: 2017-08-25 15:21
 */
@Entity
@MyEntityAnn(name="预算编制")
@Table(name="t_ec_budgeting")
public class BudgetingInfo extends CoreBaseBillInfo {
    private ProjectInfo project;//项目单位工程
    private String remark;//备注
    private Set<BudgetingDetailInfo> budgetingDetailInfos;//预算详细信息

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
