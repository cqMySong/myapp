package com.myapp.core.service.basedata;

import org.springframework.stereotype.Service;

import com.myapp.core.entity.basedate.DataGroupInfo;
import com.myapp.core.service.base.BaseInterfaceService;

import java.util.List;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年10月29日 
 * @system:
 * 系统级数据分组serveice
 *-----------MySong---------------
 */
@Service("dataGroupService")
public class DataGroupService extends BaseInterfaceService<DataGroupInfo> {

    /**
     * 根据父级id查询信息
     * @param parentId
     * @return
     */
	public List queryByParentId(String parentId){
	    String hql = "select a.name as name,a.remark as description from DataGroupInfo a " +
                "where a.parent.id=? and a.enabled = true";
	    return findByHQL(hql,new Object[]{parentId});
    }
}
