package com.myapp.entity.ec.engineering;

import com.myapp.core.annotation.MyEntityAnn;
import com.myapp.core.base.entity.CoreBaseBillInfo;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.PaymentType;
import com.myapp.entity.ec.basedata.ECUnitInfo;
import com.myapp.entity.ec.basedata.ProjectInfo;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @path：com.myapp.entity.ec.engineering
 * @description：工程进度款
 * @author： ly
 * @date: 2017-11-13 22:09
 */
@Entity
@MyEntityAnn(name="进度款结算")
@Table(name="t_ec_progress_fund")
public class ProgressFundInfo extends CoreBaseBillInfo {
    /**
     *所属项目
     */
    private ProjectInfo project;
    /**
     * 工程合同
     */
    private EngineeringContractInfo engineeringContractInfo;

    /**
     * 合同单位
     */
    private ECUnitInfo ecUnitInfo;

    /**
     * 结算金额
     */
    private BigDecimal settleAmount;
    /**
     * 经办人
     */
    private UserInfo operator;
    /**
     * 结算时间
     */
    private Date settleDate;
    /**
     * 工作内容
     */
    private String jobContent;
    /**
     * 备注
     */
    private String remark;
    /**
     * 付款类型
     */
    private PaymentType paymentType;
    /**
     * 修改前数据
     */
    private ProgressFundInfo oldProgressFundInfo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fProjectId")
    public ProjectInfo getProject() {
        return project;
    }

    public void setProject(ProjectInfo project) {
        this.project = project;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fEngineeringContractId")
    public EngineeringContractInfo getEngineeringContractInfo() {
        return engineeringContractInfo;
    }

    public void setEngineeringContractInfo(EngineeringContractInfo engineeringContractInfo) {
        this.engineeringContractInfo = engineeringContractInfo;
    }

    @Column(name="fSettleAmount",precision = 10,scale = 2)
    public BigDecimal getSettleAmount() {
        return settleAmount;
    }

    public void setSettleAmount(BigDecimal settleAmount) {
        this.settleAmount = settleAmount;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fOperatorId")
    public UserInfo getOperator() {
        return operator;
    }

    public void setOperator(UserInfo operator) {
        this.operator = operator;
    }

    @Column(name="fSettleDate")
    public Date getSettleDate() {
        return settleDate;
    }

    public void setSettleDate(Date settleDate) {
        this.settleDate = settleDate;
    }

    @Column(name="fRemark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Transient
    public ProgressFundInfo getOldProgressFundInfo() {
        return oldProgressFundInfo;
    }

    public void setOldProgressFundInfo(ProgressFundInfo oldProgressFundInfo) {
        this.oldProgressFundInfo = oldProgressFundInfo;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fEcUnitId")
    public ECUnitInfo getEcUnitInfo() {
        return ecUnitInfo;
    }

    public void setEcUnitInfo(ECUnitInfo ecUnitInfo) {
        this.ecUnitInfo = ecUnitInfo;
    }

    @Column(name="fPaymentType",length = 20)
    @Type(type="myEnum",parameters={@org.hibernate.annotations.Parameter(name="enumClass",value="com.myapp.core.enums.PaymentType")})
    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    @Column(name="fJobContent",length = 500)
    public String getJobContent() {
        return jobContent;
    }

    public void setJobContent(String jobContent) {
        this.jobContent = jobContent;
    }
}
