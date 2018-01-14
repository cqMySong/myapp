package com.myapp.entity.ec.basedata;

import com.myapp.core.annotation.MyEntityAnn;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.entity.basedate.DataGroupInfo;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * @path：com.myapp.entity.ec.basedata
 * @description：
 * @author： ly
 * @date: 2018-01-13 21:54
 */
@MyEntityAnn(name="机具例行保养检查")
@Entity
@Table(name="t_ec_machine_maintenance")
public class MachineMaintenanceInfo extends CoreBaseInfo {
    /**
     * 项目工程
     */
    private ProjectInfo project;
    /**
     * 保养单位
     */
    private String maintenanceCompany;

    /**
     * 包养对象名称
     */
    private DataGroupInfo dataGroupInfo;
    /**
     * 创建人
     */
    private UserInfo createUser;
    /**
     * 最后一次修改人
     */
    private UserInfo lastUpdateUser;
    /**
     * 检查人
     */
    private String inspector;
    /**
     * 检查日期
     */
    private Date inspectionDate;
    /**
     * 检查意见
     */
    private String inspectionOption;
    /**
     * 整改结果
     */
    private String remedialResult;

    /**
     * 保养明细信息
     */
    private Set<MachineMaintenanceDetailInfo> machineMaintenanceDetailInfos;

    @OneToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "fProjectId")
    public ProjectInfo getProject() {
        return project;
    }

    public void setProject(ProjectInfo project) {
        this.project = project;
    }

    @OneToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "fCreateUserId")
    public UserInfo getCreateUser() {
        return createUser;
    }

    public void setCreateUser(UserInfo createUser) {
        this.createUser = createUser;
    }
    @Column(name="fInspector",length=50)
    public String getInspector() {
        return inspector;
    }

    public void setInspector(String inspector) {
        this.inspector = inspector;
    }
    @Column(name="fInspectionDate")
    public Date getInspectionDate() {
        return inspectionDate;
    }

    public void setInspectionDate(Date inspectionDate) {
        this.inspectionDate = inspectionDate;
    }
    @Column(name="fInspectionOption")
    public String getInspectionOption() {
        return inspectionOption;
    }

    public void setInspectionOption(String inspectionOption) {
        this.inspectionOption = inspectionOption;
    }
    @Column(name="fRemedialResult")
    public String getRemedialResult() {
        return remedialResult;
    }

    public void setRemedialResult(String remedialResult) {
        this.remedialResult = remedialResult;
    }
    @OneToMany(cascade={CascadeType.ALL},mappedBy="parent")
    @OrderBy("seq ASC")
    public Set<MachineMaintenanceDetailInfo> getMachineMaintenanceDetailInfos() {
        return machineMaintenanceDetailInfos;
    }

    public void setMachineMaintenanceDetailInfos(Set<MachineMaintenanceDetailInfo> machineMaintenanceDetailInfos) {
        this.machineMaintenanceDetailInfos = machineMaintenanceDetailInfos;
    }
    @OneToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "fDataGroupId")
    public DataGroupInfo getDataGroupInfo() {
        return dataGroupInfo;
    }

    public void setDataGroupInfo(DataGroupInfo dataGroupInfo) {
        this.dataGroupInfo = dataGroupInfo;
    }

    @Column(name = "fMaintenanceCompany",length = 100)
    public String getMaintenanceCompany() {
        return maintenanceCompany;
    }

    public void setMaintenanceCompany(String maintenanceCompany) {
        this.maintenanceCompany = maintenanceCompany;
    }
    @OneToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "fLastUpdateUserId")
    public UserInfo getLastUpdateUser() {
        return lastUpdateUser;
    }

    public void setLastUpdateUser(UserInfo lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }
}
