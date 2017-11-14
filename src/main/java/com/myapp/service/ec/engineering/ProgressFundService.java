package com.myapp.service.ec.engineering;

import com.myapp.core.exception.db.SaveException;
import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.entity.ec.engineering.EngineeringContractInfo;
import com.myapp.entity.ec.engineering.ProgressFundInfo;
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
public class ProgressFundService extends BaseInterfaceService<ProgressFundInfo> {
    @Resource
    private EngineeringContractService engineeringContractService;
    @Override
    public Object getEntity(String id) {
        ProgressFundInfo progressFundInfo = (ProgressFundInfo) super.getEntity(id);
        ProgressFundInfo oldProgressFundInfo = new ProgressFundInfo();
        oldProgressFundInfo.setSettleAmount(progressFundInfo.getSettleAmount());
        oldProgressFundInfo.setEngineeringContractInfo(progressFundInfo.getEngineeringContractInfo());
        progressFundInfo.setOldProgressFundInfo(oldProgressFundInfo);
        return progressFundInfo;
    }

    @Override
    public Object saveEntity(Object entity) throws SaveException {
        ProgressFundInfo progressFundInfo = (ProgressFundInfo) entity;
        BigDecimal diffAmount = BigDecimal.ZERO;
        if(StringUtils.isEmpty(progressFundInfo.getId())){
            diffAmount = progressFundInfo.getSettleAmount();
        }else{
            diffAmount = progressFundInfo.getSettleAmount().subtract(
                    progressFundInfo.getOldProgressFundInfo().getSettleAmount());
        }
        engineeringContractService.subBalanceAmount(progressFundInfo.getEngineeringContractInfo().getId(),
                diffAmount);
        return super.saveEntity(progressFundInfo);
    }
}
