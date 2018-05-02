package com.myapp.entity.ec.stock;

import com.myapp.core.annotation.MyEntityAnn;
import com.myapp.core.base.entity.CoreBaseEntryInfo;
import com.myapp.core.entity.MaterialInfo;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @path：com.myapp.entity.ec.stock
 * @description：
 * @author： ly
 * @date: 2017-11-11 17:43
 */
@Entity
@MyEntityAnn(name="盘存明细信息")
@Table(name="t_ec_stock_inventory_detail")
public class StockInventoryDetailInfo extends CoreBaseEntryInfo<StockInventoryInfo> implements Serializable {
    /**
     * 库存信息
     */
    private StockInfo stockInfo;
    /**
     * 物料信息
     */
    private MaterialInfo material;
    /**
     * 库存数量
     */
    private BigDecimal stockCount;

    /**
     * 盘存数量
     */
    private BigDecimal inventoryCount;
    /**
     * 实际用量
     */
    private BigDecimal actualUseCount;
    /**
     * 预算数量
     */
    private BigDecimal quantity;
    /**
     * 入库数量
     */
    private BigDecimal inStockCount;
    /**
     * 备注
     */
    private String remark;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fStockId")
    public StockInfo getStockInfo() {
        return stockInfo;
    }

    public void setStockInfo(StockInfo stockInfo) {
        this.stockInfo = stockInfo;
    }

    @Column(name="fStockCount",precision = 10,scale = 2)
    public BigDecimal getStockCount() {
        return stockCount;
    }

    public void setStockCount(BigDecimal stockCount) {
        this.stockCount = stockCount;
    }

    @Column(name="fInventoryCount",precision = 10,scale = 2)
    public BigDecimal getInventoryCount() {
        return inventoryCount;
    }

    public void setInventoryCount(BigDecimal inventoryCount) {
        this.inventoryCount = inventoryCount;
    }

    @Column(name="fRemark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fMaterialId")
    public MaterialInfo getMaterial() {
        return material;
    }

    public void setMaterial(MaterialInfo material) {
        this.material = material;
    }

    @Column(name = "fActualUseCount")
    public BigDecimal getActualUseCount() {
        return actualUseCount;
    }

    public void setActualUseCount(BigDecimal actualUseCount) {
        this.actualUseCount = actualUseCount;
    }
    @Column(name = "fQuantity")
    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }
    @Column(name = "fInStockCount")
    public BigDecimal getInStockCount() {
        return inStockCount;
    }

    public void setInStockCount(BigDecimal inStockCount) {
        this.inStockCount = inStockCount;
    }
}
