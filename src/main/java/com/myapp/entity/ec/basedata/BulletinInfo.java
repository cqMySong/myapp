package com.myapp.entity.ec.basedata;

import com.myapp.core.annotation.MyEntityAnn;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.entity.UserInfo;

import javax.persistence.*;
import java.util.Date;

/**
 * @path：com.myapp.entity.ec.basedata
 * @description：通知通报消息
 * @author： ly
 * @date: 2018-01-12 22:03
 */
@MyEntityAnn(name="通知通报消息")
@Entity
@Table(name="t_ec_bulletin")
public class BulletinInfo extends CoreBaseInfo {
    /**
     * 项目工程
     */
    private ProjectInfo project;
    /**
     * 创建人
     */
    private UserInfo createUser;
    /**
     * 通知通报日期
     */
    private Date bulletinDate;
    /**
     * 主要内容
     */
    private String content;
    /**
     * 发文单位部门
     */
    private String dispatchUnit;

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

    @Column(name="fBulletinDate")
    public Date getBulletinDate() {
        return bulletinDate;
    }

    public void setBulletinDate(Date bulletinDate) {
        this.bulletinDate = bulletinDate;
    }

    @Column(name="fContent",length = 1000)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Column(name="fDispatchUnit",length = 100)
    public String getDispatchUnit() {
        return dispatchUnit;
    }

    public void setDispatchUnit(String dispatchUnit) {
        this.dispatchUnit = dispatchUnit;
    }
}
