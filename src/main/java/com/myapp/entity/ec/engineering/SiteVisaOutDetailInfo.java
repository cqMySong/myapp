package com.myapp.entity.ec.engineering;

import com.myapp.core.annotation.MyEntityAnn;
import com.myapp.core.base.entity.CoreBaseEntryInfo;
import com.myapp.core.enums.TypeOfWork;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @path：com.myapp.entity.ec.engineering
 * @description：现场签证支出
 * @author： ly
 * @date: 2017-11-25 16:28
 */
@Entity
@MyEntityAnn(name="现场签证支出明细")
@Table(name="t_ec_site_visa_out_detail")
public class SiteVisaOutDetailInfo extends CoreBaseEntryInfo<SiteVisaOutInfo> {
    /**
     *工种
     */
    private TypeOfWork typeOfWork;
    /**
     * 工作开始时间
     */
    private Date workStartTime;
    /**
     * 工作结束时间
     */
    private Date workEndTime;
    /**
     * 机械名称
     */
    private String mechanicalName;
    /**
     * 机械工作开始时间
     */
    private Date mechanicalStartTime;
    /**
     * 机械工作结束时间
     */
    private Date mechanicalEndTime;
    /**
     * 材料名称
     */
    private String materialName;
    /**
     * 使用数量
     */
    private BigDecimal useCount;

    @Column(name="fTypeOfWork",length = 20)
    @Type(type="myEnum",parameters={@org.hibernate.annotations.Parameter(name="enumClass",value="com.myapp.core.enums.TypeOfWork")})
    public TypeOfWork getTypeOfWork() {
        return typeOfWork;
    }

    public void setTypeOfWork(TypeOfWork typeOfWork) {
        this.typeOfWork = typeOfWork;
    }

    @Column(name="fWorkStartTime")
    public Date getWorkStartTime() {
        return workStartTime;
    }

    public void setWorkStartTime(Date workStartTime) {
        this.workStartTime = workStartTime;
    }

    @Column(name="fWorkEndTime")
    public Date getWorkEndTime() {
        return workEndTime;
    }

    public void setWorkEndTime(Date workEndTime) {
        this.workEndTime = workEndTime;
    }

    @Column(name="fMechanicalName")
    public String getMechanicalName() {
        return mechanicalName;
    }

    public void setMechanicalName(String mechanicalName) {
        this.mechanicalName = mechanicalName;
    }

    @Column(name="fMechanicalStartTime")
    public Date getMechanicalStartTime() {
        return mechanicalStartTime;
    }

    public void setMechanicalStartTime(Date mechanicalStartTime) {
        this.mechanicalStartTime = mechanicalStartTime;
    }

    @Column(name="fMechanicalEndTime")
    public Date getMechanicalEndTime() {
        return mechanicalEndTime;
    }

    public void setMechanicalEndTime(Date mechanicalEndTime) {
        this.mechanicalEndTime = mechanicalEndTime;
    }

    @Column(name="fMaterialName")
    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    @Column(name="fUseCount",precision=10,scale = 2)
    public BigDecimal getUseCount() {
        return useCount;
    }

    public void setUseCount(BigDecimal useCount) {
        this.useCount = useCount;
    }
}
