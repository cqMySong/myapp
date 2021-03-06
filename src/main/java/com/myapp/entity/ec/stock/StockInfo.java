package com.myapp.entity.ec.stock;

import com.myapp.core.annotation.MyEntityAnn;
import com.myapp.core.base.entity.CoreInfo;
import com.myapp.core.entity.MaterialInfo;
import com.myapp.core.enums.MaterialType;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.entity.ec.purchase.PurchaseStockDetailInfo;
import com.myapp.entity.ec.purchase.PurchaseStockInfo;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

/**
 * @path：com.myapp.entity.ec.stock
 * @description：库存信息
 * @author： ly
 * @date: 2017-11-03 14:26
 */
@Entity
@MyEntityAnn(name="库存信息")
@Table(name="t_ec_stock")
public class StockInfo extends CoreInfo {
    /**
     * 所属项目
     */
    private ProjectInfo projectInfo;
    /**
     * 物料信息
     */
    private MaterialInfo materialInfo;
    /**
     * 物料类型
     */
    private MaterialType materialType;
    /**
     * 规格
     */
    private String specification;
    /**
     * 计量单位名称
     */
    private String measureUnit;
    /**
     * 库存数量
     */
    private BigDecimal count;
    /**
     * 备注
     */
    private String remark;
    /**
     * 出库信息
     */
    private StockOutDetailInfo stockOutDetailInfo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fProjectId")
    public ProjectInfo getProjectInfo() {
        return projectInfo;
    }

    public void setProjectInfo(ProjectInfo projectInfo) {
        this.projectInfo = projectInfo;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fMaterialId")
    public MaterialInfo getMaterialInfo() {
        return materialInfo;
    }

    public void setMaterialInfo(MaterialInfo materialInfo) {
        this.materialInfo = materialInfo;
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

    @Column(name="fRemark",length = 150)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    @OneToOne(cascade={CascadeType.ALL},mappedBy="stockInfo")
    public StockOutDetailInfo getStockOutDetailInfo() {
        return stockOutDetailInfo;
    }

    public void setStockOutDetailInfo(StockOutDetailInfo stockOutDetailInfo) {
        this.stockOutDetailInfo = stockOutDetailInfo;
    }
    @Column(name="fMaterialType",length = 20)
    @Type(type="myEnum",parameters={@org.hibernate.annotations.Parameter(name="enumClass",value="com.myapp.core.enums.MaterialType")})
    public MaterialType getMaterialType() {
        return materialType;
    }

    public void setMaterialType(MaterialType materialType) {
        this.materialType = materialType;
    }
}
