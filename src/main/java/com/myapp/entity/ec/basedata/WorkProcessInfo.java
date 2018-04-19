package com.myapp.entity.ec.basedata;

import com.myapp.core.annotation.MyEntityAnn;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.entity.UserInfo;
import com.myapp.enums.ec.WorkType;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年12月12日 
 * @system:
 *-----------MySong---------------
 */
@MyEntityAnn(name="工作处理摘要")
@Entity
@Table(name="t_ec_work_process")
public class WorkProcessInfo extends CoreBaseInfo {
    /**
     * 工程项目
     */
    private ProjectInfo project;
    /**
     * 问题时间
     */
    private Date problemDate;
    /**
     * 工作分类
     */
    private WorkType workType;
    /**
     * 问题内容
     */
    private String problemContent;
    /**
     * 解决办法
     */
    private String solution;
    /**
     * 工作跟进
     */
    private Boolean workFollowUp;
    /**
     * 最迟解决时间
     */
    private Date endSolveDate;
    /**
     * 是否解决
     */
    private Boolean solve;
    /**
     *备注
     */
    private String remark;
    /**
     * 创建人
     */
    private UserInfo creator;
    @OneToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "fProjectId")
    public ProjectInfo getProject() {
        return project;
    }

    public void setProject(ProjectInfo project) {
        this.project = project;
    }
    @Column(name="fProblemDate")
    public Date getProblemDate() {
        return problemDate;
    }

    public void setProblemDate(Date problemDate) {
        this.problemDate = problemDate;
    }
    @Column(name="fWorkType",length = 20)
    @Type(type="myEnum",parameters={@org.hibernate.annotations.Parameter(name="enumClass",value="com.myapp.enums.ec.WorkType")})
    public WorkType getWorkType() {
        return workType;
    }

    public void setWorkType(WorkType workType) {
        this.workType = workType;
    }
    @Column(name="fProblemContent",length = 500)
    public String getProblemContent() {
        return problemContent;
    }

    public void setProblemContent(String problemContent) {
        this.problemContent = problemContent;
    }
    @Column(name="fSolution",length = 500)
    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }
    @Column(name="fWorkFollowUp")
    public Boolean getWorkFollowUp() {
        return workFollowUp;
    }

    public void setWorkFollowUp(Boolean workFollowUp) {
        this.workFollowUp = workFollowUp;
    }
    @Column(name="fEndSolveDate")
    public Date getEndSolveDate() {
        return endSolveDate;
    }

    public void setEndSolveDate(Date endSolveDate) {
        this.endSolveDate = endSolveDate;
    }
    @Column(name="fSolve")
    public Boolean getSolve() {
        return solve;
    }

    public void setSolve(Boolean solve) {
        this.solve = solve;
    }
    @Column(name="fRemark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @OneToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "fCreatorId")
    public UserInfo getCreator() {
        return creator;
    }

    public void setCreator(UserInfo creator) {
        this.creator = creator;
    }
}
