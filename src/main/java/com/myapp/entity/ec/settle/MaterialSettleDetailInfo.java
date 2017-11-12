package com.myapp.entity.ec.settle;

import com.myapp.core.annotation.MyEntityAnn;
import com.myapp.core.base.entity.CoreBaseEntryInfo;
import com.myapp.entity.ec.purchase.PurchaseContractInfo;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @path：com.myapp.entity.ec.settle
 * @description：
 * @author： ly
 * @date: 2017-11-10 11:03
 */
@Entity
@MyEntityAnn(name="物料结算明细信息")
@Table(name="t_ec_material_settle_detail")
public class MaterialSettleDetailInfo extends CoreBaseEntryInfo<MaterialSettleInfo> {
    /**
     * 采购合同信息
     */
    private PurchaseContractInfo purchaseContractInfo;
    /**
     * 结算金额
     */
    private BigDecimal settleAmount;
    /**
     * 备注
     */
    private String remark;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fPurchaseContractId")
    public PurchaseContractInfo getPurchaseContractInfo() {
        return purchaseContractInfo;
    }

    public void setPurchaseContractInfo(PurchaseContractInfo purchaseContractInfo) {
        this.purchaseContractInfo = purchaseContractInfo;
    }

    @Column(name="fSettleAmount",precision = 10,scale = 2)
    public BigDecimal getSettleAmount() {
        return settleAmount;
    }

    public void setSettleAmount(BigDecimal settleAmount) {
        this.settleAmount = settleAmount;
    }

    @Column(name="fRemark",precision = 10,scale = 2)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
