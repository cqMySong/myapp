package com.myapp.entity.ec.stock;

import com.myapp.core.annotation.MyEntityAnn;
import com.myapp.core.base.entity.CoreBaseEntryInfo;
import com.myapp.core.entity.MaterialInfo;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @path：com.myapp.entity.ec.stock
 * @description：
 * @author： ly
 * @date: 2017-11-11 17:43
 */
@Entity
@MyEntityAnn(name="图算用量详细")
@Table(name="t_ec_stock_calculation_detail")
public class StockCalculationDetailInfo extends CoreBaseEntryInfo<StockCalculationInfo> {
    /**
     * 物料信息
     */
    private MaterialInfo material;

    /**
     * 盘存明细信息
     */
    private StockInventoryDetailInfo stockInventoryDetailInfo;
    /**
     * 当期图算用量
     */
    private BigDecimal calculationCount;
    /**
     * 计量单位
     */
    private String measureUnit;

    /**
     * 备注
     */
    private String remark;


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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fStockInventoryDetailId")
    public StockInventoryDetailInfo getStockInventoryDetailInfo() {
        return stockInventoryDetailInfo;
    }

    public void setStockInventoryDetailInfo(StockInventoryDetailInfo stockInventoryDetailInfo) {
        this.stockInventoryDetailInfo = stockInventoryDetailInfo;
    }

    @Column(name="fCalculationCount",precision = 10,scale = 2)
    public BigDecimal getCalculationCount() {
        return calculationCount;
    }

    public void setCalculationCount(BigDecimal calculationCount) {
        this.calculationCount = calculationCount;
    }

    @Column(name="fMeasureUnit",length = 50)
    public String getMeasureUnit() {
        return measureUnit;
    }

    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }
}
