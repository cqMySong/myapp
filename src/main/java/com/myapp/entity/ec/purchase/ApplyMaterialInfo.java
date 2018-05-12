package com.myapp.entity.ec.purchase;

import com.myapp.core.annotation.MyEntityAnn;
import com.myapp.core.base.entity.CoreBaseBillInfo;
import com.myapp.core.enums.BillState;
import com.myapp.entity.ec.basedata.ProjectInfo;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Set;

/**
 * @path：com.myapp.entity.ec.purchase
 * @description：申购信息
 * @author： ly
 * @date: 2017-10-29 20:59
 */
@Entity
@MyEntityAnn(name="材料申购")
@Table(name="t_ec_apply_material")
public class ApplyMaterialInfo extends CoreBaseBillInfo {
    /**
     * 申购单所属项目
     */
    private ProjectInfo project;
    /**
     * 备注信息
     */
    private String remark;
    /**
     * 流程审核状态
     */
    private BillState auditState;
    /**
     * 流程进度id
     */
    private String processInstanceId;

    /**
     * 材料申购明细信息
     */
    private Set<ApplyMaterialDetailInfo> applyMaterialDetailInfos;

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
    public Set<ApplyMaterialDetailInfo> getApplyMaterialDetailInfos() {
        return applyMaterialDetailInfos;
    }

    public void setApplyMaterialDetailInfos(Set<ApplyMaterialDetailInfo> applyMaterialDetailInfos) {
        this.applyMaterialDetailInfos = applyMaterialDetailInfos;
    }

    @Column(name="fAuditState",length=20)
    @Type(type="myEnum",parameters={@org.hibernate.annotations.Parameter(name="enumClass",value="com.myapp.core.enums.BillState")})
    public BillState getAuditState() {
        return auditState;
    }

    public void setAuditState(BillState auditState) {
        this.auditState = auditState;
    }
    @Column(name="fProcessInstanceId")
    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }
}
