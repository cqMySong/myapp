package com.myapp.service.ec.settle;

import com.myapp.core.exception.db.QueryException;
import com.myapp.core.model.PageModel;
import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.core.util.BaseUtil;
import com.myapp.entity.ec.settle.MaterialSettleInfo;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @path：com.myapp.service.ec.settle
 * @description：
 * @author： ly
 * @date: 2017-11-11 15:03
 */
@Service("materialSettleService")
public class MaterialSettleService extends BaseInterfaceService<MaterialSettleInfo> {

    public PageModel queryMaterialAnalysis(Integer curPage,Integer pageSize,Map<String,Object> params)
            throws QueryException {
        StringBuffer sql = new StringBuffer();
        sql.append("select b.fstartdate,b.fenddate,c.fMaterialId,c.fCalculationCount,d.fnumber,d.fname,")
            .append("d.fSpecification,f.fname as unitName,a.fActualUseCount, ")
            .append("(select sum(b1.fQuantity) from t_ec_budgeting a1,t_ec_budgeting_detail b1 ")
            .append("where a1.fid = b1.fprentid and b1.fmaterialId = c.fMaterialId and a1.fProjectId=b.fProjectId ")
            .append("group by b1.fmaterialId) as quantity, ")
            .append("(select sum(b1.fSettleCount) from t_ec_material_settle a1,t_ec_material_settle_detail b1 ")
            .append("where a1.fid = b1.fprentid and b1.fmaterialId = c.fMaterialId and a1.fProjectId=b.fProjectId ")
            .append("and a1.fStartDate>=b.fstartdate and a1.fEndDate<=b.fenddate ")
            .append("group by b1.fmaterialId) as settleCount, ")
            .append("(select sum(b1.fCount) from t_ec_purchase_stock a1,t_ec_purchase_stock_detail b1 ")
            .append("where a1.fid = b1.fprentid and b1.fmaterialId = c.fMaterialId and a1.fProjectId=b.fProjectId ")
            .append(" and a1.fInStockDate>=b.fstartdate and a1.fInStockDate<=b.fenddate ")
            .append("group by b1.fmaterialId) as purchaseCount ")
            .append("from t_ec_stock_inventory_detail a,t_ec_stock_inventory b,t_ec_stock_calculation_detail c,")
            .append("t_base_material d,t_base_measureunit f ")
            .append("where a.fprentid = b.fid and a.fid = c.fStockInventoryDetailId and c.fMaterialId = d.fid ")
            .append("and d.fUnit = f.fid and b.fProjectId=? ");
        List<Object> paramList = new ArrayList<>();
        paramList.add(params.get("projectId"));
        if(!BaseUtil.isEmpty(params.get("startDate"))){
            sql.append("and b.fstartDate>=? ");
            paramList.add(params.get("startDate"));
        }
        if(!BaseUtil.isEmpty(params.get("endDate"))){
            sql.append("and b.fenddate>=? ");
            paramList.add(params.get("endDate"));
        }
        if(!BaseUtil.isEmpty(params.get("materialName"))){
            sql.append("and d.fname like ? ");
            paramList.add("%"+params.get("materialName")+"%");
        }
        sql.append(" order by b.fstartdate,d.fnumber,d.fname,d.fSpecification");
        return  toPageSqlQuery(curPage,pageSize,sql.toString(),paramList.toArray());
    }
}
