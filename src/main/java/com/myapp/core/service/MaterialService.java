package com.myapp.core.service;

import com.myapp.core.entity.MeasureUnitInfo;
import com.myapp.core.enums.MaterialType;
import com.myapp.core.enums.UnitClass;
import com.myapp.core.exception.db.SaveException;
import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.core.entity.MaterialInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * 功能：
 * @author:ly
 * @date:2017-10-18
 */
@Service("materialService")
public class MaterialService extends BaseInterfaceService<MaterialInfo> {
    @Resource
    private MeasureUnitService measureUnitService;
    /**
     * 功能：根据物料编码查询物料信息
     * @param materialCode
     * @return
     */
    public MaterialInfo queryByCode(String materialCode){
        String hql = "select material from MaterialInfo material where material.number=?";
        return queryEntity(MaterialInfo.class,hql,new Object[]{materialCode});
    }

    public MaterialInfo saveOrUpdate(Map materialMap) throws SaveException {
        MaterialInfo materialInfo = queryByCode(materialMap.get("matNumber").toString());
        if(materialInfo==null){
            materialInfo = new MaterialInfo();
            MeasureUnitInfo measureUnitInfo = measureUnitService.queryByName(materialMap.get("unit").toString());
            if(measureUnitInfo==null){
                measureUnitInfo = new MeasureUnitInfo();
                measureUnitInfo.setName(materialMap.get("unit").toString());
                measureUnitInfo.setUnitClass(UnitClass.OTHER);
                measureUnitInfo.setEnabled(true);
                measureUnitInfo.setNumber(materialMap.get("unit").toString());
                measureUnitInfo = (MeasureUnitInfo) measureUnitService.saveEntity(measureUnitInfo);
            }
            materialInfo.setUnit(measureUnitInfo);
            materialInfo.setNumber(materialMap.get("matNumber").toString());
            materialInfo.setName(materialMap.get("matName").toString());
            materialInfo.setSpecification(materialMap.get("model").toString());
            materialInfo.setMaterialType(MaterialType.STRUCTURE);
            materialInfo.setEnabled(true);
            materialInfo.setCreateDate(new Date());
            materialInfo = (MaterialInfo) saveEntity(materialInfo);
        }
        return materialInfo;
    }
}
