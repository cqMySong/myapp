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
     * 开始时间
     */
    private Date startDate;
    /**
     * 结束时间
     */
    private Date endDate;

    /**
     * 备注
     */
    private String remark;
    /**
     * 盘存明细
     */
    private Set<StockInventoryDetailInfo> stockInventoryDetailInfos;
    /**
     * 图算信息
     */
    private StockCalculationInfo stockCalculationInfo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fProjectId")
    public ProjectInfo getProject() {
        return project;
    }

    public void setProject(ProjectInfo project) {
        this.project = project;
    }

    @Column(name="fStartDate")
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Column(name="fEndDate")
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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

    @OneToOne(cascade={CascadeType.ALL},mappedBy="stockInventoryInfo")
    @OrderBy("seq ASC")
    public StockCalculationInfo getStockCalculationInfo() {
        return stockCalculationInfo;
    }

    public void setStockCalculationInfo(StockCalculationInfo stockCalculationInfo) {
        this.stockCalculationInfo = stockCalculationInfo;
    }
}
