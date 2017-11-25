package com.myapp.entity.ec.stock;

import com.myapp.core.annotation.MyEntityAnn;
import com.myapp.core.base.entity.CoreBaseEntryInfo;
import com.myapp.core.entity.MaterialInfo;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @path：com.myapp.entity.ec.stock.stockout
 * @description：出库单据相信信息
 * @author： ly
 * @date: 2017-11-07 15:07
 */
@Entity
@MyEntityAnn(name="出库明细信息")
@Table(name="t_ec_stock_out_detail")
public class StockOutDetailInfo extends CoreBaseEntryInfo<StockOutInfo> {
    /**
     * 物料信息
     */
    private MaterialInfo material;
    /**
     * 规格
     */
    private String specification;
    /**
     * 计量单位名称
     */
    private String measureUnit;
    /**
     * 领取数量
     */
    private BigDecimal count;
    /**
     * 备注
     */
    private String remark;
    /**
     * 库存信息
     */
    private StockInfo stockInfo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fMaterialId")
    public MaterialInfo getMaterial() {
        return material;
    }

    public void setMaterial(MaterialInfo material) {
        this.material = material;
    }

    @Column(name="fSpecification",length = 100)
    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    @Column(name="fMeasureUnit",length = 50)
    public String getMeasureUnit() {
        return measureUnit;
    }

    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }

    @Column(name="fCount",precision = 10,scale = 2)
    public BigDecimal getCount() {
        return count;
    }

    public void setCount(BigDecimal count) {
        this.count = count;
    }

    @Column(name="fRemark",length = 200)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fStockId")
    public StockInfo getStockInfo() {
        return stockInfo;
    }

    public void setStockInfo(StockInfo stockInfo) {
        this.stockInfo = stockInfo;
    }
}
