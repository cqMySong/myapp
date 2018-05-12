package com.myapp.service.ec.purchase;

import com.myapp.core.enums.BillState;
import com.myapp.core.exception.db.QueryException;
import com.myapp.core.exception.db.SaveException;
import com.myapp.core.model.PageModel;
import com.myapp.core.service.act.ActTaskService;
import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.util.EnumUtil;
import com.myapp.core.util.WebUtil;
import com.myapp.entity.ec.purchase.ApplyMaterialDetailInfo;
import com.myapp.entity.ec.purchase.ApplyMaterialInfo;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.impl.el.FixedValue;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @path：com.myapp.service.ec.purchase
 * @description：材料申购业务类
 * @author： ly
 * @date: 2017-10-29 21:48
 */
@Service("applyMaterialService")
public class ApplyMaterialService extends BaseInterfaceService<ApplyMaterialInfo> implements ExecutionListener {
    @Resource
    private ApplyMaterialDetailService applyMaterialDetailService;

    @Resource
    private ActTaskService actTaskService;

    /**
     * 审批事项
     */
    private FixedValue auditState;
    @Override
    public Object submitEntity(Object entity) throws SaveException {
        Object returnObj = super.submitEntity(entity);
        ApplyMaterialInfo applyMaterialInfo = (ApplyMaterialInfo) returnObj;
        applyMaterialInfo.setBizDate(new Date());
        String processInstanceId = actTaskService.startProcess(ApplyMaterialInfo.class,applyMaterialInfo.getId(),
                applyMaterialInfo.getName(),null,applyMaterialInfo.getCreateUser().getNumber());
        applyMaterialInfo.setProcessInstanceId(processInstanceId);
        applyMaterialInfo.setAuditState(BillState.AUDITING);
        return returnObj;
    }

    @Override
    public Object saveEntity(Object entity) throws SaveException {
        ApplyMaterialInfo applyMaterialInfo = (ApplyMaterialInfo) entity;
        if(applyMaterialInfo.getApplyMaterialDetailInfos()!=null){
            Iterator<ApplyMaterialDetailInfo> detailInfoIterator = applyMaterialInfo.getApplyMaterialDetailInfos().iterator();
            while (detailInfoIterator.hasNext()){
                ApplyMaterialDetailInfo applyMaterialDetailInfo = detailInfoIterator.next();
                if(StringUtils.isEmpty(applyMaterialDetailInfo.getId())){
                    applyMaterialDetailInfo.setProcessNo(1);
                    applyMaterialDetailInfo.setStatus("0");
                }
            }
        }
        return super.saveEntity(entity);
    }

    /**
     * 功能：查询物料的当前库存数量和累计申购数量
     * @param projectId
     * @param materialIds
     * @return
     */
    public Map queryStockAndTotalApplyCount(String projectId,String materialIds) throws QueryException {
        String[] materialIdArr = materialIds.split(",");
        StringBuffer materialIdStr = new StringBuffer();
        projectId = WebUtil.UUID_ReplaceID(projectId);
        for(String wbsId:materialIdArr){
            materialIdStr.append("'").append(wbsId).append("',");
        }
        String sql = "select b.fMaterialId as materialId,sum(b.stockCount) as stockCount,sum(b.purchaseNum) as purchaseNum from ( " +
                "select a.fMaterialId,a.fCount as stockCount,0 as purchaseNum from t_ec_stock a  " +
                "where a.fMaterialId in ("+materialIdStr.toString().substring(0,materialIdStr.toString().length()-1)+")" +
                "and a.fProjectId= ? " +
                "union all " +
                "select b.fMaterialId,0 as stockCount,sum(b.fPurchaseNum) as purchaseNum from t_ec_apply_material a,t_ec_apply_material_detail b " +
                "where a.fid = b.fprentid and b.fMaterialId in ("+materialIdStr.toString().substring(0,materialIdStr.toString().length()-1)+") " +
                "and a.fProjectId = ? and a.fcreateDate<= sysdate() " +
                "group by b.fMaterialId " +
                ") b group by b.fMaterialId";
        List<Map> result = executeSQLQuery(sql,new Object[]{projectId,projectId});
        Map<String,Map> resultMap = new HashMap<>();
        for(Map map:result){
            resultMap.put(map.get("materialId").toString(),map);
        }
        return resultMap;
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

    /**
     * 审核结束返回信息
     * @param delegateExecution
     * @throws Exception
     */
    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {
        String businessKey =  delegateExecution.getProcessBusinessKey();
        ApplyMaterialInfo applyMaterialInfo = loadEntity(businessKey);
        applyMaterialInfo.setAuditState(EnumUtil.getEnum(BillState.class.getName(),auditState.getExpressionText()));
        saveEntity(applyMaterialInfo);
    }

    public FixedValue getAuditState() {
        return auditState;
    }

    public void setAuditState(FixedValue auditState) {
        this.auditState = auditState;
    }
}
