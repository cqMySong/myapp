package com.myapp.service.ec.purchase;

import com.myapp.core.exception.db.QueryException;
import com.myapp.core.exception.db.SaveException;
import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.entity.ec.budget.BudgetingDetailInfo;
import com.myapp.entity.ec.purchase.ApplyMaterialDetailInfo;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @path：com.myapp.service.ec.purchase
 * @description：材料申购业务类
 * @author： ly
 * @date: 2017-10-29 21:48
 */
@Service("applyMaterialDetailService")
public class ApplyMaterialDetailService extends BaseInterfaceService<ApplyMaterialDetailInfo> {
    /**
     * 查找最近一次的预算申购信息
     * @param budgetingDetailInfo
     * @return
     */
    public ApplyMaterialDetailInfo queryLastApplyMaterial(BudgetingDetailInfo budgetingDetailInfo) throws QueryException {
        Criteria query = initQueryCriteria(ApplyMaterialDetailInfo.class);
        query.add(Restrictions.eq("budgetingDetailInfo",budgetingDetailInfo));
        query.addOrder(Order.desc("sno"));
        List applyMaterials = query.list();
        if(applyMaterials!=null){
            return (ApplyMaterialDetailInfo) applyMaterials.get(0);
        }
        return null;
    }

    /**
     * 查找指定序号后的申购信息
     * @param budgetingDetailInfo
     * @param sno
     * @return
     */
    public List<ApplyMaterialDetailInfo> queryAfterApplyMaterial(BudgetingDetailInfo budgetingDetailInfo,Long sno) throws QueryException {
        Criteria query = initQueryCriteria(ApplyMaterialDetailInfo.class);
        query.add(Restrictions.eq("budgetingDetailInfo",budgetingDetailInfo));
        query.add(Restrictions.gt("sno",sno));
        List applyMaterials = query.list();
        if(applyMaterials!=null){
            return applyMaterials;
        }
        return null;
    }

    /**
     * 功能：更新累计申购数量
     * @param diffCount
     * @param budgetingDetailInfo
     * @param sno
     */
    public void updateCumulativePurchaseNum(BigDecimal diffCount,BudgetingDetailInfo budgetingDetailInfo,
                                            Long sno) throws SaveException, QueryException {
        List<ApplyMaterialDetailInfo> applyMaterialDetailList = queryAfterApplyMaterial(budgetingDetailInfo,sno);
        if(applyMaterialDetailList!=null){
            for(ApplyMaterialDetailInfo materialDetailInfo:applyMaterialDetailList){
                materialDetailInfo.setCumulativePurchaseNum(materialDetailInfo.getCumulativePurchaseNum()
                        .subtract(diffCount));
                saveEntity(materialDetailInfo);
            }
        }
    }
}
