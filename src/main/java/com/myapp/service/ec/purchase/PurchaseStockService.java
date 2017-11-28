package com.myapp.service.ec.purchase;

import com.myapp.core.entity.MaterialInfo;
import com.myapp.core.exception.db.QueryException;
import com.myapp.core.exception.db.SaveException;
import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.entity.ec.purchase.PurchaseContractDetailInfo;
import com.myapp.entity.ec.purchase.PurchaseContractInfo;
import com.myapp.entity.ec.purchase.PurchaseStockDetailInfo;
import com.myapp.entity.ec.purchase.PurchaseStockInfo;
import com.myapp.entity.ec.stock.StockInfo;
import com.myapp.service.ec.stock.StockService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @path：com.myapp.service.ec.purchase
 * @description：采购入库
 * @author ： ly
 * @date: 2017-08-28 21:15
 */
@Service("purchaseStockService")
public class PurchaseStockService extends BaseInterfaceService<PurchaseStockInfo> implements ExecutionListener {
    @Resource
    private StockService stockService;
    @Resource
    private PurchaseStockDetailService purchaseStockDetailService;

    @Override
    public Object getEntity(String id) {
        PurchaseStockInfo purchaseStockInfo = (PurchaseStockInfo) super.getEntity(id);
        Set<PurchaseStockDetailInfo> purchaseStockDetailInfoSet = new HashSet<>();
        if(purchaseStockInfo.getPurchaseStockDetailInfos()!=null){
            Iterator iterator = purchaseStockInfo.getPurchaseStockDetailInfos().iterator();
            PurchaseStockDetailInfo purchaseStockDetailInfo = null;
            PurchaseStockDetailInfo purchaseStockDetailInfoOld = null;
            while (iterator.hasNext()){
                purchaseStockDetailInfoOld = (PurchaseStockDetailInfo) iterator.next();
                purchaseStockDetailInfo = new PurchaseStockDetailInfo();
                purchaseStockDetailInfo.setId(purchaseStockDetailInfoOld.getId());
                purchaseStockDetailInfo.setCount(purchaseStockDetailInfoOld.getCount());
                purchaseStockDetailInfo.setPurchaseContractDetailInfo(purchaseStockDetailInfoOld.getPurchaseContractDetailInfo());
                purchaseStockDetailInfo.setPurchaseContractInfo(purchaseStockDetailInfoOld.getPurchaseContractInfo());
                purchaseStockDetailInfo.setParent(purchaseStockDetailInfoOld.getParent());
                purchaseStockDetailInfo.setCumulativeCount(purchaseStockDetailInfoOld.getCumulativeCount());
                purchaseStockDetailInfo.setPurchaseContractDetailInfo(purchaseStockDetailInfoOld.getPurchaseContractDetailInfo());
                purchaseStockDetailInfoSet.add(purchaseStockDetailInfo);
            }
        }
        purchaseStockInfo.setOldPurchaseStockDetails(purchaseStockDetailInfoSet);
        return purchaseStockInfo;
    }

    @Override
    public Object saveEntity(Object entity) throws SaveException {
        PurchaseStockInfo purchaseStockNew = (PurchaseStockInfo)entity;
        Set<PurchaseStockDetailInfo> purchaseStockDetailInfos = purchaseStockNew.getOldPurchaseStockDetails();
        purchaseStockNew = (PurchaseStockInfo) super.saveEntity(entity);
        if(purchaseStockDetailInfos==null){
            purchaseStockDetailInfos = new HashSet<PurchaseStockDetailInfo>();
        }
        boolean isExist = false;
        BigDecimal diffCount = BigDecimal.ZERO;
        for(PurchaseStockDetailInfo purchaseStockDetailOld:purchaseStockDetailInfos){
            isExist = false;
            for(PurchaseStockDetailInfo purchaseStockDetailNew:purchaseStockNew.getPurchaseStockDetailInfos()){
                if(purchaseStockDetailOld.getId().equals(purchaseStockDetailNew.getId())){
                    isExist = true;
                    diffCount = purchaseStockDetailNew.getCount().subtract(purchaseStockDetailOld.getCount());
                    stockService.saveByMaterialIdAndInStock(purchaseStockNew.getProject(),diffCount,
                            purchaseStockDetailOld.getPurchaseContractDetailInfo().getMaterial(),
                            purchaseStockDetailOld.getPurchaseContractDetailInfo(),purchaseStockDetailOld);
                    break;
                }
            }

            if(!isExist){
                diffCount = purchaseStockDetailOld.getCumulativeCount().negate();
                stockService.deleteByInStock(purchaseStockDetailOld);
            }
            try {
                purchaseStockDetailService.updateCumulativeCount(diffCount,
                        purchaseStockDetailOld.getPurchaseContractDetailInfo(),purchaseStockDetailOld.getSno());
            } catch (QueryException e) {
                throw new SaveException(e);
            }
        }
        for(PurchaseStockDetailInfo purchaseStockDetailNew:purchaseStockNew.getPurchaseStockDetailInfos()){
            isExist = false;
            for(PurchaseStockDetailInfo purchaseStockDetailOld:purchaseStockDetailInfos){
                if(purchaseStockDetailOld.getId().equals(purchaseStockDetailNew.getId())){
                    isExist = true;
                    break;
                }
            }

            if(!isExist){
                stockService.saveByMaterialIdAndInStock(purchaseStockNew.getProject(),purchaseStockDetailNew.getCount(),
                        purchaseStockDetailNew.getPurchaseContractDetailInfo().getMaterial(),
                        purchaseStockDetailNew.getPurchaseContractDetailInfo(),purchaseStockDetailNew);
                try {
                    PurchaseStockDetailInfo purchaseStockDetailInfo = purchaseStockDetailService
                            .queryLastPurchaseStock(purchaseStockDetailNew.getPurchaseContractDetailInfo());
                    purchaseStockDetailNew.setSno(System.currentTimeMillis());
                    purchaseStockDetailNew.setCumulativeCount(purchaseStockDetailNew.getCount()
                            .add(purchaseStockDetailInfo.getCumulativeCount()==null?
                                    BigDecimal.ZERO:purchaseStockDetailInfo.getCumulativeCount()));
                } catch (QueryException e) {
                    throw new SaveException(e);
                }
            }
        }
        return purchaseStockNew;
    }

    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {

    }


}
