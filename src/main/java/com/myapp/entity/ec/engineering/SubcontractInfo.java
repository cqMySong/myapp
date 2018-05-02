package com.myapp.entity.ec.engineering;

import com.myapp.core.annotation.MyEntityAnn;
import com.myapp.core.base.entity.CoreBaseBillInfo;
import com.myapp.core.enums.ExpenseType;
import com.myapp.core.enums.PaymentMethod;
import com.myapp.core.enums.SubcontractExpenseType;
import com.myapp.entity.ec.basedata.ECUnitInfo;
import com.myapp.entity.ec.basedata.ProjectInfo;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @path：com.myapp.entity.ec.engineering
 * @description：分包合同
 * @author： ly
 * @date: 2017-11-12 21:20
 */
@Entity
@MyEntityAnn(name="分包合同")
@Table(name="t_ec_subcontract")
public class SubcontractInfo extends CoreBaseBillInfo {
    /**
     * 所属项目
     */
    private ProjectInfo project;
    /**
    * 费用类型
     */
    private SubcontractExpenseType subcontractExpenseType;

    /**
     * 合同单位
     */
    private ECUnitInfo ecUnitInfo;

    /**
     * 合同金额
     */
    private BigDecimal amount;
    /**
     * 负责人
     */
    private String director;
    /**
     * 负责人姓名
     */
    private String directorName;
    /**
     * 负责人联系电话
     */
    private String directorTel;
    /**
     * 资质
     */
    private String aptitude;
    /**
     * 付款方式
     */
    private PaymentMethod paymentMethod;
    /**
     * 合同内容
     */
    private String treatyContents;
    /**
     * 剩余结算金额
     */
    private BigDecimal balanceAmount;
    /**
     * 合同签订时间
     */
    private Date contractDate;
    /**
     * 备注
     */
    private String remark;

    /**
     * 修改前数据
     */
    private SubcontractInfo oldSubcontractInfo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fProjectId")
    public ProjectInfo getProject() {
        return project;
    }

    public void setProject(ProjectInfo project) {
        this.project = project;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fEcUnitId")
    public ECUnitInfo getEcUnitInfo() {
        return ecUnitInfo;
    }

    public void setEcUnitInfo(ECUnitInfo ecUnitInfo) {
        this.ecUnitInfo = ecUnitInfo;
    }

    @Column(name="fAmount",precision = 10,scale = 2)
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Column(name = "fDirector")
    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    @Column(name="fDirectorName",length = 30)
    public String getDirectorName() {
        return directorName;
    }

    public void setDirectorName(String directorName) {
        this.directorName = directorName;
    }
    @Column(name="fTreatyContents",length = 1000)
    public String getTreatyContents() {
        return treatyContents;
    }

    public void setTreatyContents(String treatyContents) {
        this.treatyContents = treatyContents;
    }

    @Column(name="fBalanceAmount",precision = 10,scale = 2)
    public BigDecimal getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(BigDecimal balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    @Transient
    public SubcontractInfo getOldSubcontractInfo() {
        return oldSubcontractInfo;
    }

    public void setOldSubcontractInfo(SubcontractInfo oldSubcontractInfo) {
        this.oldSubcontractInfo = oldSubcontractInfo;
    }

    @Column(name="fContractDate")
    public Date getContractDate() {
        return contractDate;
    }

    public void setContractDate(Date contractDate) {
        this.contractDate = contractDate;
    }

    @Column(name="fSubcontractExpenseType",length = 20)
    @Type(type="myEnum",parameters={@org.hibernate.annotations.Parameter(name="enumClass",value="com.myapp.core.enums.SubcontractExpenseType")})

    public SubcontractExpenseType getSubcontractExpenseType() {
        return subcontractExpenseType;
    }

    public void setSubcontractExpenseType(SubcontractExpenseType subcontractExpenseType) {
        this.subcontractExpenseType = subcontractExpenseType;
    }

    @Column(name="fDirectorTel",length = 50)
    public String getDirectorTel() {
        return directorTel;
    }

    public void setDirectorTel(String directorTel) {
        this.directorTel = directorTel;
    }

    @Column(name="fAptitude",length = 100)
    public String getAptitude() {
        return aptitude;
    }

    public void setAptitude(String aptitude) {
        this.aptitude = aptitude;
    }
    @Column(name="fPaymentMethod",length = 20)
    @Type(type="myEnum",parameters={@org.hibernate.annotations.Parameter(name="enumClass",value="com.myapp.core.enums.PaymentMethod")})
    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Column(name="fRemark",length = 200)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
