package com.myapp.service.ec.purchase;

import com.myapp.core.exception.db.QueryException;
import com.myapp.core.exception.db.SaveException;
import com.myapp.core.service.base.BaseInterfaceService;
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
import java.util.List;

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

}
