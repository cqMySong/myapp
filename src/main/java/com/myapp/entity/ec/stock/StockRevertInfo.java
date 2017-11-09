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
 * @description：归还单据信息息
 * @author： ly
 * @date: 2017-11-07 15:06
 */
@Entity
@MyEntityAnn(name="库存归还")
@Table(name="t_ec_stock_revert")
public class StockRevertInfo extends CoreBaseBillInfo {

    /**
     * 项目单位
     */
    private ProjectInfo project;
    /**
     * 归还人
     */
    private UserInfo returnPerson;
    /**
     * 归还日期
     */
    private Date revertStockDate;
    /**
     * 备注
     */
    private String remark;
    /**
     * 物料明细信息
     */
    private Set<StockRevertDetailInfo> stockRevertDetailInfos;

    private Set<StockRevertDetailInfo> oldStockRevertDetails;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fProjectId")
    public ProjectInfo getProject() {
        return project;
    }

    public void setProject(ProjectInfo project) {
        this.project = project;
    }

    @OneToOne
    @JoinColumn(name = "fReturnPersonId")
    public UserInfo getReturnPerson() {
        return returnPerson;
    }

    public void setReturnPerson(UserInfo returnPerson) {
        this.returnPerson = returnPerson;
    }

    @Column(name="fRevertStockDate")
    public Date getRevertStockDate() {
        return revertStockDate;
    }

    public void setRevertStockDate(Date revertStockDate) {
        this.revertStockDate = revertStockDate;
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
    public Set<StockRevertDetailInfo> getStockRevertDetailInfos() {
        return stockRevertDetailInfos;
    }

    public void setStockRevertDetailInfos(Set<StockRevertDetailInfo> stockRevertDetailInfos) {
        this.stockRevertDetailInfos = stockRevertDetailInfos;
    }

    @Transient
    public Set<StockRevertDetailInfo> getOldStockRevertDetails() {
        return oldStockRevertDetails;
    }

    public void setOldStockRevertDetails(Set<StockRevertDetailInfo> oldStockRevertDetails) {
        this.oldStockRevertDetails = oldStockRevertDetails;
    }
}
