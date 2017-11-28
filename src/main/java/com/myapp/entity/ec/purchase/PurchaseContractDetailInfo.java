package com.myapp.entity.ec.purchase;

import com.myapp.core.annotation.MyEntityAnn;
import com.myapp.core.base.entity.CoreBaseEntryInfo;
import com.myapp.core.entity.MaterialInfo;
import com.myapp.core.entity.MeasureUnitInfo;
import com.myapp.core.enums.MaterialType;
import com.myapp.entity.ec.budget.EnquiryPriceDetailInfo;
import com.myapp.entity.ec.budget.EnquiryPriceInfo;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

/**
 * @path：com.myapp.entity.ec.purchase
 * @description：采购合同详细信息
 * @author ： ly
 * @date: 2017-08-28 21:10
 */
@Entity
@MyEntityAnn(name="采购合同详细")
@Table(name="t_ec_purchase_contract_detail")
public class PurchaseContractDetailInfo extends CoreBaseEntryInfo<PurchaseContractInfo> {
    /**
     * 申购详细信息
     */
    private ApplyMaterialDetailInfo applyMaterialDetailInfo;
    /**
     * 物料信息
     */
    private MaterialInfo material;
    /**
     * 物料类型
     */
    private MaterialType materialType;
    /**
     * 物料名称
     */
    private String materialName;
    /**
     * 规格
     */
    private String specification;
    /**
     * 产地
     */
    private String origin;
    /**
     * 计量单位
     */
    private String  measureUnitName;
    /**
     * 采购数量
     */
    private BigDecimal quantity;
    /**
     * 采购单价
     */
    private BigDecimal purchasePrice;
    /**
     *
     */
    private BigDecimal totalPrice;
    /**
     * 合同入库信息
     */
    private Set<PurchaseStockDetailInfo> purchaseStockDetailInfoSet;

    /**
     * 序号
     */
    private Long sno;

    @OneToOne
    @JoinColumn(name = "fApplyMaterialDetailId")
    public ApplyMaterialDetailInfo getApplyMaterialDetailInfo() {
        return applyMaterialDetailInfo;
    }

    public void setApplyMaterialDetailInfo(ApplyMaterialDetailInfo applyMaterialDetailInfo) {
        this.applyMaterialDetailInfo = applyMaterialDetailInfo;
    }

    @Column(name="fQuantity",precision = 10,scale = 2)
    public BigDecimal getQuantity() {
        return quantity;
    }
    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    @OneToOne
    @JoinColumn(name = "fMaterialId")
    public MaterialInfo getMaterial() {
        return material;
    }

    public void setMaterial(MaterialInfo material) {
        this.material = material;
    }

    @Column(name="fMaterialType",length = 20)
    @Type(type="myEnum",parameters={@org.hibernate.annotations.Parameter(name="enumClass",value="com.myapp.core.enums.MaterialType")})
    public MaterialType getMaterialType() {
        return materialType;
    }

    public void setMaterialType(MaterialType materialType) {
        this.materialType = materialType;
    }

    @Column(name="fSpecification",length = 50)
    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    @Column(name="fMeasureUnitName")
    public String getMeasureUnitName() {
        return measureUnitName;
    }

    public void setMeasureUnitName(String measureUnitName) {
        this.measureUnitName = measureUnitName;
    }

    @Column(name="fOrigin",length = 100)
    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    @Column(name="fPurchasePrice",precision = 10,scale = 2)
    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    @Column(name="fMaterialName",length = 100)
    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    @Column(name="fTotalPrice",precision = 10,scale = 2)
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    @OneToMany(cascade={CascadeType.ALL},mappedBy="purchaseContractDetailInfo")
    public Set<PurchaseStockDetailInfo> getPurchaseStockDetailInfoSet() {
        return purchaseStockDetailInfoSet;
    }

    public void setPurchaseStockDetailInfoSet(Set<PurchaseStockDetailInfo> purchaseStockDetailInfoSet) {
        this.purchaseStockDetailInfoSet = purchaseStockDetailInfoSet;
    }
}
