package com.myapp.entity.ec.budget;

import com.myapp.core.annotation.MyEntityAnn;
import com.myapp.core.base.entity.CoreBaseEntryInfo;
import com.myapp.core.entity.MaterialInfo;
import com.myapp.core.entity.MeasureUnitInfo;
import com.myapp.core.enums.MaterialType;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

/**
 * @path：com.myapp.entity.ec.budget
 * @description：材料设备预算清单
 * @author ： ly
 * @date: 2017-08-25 15:21
 */
@Entity
@MyEntityAnn(name="材料设备预算清单明细")
@Table(name="t_ec_budgeting_detail")
public class BudgetingDetailInfo extends CoreBaseEntryInfo<BudgetingInfo> {
    /**
     * 预算数量
     */
    private BigDecimal quantity;
    /**
     * 预算单价
     */
    private BigDecimal budgetaryPrice;
    /**
     * 预算物料信息
     */
    private MaterialInfo material;
    /**
     * 预算物料类型
     */
    private MaterialType materialType;
    /**
     * 规格
     */
    private String specification;
    /**
     * 计量单位
     */
    private MeasureUnitInfo measureUnitInfo;
    /**
     * 备注
     */
    private String remark;
    /**
     * 物料名称
     */
    private String materialName;
    /**
     * 预算询价信息
     */
    private Set<EnquiryPriceDetailInfo> enquiryPriceDetailInfos;

    @Column(name="fQuantity",precision = 10,scale = 2)
    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    @Column(name="fBudgetaryPrice",precision = 10,scale = 4)
    public BigDecimal getBudgetaryPrice() {
        return budgetaryPrice;
    }

    public void setBudgetaryPrice(BigDecimal budgetaryPrice) {
        this.budgetaryPrice = budgetaryPrice;
    }

    @ManyToOne
    @JoinColumn(name = "fMaterialId")
    public MaterialInfo getMaterial() {
        return material;
    }

    public void setMaterial(MaterialInfo material) {
        this.material = material;
    }
    @Column(name="fRemark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    @ManyToOne
    @JoinColumn(name = "fMeasureUnitId")
    public MeasureUnitInfo getMeasureUnitInfo() {
        return measureUnitInfo;
    }

    public void setMeasureUnitInfo(MeasureUnitInfo measureUnitInfo) {
        this.measureUnitInfo = measureUnitInfo;
    }

    @Column(name="fMaterialName",length = 50)
    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    @OneToMany(cascade={CascadeType.ALL},mappedBy="budgetingDetailInfo")
    public Set<EnquiryPriceDetailInfo> getEnquiryPriceDetailInfos() {
        return enquiryPriceDetailInfos;
    }

    public void setEnquiryPriceDetailInfos(Set<EnquiryPriceDetailInfo> enquiryPriceDetailInfos) {
        this.enquiryPriceDetailInfos = enquiryPriceDetailInfos;
    }
}
