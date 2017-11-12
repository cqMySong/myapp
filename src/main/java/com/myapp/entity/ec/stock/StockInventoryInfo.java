package com.myapp.entity.ec.stock;

import com.myapp.core.annotation.MyEntityAnn;
import com.myapp.core.base.entity.CoreBaseBillInfo;
import com.myapp.core.entity.UserInfo;
import com.myapp.entity.ec.basedata.ProjectInfo;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * @path：com.myapp.entity.ec.stock
 * @description：
 * @author： ly
 * @date: 2017-11-11 17:43
 */
@Entity
@MyEntityAnn(name="盘存信息")
@Table(name="t_ec_stock_inventory")
public class StockInventoryInfo extends CoreBaseBillInfo {
    /**
     * 项目工程
     */
    private ProjectInfo project;
    /**
     * 盘存时间
     */
    private Date inventoryDate;
    /**
     * 盘存操作人
     */
    private UserInfo operator;
    /**
     * 备注
     */
    private String remark;
    /**
     * 盘存明细
     */
    private Set<StockInventoryDetailInfo> stockInventoryDetailInfos;
    /**
     * 临时数据
     */
    private Set<StockInventoryDetailInfo> oldStockInventoryDetailInfos;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fProjectId")
    public ProjectInfo getProject() {
        return project;
    }

    public void setProject(ProjectInfo project) {
        this.project = project;
    }

    @Column(name="fInventoryDate")
    public Date getInventoryDate() {
        return inventoryDate;
    }

    public void setInventoryDate(Date inventoryDate) {
        this.inventoryDate = inventoryDate;
    }

    @OneToOne
    @JoinColumn(name = "fOperatorId")
    public UserInfo getOperator() {
        return operator;
    }

    public void setOperator(UserInfo operator) {
        this.operator = operator;
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
    public Set<StockInventoryDetailInfo> getStockInventoryDetailInfos() {
        return stockInventoryDetailInfos;
    }

    public void setStockInventoryDetailInfos(Set<StockInventoryDetailInfo> stockInventoryDetailInfos) {
        this.stockInventoryDetailInfos = stockInventoryDetailInfos;
    }

    @Transient
    public Set<StockInventoryDetailInfo> getOldStockInventoryDetailInfos() {
        return oldStockInventoryDetailInfos;
    }

    public void setOldStockInventoryDetailInfos(Set<StockInventoryDetailInfo> oldStockInventoryDetailInfos) {
        this.oldStockInventoryDetailInfos = oldStockInventoryDetailInfos;
    }
}
