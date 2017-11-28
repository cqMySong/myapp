package com.myapp.service.ec.purchase;

import com.myapp.core.exception.db.QueryException;
import com.myapp.core.exception.db.SaveException;
import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.entity.ec.purchase.ApplyMaterialDetailInfo;
import com.myapp.entity.ec.purchase.ApplyMaterialInfo;
import com.myapp.entity.ec.purchase.PurchaseStockDetailInfo;
import com.myapp.entity.ec.purchase.PurchaseStockInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @path：com.myapp.service.ec.purchase
 * @description：材料申购业务类
 * @author： ly
 * @date: 2017-10-29 21:48
 */
@Service("applyMaterialService")
public class ApplyMaterialService extends BaseInterfaceService<ApplyMaterialInfo> {
    @Resource
    private ApplyMaterialDetailService applyMaterialDetailService;
    @Override
    public Object getEntity(String id) {
        ApplyMaterialInfo applyMaterialInfo = (ApplyMaterialInfo) super.getEntity(id);
        Set<ApplyMaterialDetailInfo> applyMaterialDetailSet = new HashSet<>();
        if(applyMaterialInfo.getApplyMaterialDetailInfos()!=null){
            Iterator iterator = applyMaterialInfo.getApplyMaterialDetailInfos().iterator();
            ApplyMaterialDetailInfo applyMaterialDetail = null;
            ApplyMaterialDetailInfo applyMaterialDetailOld = null;
            while (iterator.hasNext()){
                applyMaterialDetailOld = (ApplyMaterialDetailInfo) iterator.next();
                applyMaterialDetail = new ApplyMaterialDetailInfo();
                applyMaterialDetail.setId(applyMaterialDetailOld.getId());
                applyMaterialDetail.setPurchaseNum(applyMaterialDetailOld.getPurchaseNum());
                applyMaterialDetail.setSno(applyMaterialDetailOld.getSno());
                applyMaterialDetail.setBudgetingDetailInfo(applyMaterialDetailOld.getBudgetingDetailInfo());
                applyMaterialDetailSet.add(applyMaterialDetail);
            }
        }
        applyMaterialInfo.setOldApplyMaterialDetail(applyMaterialDetailSet);
        return applyMaterialInfo;
    }

    @Override
    public Object saveEntity(Object entity) throws SaveException {
        ApplyMaterialInfo applyMaterialInfo = (ApplyMaterialInfo)entity;
        Set<ApplyMaterialDetailInfo> applyMaterialDetailInfos = applyMaterialInfo.getOldApplyMaterialDetail();
        applyMaterialInfo = (ApplyMaterialInfo) super.saveEntity(entity);
        if(applyMaterialDetailInfos==null){
            applyMaterialDetailInfos = new HashSet<ApplyMaterialDetailInfo>();
        }
        boolean isExist = false;
        BigDecimal diffCount = BigDecimal.ZERO;
        for(ApplyMaterialDetailInfo applyMaterialDetailOld:applyMaterialDetailInfos){
            isExist = false;
            for(ApplyMaterialDetailInfo applyMaterialDetailNew:applyMaterialInfo.getApplyMaterialDetailInfos()){
                if(applyMaterialDetailOld.getId().equals(applyMaterialDetailNew.getId())){
                    isExist = true;
                    diffCount = applyMaterialDetailOld.getPurchaseNum().subtract(applyMaterialDetailNew.getPurchaseNum());
                    applyMaterialDetailNew.setCumulativePurchaseNum(
                            applyMaterialDetailNew.getCumulativePurchaseNum().subtract(diffCount));
                    break;
                }
            }

            if(!isExist){
                diffCount = applyMaterialDetailOld.getPurchaseNum();
            }
            try {
                applyMaterialDetailService.updateCumulativePurchaseNum(diffCount,
                        applyMaterialDetailOld.getBudgetingDetailInfo(),applyMaterialDetailOld.getSno());
            } catch (QueryException e) {
                throw new SaveException(e);
            }
        }
        for(ApplyMaterialDetailInfo applyMaterialDetailNew:applyMaterialInfo.getApplyMaterialDetailInfos()){
            isExist = false;
            for(ApplyMaterialDetailInfo applyMaterialDetailOld:applyMaterialDetailInfos){
                if(applyMaterialDetailOld.getId().equals(applyMaterialDetailNew.getId())){
                    isExist = true;
                    break;
                }
            }

            if(!isExist){
                try {
                    ApplyMaterialDetailInfo applyMaterialDetailInfo = applyMaterialDetailService
                            .queryLastApplyMaterial(applyMaterialDetailNew.getBudgetingDetailInfo());
                    applyMaterialDetailNew.setCumulativePurchaseNum(applyMaterialDetailNew
                        .getPurchaseNum().add(applyMaterialDetailInfo.getCumulativePurchaseNum()==null?
                                    BigDecimal.ZERO:applyMaterialDetailInfo.getCumulativePurchaseNum()));
                    applyMaterialDetailNew.setSno(System.currentTimeMillis());
                } catch (QueryException e) {
                    throw new SaveException(e);
                }
            }
        }
        return applyMaterialInfo;
    }
}
