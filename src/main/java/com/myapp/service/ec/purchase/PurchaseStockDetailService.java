package com.myapp.service.ec.purchase;

import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.entity.ec.purchase.PurchaseStockDetailInfo;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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


}
