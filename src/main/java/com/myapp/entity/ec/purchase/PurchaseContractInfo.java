package com.myapp.entity.ec.purchase;

import com.myapp.core.annotation.MyEntityAnn;
import com.myapp.core.base.entity.CoreBaseBillInfo;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.ExpenseType;
import com.myapp.core.enums.PaymentMethod;
import com.myapp.core.enums.PurchaseExpenseType;
import com.myapp.entity.ec.basedata.ProjectInfo;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

/**
 * @path：com.myapp.entity.ec.purchase
 * @description：采购合同
 * @author ： ly
 * @date: 2017-08-28 21:08
 */
@Entity
@MyEntityAnn(name="采购合同")
@Table(name="t_ec_purchase_contract")
public class PurchaseContractInfo extends CoreBaseBillInfo {
    /**
     * 项目单位
     */
    private ProjectInfo project;
    /**
     * 费用类型
     */
    private PurchaseExpenseType expenseType;
    /**
     * 采购公司
     */
    private String supplyCompany;
    /**
     * 付款方式
     */
    private PaymentMethod paymentMethod;
    /**
     * 合同金额
     */
    private BigDecimal amount;

    /**
     * 剩余结算金额
     */
    private BigDecimal balanceSettleAmount;
    /**
     * 经办人
     */
    private String operator;
    /**
     * 联系电话
     */
    private String contactTel;
    /**
     * 备注
     */
    private String remark;
    /**
     * 采购合同清单信息
     */
    private Set<PurchaseContractDetailInfo> purchaseContractDetailInfos;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fProjectId")
    public ProjectInfo getProject() {
        return project;
    }

    public void setProject(ProjectInfo project) {
        this.project = project;
    }
    @Column(name="fRemark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @OneToMany(cascade={CascadeType.ALL},mappedBy="parent")
    @OrderBy("seq ASC")
    public Set<PurchaseContractDetailInfo> getPurchaseContractDetailInfos() {
        return purchaseContractDetailInfos;
    }

    public void setPurchaseContractDetailInfos(Set<PurchaseContractDetailInfo> purchaseContractDetailInfos) {
        this.purchaseContractDetailInfos = purchaseContractDetailInfos;
    }
    @Column(name="fSupplyCompany",length = 100)
    public String getSupplyCompany() {
        return supplyCompany;
    }

    public void setSupplyCompany(String supplyCompany) {
        this.supplyCompany = supplyCompany;
    }

    @Column(name = "fOperatorName",length=50)
    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    @Column(name="fAmount",precision = 10,scale = 2)
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Column(name="fBalanceSettleAmount",precision = 10,scale = 2)
    public BigDecimal getBalanceSettleAmount() {
        return balanceSettleAmount;
    }

    public void setBalanceSettleAmount(BigDecimal balanceSettleAmount) {
        this.balanceSettleAmount = balanceSettleAmount;
    }

    @Column(name="fPaymentMethod",length = 20)
    @Type(type="myEnum",parameters={@org.hibernate.annotations.Parameter(name="enumClass",value="com.myapp.core.enums.PaymentMethod")})
    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Column(name="fExpenseType",length = 20)
    @Type(type="myEnum",parameters={@org.hibernate.annotations.Parameter(name="enumClass",value="com.myapp.core.enums.PurchaseExpenseType")})
    public PurchaseExpenseType getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(PurchaseExpenseType expenseType) {
        this.expenseType = expenseType;
    }

    @Column(name="fContactTel",length = 20)
    public String getContactTel() {
        return contactTel;
    }

    public void setContactTel(String contactTel) {
        this.contactTel = contactTel;
    }
}
