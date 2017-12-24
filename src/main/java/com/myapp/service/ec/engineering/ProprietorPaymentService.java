package com.myapp.service.ec.engineering;

import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.entity.ec.engineering.ProprietorPaymentInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @path：com.myapp.service.ec.engineering
 * @description：
 * @author： ly
 * @date: 2017-12-10 22:45
 */
@Service("proprietorPaymentService")
public class ProprietorPaymentService extends BaseInterfaceService<ProprietorPaymentInfo> {

    /**
     * 功能：根据工程id获取工程合同付款信息
     * @param projectId
     * @return
     */
    public List queryByProject(String projectId){
        String hql = "select pyi.deliveryAmount as deliveryAmount,pyi.deliveryDate as deliveryDate," +
                "pyi.name as name,pyi.id,pyi.approvedAmount as approvedAmount,pyi.paymentAmount as paymentAmount," +
                "pyi.actualRatio as actualRatio,pyi.paymentRatio as paymentRatio " +
                "from ProprietorPaymentInfo pyi where pyi.project.id = ? " ;
        return findByHQL(hql,new Object[]{projectId});
    }
}
