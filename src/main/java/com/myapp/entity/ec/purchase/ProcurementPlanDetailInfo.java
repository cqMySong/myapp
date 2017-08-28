package com.myapp.entity.ec.purchase;

import com.myapp.core.annotation.MyEntityAnn;
import com.myapp.core.base.entity.CoreBaseEntryInfo;
import com.myapp.entity.ec.basedata.DataDictionaryInfo;
import com.myapp.entity.ec.budget.BudgetingInfo;
import com.myapp.enums.DataDicType;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 包路径：com.myapp.entity.ec.purchase
 * 功能说明：
 * 创建人： ly
 * 创建时间: 2017-08-28 21:10
 */
@Entity
@MyEntityAnn(name="采购计划清单")
@Table(name="t_ec_procurement_plan_detail")
public class ProcurementPlanDetailInfo  extends CoreBaseEntryInfo<ProcurementPlanInfo> {
    private BigDecimal quantity;//数量
    private BigDecimal unitPrice;//单价
    private DataDictionaryInfo dataDic;//物料信息
    private DataDicType dataDicType;//物料类型
    private BigDecimal totalPrice;//总价
    private String remark;//备注

    @Column(name="fQuantity",precision = 10,scale = 2)
    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }
    @Column(name="fUnitPrice",precision = 10,scale = 4)
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    @ManyToOne
    @JoinColumn(name = "fDataDicId")
    public DataDictionaryInfo getDataDic() {
        return dataDic;
    }

    public void setDataDic(DataDictionaryInfo dataDic) {
        this.dataDic = dataDic;
    }
    @Column(name="fRemark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    @Column(name="fDataDicType",length = 20)
    public DataDicType getDataDicType() {
        return dataDicType;
    }

    public void setDataDicType(DataDicType dataDicType) {
        this.dataDicType = dataDicType;
    }
    @Column(name="fTotalPrice",precision = 12,scale = 4)
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
