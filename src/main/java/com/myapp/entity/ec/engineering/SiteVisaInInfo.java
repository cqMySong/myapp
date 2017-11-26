package com.myapp.entity.ec.engineering;

import com.myapp.core.annotation.MyEntityAnn;
import com.myapp.core.base.entity.CoreBaseBillInfo;
import com.myapp.core.enums.ChargingBasis;
import com.myapp.core.enums.HandleType;
import com.myapp.entity.ec.basedata.ProjectInfo;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @path：com.myapp.entity.ec.engineering
 * @description：现场签证支出
 * @author： ly
 * @date: 2017-11-25 16:28
 */
@Entity
@MyEntityAnn(name="现场签证收入")
@Table(name="t_ec_site_visa_in")
public class SiteVisaInInfo extends CoreBaseBillInfo {
    /**
     * 所属项目
     */
    private ProjectInfo project;
    /**
     * 现场签证支出
     */
    private SiteVisaOutInfo siteVisaOutInfo;

    /**
     * 签证单位
     */
    private String visaUnit;
    /**
     * 签证时间
     */
    private Date visaDate;

    /**
     * 办理计费依据
     */
    private ChargingBasis chargingBasis;
    /**
     * 计费说明
     */
    private String chargingContent;
    /**
     * 办理情况
     */
    private HandleType handleType;
    /**
     * 总金额
     */
    private BigDecimal amount;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fProjectId")
    public ProjectInfo getProject() {
        return project;
    }

    public void setProject(ProjectInfo project) {
        this.project = project;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fSiteVisaOutId")
    public SiteVisaOutInfo getSiteVisaOutInfo() {
        return siteVisaOutInfo;
    }

    public void setSiteVisaOutInfo(SiteVisaOutInfo siteVisaOutInfo) {
        this.siteVisaOutInfo = siteVisaOutInfo;
    }
    @Column(name="fVisaUnit",length = 100)
    public String getVisaUnit() {
        return visaUnit;
    }

    public void setVisaUnit(String visaUnit) {
        this.visaUnit = visaUnit;
    }
    @Column(name="fChargingBasis",length = 20)
    @Type(type="myEnum",parameters={@org.hibernate.annotations.Parameter(name="enumClass",value="com.myapp.core.enums.ChargingBasis")})
    public ChargingBasis getChargingBasis() {
        return chargingBasis;
    }

    public void setChargingBasis(ChargingBasis chargingBasis) {
        this.chargingBasis = chargingBasis;
    }

    @Column(name="fChargingContent",length = 100)
    public String getChargingContent() {
        return chargingContent;
    }

    public void setChargingContent(String chargingContent) {
        this.chargingContent = chargingContent;
    }
    @Column(name="fAmount",precision = 10,scale = 2)
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getVisaDate() {
        return visaDate;
    }
    @Column(name="fVisaDate")
    public void setVisaDate(Date visaDate) {
        this.visaDate = visaDate;
    }

    @Column(name="fHandleType",length = 20)
    @Type(type="myEnum",parameters={@org.hibernate.annotations.Parameter(name="enumClass",value="com.myapp.core.enums.HandleType")})
    public HandleType getHandleType() {
        return handleType;
    }

    public void setHandleType(HandleType handleType) {
        this.handleType = handleType;
    }
}
