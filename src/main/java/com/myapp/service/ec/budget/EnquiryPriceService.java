package com.myapp.service.ec.budget;

import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.entity.ec.budget.EnquiryPriceInfo;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Service;

/**
 * @path：com.myapp.service.ec.budget
 * @desciption：预算询价
 * @author ： ly
 * @date: 2017-08-28 21:15
 */
@Service("enquiryPriceService")
public class EnquiryPriceService extends BaseInterfaceService<EnquiryPriceInfo> implements ExecutionListener {
    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {

    }
}
