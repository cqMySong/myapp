package com.myapp.service.ec.purchase;

import com.myapp.core.exception.db.QueryException;
import com.myapp.core.exception.db.SaveException;
import com.myapp.core.model.PageModel;
import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.core.util.BaseUtil;
import com.myapp.entity.ec.budget.BudgetingDetailInfo;
import com.myapp.entity.ec.purchase.PurchaseStockDetailInfo;
import com.myapp.entity.ec.purchase.PurchaseContractDetailInfo;
import com.myapp.entity.ec.purchase.PurchaseStockDetailInfo;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @path：com.myapp.service.ec.purchase
 * @description：采购入库
 * @author ： ly
 * @date: 2017-08-28 21:15
 */
@Service("purchaseStockDetailService")
public class PurchaseStockDetailService extends BaseInterfaceService<PurchaseStockDetailInfo> implements ExecutionListener {

    public List<PurchaseStockDetailInfo> queryPurchaseStockDetailByParentId(String parentId){
        String hql = "select stockDetail.id,stockDetail.count from PurchaseStockDetailInfo stockDetail where stockDetail.parent.id=?";
        List aa = findByHQL(hql,new Object[]{parentId});
        return null;
    }
    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {

    }

    /**
     * 查找最近一次的采购入库信息
     * @param purchaseContractDetailInfo
     * @return
     */
    public PurchaseStockDetailInfo queryLastPurchaseStock(PurchaseContractDetailInfo purchaseContractDetailInfo)
            throws QueryException {
        Criteria query = initQueryCriteria(PurchaseStockDetailInfo.class);
        query.add(Restrictions.eq("purchaseContractDetailInfo",purchaseContractDetailInfo));
        query.addOrder(Order.desc("sno"));
        List purchaseStockDetails = query.list();
        if(purchaseStockDetails!=null){
            return (PurchaseStockDetailInfo) purchaseStockDetails.get(0);
        }
        return null;
    }

    /**
     * 查找指定序号后的采购入库
     * @param purchaseContractDetailInfo
     * @param sno
     * @return
     */
    public List<PurchaseStockDetailInfo> queryAfterPurchaseStock(
            PurchaseContractDetailInfo purchaseContractDetailInfo, Long sno) throws QueryException {
        Criteria query = initQueryCriteria(PurchaseStockDetailInfo.class);
        query.add(Restrictions.eq("purchaseContractDetailInfo",purchaseContractDetailInfo));
        query.add(Restrictions.gt("sno",sno));
        List purchaseStocks = query.list();
        if(purchaseStocks!=null){
            return purchaseStocks;
        }
        return null;
    }

    /**
     * 功能：更新累计供应数量
     * @param diffCount
     * @param purchaseContractDetailInfo
     * @param sno
     */
    public void updateCumulativeCount(BigDecimal diffCount, PurchaseContractDetailInfo purchaseContractDetailInfo,
                                            Long sno) throws SaveException, QueryException {
        List<PurchaseStockDetailInfo>purchaseStockDetailList = queryAfterPurchaseStock(purchaseContractDetailInfo,sno);
        if(purchaseStockDetailList!=null){
            for(PurchaseStockDetailInfo purchaseStockDetailInfo:purchaseStockDetailList){
                purchaseStockDetailInfo.setCumulativeCount(purchaseStockDetailInfo.getCumulativeCount()
                        .subtract(diffCount));
                saveEntity(purchaseStockDetailInfo);
            }
        }
    }

    /**
     * 功能：查询材料出入库台帐
     * @param curPage
     * @param pageSize
     * @param params
     * @return
     */
    public PageModel queryMaterialStockLedger(Integer curPage,Integer pageSize,Map<String,Object> params){
        List<Object> paramList = new ArrayList<>();
        StringBuffer sql = new StringBuffer();
        sql.append("select budget.fnumber,budget.fMaterialName,budget.fSpecification, budget.unitName,budget.fQuantity,")
        .append("ifnull(inStock.inStockCount,0) as inStockCount,ifnull(outStock.outStockCount,0) as outStockCount,")
        .append("ifnull(revertStock.revertCount,0) as revertCount,")
        .append("(ifnull(inStock.inStockCount,0)-ifnull(outStock.outStockCount,0)+ifnull(revertStock.revertCount,0))")
        .append(" as stockCount from ")
        .append("(select a.fMaterialId,b.fnumber,a.fMaterialName,a.fSpecification, c.fname as unitName,")
        .append("sum(a.fQuantity) as fQuantity from t_ec_budgeting_detail a,t_base_material b,t_base_measureunit c ")
        .append(",t_ec_budgeting d where a.fMaterialId = b.fid and a.fMeasureUnitId = c.fid and d.fid = a.fprentid ")
        .append(" and d.fProjectId = ?");
        paramList.add(params.get("projectId"));
        if(!BaseUtil.isEmpty(params.get("materialName"))){
            sql.append(" and a.fMaterialName like ? ");
            paramList.add("%"+params.get("materialName")+"%");
        }
        sql.append("group by a.fMaterialId)  budget left join ")
        .append("(select a.fMaterialId,sum(a.fCount) as inStockCount from t_ec_purchase_stock_detail a,t_ec_purchase_stock b,")
        .append("t_base_material c where b.fid = a.fprentid and c.fid = a.fMaterialId and b.fProjectId = ? ");
        paramList.add(params.get("projectId"));
        if(!BaseUtil.isEmpty(params.get("materialName"))){
            sql.append(" and c.fname like ? ");
            paramList.add("%"+params.get("materialName")+"%");
        }
        sql.append(" group by a.fMaterialId) inStock on inStock.fMaterialId = budget.fMaterialId left join")
        .append("(select a.fMaterialId,sum(a.fCount) as outStockCount from t_ec_stock_out_detail a,")
        .append("t_ec_stock_out b,t_base_material c where a.fprentid = b.fid and c.fid = a.fMaterialId ")
        .append(" and b.fprojectId = ? ");
        paramList.add(params.get("projectId"));
        if(!BaseUtil.isEmpty(params.get("materialName"))){
            sql.append(" and c.fname like ? ");
            paramList.add("%"+params.get("materialName")+"%");
        }
        sql.append(" group by  a.fMaterialId) outStock on outStock.fMaterialId = budget.fMaterialId left join")
        .append("(select a.fMaterialId,sum(a.fCount) as revertCount from t_ec_stock_revert_detail a,")
        .append("t_ec_stock_revert b,t_base_material c where a.fprentid = b.fid and c.fid = a.fMaterialId ")
        .append("and b.fprojectId = ? ");
        paramList.add(params.get("projectId"));
        if(!BaseUtil.isEmpty(params.get("materialName"))){
            sql.append(" and c.fname like ? ");
            paramList.add("%"+params.get("materialName")+"%");
        }
        sql.append("group by a.fMaterialId) revertStock on revertStock.fMaterialId = budget.fMaterialId ")
         .append("order by budget.fMaterialName,budget.fSpecification,budget.unitName");
        return  toPageSqlQuery(curPage,pageSize,sql.toString(),paramList.toArray());
    }

}
