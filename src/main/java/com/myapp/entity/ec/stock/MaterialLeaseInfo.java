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
import java.util.Set;

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
     * 租用日期
     */
    private Date leaseDate;

    /**
     * 出租单位
     */
    private String leaseUnit;
    /**
     * 备注
     */
    private String remark;

    /**
     * 出租详细信息
     */
    private Set<MaterialLeaseDetailInfo> materialLeaseDetailInfos;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fProjectId")
    public ProjectInfo getProject() {
        return project;
    }

    public void setProject(ProjectInfo project) {
        this.project = project;
    }

    @Column(name="fLeaseDate")
    public Date getLeaseDate() {
        return leaseDate;
    }

    public void setLeaseDate(Date leaseDate) {
        this.leaseDate = leaseDate;
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

    @OneToMany(cascade={CascadeType.ALL},mappedBy="parent")
    @OrderBy("seq ASC")
    public Set<MaterialLeaseDetailInfo> getMaterialLeaseDetailInfos() {
        return materialLeaseDetailInfos;
    }

    public void setMaterialLeaseDetailInfos(Set<MaterialLeaseDetailInfo> materialLeaseDetailInfos) {
        this.materialLeaseDetailInfos = materialLeaseDetailInfos;
    }
}
