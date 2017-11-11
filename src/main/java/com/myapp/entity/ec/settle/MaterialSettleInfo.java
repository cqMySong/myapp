package com.myapp.entity.ec.settle;

import com.myapp.core.annotation.MyEntityAnn;
import com.myapp.core.base.entity.CoreBaseBillInfo;
import com.myapp.core.entity.UserInfo;
import com.myapp.entity.ec.basedata.ProjectInfo;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

/**
 * @path：com.myapp.entity.ec.settle
 * @description：结算信息
 * @author： ly
 * @date: 2017-11-10 10:54
 */
@Entity
@MyEntityAnn(name="物料结算信息")
@Table(name="t_ec_material_settle")
public class MaterialSettleInfo extends CoreBaseBillInfo {
    /**
     * 所属项目
     */
    private ProjectInfo project;
    /**
     * 结算时间
     */
    private Date settleDate;
    /**
     * 经办人
     */
    private UserInfo operator;
    /**
     * 结算总金额
     */
    private BigDecimal settleTotalAmount;
    /**
     * 备注
     */
    private String remark;
    /**
     * 结算明细信息
     */
    private Set<MaterialSettleDetailInfo> materialSettleDetailInfos;

    private Set<MaterialSettleDetailInfo> oldMaterialSettleDetails;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fProjectId")
    public ProjectInfo getProject() {
        return project;
    }

    public void setProject(ProjectInfo project) {
        this.project = project;
    }

    @Column(name="fSettleDate")
    public Date getSettleDate() {
        return settleDate;
    }

    public void setSettleDate(Date settleDate) {
        this.settleDate = settleDate;
    }

    @OneToOne
    @JoinColumn(name = "fOperatorId")
    public UserInfo getOperator() {
        return operator;
    }

    public void setOperator(UserInfo operator) {
        this.operator = operator;
    }

    @Column(name="fSettleTotalAmount",precision = 14,scale = 2)
    public BigDecimal getSettleTotalAmount() {
        return settleTotalAmount;
    }

    public void setSettleTotalAmount(BigDecimal settleTotalAmount) {
        this.settleTotalAmount = settleTotalAmount;
    }

    @Column(name="fRemark",length = 200)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @OneToMany(cascade={CascadeType.ALL},mappedBy="parent")
    @OrderBy("seq ASC")
    public Set<MaterialSettleDetailInfo> getMaterialSettleDetailInfos() {
        return materialSettleDetailInfos;
    }

    public void setMaterialSettleDetailInfos(Set<MaterialSettleDetailInfo> materialSettleDetailInfos) {
        this.materialSettleDetailInfos = materialSettleDetailInfos;
    }

    @Transient
    public Set<MaterialSettleDetailInfo> getOldMaterialSettleDetails() {
        return oldMaterialSettleDetails;
    }

    public void setOldMaterialSettleDetails(Set<MaterialSettleDetailInfo> oldMaterialSettleDetails) {
        this.oldMaterialSettleDetails = oldMaterialSettleDetails;
    }
}
