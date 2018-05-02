package com.myapp.service.ec.engineering;

import com.myapp.core.exception.db.QueryException;
import com.myapp.core.exception.db.SaveException;
import com.myapp.core.model.PageModel;
import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.core.util.BaseUtil;
import com.myapp.entity.ec.engineering.SubContractPaymentInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @path：com.myapp.service.ec.engineering
 * @description：
 * @author： ly
 * @date: 2017-11-13 22:46
 */
@Service("subContractPaymentService")
public class SubContractPaymentService extends BaseInterfaceService<SubContractPaymentInfo> {
    @Resource
    private SubcontractService subcontractService;
    @Override
    public Object getEntity(String id) {
        SubContractPaymentInfo subContractPaymentInfo = (SubContractPaymentInfo) super.getEntity(id);
        SubContractPaymentInfo oldSubContractPaymentInfo = new SubContractPaymentInfo();
        oldSubContractPaymentInfo.setSettleAmount(subContractPaymentInfo.getSettleAmount());
        oldSubContractPaymentInfo.setSubcontractInfo(subContractPaymentInfo.getSubcontractInfo());
        subContractPaymentInfo.setOldSubContractPaymentInfo(oldSubContractPaymentInfo);
        return subContractPaymentInfo;
    }

    @Override
    public Object saveEntity(Object entity) throws SaveException {
        SubContractPaymentInfo subContractPaymentInfo = (SubContractPaymentInfo) entity;
        BigDecimal diffAmount = BigDecimal.ZERO;
        if(StringUtils.isEmpty(subContractPaymentInfo.getId())){
            diffAmount = subContractPaymentInfo.getSettleAmount();
        }else{
            diffAmount = subContractPaymentInfo.getSettleAmount().subtract(
                    subContractPaymentInfo.getOldSubContractPaymentInfo().getSettleAmount());
        }
        subcontractService.subBalanceAmount(subContractPaymentInfo.getSubcontractInfo().getId(),
                diffAmount);
        return super.saveEntity(subContractPaymentInfo);
    }

    /**
     * 功能：获取分包结算台帐信息
     * @param curPage
     * @param pageSize
     * @param params
     * @return
     * @throws QueryException
     */
    public PageModel querySubContractPaymentLedger(Integer curPage, Integer pageSize, Map<String,Object> params)
            throws QueryException {
        List<Object> paramList = new ArrayList<>();
        StringBuffer sql = new StringBuffer();
        sql.append("select b.fnumber,b.fname,c.fname as unitName,b.fAmount,a.fid,a.fRemark,")
                .append("a.fJobContent,a.fStartDate,a.fEndDate,a.fSettleAmount,a.fOperator,")
                .append("case when b.fSubcontractExpenseType='ARTIFICIAL' then '人工费' else '其他' end expenseType,")
                .append("case when a.fPaymentType='INTERIM' then '进度' else '结算' end paymentType,")
                .append("(select count(t.fid) from t_base_attachFile t where t.fsourceBillId = a.fid) as attach ")
                .append("from t_ec_subcontract_payment a,t_ec_subcontract b,t_ec_ecunit c ")
                .append("where a.fSubcontracId = b.fid and b.fEcUnitId = c.fid and a.fProjectId=? ");
        paramList.add(params.get("projectId"));
        if(!BaseUtil.isEmpty(params.get("startDate"))){
            sql.append("and a.fStartDate>=? ");
            paramList.add(params.get("startDate"));
        }
        if(!BaseUtil.isEmpty(params.get("endDate"))){
            sql.append("and a.fEndDate>=? ");
            paramList.add(params.get("endDate"));
        }
        if(!BaseUtil.isEmpty(params.get("contractName"))){
            sql.append("and c.fname like ? ");
            paramList.add("%"+params.get("contractName")+"%");
        }
        sql.append(" union all ")
                .append("select b.fnumber,b.fname,b.fSupplyCompany as unitName,b.fAmount,a.fid,a.fRemark,")
                .append("a.fJobContent,a.fStartDate,a.fEndDate,a.fSettleAmount,c.fname as fOperator,")
                .append("case when b.fExpenseType='MATERIAL' then '材料费' when b.fExpenseType='EQUIPMENT' then '机械费' else '其他' end expenseType,")
                .append("'结算' as paymentType,")
                .append("(select count(t.fid) from t_base_attachFile t where t.fsourceBillId = a.fid) as attach ")
                .append("from t_ec_material_settle a,t_ec_purchase_contract b,t_pm_user c ")
                .append("where a.fPurchaseContractId = b.fid and c.fid = a.fOperatorId and a.fProjectId=?");
        paramList.add(params.get("projectId"));
        if(!BaseUtil.isEmpty(params.get("startDate"))){
            sql.append("and a.fStartDate>=? ");
            paramList.add(params.get("startDate"));
        }
        if(!BaseUtil.isEmpty(params.get("endDate"))){
            sql.append("and a.fEndDate>=? ");
            paramList.add(params.get("endDate"));
        }
        if(!BaseUtil.isEmpty(params.get("contractName"))){
            sql.append("and b.fSupplyCompany like ? ");
            paramList.add("%"+params.get("contractName")+"%");
        }
        return toPageSqlQuery(curPage,pageSize,sql.toString(),paramList.toArray());
    }
}
