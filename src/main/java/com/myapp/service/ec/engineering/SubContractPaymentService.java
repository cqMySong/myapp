package com.myapp.service.ec.engineering;

import com.myapp.core.exception.db.SaveException;
import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.entity.ec.engineering.SubContractPaymentInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @path：com.myapp.service.ec.engineering
 * @description：
 * @author： ly
 * @date: 2017-11-13 22:46
 */
@Service("progressFundService")
public class SubContractPaymentService extends BaseInterfaceService<SubContractPaymentInfo> {
    @Resource
    private SubcontractService subcontractService;
    @Override
    public Object getEntity(String id) {
        SubContractPaymentInfo subContractPaymentInfo = (SubContractPaymentInfo) super.getEntity(id);
        SubContractPaymentInfo oldSubContractPaymentInfo = new SubContractPaymentInfo();
        oldSubContractPaymentInfo.setSettleAmount(subContractPaymentInfo.getSettleAmount());
        oldSubContractPaymentInfo.setSubcontractInfo(subContractPaymentInfo.getSubcontractInfo());
        subContractPaymentInfo.setOldSubContractPaymentInfo(oldSubContractPaymentInfo);
        return subContractPaymentInfo;
    }

    @Override
    public Object saveEntity(Object entity) throws SaveException {
        SubContractPaymentInfo subContractPaymentInfo = (SubContractPaymentInfo) entity;
        BigDecimal diffAmount = BigDecimal.ZERO;
        if(StringUtils.isEmpty(subContractPaymentInfo.getId())){
            diffAmount = subContractPaymentInfo.getSettleAmount();
        }else{
            diffAmount = subContractPaymentInfo.getSettleAmount().subtract(
                    subContractPaymentInfo.getOldSubContractPaymentInfo().getSettleAmount());
        }
        subcontractService.subBalanceAmount(subContractPaymentInfo.getSubcontractInfo().getId(),
                diffAmount);
        return super.saveEntity(subContractPaymentInfo);
    }
}
