package com.myapp.entity.ec.budget;

import com.myapp.core.annotation.MyEntityAnn;
import com.myapp.core.base.entity.CoreBaseBillInfo;
import com.myapp.core.base.entity.CoreBaseDataInfo;
import com.myapp.core.base.entity.CoreBaseEntryInfo;
import com.myapp.entity.ec.basedata.DataDictionaryInfo;
import com.myapp.entity.ec.plan.ProjectPlanReportInfo;
import org.hibernate.mapping.ToOne;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 包路径：com.myapp.entity.ec.budget
 * 功能说明：预算编制
 * 创建人： ly
 * 创建时间: 2017-08-25 15:21
 */
@Entity
@MyEntityAnn(name="预算编制明细")
@Table(name="t_ec_budgeting_detail")
public class BudgetingDetailInfo extends CoreBaseEntryInfo<BudgetingInfo> {
    private BigDecimal quantity;//数量
    private BigDecimal unitPrice;//单价
    private DataDictionaryInfo dataDic;//物料信息

    @Column(name="fQuantity",precision = 2)
    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }
    @Column(name="fUnitPrice",precision = 4)
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
}
