package com.myapp.entity.ec.stock;

import com.myapp.core.annotation.MyEntityAnn;
import com.myapp.core.base.entity.CoreBaseEntryInfo;
import com.myapp.core.entity.MaterialInfo;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @path：com.myapp.entity.ec.stock.stockout
 * @description：归还单据相信信息
 * @author： ly
 * @date: 2017-11-07 15:07
 */
@Entity
@MyEntityAnn(name="归还明细信息")
@Table(name="t_ec_stock_revert_detail")
public class StockRevertDetailInfo extends CoreBaseEntryInfo<StockRevertInfo> {

    /**
     * 领料订单号
     */
    private String stockOutNumber;
    /**
     * 领取详细信息
     */
    private StockOutDetailInfo stockOutDetailInfo;
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
     * 归还数量
     */
    private BigDecimal count;
    /**
     * 备注
     */
    private String remark;

    @Column(name="fStockOutNumber",length = 100)
    public String getStockOutNumber() {
        return stockOutNumber;
    }

    public void setStockOutNumber(String stockOutNumber) {
        this.stockOutNumber = stockOutNumber;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fStockOutDetailId")
    public StockOutDetailInfo getStockOutDetailInfo() {
        return stockOutDetailInfo;
    }

    public void setStockOutDetailInfo(StockOutDetailInfo stockOutDetailInfo) {
        this.stockOutDetailInfo = stockOutDetailInfo;
    }

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
}
