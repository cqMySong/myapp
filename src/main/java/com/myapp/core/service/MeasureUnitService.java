package com.myapp.core.service;

import com.myapp.core.entity.MaterialInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myapp.core.entity.MeasureUnitInfo;
import com.myapp.core.service.base.BaseInterfaceService;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年10月22日 
 * @system: 计量单位
 *-----------MySong---------------
 */
@Service("measureUnitService")
@Transactional
public class MeasureUnitService extends BaseInterfaceService<MeasureUnitInfo> {

	public MeasureUnitInfo queryByName(String name){
	    String hql = "select measureUnit from MeasureUnitInfo measureUnit where measureUnit.name=?";
        return queryEntity(MeasureUnitInfo.class,hql,new Object[]{name});
    }
}
