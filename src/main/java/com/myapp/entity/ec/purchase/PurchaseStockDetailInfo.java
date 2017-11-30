package com.myapp.entity.ec.purchase;

import com.myapp.core.annotation.MyEntityAnn;
import com.myapp.core.base.entity.CoreBaseEntryInfo;
import com.myapp.entity.ec.stock.StockInfo;
import com.myapp.entity.ec.stock.StockOutDetailInfo;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

/**
 * @path：com.myapp.entity.ec.purchase
 * @description：采购入库细信息
 * @author ： ly
 * @date: 2017-08-28 21:10
 */
@Entity
@MyEntityAnn(name="采购入库详细")
@Table(name="t_ec_purchase_stock_detail")
public class PurchaseStockDetailInfo extends CoreBaseEntryInfo<PurchaseStockInfo> {
    /**
     * 合同信息
     */
    private PurchaseContractInfo purchaseContractInfo;
    /**
     * 采购合同详细信息
     */
    private PurchaseContractDetailInfo purchaseContractDetailInfo;

    /**
     * 入库数量
     */
    private BigDecimal count;
    /**
     * 累计入库数量
     */
    private BigDecimal cumulativeCount;
    /**
     * 备注
     */
    private String remark;
    /**
     * 序号
     */
    private Long sno;
    /**
     * 出库信息
     */
    private StockInfo stockInfo;
    @OneToOne
    @JoinColumn(name = "fPurchaseContractId")
    public PurchaseContractInfo getPurchaseContractInfo() {
        return purchaseContractInfo;
    }

    public void setPurchaseContractInfo(PurchaseContractInfo purchaseContractInfo) {
        this.purchaseContractInfo = purchaseContractInfo;
    }

    @OneToOne
    @JoinColumn(name = "fPurchaseContractDetailId")
    public PurchaseContractDetailInfo getPurchaseContractDetailInfo() {
        return purchaseContractDetailInfo;
    }

    public void setPurchaseContractDetailInfo(PurchaseContractDetailInfo purchaseContractDetailInfo) {
        this.purchaseContractDetailInfo = purchaseContractDetailInfo;
    }

    @Column(name="fCount",precision = 10,scale = 2)
    public BigDecimal getCount() {
        return count;
    }

    public void setCount(BigDecimal count) {
        this.count = count;
    }

    @Column(name="fRemark",length = 150)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name="fCumulativeCount",precision = 10,scale = 2)
    public BigDecimal getCumulativeCount() {
        return cumulativeCount;
    }

    public void setCumulativeCount(BigDecimal cumulativeCount) {
        this.cumulativeCount = cumulativeCount;
    }
    @Column(name="fSno")
    public Long getSno() {
        return sno;
    }

    public void setSno(Long sno) {
        this.sno = sno;
    }

    @OneToOne(cascade={CascadeType.ALL},mappedBy="purchaseStockDetailInfo")
    public StockInfo getStockInfo() {
        return stockInfo;
    }

    public void setStockInfo(StockInfo stockInfo) {
        this.stockInfo = stockInfo;
    }
}
