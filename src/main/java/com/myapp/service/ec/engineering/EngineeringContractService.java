package com.myapp.service.ec.engineering;

import com.myapp.core.exception.db.SaveException;
import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.entity.ec.engineering.EngineeringContractInfo;
import com.myapp.entity.ec.stock.StockRevertInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @path：com.myapp.service.ec.engineering
 * @description：
 * @author： ly
 * @date: 2017-11-12 23:09
 */
@Service("engineeringContractService")
public class EngineeringContractService  extends BaseInterfaceService<EngineeringContractInfo> {
    @Override
    public Object getEntity(String id) {
        EngineeringContractInfo engineeringContractInfo = (EngineeringContractInfo) super.getEntity(id);
        EngineeringContractInfo oldEngineeringContract = new EngineeringContractInfo();
        oldEngineeringContract.setAmount(engineeringContractInfo.getAmount());
        oldEngineeringContract.setBalanceAmount(engineeringContractInfo.getBalanceAmount());
        engineeringContractInfo.setOldEngineeringContractInfo(oldEngineeringContract);
        return engineeringContractInfo;
    }

    @Override
    public Object saveEntity(Object entity) throws SaveException {
        EngineeringContractInfo engineeringContractInfo = (EngineeringContractInfo) entity;
        if(StringUtils.isEmpty(engineeringContractInfo.getId())){
            engineeringContractInfo.setBalanceAmount(engineeringContractInfo.getAmount());
        }else{
            BigDecimal diffAmount = engineeringContractInfo.getAmount()
                    .subtract(engineeringContractInfo.getOldEngineeringContractInfo().getAmount());
            engineeringContractInfo.setBalanceAmount(engineeringContractInfo.getOldEngineeringContractInfo()
                    .getBalanceAmount().add(diffAmount));
        }
        if(engineeringContractInfo.getBalanceAmount().compareTo(BigDecimal.ZERO)<0){
            throw new RuntimeException("合同金额,小于已结算金额");
        }
        return super.saveEntity(engineeringContractInfo);
    }

    /**
     * * 功能：更新余额信息
     * @param contractId 合同id
     * @param changeAmount 剩余金额
     * @throws SaveException
     */
    public void subBalanceAmount(String contractId,BigDecimal changeAmount) throws SaveException {
        EngineeringContractInfo engineeringContractInfo = getEntity(EngineeringContractInfo.class,contractId);
        if(engineeringContractInfo==null){
            throw new RuntimeException("未找到对应的合同信息");
        }
        if((engineeringContractInfo.getBalanceAmount().subtract(changeAmount)).compareTo(BigDecimal.ZERO)<0){
            throw new RuntimeException("支付金额大于合同剩余金额");
        }
        engineeringContractInfo.setBalanceAmount(engineeringContractInfo.getBalanceAmount()
                .subtract(changeAmount));
        super.saveEntity(engineeringContractInfo);
    }
}
