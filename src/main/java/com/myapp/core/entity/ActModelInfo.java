package com.myapp.core.entity;

import com.myapp.core.base.entity.CoreBaseInfo;

/**
 * 包路径：com.myapp.core.entity
 * 功能说明：流程模型信息
 * 创建人： ly
 * 创建时间: 2017-07-02 22:48
 */
public class ActModelInfo extends CoreBaseInfo {
    private String category;
    private String description;
    private UserInfo userInfo;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
