package com.myapp.service.ec.settle;

import com.myapp.core.exception.db.SaveException;
import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.entity.ec.settle.MaterialSettleDetailInfo;
import com.myapp.entity.ec.settle.MaterialSettleInfo;
import com.myapp.service.ec.purchase.PurchaseContractService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @path：com.myapp.service.ec.settle
 * @description：
 * @author： ly
 * @date: 2017-11-11 15:03
 */
@Service("materialSettleService")
public class MaterialSettleService extends BaseInterfaceService<MaterialSettleInfo> {
    @Resource
    private PurchaseContractService purchaseContractService;
    @Override
    public Object getEntity(String id) {
        MaterialSettleInfo materialSettleInfo = (MaterialSettleInfo) super.getEntity(id);
        if(materialSettleInfo.getMaterialSettleDetailInfos()!=null){
            Set<MaterialSettleDetailInfo> materialSettleDetailInfos = new HashSet<>();
            Iterator iterator = materialSettleInfo.getMaterialSettleDetailInfos().iterator();
            MaterialSettleDetailInfo materialSettleDetail = null;
            MaterialSettleDetailInfo materialSettleDetailOld = null;
            while (iterator.hasNext()){
                materialSettleDetailOld = (MaterialSettleDetailInfo) iterator.next();
                materialSettleDetail = new MaterialSettleDetailInfo();
                materialSettleDetail.setSettleAmount(materialSettleDetailOld.getSettleAmount());
                materialSettleDetail.setPurchaseContractInfo(materialSettleDetailOld.getPurchaseContractInfo());
                materialSettleDetail.setId(materialSettleDetailOld.getId());
                materialSettleDetailInfos.add(materialSettleDetail);
            }
            materialSettleInfo.setOldMaterialSettleDetails(materialSettleDetailInfos);
        }
        return materialSettleInfo;
    }

    @Override
    public Object saveEntity(Object entity) throws SaveException {
        MaterialSettleInfo materialSettleInfoNew = (MaterialSettleInfo) entity;
        Set<MaterialSettleDetailInfo> materialSettleDetailInfos = materialSettleInfoNew.getOldMaterialSettleDetails();
        materialSettleInfoNew = (MaterialSettleInfo) super.saveEntity(entity);
        if(materialSettleDetailInfos==null){
            materialSettleDetailInfos = new HashSet<MaterialSettleDetailInfo>();
        }
        boolean isExist = false;
        BigDecimal diffCount = BigDecimal.ZERO;
        for(MaterialSettleDetailInfo materialSettleDetailInfo:materialSettleDetailInfos){
            isExist = false;
            for(MaterialSettleDetailInfo materialSettleDetailInfoNew:materialSettleInfoNew.getMaterialSettleDetailInfos()){
                if(materialSettleDetailInfo.getId().equals(materialSettleDetailInfoNew.getId())){
                    isExist = true;
                    diffCount = materialSettleDetailInfoNew.getSettleAmount().subtract(
                            materialSettleDetailInfo.getSettleAmount());
                    break;
                }
            }

            if(!isExist){
                diffCount = materialSettleDetailInfo.getSettleAmount().negate();
            }
            purchaseContractService.updateBalanceSettleAmountById(
                    materialSettleDetailInfo.getPurchaseContractInfo().getId(),diffCount);
        }
        for(MaterialSettleDetailInfo materialSettleDetailInfoNew:materialSettleInfoNew.getMaterialSettleDetailInfos()){
            isExist = false;
            for(MaterialSettleDetailInfo materialSettleDetailInfo:materialSettleDetailInfos){
                if(materialSettleDetailInfoNew.getId().equals(materialSettleDetailInfo.getId())){
                    isExist = true;
                    break;
                }
            }

            if(!isExist){
                purchaseContractService.updateBalanceSettleAmountById(
                        materialSettleDetailInfoNew.getPurchaseContractInfo().getId(),
                        materialSettleDetailInfoNew.getSettleAmount());
            }
        }
        return super.saveEntity(entity);
    }
}
