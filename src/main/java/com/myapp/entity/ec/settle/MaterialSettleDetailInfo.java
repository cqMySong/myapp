package com.myapp.entity.ec.settle;

import com.myapp.core.annotation.MyEntityAnn;
import com.myapp.core.base.entity.CoreBaseEntryInfo;
import com.myapp.core.entity.MaterialInfo;
import com.myapp.entity.ec.purchase.PurchaseContractDetailInfo;
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
     * 物料信息
     */
    private MaterialInfo materialInfo;
    /**
     * 结算金额
     */
    private BigDecimal settleAmount;
    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 采购合同详细信息
     */
    private PurchaseContractDetailInfo purchaseContractDetailInfo;
    /**
     * 结算数量
     */
    private BigDecimal settleCount;

    /**
     * 备注
     */
    private String remark;


    @Column(name="fSettleAmount",precision = 15,scale = 2)
    public BigDecimal getSettleAmount() {
        return settleAmount;
    }

    public void setSettleAmount(BigDecimal settleAmount) {
        this.settleAmount = settleAmount;
    }

    @Column(name="fRemark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @OneToOne
    @JoinColumn(name = "fMaterialId")
    public MaterialInfo getMaterialInfo() {
        return materialInfo;
    }

    public void setMaterialInfo(MaterialInfo materialInfo) {
        this.materialInfo = materialInfo;
    }

    @Column(name="fPrice",precision = 15,scale = 2)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @OneToOne
    @JoinColumn(name = "fPurchaseContractDetailId")
    public PurchaseContractDetailInfo getPurchaseContractDetailInfo() {
        return purchaseContractDetailInfo;
    }

    public void setPurchaseContractDetailInfo(PurchaseContractDetailInfo purchaseContractDetailInfo) {
        this.purchaseContractDetailInfo = purchaseContractDetailInfo;
    }

    @Column(name="fSettleCount",precision = 15,scale = 2)
    public BigDecimal getSettleCount() {
        return settleCount;
    }

    public void setSettleCount(BigDecimal settleCount) {
        this.settleCount = settleCount;
    }
}
