package com.myapp.entity.ec.basedata;

import com.myapp.core.annotation.MyEntityAnn;
import com.myapp.core.base.entity.CoreBaseBillInfo;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.enums.TransferTypeEnum;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

/**
 * @path：com.myapp.entity.ec.basedata
 * @description：场地移交协议
 * @author： ly
 * @date: 2017-12-21 21:40
 */
@MyEntityAnn(name="场地移交协议")
@Entity
@Table(name="t_ec_site_transfer")
public class SiteTransferInfo extends CoreBaseBillInfo {
    /**
     * 项目工程
     */
    private ProjectInfo project;
    /**
     * 移交类型
     */
    private TransferTypeEnum transferType;
    /**
     * 移交单位
     */
    private String transferUnit;
    /**
     * 接受单位
     */
    private String receivingUnit;
    /**
     * 移交时间
     */
    private Date transferDate;
    /**
     * 移交范围
     */
    private String transferRange;
    /**
     * 场地现状
     */
    private String siteCondition;
    /**
     * 接受职责
     */
    private String receivingDuty;
    /**
     * 承诺事项
     */
    private String commitment;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fProjectId")
    public ProjectInfo getProject() {
        return project;
    }

    public void setProject(ProjectInfo project) {
        this.project = project;
    }

    @Column(name="fTransferUnit",length = 100)
    public String getTransferUnit() {
        return transferUnit;
    }

    public void setTransferUnit(String transferUnit) {
        this.transferUnit = transferUnit;
    }
    @Column(name="fReceivingUnit",length = 100)
    public String getReceivingUnit() {
        return receivingUnit;
    }

    public void setReceivingUnit(String receivingUnit) {
        this.receivingUnit = receivingUnit;
    }
    @Column(name="fTransferDate")
    public Date getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(Date transferDate) {
        this.transferDate = transferDate;
    }

    @Column(name="fTransferRange",length = 1000)
    public String getTransferRange() {
        return transferRange;
    }

    public void setTransferRange(String transferRange) {
        this.transferRange = transferRange;
    }

    @Column(name="fSiteCondition",length = 1000)
    public String getSiteCondition() {
        return siteCondition;
    }

    public void setSiteCondition(String siteCondition) {
        this.siteCondition = siteCondition;
    }

    @Column(name="fReceivingDuty",length = 1000)
    public String getReceivingDuty() {
        return receivingDuty;
    }

    public void setReceivingDuty(String receivingDuty) {
        this.receivingDuty = receivingDuty;
    }

    @Column(name="fCommitment",length = 1000)
    public String getCommitment() {
        return commitment;
    }

    public void setCommitment(String commitment) {
        this.commitment = commitment;
    }
    @Column(name="fTransferType",length = 20)
    @Type(type="myEnum",parameters={@org.hibernate.annotations.Parameter(name="enumClass",value="com.myapp.core.enums.TransferTypeEnum")})
    public TransferTypeEnum getTransferType() {
        return transferType;
    }

    public void setTransferType(TransferTypeEnum transferType) {
        this.transferType = transferType;
    }
}
