package com.myapp.service.ec.basedata;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.exception.db.QueryException;
import com.myapp.core.exception.db.SaveException;
import com.myapp.core.model.PageModel;
import com.myapp.core.util.BaseUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.entity.ec.basedata.ConstructionSchemeInfo;

import java.util.ArrayList;
import java.util.Date;
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
        .append(" from t_ec_project b,t_ec_schemeType c,t_ec_constructionScheme a left join t_pm_user d on a.fcompilerId = d.fid ")
        .append("where a.fprojectId = b.fid and a.fschemeTypeId = c.fid ")
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

    /**
     * 功能：保存施工方案
     * @param schemeBatch
     * @param userInfo
     */
    public void batchSave(String schemeBatch,UserInfo userInfo) throws SaveException {
        if(StringUtils.isEmpty(schemeBatch)){
            throw new RuntimeException("请选择施工方案");
        }
        List<ConstructionSchemeInfo> constructionSchemeInfoList =
                JSON.parseArray(schemeBatch,ConstructionSchemeInfo.class);
        if(constructionSchemeInfoList==null||constructionSchemeInfoList.size()==0){
            throw new RuntimeException("请选择施工方案");
        }
        for(ConstructionSchemeInfo constructionSchemeInfo:constructionSchemeInfoList){
            constructionSchemeInfo.setCreateUser(userInfo);
            constructionSchemeInfo.setCreateDate(new Date());
            constructionSchemeInfo.setCompiler(userInfo);
            saveEntity(constructionSchemeInfo);
        }
    }
}
