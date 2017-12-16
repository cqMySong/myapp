package com.myapp.service.ec.stock;

import com.myapp.core.exception.db.SaveException;
import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.entity.ec.stock.StockInventoryDetailInfo;
import com.myapp.entity.ec.stock.StockInventoryInfo;
import com.myapp.service.ec.purchase.PurchaseStockService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * @path：com.myapp.service.ec.stock
 * @description：
 * @author： ly
 * @date: 2017-11-11 22:41
 */
@Service("stockInventoryService")
public class StockInventoryService extends BaseInterfaceService<StockInventoryInfo> {
    @Resource
    private PurchaseStockService purchaseStockService;
    @Override
    public Object saveEntity(Object entity) throws SaveException {
        StockInventoryInfo stockInventoryInfo = (StockInventoryInfo) super.saveEntity(entity);
        Iterator<StockInventoryDetailInfo> iterator = stockInventoryInfo.getStockInventoryDetailInfos().iterator();
        StockInventoryDetailInfo stockInventoryDetailInfo = null;
        while (iterator.hasNext()){
            stockInventoryDetailInfo = iterator.next();
            stockInventoryDetailInfo.setActualUseCount(
                    getMaterialActualUseCount(stockInventoryDetailInfo.getMaterial().getId(),
                            stockInventoryInfo.getStartDate(),stockInventoryInfo.getEndDate(),
                            stockInventoryInfo.getProject().getId(),stockInventoryDetailInfo.getInventoryCount()));
        }
        return stockInventoryInfo;
    }

    /**
     * 获取最近一次物料盘存的数量
     * @param endDate
     * @param materialId
     * @param projectId
     * @return
     */
    public BigDecimal getTopInventoryCount(Date endDate,String materialId,String projectId){
        String hql = "select stockInvDetail.inventoryCount as invCount from  StockInventoryInfo stockInv,StockInventoryDetailInfo stockInvDetail " +
                "where stockInv.id = stockInvDetail.parent.id and stockInvDetail.material.id = ? and " +
                "stockInv.project.id = ? and stockInv.endDate<=? order by stockInv.endDate desc";
        List list = findByHQL(hql,new Object[]{materialId,projectId,endDate});
        if(list!=null&&list.size()!=0){
            Map<String,Object> result = (Map<String, Object>) list.get(0);
            return (BigDecimal) result.get("invCount");
        }
        return BigDecimal.ZERO;
    }

    /**
     * 功能：获取物料当期实际用量
     * @param materialId 物料id
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @param projectId 工程id
     * @param inventoryCount 盘存数量
     * @return
     */
    public BigDecimal getMaterialActualUseCount(String materialId, Date startDate,Date endDate,
                                                String projectId,BigDecimal inventoryCount){
        //获取当期的入库数量
        BigDecimal materialInStockCount = purchaseStockService.getMaterialInStockCount(
                materialId,projectId,startDate,endDate);
        //获取上一次的盘存数量
        BigDecimal lastInvCount = getTopInventoryCount(startDate,materialId,projectId);
        return  lastInvCount.add(materialInStockCount).subtract(inventoryCount);
    }
}
