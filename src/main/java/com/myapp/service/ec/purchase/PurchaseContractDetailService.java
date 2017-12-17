package com.myapp.service.ec.purchase;

import com.myapp.core.entity.MaterialInfo;
import com.myapp.core.enums.MaterialType;
import com.myapp.core.exception.db.QueryException;
import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.entity.ec.purchase.PurchaseContractDetailInfo;
import com.myapp.entity.ec.stock.StockInfo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @path：com.myapp.service.ec.purchase
 * @description：采购合同详细信息
 * @author ： ly
 * @date: 2017-08-28 21:15
 */
@Service("purchaseContractDetailService")
public class PurchaseContractDetailService extends BaseInterfaceService<PurchaseContractDetailInfo> {

    /**
     * 功能：根据采购合同id获取详细信息
     * @param purchaseContractId
     * @return
     */
    public List getByPurchaseContractId(String purchaseContractId){
        String hql = "select pcdi.materialType as materialType,pcdi.material.id as materialId,pcdi.materialName as materialName," +
                "pcdi.specification as specification,pcdi.measureUnitName as measureUnitName,pcdi.purchasePrice as price," +
                "pcdi.id as purchaseContractDetailId from PurchaseContractDetailInfo pcdi where pcdi.parent.id=? order by pcdi.materialName";
        List<Map<String,Object>> purchaseContractDetailList = findByHQL(hql,new String[]{purchaseContractId});
        if(purchaseContractDetailList!=null) {
            Map<String, String> pro = null;
            for (Map<String, Object> map : purchaseContractDetailList) {
                pro = new HashMap<>();
                pro.put("key",map.get("materialType").toString());
                pro.put("val",MaterialType.map.get(map.get("materialType").toString()).getName());
                map.put("materialType",pro);
                pro = new HashMap<>();
                pro.put("id",map.get("purchaseContractDetailId").toString());
                map.put("purchaseContractDetailInfo",pro);
                pro = new HashMap<>();
                pro.put("id",map.get("materialId").toString());
                map.put("materialInfo",pro);
            }
        }
        return purchaseContractDetailList;
    }
}
