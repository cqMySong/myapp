package com.myapp.entity.ec.basedata;

import com.myapp.core.annotation.MyEntityAnn;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.entity.UserInfo;
import org.activiti.engine.identity.User;

import javax.persistence.*;
import java.util.Date;

/**
 * @path：com.myapp.entity.ec.basedata
 * @description：试件信息
 * @author： ly
 * @date: 2017-12-25 22:21
 */
@Entity
@MyEntityAnn(name="试件信息")
@Table(name="t_ec_test_piece")
public class TestPieceInfo extends CoreBaseInfo {
    /**
     * 项目工程信息
     */
    private ProjectInfo projectInfo;
    /**
     * 规格信息
     */
    private String specification;
    /**
     * 生产日期
     */
    private Date productionDate;
    /**
     * 送检日期
     */
    private Date inspectionDate;
    /**
     * 检验单号
     */
    private String inspectionNo;
    /**
     * 检验结果
     */
    private Boolean inspectionResult = Boolean.FALSE;
    /**
     * 是否见证送样
     */
    private Boolean evidentialTesting = Boolean.FALSE;
    /**
     * 备注
     */
    private String remark;
    /**
     * 创建人
     */
    private UserInfo creator;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="fProjectId")
    public ProjectInfo getProjectInfo() {
        return projectInfo;
    }

    public void setProjectInfo(ProjectInfo projectInfo) {
        this.projectInfo = projectInfo;
    }
    @Column(name="fSpecification")
    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }
    @Column(name="fProductionDate")
    public Date getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
    }
    @Column(name="fInspectionDate")
    public Date getInspectionDate() {
        return inspectionDate;
    }

    public void setInspectionDate(Date inspectionDate) {
        this.inspectionDate = inspectionDate;
    }
    @Column(name="fInspectionNo",length=20)
    public String getInspectionNo() {
        return inspectionNo;
    }

    public void setInspectionNo(String inspectionNo) {
        this.inspectionNo = inspectionNo;
    }
    @Column(name="fInspectionResult",length=2)
    public Boolean getInspectionResult() {
        return inspectionResult;
    }

    public void setInspectionResult(Boolean inspectionResult) {
        this.inspectionResult = inspectionResult;
    }
    @Column(name="fEvidentialTest",length=2)
    public Boolean getEvidentialTesting() {
        return evidentialTesting;
    }

    public void setEvidentialTesting(Boolean evidentialTesting) {
        this.evidentialTesting = evidentialTesting;
    }
    @Column(name="fRemark",length=500)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="fUserId")
    public UserInfo getCreator() {
        return creator;
    }

    public void setCreator(UserInfo creator) {
        this.creator = creator;
    }
}
