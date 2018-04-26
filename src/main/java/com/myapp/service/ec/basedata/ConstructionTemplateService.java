package com.myapp.service.ec.basedata;

import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.entity.ec.basedata.ConstructionTemplateInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @path：com.myapp.service.ec.basedata
 * @description：
 * @author： ly
 * @date: 2018-01-31 0:44
 */
@Service("constructionTemplateService")
public class ConstructionTemplateService extends BaseInterfaceService<ConstructionTemplateInfo> {
    /**
     * 功能查询所有清单信息
     * @return
     */
    public List queryAllConstructionTemplate(){
        String hql = "select a.id as id,a.templateType as name " +
                "from ConstructionTemplateInfo a where a.enabled=? order by a.name";
        return findByHQL(hql,new Object[]{Boolean.TRUE});
    }
}
