package com.myapp.entity.ec.stock;

import com.myapp.core.annotation.MyEntityAnn;
import com.myapp.core.base.entity.CoreBaseBillInfo;
import com.myapp.core.base.entity.CoreInfo;
import com.myapp.core.entity.MaterialInfo;
import com.myapp.core.entity.MeasureUnitInfo;
import com.myapp.entity.ec.basedata.ProjectInfo;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @path：com.myapp.entity.ec.stock
 * @description：材料周转、租用信息
 * @author： ly
 * @date: 2018-01-06 23:56
 */
@Entity
@MyEntityAnn(name="材料周转、租用信息")
@Table(name="t_ec_material_lease")
public class MaterialLeaseInfo extends CoreBaseBillInfo {
    /**
     * 项目工程
     */
    private ProjectInfo project;
    /**
     * 材料信息
     */
    private MaterialInfo materialInfo;
    /**
     * 单位
     */
    private MeasureUnitInfo measureUnitInfo;
    /**
     * 租用日期
     */
    private Date leaseDate;
    /**
     * 租用数量
     */
    private BigDecimal leaseCount;

    /**
     * 出租单位
     */
    private String leaseUnit;
    /**
     * 备注
     */
    private String remark;

    private MaterialLeaseBackInfo materialLeaseBackInfo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fProjectId")
    public ProjectInfo getProject() {
        return project;
    }

    public void setProject(ProjectInfo project) {
        this.project = project;
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
    @Column(name="fLeaseDate")
    public Date getLeaseDate() {
        return leaseDate;
    }

    public void setLeaseDate(Date leaseDate) {
        this.leaseDate = leaseDate;
    }
    @Column(name="fLeaseCount",precision = 10,scale = 2)
    public BigDecimal getLeaseCount() {
        return leaseCount;
    }

    public void setLeaseCount(BigDecimal leaseCount) {
        this.leaseCount = leaseCount;
    }
    @Column(name="fLeaseUnit",length = 200)
    public String getLeaseUnit() {
        return leaseUnit;
    }

    public void setLeaseUnit(String leaseUnit) {
        this.leaseUnit = leaseUnit;
    }
    @Column(name="fRemark",length = 500)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @OneToOne(cascade={CascadeType.ALL},mappedBy="materialLeaseInfo")
    public MaterialLeaseBackInfo getMaterialLeaseBackInfo() {
        return materialLeaseBackInfo;
    }

    public void setMaterialLeaseBackInfo(MaterialLeaseBackInfo materialLeaseBackInfo) {
        this.materialLeaseBackInfo = materialLeaseBackInfo;
    }
}
