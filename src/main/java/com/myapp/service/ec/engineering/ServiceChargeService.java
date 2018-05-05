package com.myapp.service.ec.engineering;

import com.myapp.core.exception.db.QueryException;
import com.myapp.core.model.PageModel;
import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.core.util.BaseUtil;
import com.myapp.entity.ec.engineering.ServiceChargeInfo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @path：com.myapp.service.ec.engineering
 * @description：
 * @author： ly
 * @date: 2018-01-08 22:24
 */
@Service("serviceChargeService")
public class ServiceChargeService extends BaseInterfaceService<ServiceChargeInfo> {

    /**
     * 功能：人工费（劳务费）结算、支付台帐
     * @param curPage
     * @param pageSize
     * @param params
     * @return
     * @throws QueryException
     */
    public PageModel queryServiceChargeLedger(Integer curPage, Integer pageSize, Map<String,Object> params)
            throws QueryException {
        List<Object> paramList = new ArrayList<>();
        StringBuffer sql = new StringBuffer();
        sql.append("select case when b.fPaymentType='INTERIM' then '进度款' else '结算' end as paymentType,")
            .append("a.fnumber as contractNumber,a.fname as contractName,a.fDirectorName as directorName,")
            .append("a.fAmount as contractAmount,b.fJobContent as jobContent,b.fSettleAmount as settleAmount,")
            .append("c.fPayAmount as payAmount,c.fPayDate as payDate,c.fContractRatio as contractRatio,c.fRemark as remark,")
            .append("(select sum(sp.fSettleAmount) from t_ec_subcontract_payment sp where sp.fProjectId = b.fProjectId ")
            .append("and sp.fSubcontracId = b.fSubcontracId and sp.fcreateDate<=b.fcreateDate and ")
            .append("sp.fPaymentType = b.fPaymentType) as totalSettleAmount,")
            .append("(select sum(sc.fPayAmount) from t_ec_service_charge sc,t_ec_subcontract_payment sp ")
            .append(" where sc.fSubContractPaymentId = sp.fid  and ")
            .append("sp.fSubcontracId = b.fSubcontracId and sp.fcreateDate<=b.fcreateDate and ")
            .append("sp.fPaymentType = b.fPaymentType) as totalPayAmount ")
            .append("from t_ec_subcontract a,t_ec_subcontract_payment b ")
            .append("left join t_ec_service_charge c on c.fSubContractPaymentId = b.fid ")
            .append("where a.fid = b.fSubcontracId and a.fProjectId = ? ");
        paramList.add(params.get("projectId"));
        if(!BaseUtil.isEmpty(params.get("startDate"))){
            sql.append("and c.fPayDate>=? ");
            paramList.add(params.get("startDate"));
        }
        if(!BaseUtil.isEmpty(params.get("endDate"))){
            sql.append("and c.fPayDate<=? ");
            paramList.add(params.get("endDate"));
        }
        if(!BaseUtil.isEmpty(params.get("contractName"))){
            sql.append("and a.fname like ? ");
            paramList.add("%"+params.get("contractName")+"%");
        }
        sql.append(" order by b.fPaymentType,a.fnumber,b.fcreateDate,c.fcreateDate");
        return toPageSqlQuery(curPage,pageSize,sql.toString(),paramList.toArray());
    }
}
