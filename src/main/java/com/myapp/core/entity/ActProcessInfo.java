package com.myapp.core.entity;

import com.myapp.core.base.entity.CoreBaseInfo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 包路径：com.myapp.core.entity
 * 功能说明：流程信息
 * 创建人： ly
 * 创建时间: 2017-06-24 23:40
 */
public class ActProcessInfo extends CoreBaseInfo{
    private String version;
    private String xmlResourcesName;
    private String pictureResourcesName;
    private Date deployDate;
    private String category;
    private boolean isSuspended;
    private boolean hasStartFormKey;
    private boolean hasGraphicalNotation;
    private String description;
    private String deploymentId;
    private String tenantId;
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getXmlResourcesName() {
        return xmlResourcesName;
    }

    public void setXmlResourcesName(String xmlResourcesName) {
        this.xmlResourcesName = xmlResourcesName;
    }

    public String getPictureResourcesName() {
        return pictureResourcesName;
    }

    public void setPictureResourcesName(String pictureResourcesName) {
        this.pictureResourcesName = pictureResourcesName;
    }

    public Date getDeployDate() {
        return deployDate;
    }

    public void setDeployDate(Date deployDate) {
        this.deployDate = deployDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isSuspended() {
        return isSuspended;
    }

    public void setSuspended(boolean suspended) {
        isSuspended = suspended;
    }

    public boolean isHasStartFormKey() {
        return hasStartFormKey;
    }

    public void setHasStartFormKey(boolean hasStartFormKey) {
        this.hasStartFormKey = hasStartFormKey;
    }

    public boolean isHasGraphicalNotation() {
        return hasGraphicalNotation;
    }

    public void setHasGraphicalNotation(boolean hasGraphicalNotation) {
        this.hasGraphicalNotation = hasGraphicalNotation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeploymentId() {
        return deploymentId;
    }

    public void setDeploymentId(String deploymentId) {
        this.deploymentId = deploymentId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
