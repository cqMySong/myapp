package com.myapp.service.ec.purchase;

import com.myapp.core.exception.db.QueryException;
import com.myapp.core.exception.db.SaveException;
import com.myapp.core.model.PageModel;
import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.core.util.BaseUtil;
import com.myapp.entity.ec.purchase.ApplyMaterialDetailInfo;
import com.myapp.entity.ec.purchase.ApplyMaterialInfo;
import com.myapp.entity.ec.purchase.PurchaseStockDetailInfo;
import com.myapp.entity.ec.purchase.PurchaseStockInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

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

    /**
     * 功能：查询申购供应台帐
     * @param curPage
     * @param pageSize
     * @param params
     * @return
     */
    public PageModel queryMaterialApplyLedger(Integer curPage,Integer pageSize,Map<String,Object> params)
        throws QueryException {
            List<Object> paramList = new ArrayList<>();
            StringBuffer sql = new StringBuffer();
            sql.append("select e.*,b.fnumber as applyNumber,a.fArrivalTime,a.fPurchaseNum,a.fMaterialId,c.fCount,");
            sql.append("(select sum(a1.fPurchaseNum) from t_ec_apply_material  b1,")
            .append("t_ec_apply_material_detail a1 where a1.fprentid = b1.fid and b1.fprojectid=b.fProjectId and ")
            .append("a1.fmaterialId = a.fmaterialId and a1.fArrivalTime<=a.fArrivalTime ");
            if(!BaseUtil.isEmpty(params.get("startDate"))){
                sql.append("and a1.fArrivalTime>=? ");
                paramList.add(params.get("startDate"));
            }
            sql.append(") as totalPurchaseNum,");
            sql.append("(select sum(a1.fCount) from t_ec_purchase_stock  b1,")
            .append("t_ec_purchase_stock_detail a1 where a1.fprentid = b1.fid and b1.fprojectid=b.fProjectId and ")
            .append("a1.fApplyMaterialDetailId = a.fid and b1.fInStockDate<=d.fInStockDate) as totalCount,")
            .append("d.fInStockDate from t_ec_apply_material  b,")
            .append("(select b.fnumber as materialNumber,a.fMaterialName,a.fSpecification,c.fname as unitName,")
            .append("sum(a.fQuantity) as fQuantity,b.fid as materialId from t_ec_budgeting_detail a,t_base_material b,")
            .append("t_base_measureunit c,t_ec_budgeting d where a.fmaterialId = b.fid and a.fMeasureUnitId = c.fid and ")
            .append("d.fid = a.fprentid and d.fprojectId=? ");
            paramList.add(params.get("projectId"));
            if(!BaseUtil.isEmpty(params.get("materialName"))){
                sql.append("and a.fMaterialName like ? ");
                paramList.add("%"+params.get("materialName")+"%");
            }
            sql.append("group by b.fid,a.fMaterialName,a.fSpecification) e,t_ec_apply_material_detail a ")
            .append("left join t_ec_purchase_stock_detail c on c.fApplyMaterialDetailId = a.fid ")
            .append("left join t_ec_purchase_stock d on c.fprentid = d.fid ")
            .append("where a.fprentid = b.fid and e.materialId = a.fMaterialId and b.fProjectId=? ");
            paramList.add(params.get("projectId"));
            if(!BaseUtil.isEmpty(params.get("startDate"))){
                sql.append("and a.fArrivalTime>=? ");
                paramList.add(params.get("startDate"));
            }
            if(!BaseUtil.isEmpty(params.get("endDate"))){
                sql.append("and a.fArrivalTime<=? ");
                paramList.add(params.get("endDate"));
            }

            sql.append("order by e.fMaterialName,e.fSpecification,a.fArrivalTime,d.fInStockDate");
        return  toPageSqlQuery(curPage,pageSize,sql.toString(),paramList.toArray());
    }
}
