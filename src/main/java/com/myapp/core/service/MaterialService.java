package com.myapp.core.service;

import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.core.entity.MaterialInfo;
import org.springframework.stereotype.Service;

/**
 * 功能：
 * @author:ly
 * @date:2017-10-18
 */
@Service("materialService")
public class MaterialService extends BaseInterfaceService<MaterialInfo> {
    /**
     * 功能：根据物料编码查询物料信息
     * @param materialCode
     * @return
     */
    public MaterialInfo queryByCode(String materialCode){
        String hql = "select material from MaterialInfo material where material.number=?";
        return queryEntity(MaterialInfo.class,hql,new Object[]{materialCode});
    }
}
