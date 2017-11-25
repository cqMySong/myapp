package com.myapp.service.ec.stock;

import com.myapp.core.exception.db.SaveException;
import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.entity.ec.purchase.PurchaseStockDetailInfo;
import com.myapp.entity.ec.purchase.PurchaseStockInfo;
import com.myapp.entity.ec.stock.StockOutDetailInfo;
import com.myapp.entity.ec.stock.StockOutInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @path：com.myapp.service.ec.stock
 * @description：
 * @author： ly
 * @date: 2017-11-07 20:16
 */
@Service("stockOutService")
public class StockOutService extends BaseInterfaceService<StockOutInfo> {
    @Resource
    private StockService stockService;
    @Override
    public Object getEntity(String id) {
        StockOutInfo stockOutInfo = (StockOutInfo) super.getEntity(id);
        if(stockOutInfo.getStockOutDetailInfos()!=null){
            Set<StockOutDetailInfo> stockOutDetailInfos = new HashSet<>();
            Iterator iterator = stockOutInfo.getStockOutDetailInfos().iterator();
            StockOutDetailInfo stockOutDetailInfo = null;
            StockOutDetailInfo stockOutDetailInfoOld = null;
            while (iterator.hasNext()){
                stockOutDetailInfoOld = (StockOutDetailInfo) iterator.next();
                stockOutDetailInfo = new StockOutDetailInfo();
                stockOutDetailInfo.setCount(stockOutDetailInfoOld.getCount());
                stockOutDetailInfo.setMaterial(stockOutDetailInfoOld.getMaterial());
                stockOutDetailInfo.setMeasureUnit(stockOutDetailInfoOld.getMeasureUnit());
                stockOutDetailInfo.setId(stockOutDetailInfoOld.getId());
                stockOutDetailInfo.setStockInfo(stockOutDetailInfoOld.getStockInfo());
                stockOutDetailInfos.add(stockOutDetailInfo);
            }
            stockOutInfo.setOldStockOutDetails(stockOutDetailInfos);
        }
        return stockOutInfo;
    }

    @Override
    public Object saveEntity(Object entity) throws SaveException {
        StockOutInfo stockOutInfoNew = (StockOutInfo) entity;
        Set<StockOutDetailInfo> stockOutDetailInfos = stockOutInfoNew.getOldStockOutDetails();
        stockOutInfoNew = (StockOutInfo) super.saveEntity(entity);
        if(stockOutDetailInfos==null){
            stockOutDetailInfos = new HashSet<StockOutDetailInfo>();
        }
        boolean isExist = false;
        BigDecimal diffCount = BigDecimal.ZERO;
        for(StockOutDetailInfo stockOutDetailInfo:stockOutDetailInfos){
            isExist = false;
            for(StockOutDetailInfo stockOutDetailNew:stockOutInfoNew.getStockOutDetailInfos()){
                if(stockOutDetailInfo.getId().equals(stockOutDetailNew.getId())){
                    isExist = true;
                    diffCount = stockOutDetailInfo.getCount().subtract(stockOutDetailNew.getCount());
                    break;
                }
            }

            if(!isExist){
                diffCount = stockOutDetailInfo.getCount();
            }
            stockService.subByStock(diffCount,stockOutDetailInfo.getStockInfo());
        }
        for(StockOutDetailInfo stockOutDetailNew:stockOutInfoNew.getStockOutDetailInfos()){
            isExist = false;
            for(StockOutDetailInfo stockOutDetailInfo:stockOutDetailInfos){
                if(stockOutDetailNew.getId().equals(stockOutDetailInfo.getId())){
                    isExist = true;
                    break;
                }
            }

            if(!isExist){
                stockService.subByStock(stockOutDetailNew.getCount(),stockOutDetailNew.getStockInfo());
            }
        }
        return super.saveEntity(entity);
    }
}
