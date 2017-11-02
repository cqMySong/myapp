package com.myapp.entity.ec.budget;

import com.myapp.core.annotation.MyEntityAnn;
import com.myapp.core.base.entity.CoreBaseEntryInfo;
import com.myapp.core.enums.ContractSignMethod;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @path：com.myapp.entity.ec.budget
 * @description：
 * @author ： ly
 * @date: 2017-08-28 21:10
 */
@Entity
@MyEntityAnn(name="预算询价明细")
@Table(name="t_ec_enquiry_price_detail")
public class EnquiryPriceDetailInfo extends CoreBaseEntryInfo<EnquiryPriceInfo> {
    /**
     * 预算明细信息
     */
    private BudgetingDetailInfo budgetingDetailInfo;
    /**
     * 采购意向价格
     */
    private BigDecimal intentionPrice;
    /**
     * 付款方式
     */
    private String paymentMethod;
    /**
     * 产地
     */
    private String origin;
    /**
     * 供货周期
     */
    private String supplyCycle;
    /**
     * 供货单位
     */
    private String supplyCompany;
    /**
     * 联系人
     */
    private String contactMan;
    /**
     * 联系电话
     */
    private String contactTel;
    /**
     * 合同签订方式
     */
    private ContractSignMethod contractSignMethod;


    @Column(name="fIntentionPrice",precision = 10,scale = 2)
    public BigDecimal getIntentionPrice() {
        return intentionPrice;
    }

    public void setIntentionPrice(BigDecimal intentionPrice) {
        this.intentionPrice = intentionPrice;
    }

    @Column(name="fPaymentMethod",length = 30)
    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Column(name="fOrigin",length = 100)
    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    @Column(name="fSupplyCycle",length = 50)
    public String getSupplyCycle() {
        return supplyCycle;
    }

    public void setSupplyCycle(String supplyCycle) {
        this.supplyCycle = supplyCycle;
    }

    @Column(name="fSupplyCompany",length = 100)
    public String getSupplyCompany() {
        return supplyCompany;
    }

    public void setSupplyCompany(String supplyCompany) {
        this.supplyCompany = supplyCompany;
    }

    @Column(name="fContactMan",length = 50)
    public String getContactMan() {
        return contactMan;
    }

    public void setContactMan(String contactMan) {
        this.contactMan = contactMan;
    }

    @Column(name="fContactTel",length = 30)
    public String getContactTel() {
        return contactTel;
    }

    public void setContactTel(String contactTel) {
        this.contactTel = contactTel;
    }

    public void setBudgetingDetailInfo(BudgetingDetailInfo budgetingDetailInfo) {
        this.budgetingDetailInfo = budgetingDetailInfo;
    }
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fBudgetingDetailId")
    public BudgetingDetailInfo getBudgetingDetailInfo() {
        return budgetingDetailInfo;
    }

    @Column(name="fContractSignMethod",length = 20)
    @Type(type="myEnum",parameters={@org.hibernate.annotations.Parameter(name="enumClass",value="com.myapp.core.enums.ContractSignMethod")})
    public ContractSignMethod getContractSignMethod() {
        return contractSignMethod;
    }

    public void setContractSignMethod(ContractSignMethod contractSignMethod) {
        this.contractSignMethod = contractSignMethod;
    }

}
