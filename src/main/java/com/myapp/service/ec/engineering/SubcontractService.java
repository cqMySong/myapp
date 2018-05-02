package com.myapp.service.ec.engineering;

import com.myapp.core.exception.db.QueryException;
import com.myapp.core.exception.db.SaveException;
import com.myapp.core.model.PageModel;
import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.core.util.BaseUtil;
import com.myapp.entity.ec.engineering.SubcontractInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @path：com.myapp.service.ec.engineering
 * @description：
 * @author： ly
 * @date: 2017-11-12 23:09
 */
@Service("subcontractService")
public class SubcontractService extends BaseInterfaceService<SubcontractInfo> {
    @Override
    public Object getEntity(String id) {
        SubcontractInfo subcontractInfo = (SubcontractInfo) super.getEntity(id);
        SubcontractInfo oldEngineeringContract = new SubcontractInfo();
        oldEngineeringContract.setAmount(subcontractInfo.getAmount());
        oldEngineeringContract.setBalanceAmount(subcontractInfo.getBalanceAmount());
        subcontractInfo.setOldSubcontractInfo(oldEngineeringContract);
        return subcontractInfo;
    }

    @Override
    public Object saveEntity(Object entity) throws SaveException {
        SubcontractInfo subcontractInfo = (SubcontractInfo) entity;
        if(StringUtils.isEmpty(subcontractInfo.getId())){
            subcontractInfo.setBalanceAmount(subcontractInfo.getAmount());
        }else{
            BigDecimal diffAmount = subcontractInfo.getAmount()
                    .subtract(subcontractInfo.getOldSubcontractInfo().getAmount());
            subcontractInfo.setBalanceAmount(subcontractInfo.getOldSubcontractInfo()
                    .getBalanceAmount().add(diffAmount));
        }
        if(subcontractInfo.getBalanceAmount().compareTo(BigDecimal.ZERO)<0){
            throw new RuntimeException("合同金额,小于已结算金额");
        }
        return super.saveEntity(subcontractInfo);
    }

    /**
     * * 功能：更新余额信息
     * @param contractId 合同id
     * @param changeAmount 剩余金额
     * @throws SaveException
     */
    public void subBalanceAmount(String contractId,BigDecimal changeAmount) throws SaveException {
        SubcontractInfo subcontractInfo = getEntity(SubcontractInfo.class,contractId);
        if(subcontractInfo ==null){
            throw new RuntimeException("未找到对应的合同信息");
        }
        if((subcontractInfo.getBalanceAmount().subtract(changeAmount)).compareTo(BigDecimal.ZERO)<0){
            throw new RuntimeException("支付金额大于合同剩余金额");
        }
        subcontractInfo.setBalanceAmount(subcontractInfo.getBalanceAmount()
                .subtract(changeAmount));
        super.saveEntity(subcontractInfo);
    }


    /**
     * 功能：劳务分包登记备案台帐
     * @param curPage
     * @param pageSize
     * @param params
     * @return
     * @throws QueryException
     */
    public PageModel queryArtificialContractLedger(Integer curPage, Integer pageSize, Map<String,Object> params)
            throws QueryException {
        List<Object> paramList = new ArrayList<>();
        StringBuffer sql = new StringBuffer();
        sql.append("select b.fname as unitName,a.fTreatyContents as treatyContents,")
                .append("a.fDirectorName as directorName,a.fDirectorTel as directorTel,")
                .append("a.fAptitude as aptitude,a.fRemark as remark ")
                .append("from t_ec_subcontract a,t_ec_ecUnit b ")
                .append("where a.fEcUnitId = b.fId and a.fSubcontractExpenseType = 'ARTIFICIAL' and a.fProjectId=? ");
        paramList.add(params.get("projectId"));
        if(!BaseUtil.isEmpty(params.get("unitName"))){
            sql.append("and b.fname like ? ");
            paramList.add("%"+params.get("unitName")+"%");
        }
        if(!BaseUtil.isEmpty(params.get("directorName"))){
            sql.append("and a.fdirectorName like ? ");
            paramList.add("%"+params.get("directorName")+"%");
        }
        return toPageSqlQuery(curPage,pageSize,sql.toString(),paramList.toArray());
    }
}
