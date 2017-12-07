package com.myapp.entity.ec.purchase;

import com.myapp.core.annotation.MyEntityAnn;
import com.myapp.core.base.entity.CoreBaseEntryInfo;
import com.myapp.core.entity.MaterialInfo;
import com.myapp.core.entity.MeasureUnitInfo;
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
     * 申购信息
     */
    private ApplyMaterialInfo applyMaterialInfo;
    /**
     * 申购明细信息
     */
    private ApplyMaterialDetailInfo applyMaterialDetailInfo;
    /**
     * 物料信息
     */
    private MaterialInfo material;
    /**
     * 剂量单位
     */
    private String measureUnitName;
    /**
     * 规格
     */
    private String specification;
    /**
     * 产地
     */
    private String origin;
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

    @OneToOne
    @JoinColumn(name = "fApplyMaterialId")
    public ApplyMaterialInfo getApplyMaterialInfo() {
        return applyMaterialInfo;
    }

    public void setApplyMaterialInfo(ApplyMaterialInfo applyMaterialInfo) {
        this.applyMaterialInfo = applyMaterialInfo;
    }

    @OneToOne
    @JoinColumn(name = "fApplyMaterialDetailId")
    public ApplyMaterialDetailInfo getApplyMaterialDetailInfo() {
        return applyMaterialDetailInfo;
    }

    public void setApplyMaterialDetailInfo(ApplyMaterialDetailInfo applyMaterialDetailInfo) {
        this.applyMaterialDetailInfo = applyMaterialDetailInfo;
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

    @OneToOne
    @JoinColumn(name = "fMaterialId")
    public MaterialInfo getMaterial() {
        return material;
    }

    public void setMaterial(MaterialInfo material) {
        this.material = material;
    }

    @Column(name="fMeasureUnitName",length = 150)
    public String getMeasureUnitName() {
        return measureUnitName;
    }

    public void setMeasureUnitName(String measureUnitName) {
        this.measureUnitName = measureUnitName;
    }

    @Column(name="fSpecification",length = 150)
    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    @Column(name="fOrigin",length = 250)
    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }
}
