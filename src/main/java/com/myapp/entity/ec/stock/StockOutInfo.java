package com.myapp.entity.ec.stock;

import com.myapp.core.annotation.MyEntityAnn;
import com.myapp.core.base.entity.CoreBaseBillInfo;
import com.myapp.core.entity.MaterialInfo;
import com.myapp.core.entity.UserInfo;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.entity.ec.purchase.PurchaseStockDetailInfo;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

/**
 * @path：com.myapp.entity.ec.stock
 * @description：出库单据信息
 * @author： ly
 * @date: 2017-11-07 15:06
 */
@Entity
@MyEntityAnn(name="出库信息")
@Table(name="t_ec_stock_out")
public class StockOutInfo extends CoreBaseBillInfo {

    /**
     * 项目单位
     */
    private ProjectInfo project;
    /**
     * 领料人
     */
    private UserInfo picker;
    /**
     * 领料时间
     */
    private Date outStockDate;
    /**
     * 备注
     */
    private String remark;
    /**
     * 物料明细信息
     */
    private Set<StockOutDetailInfo> stockOutDetailInfos;

    private Set<StockOutDetailInfo> oldStockOutDetails;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fProjectId")
    public ProjectInfo getProject() {
        return project;
    }

    public void setProject(ProjectInfo project) {
        this.project = project;
    }

    @OneToOne
    @JoinColumn(name = "fPickerId")
    public UserInfo getPicker() {
        return picker;
    }

    public void setPicker(UserInfo picker) {
        this.picker = picker;
    }

    @Column(name="fOutStockDate")
    public Date getOutStockDate() {
        return outStockDate;
    }

    public void setOutStockDate(Date outStockDate) {
        this.outStockDate = outStockDate;
    }

    @Column(name="fRemark",length = 200)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @OneToMany(cascade={CascadeType.ALL},mappedBy="parent")
    @OrderBy("seq ASC")
    public Set<StockOutDetailInfo> getStockOutDetailInfos() {
        return stockOutDetailInfos;
    }

    public void setStockOutDetailInfos(Set<StockOutDetailInfo> stockOutDetailInfos) {
        this.stockOutDetailInfos = stockOutDetailInfos;
    }

    @Transient
    public Set<StockOutDetailInfo> getOldStockOutDetails() {
        return oldStockOutDetails;
    }

    public void setOldStockOutDetails(Set<StockOutDetailInfo> oldStockOutDetails) {
        this.oldStockOutDetails = oldStockOutDetails;
    }
}
