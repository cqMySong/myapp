package com.myapp.entity.ec.basedata;

import com.myapp.core.annotation.MyEntityAnn;
import com.myapp.core.base.entity.CoreBaseEntryInfo;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.entity.UserInfo;
import com.myapp.entity.ec.budget.EnquiryPriceInfo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * @path：com.myapp.entity.ec.basedata
 * @description：
 * @author： ly
 * @date: 2018-01-13 21:54
 */
@MyEntityAnn(name="机具例行保养检查明细")
@Entity
@Table(name="t_ec_machine_maintenance_detail")
public class MachineMaintenanceDetailInfo extends CoreBaseEntryInfo<MachineMaintenanceInfo> {
    /**
     * 检查项目
     */
    private String inspectionItem;
    /**
     * 要求及说明说明
     */
    private String description;
    /**
     * 评定意见
     */
    private String evaluationOption;
    @Column(name="fInspectionItem",length = 100)
    public String getInspectionItem() {
        return inspectionItem;
    }

    public void setInspectionItem(String inspectionItem) {
        this.inspectionItem = inspectionItem;
    }
    @Column(name="fDescription",length = 1000)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    @Column(name="fEvaluationOption",length = 1000)
    public String getEvaluationOption() {
        return evaluationOption;
    }

    public void setEvaluationOption(String evaluationOption) {
        this.evaluationOption = evaluationOption;
    }
}
