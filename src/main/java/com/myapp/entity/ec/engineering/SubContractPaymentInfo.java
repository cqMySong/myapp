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
 * @description：分包付款
 * @author： ly
 * @date: 2017-11-13 22:09
 */
@Entity
@MyEntityAnn(name="分包付款")
@Table(name="t_ec_subcontract_payment")
public class SubContractPaymentInfo extends CoreBaseBillInfo {
    /**
     *所属项目
     */
    private ProjectInfo project;
    /**
     * 分包合同
     */
    private SubcontractInfo subcontractInfo;

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
    private String operator;
    /**
     * 开始时间
     */
    private Date startDate;
    /**
     * 结束时间
     */
    private Date endDate;
    /**
     * 工作内容
     */
    private String jobContent;
    /**
     * 备注
     */
    private String remark;
    /**
     * 结算类型
     */
    private SettleType settleType;
    /**
     * 是否最终结算
     */
    private Boolean endSettle;
    /**
     * 修改前数据
     */
    private SubContractPaymentInfo oldSubContractPaymentInfo;

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
    public SubcontractInfo getSubcontractInfo() {
        return subcontractInfo;
    }

    public void setSubcontractInfo(SubcontractInfo subcontractInfo) {
        this.subcontractInfo = subcontractInfo;
    }

    @Column(name="fSettleAmount",precision = 10,scale = 2)
    public BigDecimal getSettleAmount() {
        return settleAmount;
    }

    public void setSettleAmount(BigDecimal settleAmount) {
        this.settleAmount = settleAmount;
    }

    @Column(name = "fOperator")
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

    @Transient
    public SubContractPaymentInfo getOldSubContractPaymentInfo() {
        return oldSubContractPaymentInfo;
    }

    public void setOldSubContractPaymentInfo(SubContractPaymentInfo oldSubContractPaymentInfo) {
        this.oldSubContractPaymentInfo = oldSubContractPaymentInfo;
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
    @Type(type="myEnum",parameters={@org.hibernate.annotations.Parameter(name="enumClass",value="com.myapp.core.enums.SettleType")})
    public SettleType getSettleType() {
        return settleType;
    }

    public void setSettleType(SettleType settleType) {
        this.settleType = settleType;
    }

    @Column(name="fJobContent",length = 500)
    public String getJobContent() {
        return jobContent;
    }

    public void setJobContent(String jobContent) {
        this.jobContent = jobContent;
    }

    @Column(name = "fStartDate")
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Column(name = "fEndDate")
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Column(name = "fEndSettle")
    public Boolean getEndSettle() {
        return endSettle;
    }

    public void setEndSettle(Boolean endSettle) {
        this.endSettle = endSettle;
    }
}
