package com.myapp.entity.ec.purchase;

import com.myapp.core.annotation.MyEntityAnn;
import com.myapp.core.base.entity.CoreBaseBillInfo;
import com.myapp.core.entity.UserInfo;
import com.myapp.entity.ec.basedata.ProjectInfo;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

/**
 * @path：com.myapp.entity.ec.purchase
 * @description：采购入库单
 * @author ： ly
 * @date: 2017-08-28 21:08
 */
@Entity
@MyEntityAnn(name="采购入库")
@Table(name="t_ec_purchase_stock")
public class PurchaseStockInfo extends CoreBaseBillInfo{
    /**
     * 项目单位
     */
    private ProjectInfo project;
    /**
     * 收货人
     */
    private UserInfo consignee;
    /**
     * 入库时间
     */
    private Date inStockDate;

    /**
     * 总金额
     */
    private BigDecimal totalPrice;
    /**
     * 备注
     */
    private String remark;


    private Set<PurchaseStockDetailInfo> oldPurchaseStockDetails;
    /**
     * 采购合同清单信息
     */
    private Set<PurchaseStockDetailInfo> purchaseStockDetailInfos;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fProjectId")
    public ProjectInfo getProject() {
        return project;
    }

    @OneToOne
    @JoinColumn(name = "fConsignee")
    public UserInfo getConsignee() {
        return consignee;
    }

    public void setConsignee(UserInfo consignee) {
        this.consignee = consignee;
    }

    @Column(name="fInStockDate")
    public Date getInStockDate() {
        return inStockDate;
    }

    public void setInStockDate(Date inStockDate) {
        this.inStockDate = inStockDate;
    }
    @Column(name="fTotalPrice")
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
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
    public Set<PurchaseStockDetailInfo> getPurchaseStockDetailInfos() {
        return purchaseStockDetailInfos;
    }

    public void setPurchaseStockDetailInfos(Set<PurchaseStockDetailInfo> purchaseStockDetailInfos) {
        this.purchaseStockDetailInfos = purchaseStockDetailInfos;
    }
    @Transient
    public Set<PurchaseStockDetailInfo> getOldPurchaseStockDetails() {
        return oldPurchaseStockDetails;
    }

    public void setOldPurchaseStockDetails(Set<PurchaseStockDetailInfo> oldPurchaseStockDetails) {
        this.oldPurchaseStockDetails = oldPurchaseStockDetails;
    }
}
