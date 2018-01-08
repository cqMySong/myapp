package com.myapp.entity.ec.engineering;

import com.myapp.core.annotation.MyEntityAnn;
import com.myapp.core.base.entity.CoreBaseBillInfo;
import com.myapp.entity.ec.basedata.ProjectInfo;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @path：com.myapp.entity.ec.engineering
 * @description：劳务费支付
 * @author： ly
 * @date: 2018-01-08 22:12
 */
@Entity
@MyEntityAnn(name="业主付款")
@Table(name="t_ec_service_charge")
public class ServiceChargeInfo extends CoreBaseBillInfo {
    /**
     * 工程项目
     */
    private ProjectInfo project;
    /**
     * 分包付款
     */
    private SubContractPaymentInfo subContractPaymentInfo;
    /**
     * 分包合同信息
     */
    private SubcontractInfo subcontractInfo;
    /**
     * 支付金额
     */
    private BigDecimal payAmount;
    /**
     * 支付日期
     */
    private Date payDate;
    /**
     * 合同约定比例
     */
    private BigDecimal contractRatio;
    /**
     * 备注
     */
    private String remark;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fProjectId")
    public ProjectInfo getProject() {
        return project;
    }

    public void setProject(ProjectInfo project) {
        this.project = project;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fSubContractPaymentId")
    public SubContractPaymentInfo getSubContractPaymentInfo() {
        return subContractPaymentInfo;
    }

    public void setSubContractPaymentInfo(SubContractPaymentInfo subContractPaymentInfo) {
        this.subContractPaymentInfo = subContractPaymentInfo;
    }

    @Column(name="fPayAmount",precision = 15,scale = 2)
    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }
    @Column(name="fPayDate")
    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    @Column(name="fContractRatio",precision = 5,scale = 2)
    public BigDecimal getContractRatio() {
        return contractRatio;
    }

    public void setContractRatio(BigDecimal contractRatio) {
        this.contractRatio = contractRatio;
    }
    @Column(name="fRemark",length = 500)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fSubContractId")
    public SubcontractInfo getSubcontractInfo() {
        return subcontractInfo;
    }

    public void setSubcontractInfo(SubcontractInfo subcontractInfo) {
        this.subcontractInfo = subcontractInfo;
    }
}
