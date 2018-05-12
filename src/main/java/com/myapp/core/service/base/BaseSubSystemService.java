package com.myapp.core.service.base;
import com.myapp.core.entity.SubsystemTreeInfo;
import com.myapp.core.enums.EntityTypeEnum;
import com.myapp.core.exception.db.QueryException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 包路径：com.myapp.core.service.base
 * 功能说明：
 * 创建人： ly
 * 创建时间: 2017-08-18 21:32
 */
@Service("baseSubSystemService")
public class BaseSubSystemService extends BaseInterfaceService<SubsystemTreeInfo> {
    /**
     * 功能：根据类型查询系统结构表
     * @param entityType
     * @return
     */
    public List queryByEntityType(EntityTypeEnum entityType) throws QueryException {
        if(entityType==null){
            return null;
        }
        String hql = "select fentityObjectType,fentityName from t_base_subsystem where fentityType=?";
        return executeSQLQuery(hql,new String[]{entityType.getValue()});
    }

    /**
     * 功能：根据对象获取主键信息
     * @param classParam
     * @return
     */
    public String queryByEntityClaz(Class classParam){
        String hql = "select entityObjectType as objectType from SubsystemTreeInfo where entityClaz=?";
        List<Map> fIdList = findByHQL(hql,new String[]{classParam.getName()});
        if(fIdList==null||fIdList.size()==0){
            return null;
        }
        return fIdList.get(0).get("objectType").toString();
    }


}
