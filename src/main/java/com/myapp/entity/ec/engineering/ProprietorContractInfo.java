package com.myapp.entity.ec.engineering;

import com.myapp.core.annotation.MyEntityAnn;
import com.myapp.core.base.entity.CoreBaseBillInfo;
import com.myapp.core.enums.ProprietorContractType;
import com.myapp.core.enums.SubcontractExpenseType;
import com.myapp.entity.ec.basedata.ECUnitInfo;
import com.myapp.entity.ec.basedata.ProjectInfo;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @path：com.myapp.entity.ec.engineering
 * @description：分包合同
 * @author： ly
 * @date: 2017-11-12 21:20
 */
@Entity
@MyEntityAnn(name="业主合同")
@Table(name="t_ec_proprietor_contract")
public class ProprietorContractInfo extends CoreBaseBillInfo {
    /**
     * 所属项目
     */
    private ProjectInfo project;
    /**
     * 类型
     */
    private ProprietorContractType  proprietorContractType;
    /**
     * 计价范围
     */
    private String rangeValuation;
    /**
     * 计价依据
     */
    private String basisValuation;
    /**
     * 金额
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

    @Column(name="fRangeValuation",length = 500)
    public String getRangeValuation() {
        return rangeValuation;
    }

    public void setRangeValuation(String rangeValuation) {
        this.rangeValuation = rangeValuation;
    }

    @Column(name="fBasisValuation",length = 500)
    public String getBasisValuation() {
        return basisValuation;
    }

    public void setBasisValuation(String basisValuation) {
        this.basisValuation = basisValuation;
    }

    @Column(name="fAmount",precision = 15,scale = 2)
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Column(name="fProprietorContractType",length = 20)
    @Type(type="myEnum",parameters={@org.hibernate.annotations.Parameter(name="enumClass",value="com.myapp.core.enums.ProprietorContractType")})
    public ProprietorContractType getProprietorContractType() {
        return proprietorContractType;
    }

    public void setProprietorContractType(ProprietorContractType proprietorContractType) {
        this.proprietorContractType = proprietorContractType;
    }
}
