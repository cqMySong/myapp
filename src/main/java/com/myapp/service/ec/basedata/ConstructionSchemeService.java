package com.myapp.service.ec.basedata;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.SchemeState;
import com.myapp.core.exception.db.QueryException;
import com.myapp.core.exception.db.SaveException;
import com.myapp.core.model.PageModel;
import com.myapp.core.model.WebDataModel;
import com.myapp.core.util.BaseUtil;
import com.myapp.entity.ec.basedata.*;
import com.myapp.enums.ec.ProWbsType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.myapp.core.service.base.BaseInterfaceService;

import java.util.*;

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
        .append("a.fcompileDate as complileDate,a.flastFinishDate as lastFinishDate,a.fSchemeState as billState,b.fname as projectName,")
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

    // 导入单位工程对应下的标准分解结构
    public WebDataModel batchSave(String projectId, UserInfo userInfo, String wbsIds) throws SaveException {
        WebDataModel wdm = new WebDataModel();
        wdm.setData(null);
        int code = 0;
        String mesg = "";
        if(BaseUtil.isEmpty(projectId)){
            wdm.setStatusCode(-1);
            wdm.setStatusMesg("对应的工程项目单位工程为空，无法完成对应项目工程下的施工方案导入!");
            return wdm;
        }
        if(BaseUtil.isEmpty(wbsIds)){
            wdm.setStatusCode(-1);
            wdm.setStatusMesg("未选择对应的标准信息!");
            return wdm;
        }
        String hql = " from SchemeTypeInfo as sti where sti.enabled=? "
                + " and sti.id in('"+wbsIds.replaceAll(",","','")+"') " +
                "and not exists(from ConstructionSchemeInfo as csi where csi.schemeType.id = sti.id and csi.project.id=?)"
                + " order by sti.name asc";
        List params = new ArrayList();
        params.add(Boolean.TRUE);
        params.add(projectId);

        List<SchemeTypeInfo> datas = findByHQL(hql, params.toArray());
        if(datas!=null&&datas.size()>0){
            code = 0;
            mesg = "施工方案成功导入["+datas.size()+"]个!";
            for(SchemeTypeInfo dgInfo:datas){
                ConstructionSchemeInfo constructionSchemeInfo = new ConstructionSchemeInfo();
                ProjectInfo pinfo = new ProjectInfo();
                pinfo.setId(projectId);
                constructionSchemeInfo.setProject(pinfo);
                constructionSchemeInfo.setName(dgInfo.getName());
                constructionSchemeInfo.setNumber(dgInfo.getNumber());
                constructionSchemeInfo.setSchemeState(SchemeState.COMPANY);
                constructionSchemeInfo.setCompiler(userInfo);
                constructionSchemeInfo.setCreateUser(userInfo);
                constructionSchemeInfo.setCreateDate(new Date());
                constructionSchemeInfo.setSchemeType(dgInfo);
                addNewEntity(constructionSchemeInfo);
            }
        }else{
            code = 1;
            mesg = "没有对应的工程分解结构数据可供导入!";
        }
        wdm.setStatusCode(code);
        wdm.setStatusMesg(mesg);
        return wdm;
    }
}
