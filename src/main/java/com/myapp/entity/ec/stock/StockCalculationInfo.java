package com.myapp.entity.ec.stock;

import com.myapp.core.annotation.MyEntityAnn;
import com.myapp.core.base.entity.CoreBaseBillInfo;
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
@MyEntityAnn(name="图算用量信息")
@Table(name="t_ec_stock_calculation")
public class StockCalculationInfo extends CoreBaseBillInfo {
    /**
     * 项目工程
     */
    private ProjectInfo project;
    /**
     * 盘存信息
     */
    private StockInventoryInfo stockInventoryInfo;

    /**
     * 备注
     */
    private String remark;
    /**
     * 图算明细
     */
    private Set<StockCalculationDetailInfo> stockCalculationDetailInfos;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fProjectId")
    public ProjectInfo getProject() {
        return project;
    }

    public void setProject(ProjectInfo project) {
        this.project = project;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fStockInventoryId")
    public StockInventoryInfo getStockInventoryInfo() {
        return stockInventoryInfo;
    }

    public void setStockInventoryInfo(StockInventoryInfo stockInventoryInfo) {
        this.stockInventoryInfo = stockInventoryInfo;
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
    public Set<StockCalculationDetailInfo> getStockCalculationDetailInfos() {
        return stockCalculationDetailInfos;
    }

    public void setStockCalculationDetailInfos(Set<StockCalculationDetailInfo> stockCalculationDetailInfos) {
        this.stockCalculationDetailInfos = stockCalculationDetailInfos;
    }
}
