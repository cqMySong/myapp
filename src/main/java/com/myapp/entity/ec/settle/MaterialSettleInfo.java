package com.myapp.entity.ec.settle;

import com.myapp.core.annotation.MyEntityAnn;
import com.myapp.core.base.entity.CoreBaseBillInfo;
import com.myapp.core.entity.UserInfo;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.entity.ec.purchase.PurchaseContractInfo;

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
    private Date startDate;
    /**
     * 结束时间
     */
    private Date endDate;
    /**
     * 经办人
     */
    private UserInfo operator;
    /**
     * 结算总金额
     */
    private BigDecimal settleAmount;
    /**
     * 备注
     */
    private String remark;
    /**
     * 采购合同信息
     */
    private PurchaseContractInfo purchaseContractInfo;
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
    @Column(name="fStartDate")
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    @Column(name="fEndDate")
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    @Column(name="fSettleAmount",precision = 14,scale = 2)
    public BigDecimal getSettleAmount() {
        return settleAmount;
    }

    public void setSettleAmount(BigDecimal settleAmount) {
        this.settleAmount = settleAmount;
    }

    @OneToOne
    @JoinColumn(name = "fOperatorId")
    public UserInfo getOperator() {
        return operator;
    }

    public void setOperator(UserInfo operator) {
        this.operator = operator;
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

    @OneToOne
    @JoinColumn(name = "fPurchaseContractId")
    public PurchaseContractInfo getPurchaseContractInfo() {
        return purchaseContractInfo;
    }

    public void setPurchaseContractInfo(PurchaseContractInfo purchaseContractInfo) {
        this.purchaseContractInfo = purchaseContractInfo;
    }
}
