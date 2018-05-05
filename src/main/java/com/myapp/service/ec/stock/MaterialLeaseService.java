package com.myapp.service.ec.stock;

import com.myapp.core.exception.db.QueryException;
import com.myapp.core.model.PageModel;
import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.core.util.BaseUtil;
import com.myapp.entity.ec.stock.MaterialLeaseInfo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @path：com.myapp.service.ec.stock
 * @description：
 * @author： ly
 * @date: 2018-01-07 10:40
 */
@Service("materialLeaseService")
public class MaterialLeaseService extends BaseInterfaceService<MaterialLeaseInfo> {

    /**
     * 功能：获取现场签证支出台帐信息
     * @param curPage
     * @param pageSize
     * @param params
     * @return
     * @throws QueryException
     */
    public PageModel queryLeaseLedger(Integer curPage, Integer pageSize, Map<String,Object> params)
            throws QueryException {
        List<Object> paramList = new ArrayList<>();
        StringBuffer sql = new StringBuffer();
        sql.append("select b.fid as leaseId,a.fMaterialId as materialId,c.fnumber as materialNumber,")
           .append("c.fname as materialName,d.fname as unitName,a.fLeaseCount as leaseCount,")
           .append("b.fLeaseDate as leaseDate,b.fLeaseUnit as leaseUnit,")
           .append("(select sum(t.fLeaseCount) from t_ec_material_lease_detail t,t_ec_material_lease t1 ")
           .append(" where t1.fid = t.fprentid and t1.fProjectId = b.fProjectId and t.fMaterialId = a.fMaterialId ")
           .append(" and t1.fcreateDate<=b.fcreateDate) as leaseTotalCount,")
           .append("(select count(t.fid) from t_base_attachFile t where t.fsourceBillId = b.fid) as leaseAttach ")
           .append(" from t_ec_material_lease_detail a,t_ec_material_lease b,t_base_material c,t_base_measureunit d")
           .append(" where a.fprentid = b.fid and a.fMaterialId = c.fid and c.fUnit = d.fid and b.fProjectId = ? ");
        paramList.add(params.get("projectId"));
        if(!BaseUtil.isEmpty(params.get("leaseUnit"))){
            sql.append(" and c.fLeaseUnit like ? ");
            paramList.add("%"+params.get("leaseUnit")+"%");
        }
        if(!BaseUtil.isEmpty(params.get("materialName"))){
            sql.append(" and c.fname like ? ");
            paramList.add("%"+params.get("materialName")+"%");
        }
        sql.append(" order by c.fname,d.fname,b.fLeaseDate,b.fcreateDate");
        //出租信息
        List<Map> leaseList =  executeSQLQuery(sql.toString(),paramList.toArray());
        List<Object> backParamList = new ArrayList<>();
        StringBuffer backSql = new StringBuffer();
        backSql.append("select b.fid as backId,a.fMaterialId as materialId,c.fnumber as materialNumber,")
            .append("c.fname as materialName,d.fname as unitName,a.fBackCount as backCount,")
            .append("b.fBackDate as backDate,b.fLeaseUnit as leaseUnit,a.fremark as remark,")
            .append("(select sum(t.fBackCount) from t_ec_material_lease_back_detail t,t_ec_material_lease_back t1 ")
            .append(" where t1.fid = t.fprentid and t1.fProjectId = b.fProjectId and t.fMaterialId = a.fMaterialId ")
            .append(" and t1.fcreateDate<=b.fcreateDate) as backTotalCount,")
            .append("(select count(t.fid) from t_base_attachFile t where t.fsourceBillId = b.fid) as backAttach ")
            .append(" from t_ec_material_lease_back_detail a,t_ec_material_lease_back b,t_base_material c,t_base_measureunit d")
            .append(" where a.fprentid = b.fid and a.fMaterialId = c.fid and c.fUnit = d.fid and b.fProjectId = ? ");
        backParamList.add(params.get("projectId"));
        if(!BaseUtil.isEmpty(params.get("leaseUnit"))){
            backSql.append(" and c.fLeaseUnit like ? ");
            backParamList.add("%"+params.get("leaseUnit")+"%");
        }
        if(!BaseUtil.isEmpty(params.get("materialName"))){
            backSql.append(" and c.fname like ? ");
            backParamList.add("%"+params.get("materialName")+"%");
        }
        backSql.append(" order by c.fname,d.fname,b.fBackDate,b.fcreateDate");
        //归还信息
        List<Map> backList =  executeSQLQuery(backSql.toString(),backParamList.toArray());
        int leaseIndex = 0;
        int backIndex = 0;
        List<Map> materialLease = new ArrayList<>();
        Map lease = null;
        Map back = null;
        while (true){
            lease = null;
            back = null;
            if(leaseIndex<leaseList.size()){
                lease = leaseList.get(leaseIndex);
            }
            if(backIndex<backList.size()){
                back = backList.get(backIndex);
            }
            if(lease!=null&&back!=null&&lease.get("materialId").toString()
                    .equals(back.get("materialId").toString())){
                lease.putAll(back);
                materialLease.add(lease);
                leaseIndex++;
                backIndex++;
            }else if(lease!=null&&back!=null&&!lease.get("materialId").toString()
                    .equals(back.get("materialId").toString())){
                if(materialLease.size()==0){
                    materialLease.add(lease);
                    leaseIndex++;
                }else if(lease.get("materialId").toString().equals(
                        materialLease.get(materialLease.size()-1).get("materialId").toString())){
                    materialLease.add(lease);
                    leaseIndex++;
                }else if(back.get("materialId").toString().equals(
                        materialLease.get(materialLease.size()-1).get("materialId").toString())){
                    if(materialLease.size()!=0){
                        back.put("leaseTotalCount",materialLease.get(materialLease.size()-1).get("leaseTotalCount"));
                    }
                    materialLease.add(back);
                    backIndex++;
                }
            }else if(lease!=null&&back==null){
                materialLease.add(lease);
                leaseIndex++;
            }else if(lease==null&&back!=null){
                if(materialLease.size()!=0){
                    back.put("leaseTotalCount",materialLease.get(materialLease.size()-1).get("leaseTotalCount"));
                }
                materialLease.add(back);
                backIndex++;
            }else{
                break;
            }

        }
        PageModel pageModel = new PageModel(1,Integer.MAX_VALUE,Integer.MAX_VALUE);
        pageModel.setDatas(materialLease);
        return pageModel;
    }
}
