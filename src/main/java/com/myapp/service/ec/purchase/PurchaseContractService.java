package com.myapp.service.ec.purchase;

import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.entity.ec.purchase.PurchaseContractInfo;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Service;

/**
 * @path：com.myapp.service.ec.purchase
 * @description：采购计划业务类
 * @author ： ly
 * @date: 2017-08-28 21:15
 */
@Service("purchaseContractService")
public class PurchaseContractService extends BaseInterfaceService<PurchaseContractInfo> implements ExecutionListener {
    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {

    }
}
