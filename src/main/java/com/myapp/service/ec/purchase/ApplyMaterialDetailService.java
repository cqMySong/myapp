package com.myapp.service.ec.purchase;

import com.myapp.core.exception.db.QueryException;
import com.myapp.core.exception.db.SaveException;
import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.core.util.DateUtil;
import com.myapp.core.util.WebUtil;
import com.myapp.entity.ec.budget.BudgetingDetailInfo;
import com.myapp.entity.ec.purchase.ApplyMaterialDetailInfo;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @path：com.myapp.service.ec.purchase
 * @description：材料申购业务类
 * @author： ly
 * @date: 2017-10-29 21:48
 */
@Service("applyMaterialDetailService")
public class ApplyMaterialDetailService extends BaseInterfaceService<ApplyMaterialDetailInfo> {
    /**
     * 部门负责人查看界面
     * @param businessKey
     * @return
     * @throws QueryException
     */
    public List<Map> queryDeptManagerAudit(String businessKey) throws QueryException {
        String sql = "select c.fquantity as quantity,c.fBudgetaryPrice as budgetaryPrice,c.fSpecification as specification," +
                "d.fname as materialName,d.fnumber as materialNumber,e.fname as materialUnit,b.fbizDate as submitDate," +
                "a.fpurchaseNum as purchaseNum,b.fnumber as applyNumber,a.fStockCount as stockCount," +
                "a.fArrivalTime as arrivalTime,a.fCumulativePurchaseNum as cumulativePurchaseNum,a.fid as id " +
                " from t_ec_apply_material_detail a,t_ec_apply_material b,t_ec_budgeting_detail c,t_base_material d," +
                "t_base_measureunit e " +
                "where a.fprentId = b.fid and a.fBudgetingDetailId = c.fid and c.fMaterialId = d.fid" +
                " and e.fid = c.fMeasureUnitId and b.fid=?";
        return executeSQLQuery(sql,new Object[]{businessKey});
    }

    /**
     * 技术负责人审核查看界面
     * @param businessKey
     * @return
     * @throws QueryException
     */
    public List<Map> queryTechnicalManagerAudit(String businessKey) throws QueryException {
        String sql = "select c.fquantity as quantity,c.fBudgetaryPrice as budgetaryPrice,c.fSpecification as specification," +
                "d.fname as materialName,d.fnumber as materialNumber,e.fname as materialUnit,b.fbizDate as submitDate," +
                "a.fpurchaseNum as purchaseNum,b.fnumber as applyNumber,a.fStockCount as stockCount," +
                "a.fArrivalTime as arrivalTime,a.fCumulativePurchaseNum as cumulativePurchaseNum,a.fid as id " +
                " from t_ec_apply_material_detail a,t_ec_apply_material b,t_ec_budgeting_detail c,t_base_material d," +
                "t_base_measureunit e " +
                "where a.fprentId = b.fid and a.fBudgetingDetailId = c.fid and c.fMaterialId = d.fid" +
                " and e.fid = c.fMeasureUnitId and b.fid=?";
        return executeSQLQuery(sql,new Object[]{businessKey});
    }
    /**
     * 材料员审核界面
     * @param businessKey
     * @return
     * @throws QueryException
     */
    public List<Map> queryMaterialManagerAudit(String businessKey) throws QueryException {
        String sql = "select c.fquantity as quantity,c.fBudgetaryPrice as budgetaryPrice,c.fSpecification as specification," +
                "d.fname as materialName,d.fnumber as materialNumber,e.fname as materialUnit,b.fbizDate as submitDate," +
                "a.fpurchaseNum as purchaseNum,b.fnumber as applyNumber,a.fStockCount as stockCount,a.fpurchasePrice as purchasePrice," +
                "a.fArrivalTime as arrivalTime,a.fCumulativePurchaseNum as cumulativePurchaseNum,a.fid as id,a.fPurchaseArrivalTime as purchaseArrivalTime " +
                " from t_ec_apply_material_detail a,t_ec_apply_material b,t_ec_budgeting_detail c,t_base_material d," +
                "t_base_measureunit e " +
                "where a.fprentId = b.fid and a.fBudgetingDetailId = c.fid and c.fMaterialId = d.fid" +
                " and e.fid = c.fMeasureUnitId and b.fid=?";
        return executeSQLQuery(sql,new Object[]{businessKey});
    }

    /**
     * 项目经理审核查看界面
     * @param businessKey
     * @return
     * @throws QueryException
     */
    public List<Map> queryProjectManagerAudit(String businessKey) throws QueryException {
        String sql = "select c.fquantity as quantity,c.fBudgetaryPrice as budgetaryPrice,c.fSpecification as specification," +
                "d.fname as materialName,d.fnumber as materialNumber,e.fname as materialUnit,b.fbizDate as submitDate," +
                "a.fpurchaseNum as purchaseNum,b.fnumber as applyNumber,a.fStockCount as stockCount,a.fpurchasePrice as purchasePrice," +
                "a.fArrivalTime as arrivalTime,a.fCumulativePurchaseNum as cumulativePurchaseNum,a.fid as id,a.fPurchaseArrivalTime as purchaseArrivalTime " +
                " from t_ec_apply_material_detail a,t_ec_apply_material b,t_ec_budgeting_detail c,t_base_material d," +
                "t_base_measureunit e " +
                "where a.fprentId = b.fid and a.fBudgetingDetailId = c.fid and c.fMaterialId = d.fid" +
                " and e.fid = c.fMeasureUnitId and b.fid=?";
        return executeSQLQuery(sql,new Object[]{businessKey});
    }

    public void editAuditData(String purchasePrice,String purchaseArrivalTime,String id,
                              String purchaseNum,String arrivalTime) throws SaveException {
        ApplyMaterialDetailInfo applyMaterialDetailInfo = loadEntity(id);
        if(!StringUtils.isEmpty(purchasePrice)){
            applyMaterialDetailInfo.setPurchasePrice(new BigDecimal(purchasePrice));
        }
        if(!StringUtils.isEmpty(purchaseArrivalTime)){
            applyMaterialDetailInfo.setPurchaseArrivalTime(DateUtil.parseDate(purchaseArrivalTime));
        }
        if(!StringUtils.isEmpty(purchaseNum)){
            applyMaterialDetailInfo.setPurchaseNum(new BigDecimal(purchaseNum));
        }
        if(!StringUtils.isEmpty(arrivalTime)){
            applyMaterialDetailInfo.setArrivalTime(DateUtil.parseDate(arrivalTime));
        }
        saveEntity(applyMaterialDetailInfo);
    }
}
