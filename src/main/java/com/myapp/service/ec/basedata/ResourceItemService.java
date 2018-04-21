package com.myapp.service.ec.basedata;

import com.myapp.core.exception.db.QueryException;
import com.myapp.enums.ec.ResourceType;
import org.springframework.stereotype.Service;

import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.entity.ec.basedata.ResourceItemInfo;

import java.util.List;
import java.util.Map;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年11月30日 
 * @system:
 * 资料类别对应的资料目录
 *-----------MySong---------------
 */
@Service("resourceItemService")
public class ResourceItemService extends BaseInterfaceService<ResourceItemInfo> {
    /**
     * 功能：根据资料类型查询信息
     * @param resourceType
     * @return
     */
    public List<Map> queryByResourceType(String resourceType) throws QueryException {
        String hql = "select ri.fname as name,ri.fid as id from t_ec_resourceitem ri where ri.ftype=?";
        return executeSQLQuery(hql,new Object[]{resourceType});
    }
}
