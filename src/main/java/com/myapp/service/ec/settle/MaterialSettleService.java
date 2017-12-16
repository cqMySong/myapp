package com.myapp.service.ec.settle;

import com.myapp.core.exception.db.QueryException;
import com.myapp.core.exception.db.SaveException;
import com.myapp.core.model.PageModel;
import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.entity.ec.settle.MaterialSettleDetailInfo;
import com.myapp.entity.ec.settle.MaterialSettleInfo;
import com.myapp.service.ec.purchase.PurchaseContractService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

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

    public PageModel queryMaterialAnalysis(Integer curPage,Integer pageSize,Map<String,Object> params)
            throws QueryException {
        String sql = "select b.fstartdate,b.fenddate,c.fMaterialId,c.fCalculationCount,d.fnumber,d.fname," +
                "d.fSpecification,f.fname as unitName,a.fActualUseCount, " +
                "(select sum(b1.fCount) from t_ec_purchase_stock a1,t_ec_purchase_stock_detail b1 " +
                "where a1.fid = b1.fprentid and b1.fmaterialId = c.fMaterialId and a1.fInStockDate>=b.fstartdate and a1.fInStockDate<=b.fenddate " +
                "group by b1.fmaterialId) as purchaseCount " +
                "from t_ec_stock_inventory_detail a,t_ec_stock_inventory b,t_ec_stock_calculation_detail c,t_base_material d,t_base_measureunit f " +
                "where a.fprentid = b.fid and a.fid = c.fStockInventoryDetailId and c.fMaterialId = d.fid and d.fUnit = f.fid " +
                "order by b.fstartdate,d.fnumber,d.fname,d.fSpecification";
        return  toPageSqlQuery(curPage,pageSize,sql,null);
    }
}
