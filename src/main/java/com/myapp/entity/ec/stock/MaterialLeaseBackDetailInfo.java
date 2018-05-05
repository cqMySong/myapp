package com.myapp.entity.ec.stock;

import com.myapp.core.annotation.MyEntityAnn;
import com.myapp.core.base.entity.CoreBaseEntryInfo;
import com.myapp.core.entity.MaterialInfo;
import com.myapp.core.entity.MeasureUnitInfo;
import com.myapp.core.enums.MaterialType;
import com.myapp.entity.ec.basedata.ProjectInfo;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @path：com.myapp.entity.ec.stock
 * @description：材料周转、租用归还信息
 * @author： ly
 * @date: 2018-01-06 23:56
 */
@Entity
@MyEntityAnn(name="材料周转、租用归还详细信息")
@Table(name="t_ec_material_lease_back_detail")
public class MaterialLeaseBackDetailInfo extends CoreBaseEntryInfo<MaterialLeaseBackInfo> {

    /**
     * 物料类型
     */
    private MaterialType materialType;
    /**
     * 材料信息
     */
    private MaterialInfo materialInfo;

    /**
     * 单位
     */
    private MeasureUnitInfo measureUnitInfo;

    private String specification;

    private BigDecimal backCount;

    private String remark;

    @Column(name="fMaterialType",length = 20)
    @Type(type="myEnum",parameters={@org.hibernate.annotations.Parameter(name="enumClass",value="com.myapp.core.enums.MaterialType")})
    public MaterialType getMaterialType() {
        return materialType;
    }

    public void setMaterialType(MaterialType materialType) {
        this.materialType = materialType;
    }

    @Column(name="fBackCount",precision = 10,scale = 2)
    public BigDecimal getBackCount() {
        return backCount;
    }

    public void setBackCount(BigDecimal backCount) {
        this.backCount = backCount;
    }

    @Column(name="fRemark",length = 500)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fMaterialId")
    public MaterialInfo getMaterialInfo() {
        return materialInfo;
    }

    public void setMaterialInfo(MaterialInfo materialInfo) {
        this.materialInfo = materialInfo;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fMeasureUnitId")
    public MeasureUnitInfo getMeasureUnitInfo() {
        return measureUnitInfo;
    }

    public void setMeasureUnitInfo(MeasureUnitInfo measureUnitInfo) {
        this.measureUnitInfo = measureUnitInfo;
    }

    @Column(name="fSpecification",length = 50)
    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

}
