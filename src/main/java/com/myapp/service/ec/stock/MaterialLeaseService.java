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
        sql.append("select a.fnumber as materialNumber,a.fname as materialName,b.fname as materialUnit,")
            .append("c.fLeaseUnit as leaseUnit,c.fLeaseDate as leaseDate,c.fLeaseCount as leaseCount,")
            .append("d.fBackDate as backDate,d.fBackCount as backCount,")
            .append("case when d.fBackCount is null then '' else (c.fLeaseCount-d.fBackCount) end as diffCount,")
            .append("case when d.fBackCount is null then '' else ")
            .append("convert((c.fLeaseCount-d.fBackCount)*100/c.fLeaseCount,decimal(4,2)) end as diffRatio")
            .append(" from t_base_material a,t_base_measureunit b,t_ec_material_lease c ")
            .append("left join t_ec_material_back d on c.fid = d.fMaterialLeaseId ")
            .append("where a.fid=c.fMaterialId and c.fMeasureUnitId = b.fid")
            .append(" and c.fProjectId=?");
        paramList.add(params.get("projectId"));
        if(!BaseUtil.isEmpty(params.get("startDate"))){
            sql.append("and c.fLeaseDate>=? ");
            paramList.add(params.get("startDate"));
        }
        if(!BaseUtil.isEmpty(params.get("endDate"))){
            sql.append("and c.fLeaseDate<=? ");
            paramList.add(params.get("endDate"));
        }
        if(!BaseUtil.isEmpty(params.get("leaseUnit"))){
            sql.append("and c.fLeaseUnit like ? ");
            paramList.add("%"+params.get("leaseUnit")+"%");
        }
        sql.append(" order by c.fLeaseUnit,a.fName");
        return toPageSqlQuery(curPage,pageSize,sql.toString(),paramList.toArray());
    }
}
