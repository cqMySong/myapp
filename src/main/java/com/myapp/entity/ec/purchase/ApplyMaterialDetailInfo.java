package com.myapp.entity.ec.purchase;

import com.myapp.core.annotation.MyEntityAnn;
import com.myapp.core.base.entity.CoreBaseEntryInfo;
import com.myapp.core.entity.MaterialInfo;
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
     * 预算明细信息
     */
    private BudgetingDetailInfo budgetingDetailInfo;
    /**
     * 物料信息
     */
    private MaterialInfo materialInfo;
    /**
     * 申购数量
     */
    private BigDecimal purchaseNum;
    /**
     * 到场时间
     */
    private Date arrivalTime;
    /**
     * 库存数量
     */
    private BigDecimal stockCount;
    /**
     * 进度序号 1、施工员  2、技术负责人 3、材料员  4、项目经理 5、完结
     */
    private int processNo;
    /**
     * 状态
     */
    private String status;

    /**
     * 累计申购数量
     */
    private BigDecimal cumulativePurchaseNum;
    /**
     * 预计采购单价
     */
    private BigDecimal purchasePrice;
    /**
     * 预计到货时间
     */
    private Date purchaseArrivalTime;
    /**
     * 序号
     */
    private Long sno;
    /**
     * 采购合同详细
     */
    private Set<PurchaseContractDetailInfo> purchaseContractDetailInfoSet;
    /**
     * 采购入库明细
     */
    private Set<PurchaseStockDetailInfo> purchaseStockDetailInfoSet;

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

    @OneToMany(cascade={CascadeType.ALL},mappedBy="applyMaterialDetailInfo")
    public Set<PurchaseStockDetailInfo> getPurchaseStockDetailInfoSet() {
        return purchaseStockDetailInfoSet;
    }

    public void setPurchaseStockDetailInfoSet(Set<PurchaseStockDetailInfo> purchaseStockDetailInfoSet) {
        this.purchaseStockDetailInfoSet = purchaseStockDetailInfoSet;
    }

    @OneToOne
    @JoinColumn(name = "fMaterialId")
    public MaterialInfo getMaterialInfo() {
        return materialInfo;
    }

    public void setMaterialInfo(MaterialInfo materialInfo) {
        this.materialInfo = materialInfo;
    }

    @Column(name="fStockCount")
    public BigDecimal getStockCount() {
        return stockCount;
    }

    public void setStockCount(BigDecimal stockCount) {
        this.stockCount = stockCount;
    }
    @Column(name="fProcessNo")
    public int getProcessNo() {
        return processNo;
    }

    public void setProcessNo(int processNo) {
        this.processNo = processNo;
    }
    @Column(name="fStatus",length = 1)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name="fPurchasePrice",precision = 10,scale = 2)
    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    @Column(name="fPurchaseArrivalTime")
    public Date getPurchaseArrivalTime() {
        return purchaseArrivalTime;
    }

    public void setPurchaseArrivalTime(Date purchaseArrivalTime) {
        this.purchaseArrivalTime = purchaseArrivalTime;
    }
}
