package com.myapp.service.ec.basedata;

import com.myapp.core.exception.db.QueryException;
import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.entity.ec.basedata.QualityTemplateInfo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @path：com.myapp.service.ec.basedata
 * @description：
 * @author： ly
 * @date: 2018-01-11 0:16
 */
@Service("qualityTemplateService")
public class QualityTemplateService extends  BaseInterfaceService<QualityTemplateInfo> {
    /**
     * 功能：根据岗位id和分部分项查询工作要求信息
     * @param branchBaseWbsId
     * @param subentryId
     * @param positionId
     * @return
     */
    public String queryByPositionAndSubEntry(String branchBaseWbsId,
                                                          String subentryId,String positionId){
        String hql = "select qtd.jobRequirement as jobRequirement from QualityTemplateInfo as qt,QualityTemplateDetailInfo as qtd " +
                "where qtd.parent.id = qt.id and qt.branchBaseWbs.id = ? and qt.subentry.id=? " +
                "and qtd.position.id=? and qt.enabled=true and qtd.enable = true";
        List<Map<String,String>> result = findByHQL(hql,new Object[]{branchBaseWbsId,subentryId,positionId});
        if(result!=null&&result.size()>0){
            return result.get(0).get("jobRequirement");
        }
        return null;
    }

    /**
     * 功能：查询当前工程的分部分项  质量样板工作
     * @param projectId
     * @return
     */
    public List queryProjectQualityTemplate(String projectId) throws QueryException {
        String sql = "select distinct a.fid as id,a.fname as name,a.fnumber as number,b.fid as proWbsId " +
                " from t_ec_quality_template a,t_ec_projectwbs b " +
                " where b.fprojectId =? and (a.fBranchBaseWbsId = b.fbasewWbsId " +
                " or a.fSubentryId = b.fbasewWbsId) order by b.flevel,a.fname";
        return executeSQLQuery(sql,new Object[]{projectId});
    }

    /**
     * 功能：查询当前工程的分部分项  质量样板工作
     * @param projectId
     * @return
     */
    public List queryProjectQualityTemplateTree(String projectId) throws QueryException {
        String sql = "select distinct b.fname as typeName,ifnull(b.fprentid,'0') as parentId,b.fid as id " +
                " from t_ec_quality_template a,t_ec_projectwbs b " +
                " where b.fprojectId =? and (a.fBranchBaseWbsId = b.fbasewWbsId " +
                " or a.fSubentryId = b.fbasewWbsId) order by b.flevel,a.fname";
        return executeSQLQuery(sql,new Object[]{projectId});
    }
}
