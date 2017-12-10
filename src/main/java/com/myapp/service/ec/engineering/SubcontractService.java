package com.myapp.service.ec.engineering;

import com.myapp.core.exception.db.SaveException;
import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.entity.ec.engineering.SubcontractInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @path：com.myapp.service.ec.engineering
 * @description：
 * @author： ly
 * @date: 2017-11-12 23:09
 */
@Service("subcontractService")
public class SubcontractService extends BaseInterfaceService<SubcontractInfo> {
    @Override
    public Object getEntity(String id) {
        SubcontractInfo subcontractInfo = (SubcontractInfo) super.getEntity(id);
        SubcontractInfo oldEngineeringContract = new SubcontractInfo();
        oldEngineeringContract.setAmount(subcontractInfo.getAmount());
        oldEngineeringContract.setBalanceAmount(subcontractInfo.getBalanceAmount());
        subcontractInfo.setOldSubcontractInfo(oldEngineeringContract);
        return subcontractInfo;
    }

    @Override
    public Object saveEntity(Object entity) throws SaveException {
        SubcontractInfo subcontractInfo = (SubcontractInfo) entity;
        if(StringUtils.isEmpty(subcontractInfo.getId())){
            subcontractInfo.setBalanceAmount(subcontractInfo.getAmount());
        }else{
            BigDecimal diffAmount = subcontractInfo.getAmount()
                    .subtract(subcontractInfo.getOldSubcontractInfo().getAmount());
            subcontractInfo.setBalanceAmount(subcontractInfo.getOldSubcontractInfo()
                    .getBalanceAmount().add(diffAmount));
        }
        if(subcontractInfo.getBalanceAmount().compareTo(BigDecimal.ZERO)<0){
            throw new RuntimeException("合同金额,小于已结算金额");
        }
        return super.saveEntity(subcontractInfo);
    }

    /**
     * * 功能：更新余额信息
     * @param contractId 合同id
     * @param changeAmount 剩余金额
     * @throws SaveException
     */
    public void subBalanceAmount(String contractId,BigDecimal changeAmount) throws SaveException {
        SubcontractInfo subcontractInfo = getEntity(SubcontractInfo.class,contractId);
        if(subcontractInfo ==null){
            throw new RuntimeException("未找到对应的合同信息");
        }
        if((subcontractInfo.getBalanceAmount().subtract(changeAmount)).compareTo(BigDecimal.ZERO)<0){
            throw new RuntimeException("支付金额大于合同剩余金额");
        }
        subcontractInfo.setBalanceAmount(subcontractInfo.getBalanceAmount()
                .subtract(changeAmount));
        super.saveEntity(subcontractInfo);
    }
}
