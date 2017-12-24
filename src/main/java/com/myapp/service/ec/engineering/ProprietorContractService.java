package com.myapp.service.ec.engineering;

import com.myapp.core.enums.ProprietorContractType;
import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.entity.ec.engineering.ProprietorContractInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @path：com.myapp.service.ec.engineering
 * @description：
 * @author： ly
 * @date: 2017-11-12 23:09
 */
@Service("proprietorContractService")
public class ProprietorContractService extends BaseInterfaceService<ProprietorContractInfo> {
    /**
     * 功能：根据工程id和类型获工程合同信息
     * @param projectId
     * @param type
     * @return
     */
    public List queryByProjectAndType(String projectId,ProprietorContractType type){
        String hql = "select pci.rangeValuation as rangeValuation,pci.amount as amount," +
                "pci.name as name,pci.id,pci.basisValuation as basisValuation " +
                "from ProprietorContractInfo pci where pci.project.id = ? " +
                "and pci.proprietorContractType=? order by pci.name";
        return findByHQL(hql,new Object[]{projectId, type});
    }
}
