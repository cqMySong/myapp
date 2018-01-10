package com.myapp.entity.ec.basedata;

import com.myapp.core.annotation.MyEntityAnn;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.ImageDataType;
import org.hibernate.annotations.Type;

import javax.persistence.*;

/**
 * @path：com.myapp.entity.ec.basedata
 * @description：
 * @author： ly
 * @date: 2018-01-10 22:28
 */
@MyEntityAnn(name="工程影像资料")
@Entity
@Table(name="t_ec_image_data")
public class ImageDataInfo extends CoreBaseInfo {
    /**
     * 项目工程
     */
    private ProjectInfo project;
    /**
     * 单位工程
     */
    private ProStructureInfo proStructure;
    /**
     * 项目级别检验批划分
     */
    private ProBatchTestInfo proBatchTest;
    /**
     * 类型
     */
    private ImageDataType imageDataType;
    /**
     * 创建人
     */
    private UserInfo createUser;
    /**
     * 最后更新人
     */
    private UserInfo lastUpdateUser;
    /**
     * 备注
     */
    private String remark;

    @OneToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "fProjectId")
    public ProjectInfo getProject() {
        return project;
    }

    public void setProject(ProjectInfo project) {
        this.project = project;
    }

    @OneToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "fProStructureId")
    public ProStructureInfo getProStructure() {
        return proStructure;
    }

    public void setProStructure(ProStructureInfo proStructure) {
        this.proStructure = proStructure;
    }

    @OneToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "fProBatchTestId")
    public ProBatchTestInfo getProBatchTest() {
        return proBatchTest;
    }

    public void setProBatchTest(ProBatchTestInfo proBatchTest) {
        this.proBatchTest = proBatchTest;
    }
    @Column(name="fImageDataType",length = 20)
    @Type(type="myEnum",parameters={@org.hibernate.annotations.Parameter(name="enumClass",value="com.myapp.core.enums.ImageDataType")})
    public ImageDataType getImageDataType() {
        return imageDataType;
    }

    public void setImageDataType(ImageDataType imageDataType) {
        this.imageDataType = imageDataType;
    }
    @OneToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "fCreateUserId")
    public UserInfo getCreateUser() {
        return createUser;
    }

    public void setCreateUser(UserInfo createUser) {
        this.createUser = createUser;
    }

    @OneToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "fLastUpdateUserId")
    public UserInfo getLastUpdateUser() {
        return lastUpdateUser;
    }

    public void setLastUpdateUser(UserInfo lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }
    @Column(name="fRemark",length = 500)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
