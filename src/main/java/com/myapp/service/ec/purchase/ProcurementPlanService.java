package com.myapp.service.ec.purchase;

import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.entity.ec.purchase.ProcurementPlanInfo;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Service;

/**
 * 包路径：com.myapp.service.ec.purchase
 * 功能说明：采购计划业务类
 * 创建人： ly
 * 创建时间: 2017-08-28 21:15
 */
@Service("procurementPlanService")
public class ProcurementPlanService extends BaseInterfaceService<ProcurementPlanInfo> implements ExecutionListener {
    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {

    }
}
