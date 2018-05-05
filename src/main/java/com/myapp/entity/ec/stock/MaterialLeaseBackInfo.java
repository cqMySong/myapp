package com.myapp.entity.ec.stock;

import com.myapp.core.annotation.MyEntityAnn;
import com.myapp.core.base.entity.CoreBaseBillInfo;
import com.myapp.entity.ec.basedata.ProjectInfo;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * @path：com.myapp.entity.ec.stock
 * @description：材料周转、租用归还信息
 * @author： ly
 * @date: 2018-01-06 23:56
 */
@Entity
@MyEntityAnn(name="材料周转、租用归还信息")
@Table(name="t_ec_material_lease_back")
public class MaterialLeaseBackInfo extends CoreBaseBillInfo {
    /**
     * 项目工程
     */
    private ProjectInfo project;
    /**
     * 出租单位
     */
    private String leaseUnit;


    private Date backDate;

    private String remark;

    /**
     * 归还详细信息
     */
    private Set<MaterialLeaseBackDetailInfo> materialLeaseBackDetailInfos;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fProjectId")
    public ProjectInfo getProject() {
        return project;
    }

    public void setProject(ProjectInfo project) {
        this.project = project;
    }


    @Column(name="fBackDate")
    public Date getBackDate() {
        return backDate;
    }

    public void setBackDate(Date backDate) {
        this.backDate = backDate;
    }


    @Column(name="fRemark",length = 500)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name="fLeaseUnit",length = 200)
    public String getLeaseUnit() {
        return leaseUnit;
    }

    public void setLeaseUnit(String leaseUnit) {
        this.leaseUnit = leaseUnit;
    }

    @OneToMany(cascade={CascadeType.ALL},mappedBy="parent")
    @OrderBy("seq ASC")
    public Set<MaterialLeaseBackDetailInfo> getMaterialLeaseBackDetailInfos() {
        return materialLeaseBackDetailInfos;
    }

    public void setMaterialLeaseBackDetailInfos(Set<MaterialLeaseBackDetailInfo> materialLeaseBackDetailInfos) {
        this.materialLeaseBackDetailInfos = materialLeaseBackDetailInfos;
    }
}
