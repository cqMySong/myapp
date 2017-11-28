package com.myapp.entity.ec.purchase;

import com.myapp.core.annotation.MyEntityAnn;
import com.myapp.core.base.entity.CoreBaseEntryInfo;
import com.myapp.entity.ec.budget.BudgetingDetailInfo;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

/**
 * @path：com.myapp.entity.ec.purchase
 * @description：材料申购详细信息
 * @author： ly
 * @date: 2017-10-29 20:59
 */
@Entity
@MyEntityAnn(name="材料申购详细")
@Table(name="t_ec_apply_material_detail")
public class ApplyMaterialDetailInfo extends CoreBaseEntryInfo<ApplyMaterialInfo> {
    /**
     * 采购明细信息
     */
    private BudgetingDetailInfo budgetingDetailInfo;
    /**
     * 申购数量
     */
    private BigDecimal purchaseNum;
    /**
     * 到场时间
     */
    private Date arrivalTime;
    /**
     * 累计申购数量
     */
    private BigDecimal cumulativePurchaseNum;
    /**
     * 序号
     */
    private Long sno;
    /**
     * 采购合同详细
     */
    private Set<PurchaseContractDetailInfo> purchaseContractDetailInfoSet;

    @OneToOne
    @JoinColumn(name = "fBudgetingDetailId")
    public BudgetingDetailInfo getBudgetingDetailInfo() {
        return budgetingDetailInfo;
    }

    public void setBudgetingDetailInfo(BudgetingDetailInfo budgetingDetailInfo) {
        this.budgetingDetailInfo = budgetingDetailInfo;
    }

    @Column(name="fPurchaseNum",precision = 10,scale = 2)
    public BigDecimal getPurchaseNum() {
        return purchaseNum;
    }

    public void setPurchaseNum(BigDecimal purchaseNum) {
        this.purchaseNum = purchaseNum;
    }

    @Column(name="fArrivalTime")
    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    @Column(name="fCumulativePurchaseNum",precision = 10,scale = 2)
    public BigDecimal getCumulativePurchaseNum() {
        return cumulativePurchaseNum;
    }

    public void setCumulativePurchaseNum(BigDecimal cumulativePurchaseNum) {
        this.cumulativePurchaseNum = cumulativePurchaseNum;
    }

    @Column(name="fSno")
    public Long getSno() {
        return sno;
    }

    public void setSno(Long sno) {
        this.sno = sno;
    }

    @OneToMany(cascade={CascadeType.ALL},mappedBy="applyMaterialDetailInfo")
    public Set<PurchaseContractDetailInfo> getPurchaseContractDetailInfoSet() {
        return purchaseContractDetailInfoSet;
    }

    public void setPurchaseContractDetailInfoSet(Set<PurchaseContractDetailInfo> purchaseContractDetailInfoSet) {
        this.purchaseContractDetailInfoSet = purchaseContractDetailInfoSet;
    }
}
