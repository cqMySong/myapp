package com.myapp.service.ec.stock;

import com.myapp.core.exception.db.SaveException;
import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.entity.ec.stock.StockRevertDetailInfo;
import com.myapp.entity.ec.stock.StockRevertInfo;
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
 * @date: 2017-11-08 21:56
 */
@Service("stockRevertService")
public class StockRevertService extends BaseInterfaceService<StockRevertInfo> {
    @Resource
    private StockService stockService;
    @Override
    public Object getEntity(String id) {
        StockRevertInfo stockRevertInfo = (StockRevertInfo) super.getEntity(id);
        if(stockRevertInfo.getStockRevertDetailInfos()!=null){
            Set<StockRevertDetailInfo> stockRevertDetails = new HashSet<>();
            Iterator iterator = stockRevertInfo.getStockRevertDetailInfos().iterator();
            StockRevertDetailInfo stockRevertDetail = null;
            StockRevertDetailInfo stockRevertDetailInfoOld = null;
            while (iterator.hasNext()){
                stockRevertDetailInfoOld = (StockRevertDetailInfo) iterator.next();
                stockRevertDetail = new StockRevertDetailInfo();
                stockRevertDetail.setCount(stockRevertDetailInfoOld.getCount());
                stockRevertDetail.setMaterial(stockRevertDetailInfoOld.getMaterial());
                stockRevertDetail.setMeasureUnit(stockRevertDetailInfoOld.getMeasureUnit());
                stockRevertDetail.setId(stockRevertDetailInfoOld.getId());
                stockRevertDetails.add(stockRevertDetail);
            }
            stockRevertInfo.setOldStockRevertDetails(stockRevertDetails);
        }
        return stockRevertInfo;
    }

    @Override
    public Object saveEntity(Object entity) throws SaveException {
        StockRevertInfo stockRevertInfoNew = (StockRevertInfo) entity;
        Set<StockRevertDetailInfo> stockRevertDetailInfos = stockRevertInfoNew.getOldStockRevertDetails();
        stockRevertInfoNew = (StockRevertInfo) super.saveEntity(entity);
        if(stockRevertDetailInfos==null){
            stockRevertDetailInfos = new HashSet<StockRevertDetailInfo>();
        }
        boolean isExist = false;
        BigDecimal diffCount = BigDecimal.ZERO;
        for(StockRevertDetailInfo stockRevertDetailInfo:stockRevertDetailInfos){
            isExist = false;
            for(StockRevertDetailInfo stockRevertDetailNew:stockRevertInfoNew.getStockRevertDetailInfos()){
                if(stockRevertDetailInfo.getId().equals(stockRevertDetailNew.getId())){
                    isExist = true;
                    diffCount = stockRevertDetailNew.getCount().subtract(stockRevertDetailInfo.getCount());
                    break;
                }
            }

            if(!isExist){
                diffCount = stockRevertDetailInfo.getCount();
            }
            stockService.saveStockByMaterialId(stockRevertInfoNew.getProject(),diffCount,
                    stockRevertDetailInfo.getMaterial(),null);
        }
        for(StockRevertDetailInfo stockRevertDetailNew:stockRevertInfoNew.getStockRevertDetailInfos()){
            isExist = false;
            for(StockRevertDetailInfo stockRevertDetailInfo:stockRevertDetailInfos){
                if(stockRevertDetailNew.getId().equals(stockRevertDetailInfo.getId())){
                    isExist = true;
                    break;
                }
            }

            if(!isExist){
                stockService.saveStockByMaterialId(stockRevertInfoNew.getProject(),stockRevertDetailNew.getCount(),
                        stockRevertDetailNew.getMaterial(),null);
            }
        }
        return super.saveEntity(entity);
    }
}
