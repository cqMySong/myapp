package com.myapp.service.ec.stock;

import com.myapp.core.entity.MaterialInfo;
import com.myapp.core.exception.db.QueryException;
import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.entity.ec.stock.StockInfo;
import com.myapp.entity.ec.stock.StockInventoryDetailInfo;
import com.myapp.entity.ec.stock.StockInventoryInfo;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @path：com.myapp.service.ec.stock
 * @description：
 * @author： ly
 * @date: 2017-11-11 22:41
 */
@Service("stockInventoryDetailService")
public class StockInventoryDetailService extends BaseInterfaceService<StockInventoryDetailInfo> {
    /**
     * 功能：根据盘存id查询明细新
     * @param inventoryId
     * @return
     */
    public List queryByInventoryId(String inventoryId) {
        String hql = "select detail.id as id,detail.stockInfo as stockInfo,detail.stockCount as stockCount," +
                "detail.material as material,detail.inventoryCount as inventoryCount " +
                "from StockInventoryDetailInfo detail where detail.parent.id=?";
        List<Map<String,Object>> inventoryList = findByHQL(hql,new String[]{inventoryId});
        List<Map<String,Object>> result = new ArrayList<>();
        if(inventoryList!=null){
            Map<String,Object> inventory = null;
            Map<String,String> pro = null;
            StockInfo stockInfo = null;
            MaterialInfo materialInfo = null;
            for (Map<String,Object> map:inventoryList){
                inventory = new HashMap<>();
                stockInfo = (StockInfo) map.get("stockInfo");
                materialInfo = (MaterialInfo) map.get("material");
                pro = new HashMap<>();
                pro.put("key",stockInfo.getMaterialType().getValue());
                pro.put("val",stockInfo.getMaterialType().getName());
                inventory.put("materialType",pro);
                inventory.put("number",materialInfo.getNumber());
                pro = new HashMap<>();
                pro.put("id",materialInfo.getId());
                pro.put("name",materialInfo.getName());
                inventory.put("material",pro);
                inventory.put("specification",stockInfo.getSpecification());
                inventory.put("measureUnit",stockInfo.getMeasureUnit());
                inventory.put("stockCount",map.get("stockCount"));
                inventory.put("inventoryCount",map.get("inventoryCount"));
                pro = new HashMap<>();
                pro.put("id",map.get("id").toString());
                inventory.put("stockInventoryDetailInfo",pro);
                result.add(inventory);
            }
        }
        return result;
    }
}
