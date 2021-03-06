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
import java.util.*;

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
                purchaseStockDetailInfo.setMaterial(purchaseStockDetailInfoOld.getMaterial());
                purchaseStockDetailInfo.setSpecification(purchaseStockDetailInfoOld.getSpecification());
                purchaseStockDetailInfo.setParent(purchaseStockDetailInfoOld.getParent());
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
                    break;
                }
            }

            if(!isExist){
                diffCount = purchaseStockDetailOld.getCount().negate();
            }
            stockService.saveByMaterialInfo(purchaseStockNew.getProject(),diffCount,purchaseStockDetailOld.getMaterial());
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
                stockService.saveByMaterialInfo(purchaseStockNew.getProject(),purchaseStockDetailNew.getCount(),
                        purchaseStockDetailNew.getMaterial());
            }
        }
        return purchaseStockNew;
    }

    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {

    }

    /**
     * 获取物料时间段的入库数量
     * @param materialId
     * @param projectId
     * @param start
     * @param end
     * @return
     */
    public BigDecimal getMaterialInStockCount(String materialId, String projectId, Date start, Date end){
        String hql = "select sum(inStockDetail.count) from PurchaseStockInfo inStock,PurchaseStockDetailInfo inStockDetail " +
                "where inStock.id = inStockDetail.parent.id and inStock.project.id = ? and " +
                "inStockDetail.material.id = ? and  inStock.inStockDate between ? and ?";
        BigDecimal inStockCount = queryEntity(hql,new Object[]{projectId,materialId,start,end});
        return inStockCount==null?BigDecimal.ZERO:inStockCount;
    }

}
