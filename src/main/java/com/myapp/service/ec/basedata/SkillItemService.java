package com.myapp.service.ec.basedata;

import java.util.List;

import com.myapp.core.exception.db.QueryException;
import org.springframework.stereotype.Service;

import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.entity.ec.basedata.SkillClassInfo;
import com.myapp.entity.ec.basedata.SkillItemInfo;
import com.myapp.enums.ec.SkillType;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年10月22日 
 * @system: 技术分类 基础资料类别
 *-----------MySong---------------
 */
@Service("skillItemService")
public class SkillItemService extends BaseInterfaceService<SkillItemInfo> {

    /**
     * 显示树形结构
     * @return
     * @throws QueryException
     */
    public List showTree(String skillType) throws QueryException {
        String sql = "select a.fid as id,a.fname as name,a.fskillclass as parent " +
                "from t_ec_skillitem a where a.fskilltype=? order by a.fname";
        return executeSQLQuery(sql,new Object[]{skillType});
    }
}
