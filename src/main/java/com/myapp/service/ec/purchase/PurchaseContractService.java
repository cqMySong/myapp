package com.myapp.service.ec.purchase;

import com.myapp.core.entity.MaterialInfo;
import com.myapp.core.exception.db.SaveException;
import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.entity.ec.purchase.PurchaseContractDetailInfo;
import com.myapp.entity.ec.purchase.PurchaseContractInfo;
import com.myapp.entity.ec.stock.StockInfo;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

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

    /**
     *根据合同id，修改结算金额
     * @param purchaseContractId
     * @param settleAmount
     * @return
     * @throws SaveException
     */
    public PurchaseContractInfo updateBalanceSettleAmountById(String purchaseContractId,BigDecimal settleAmount)
            throws SaveException {
        PurchaseContractInfo purchaseContractInfo = getEntity(PurchaseContractInfo.class,purchaseContractId);
        purchaseContractInfo.setBalanceSettleAmount(purchaseContractInfo.getBalanceSettleAmount().subtract(settleAmount));
        return (PurchaseContractInfo) saveEntity(purchaseContractInfo);
    }
}
