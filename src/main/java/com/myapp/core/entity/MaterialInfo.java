package com.myapp.core.entity;

import com.myapp.core.annotation.MyEntityAnn;
import com.myapp.core.base.entity.CoreBaseDataInfo;
import com.myapp.core.enums.MaterialType;
import org.hibernate.annotations.Type;

import javax.persistence.*;

/**
 * 包路径：com.myapp.entity.ec.basedata
 * 功能说明：
 * @author ： ly
 * @date: 2017-08-25 16:47
 */
@Entity
@MyEntityAnn(name="物料信息")
@Table(name="t_base_material")
public class MaterialInfo extends CoreBaseDataInfo {
    /**
     * 类型 MATERIAL 材料  EQUIPMENT 设备
     */
    private MaterialType materialType;
    /**
     * 拼音码 方便检索
     */
    private String pinyin;
    /**
     *规格
     */
    private String specification;
    /**
     * 单位
     */
    private MeasureUnitInfo unit;

    @Column(name="fMaterialType",length = 20)
    @Type(type="myEnum",parameters={@org.hibernate.annotations.Parameter(name="enumClass",value="com.myapp.core.enums.MaterialType")})
    public MaterialType getMaterialType() {
        return materialType;
    }

    public void setMaterialType(MaterialType materialType) {
        this.materialType = materialType;
    }

    @Column(name="fPinyin",length = 10)
    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    @Column(name="fSpecification",length = 50)
    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fUnit")
    public MeasureUnitInfo getUnit() {
        return unit;
    }

    public void setUnit(MeasureUnitInfo unit) {
        this.unit = unit;
    }
}
