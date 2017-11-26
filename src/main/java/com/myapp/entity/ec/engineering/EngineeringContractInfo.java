package com.myapp.entity.ec.engineering;

import com.myapp.core.annotation.MyEntityAnn;
import com.myapp.core.base.entity.CoreBaseBillInfo;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.ContractType;
import com.myapp.core.enums.ExpenseType;
import com.myapp.entity.ec.basedata.ECUnitInfo;
import com.myapp.entity.ec.basedata.ProjectInfo;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @path：com.myapp.entity.ec.engineering
 * @description：工程合同
 * @author： ly
 * @date: 2017-11-12 21:20
 */
@Entity
@MyEntityAnn(name="工程合同信息")
@Table(name="t_ec_engineering_contract")
public class EngineeringContractInfo extends CoreBaseBillInfo {
    /**
     * 所属项目
     */
    private ProjectInfo project;

    /**
     * 合同类型
     */
    private ContractType contractType;
    /**
     * 费用类型
     */
    private ExpenseType expenseType;

    /**
     * 合同单位
     */
    private ECUnitInfo ecUnitInfo;

    /**
     * 合同金额
     */
    private BigDecimal amount;
    /**
     * 负责人
     */
    private UserInfo director;
    /**
     * 负责人姓名
     */
    private String directorName;
    /**
     * 合同内容
     */
    private String treatyContents;
    /**
     * 剩余结算金额
     */
    private BigDecimal balanceAmount;
    /**
     * 合同签订时间
     */
    private Date contractDate;

    /**
     * 修改前数据
     */
    private EngineeringContractInfo oldEngineeringContractInfo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fProjectId")
    public ProjectInfo getProject() {
        return project;
    }

    public void setProject(ProjectInfo project) {
        this.project = project;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fEcUnitId")
    public ECUnitInfo getEcUnitInfo() {
        return ecUnitInfo;
    }

    public void setEcUnitInfo(ECUnitInfo ecUnitInfo) {
        this.ecUnitInfo = ecUnitInfo;
    }

    @Column(name="fAmount",precision = 10,scale = 2)
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fDirectorId")
    public UserInfo getDirector() {
        return director;
    }

    public void setDirector(UserInfo director) {
        this.director = director;
    }

    @Column(name="fDirectorName",length = 30)
    public String getDirectorName() {
        return directorName;
    }

    public void setDirectorName(String directorName) {
        this.directorName = directorName;
    }
    @Column(name="fTreatyContents",length = 1000)
    public String getTreatyContents() {
        return treatyContents;
    }

    public void setTreatyContents(String treatyContents) {
        this.treatyContents = treatyContents;
    }

    @Column(name="fBalanceAmount",precision = 10,scale = 2)
    public BigDecimal getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(BigDecimal balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    @Transient
    public EngineeringContractInfo getOldEngineeringContractInfo() {
        return oldEngineeringContractInfo;
    }

    public void setOldEngineeringContractInfo(EngineeringContractInfo oldEngineeringContractInfo) {
        this.oldEngineeringContractInfo = oldEngineeringContractInfo;
    }

    @Column(name="fContractDate")
    public Date getContractDate() {
        return contractDate;
    }

    public void setContractDate(Date contractDate) {
        this.contractDate = contractDate;
    }

    @Column(name="fContractType",length = 20)
    @Type(type="myEnum",parameters={@org.hibernate.annotations.Parameter(name="enumClass",value="com.myapp.core.enums.ContractType")})
    public ContractType getContractType() {
        return contractType;
    }

    public void setContractType(ContractType contractType) {
        this.contractType = contractType;
    }

    @Column(name="fExpenseType",length = 20)
    @Type(type="myEnum",parameters={@org.hibernate.annotations.Parameter(name="enumClass",value="com.myapp.core.enums.ExpenseType")})
    public ExpenseType getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(ExpenseType expenseType) {
        this.expenseType = expenseType;
    }
}
