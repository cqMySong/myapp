package com.myapp.entity.ec.engineering;

import com.myapp.core.annotation.MyEntityAnn;
import com.myapp.core.base.entity.CoreBaseBillInfo;
import com.myapp.core.enums.SettleType;
import com.myapp.entity.ec.basedata.ECUnitInfo;
import com.myapp.entity.ec.basedata.ProjectInfo;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @path：com.myapp.entity.ec.engineering
 * @description：业主付款
 * @author： ly
 * @date: 2017-11-13 22:09
 */
@Entity
@MyEntityAnn(name="业主付款")
@Table(name="t_ec_proprietor_payment")
public class ProprietorPaymentInfo extends CoreBaseBillInfo {
    /**
     *所属项目
     */
    private ProjectInfo project;


    /**
     * 报送金额
     */
    private BigDecimal deliveryAmount;
    /**
     * 报送时间
     */
    private Date deliveryDate;
    /**
     * 核定金额
     */
    private BigDecimal approvedAmount;
    /**
     * 付款金额
     */
    private BigDecimal paymentAmount;

    /**
     * 实际支付比例
     */
    private BigDecimal actualRatio;
    /**
     * 约定支付比例
     */
    private BigDecimal paymentRatio;
    /**
     * 经办人
     */
    private String operator;
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

    @Column(name="fDeliveryAmount",precision = 15,scale = 2)
    public BigDecimal getDeliveryAmount() {
        return deliveryAmount;
    }

    public void setDeliveryAmount(BigDecimal deliveryAmount) {
        this.deliveryAmount = deliveryAmount;
    }

    @Column(name="fDeliveryDate")
    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    @Column(name="fApprovedAmount",precision = 15,scale = 2)
    public BigDecimal getApprovedAmount() {
        return approvedAmount;
    }

    public void setApprovedAmount(BigDecimal approvedAmount) {
        this.approvedAmount = approvedAmount;
    }

    @Column(name="fPaymentAmount",precision = 15,scale = 2)
    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    @Column(name="fPaymentRatio",precision = 10,scale = 2)
    public BigDecimal getPaymentRatio() {
        return paymentRatio;
    }

    public void setPaymentRatio(BigDecimal paymentRatio) {
        this.paymentRatio = paymentRatio;
    }

    @Column(name="fOperator")
    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
    @Column(name="fRemark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name="fActualRatio",precision = 10,scale = 2)
    public BigDecimal getActualRatio() {
        return actualRatio;
    }

    public void setActualRatio(BigDecimal actualRatio) {
        this.actualRatio = actualRatio;
    }
}
