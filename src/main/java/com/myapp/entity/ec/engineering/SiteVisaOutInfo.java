package com.myapp.entity.ec.engineering;

import com.myapp.core.annotation.MyEntityAnn;
import com.myapp.core.base.entity.CoreBaseBillInfo;
import com.myapp.core.enums.ChargingBasis;
import com.myapp.entity.ec.basedata.ProjectInfo;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

/**
 * @path：com.myapp.entity.ec.engineering
 * @description：现场签证支出
 * @author： ly
 * @date: 2017-11-25 16:28
 */
@Entity
@MyEntityAnn(name="现场签证支出")
@Table(name="t_ec_site_visa_out")
public class SiteVisaOutInfo extends CoreBaseBillInfo {
    /**
     * 所属项目
     */
    private ProjectInfo project;
    /**
     * 签字时间
     */
    private Date visaDate;
    /**
     * 签证单位
     */
    private String visaUnit;
    /**
     * 工作部位
     */
    private String workPart;
    /**
     * 工作内容
     */
    private String jobContent;
    /**
     * 计费依据
     */
    private ChargingBasis chargingBasis;
    /**
     * 计费说明
     */
    private String chargingContent;
    /**
     * 总金额
     */
    private BigDecimal amount;
    /**
     * 现场签证(收入)
     */
    private SiteVisaInInfo siteVisaInInfo;
    /**
     * 现场签证支出明细
     */
    private Set<SiteVisaOutDetailInfo> siteVisaOutDetailInfos;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fProjectId")
    public ProjectInfo getProject() {
        return project;
    }

    public void setProject(ProjectInfo project) {
        this.project = project;
    }

    @Column(name="fVisaDate")
    public Date getVisaDate() {
        return visaDate;
    }

    public void setVisaDate(Date visaDate) {
        this.visaDate = visaDate;
    }

    @Column(name="fVisaUnit",length = 100)
    public String getVisaUnit() {
        return visaUnit;
    }

    public void setVisaUnit(String visaUnit) {
        this.visaUnit = visaUnit;
    }

    @Column(name="fWorkPart",length = 100)
    public String getWorkPart() {
        return workPart;
    }

    public void setWorkPart(String workPart) {
        this.workPart = workPart;
    }

    @Column(name="fJobContent",length = 500)
    public String getJobContent() {
        return jobContent;
    }

    public void setJobContent(String jobContent) {
        this.jobContent = jobContent;
    }
    @Column(name="fChargingBasis",length = 20)
    @Type(type="myEnum",parameters={@org.hibernate.annotations.Parameter(name="enumClass",value="com.myapp.core.enums.ChargingBasis")})
    public ChargingBasis getChargingBasis() {
        return chargingBasis;
    }

    public void setChargingBasis(ChargingBasis chargingBasis) {
        this.chargingBasis = chargingBasis;
    }
    @Column(name="fChargingContent",length = 50)
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

    @OneToOne(cascade={CascadeType.ALL},mappedBy="siteVisaOutInfo")
    public SiteVisaInInfo getSiteVisaInInfo() {
        return siteVisaInInfo;
    }

    public void setSiteVisaInInfo(SiteVisaInInfo siteVisaInInfo) {
        this.siteVisaInInfo = siteVisaInInfo;
    }

    @OneToMany(cascade={CascadeType.ALL},mappedBy="parent")
    @OrderBy("seq ASC")
    public Set<SiteVisaOutDetailInfo> getSiteVisaOutDetailInfos() {
        return siteVisaOutDetailInfos;
    }

    public void setSiteVisaOutDetailInfos(Set<SiteVisaOutDetailInfo> siteVisaOutDetailInfos) {
        this.siteVisaOutDetailInfos = siteVisaOutDetailInfos;
    }
}
