package com.myapp.service.ec.basedata;

import com.myapp.core.exception.db.QueryException;
import com.myapp.core.model.PageModel;
import com.myapp.core.util.BaseUtil;
import org.springframework.stereotype.Service;

import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.entity.ec.basedata.ConstructionSchemeInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
@Service("constructionSchemeService")
public class ConstructionSchemeService extends BaseInterfaceService<ConstructionSchemeInfo> {
    /**
     * 功能：获取施工方案列表信息
     * @param curPage
     * @param pageSize
     * @param params
     * @return
     * @throws QueryException
     */
    public PageModel queryConstructionSchemeLedger(Integer curPage, Integer pageSize, Map<String,Object> params)
            throws QueryException {
        List<Object> paramList = new ArrayList<>();
        StringBuffer sql = new StringBuffer();
        sql.append("select a.fnumber as number,a.fname as name,c.fname as schemeTypeName, d.fname as compilerName,a.fid as id,")
        .append("a.fcompileDate as complileDate,a.flastFinishDate as lastFinishDate,a.fBillState as billState,b.fname as projectName,")
        .append("(select min(e.fbegdate) from t_ec_proWorkPlanReport e where e.fprojectId = a.fprojectId) as projectBegDate ")
        .append(" from t_ec_constructionScheme a,t_ec_project b,t_ec_schemeType c,t_pm_user d ")
        .append("where a.fprojectId = b.fid and a.fschemeTypeId = c.fid and a.fcompilerId = d.fid")
        .append(" and a.fProjectId=?");
        paramList.add(params.get("projectId"));
        if(params.get("key")!=null){
            if("number".equals(params.get("key"))){
                sql.append(" and a.fnumber =?");
                paramList.add(params.get("value"));
            }else if("name".equals(params.get("key"))){
                sql.append(" and a.fname like ?");
                paramList.add("%"+params.get("value")+"%");
            }else if("compilerName".equals(params.get("key"))){
                sql.append(" and d.fname like ?");
                paramList.add("%"+params.get("value")+"%");
            }

        }
        sql.append(" order by a.fnumber,a.fname");
        return toPageSqlQuery(curPage,pageSize,sql.toString(),paramList.toArray());
    }
}
