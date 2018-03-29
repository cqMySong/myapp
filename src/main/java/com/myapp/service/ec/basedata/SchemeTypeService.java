package com.myapp.service.ec.basedata;

import com.myapp.core.exception.db.QueryException;
import org.springframework.stereotype.Service;

import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.entity.ec.basedata.SchemeTypeInfo;

import java.util.List;

@Service("schemeTypeService")
public class SchemeTypeService extends BaseInterfaceService<SchemeTypeInfo> {
    /**
     * 显示树形结构
     * @return
     * @throws QueryException
     */
    public List showTree() throws QueryException {
        String sql = "select a.fid as id,a.fname as name,a.fworkchecktype as parent " +
                "from t_ec_schemeType a order by a.fname";
        return executeSQLQuery(sql,null);
    }
}
