package com.myapp.service.ec.engineering;

import com.myapp.core.exception.db.QueryException;
import com.myapp.core.model.PageModel;
import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.core.util.BaseUtil;
import com.myapp.entity.ec.engineering.SiteVisaInInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @path：com.myapp.service.ec.engineering
 * @description：现场签证(收入)
 * @author： ly
 * @date: 2017-11-13 22:46
 */
@Service("siteVisaInService")
public class SiteVisaInService extends BaseInterfaceService<SiteVisaInInfo> {

    /**
     * 功能：根据工程id现场签证收入数据
     * @param projectId
     * @return
     */
    public List queryByProject(String projectId){
        String hql = "select svi.name as name,svi.chargingContent as chargingContent," +
                "svi.amount as amount,svi.id as id,svi.chargingBasis as chargingBasis " +
                "from SiteVisaInInfo svi where svi.project.id = ? order by svi.name" ;
        return findByHQL(hql,new Object[]{projectId});
    }

    /**
     * 功能：获取现场签证支出台帐信息
     * @param curPage
     * @param pageSize
     * @param params
     * @return
     * @throws QueryException
     */
    public PageModel querySiteVisaInLedger(Integer curPage, Integer pageSize, Map<String,Object> params)
            throws QueryException {
        List<Object> paramList = new ArrayList<>();
        StringBuffer sql = new StringBuffer();
        sql.append("select a.fVisaDate,a.fVisaUnit,a.fWorkPart,a.fJobContent,a.fAmount,a.fid,b.fVisaUnit as fVisaUnitIn,")
                .append("(select count(t.fid) from t_base_attachFile t where t.fsourceBillId = a.fid) as outAttachs,")
                .append("a.fChargingBasis,b.fChargingBasis as fChargingBasisIn,b.fAmount as fAmountIn,b.fid as fidIn,b.fHandleType,")
                .append("(select count(t.fid) from t_base_attachFile t where t.fsourceBillId = b.fid) as outAttachsIn ")
                .append(" from t_ec_site_visa_out a ")
                .append("left join t_ec_site_visa_in b on a.fid = b.fSiteVisaOutId ")
                .append("where a.fProjectId=?");
        paramList.add(params.get("projectId"));
        if(!BaseUtil.isEmpty(params.get("startDate"))){
            sql.append("and a.fVisaDate>=? ");
            paramList.add(params.get("startDate"));
        }
        if(!BaseUtil.isEmpty(params.get("endDate"))){
            sql.append("and a.fVisaDate<=? ");
            paramList.add(params.get("endDate"));
        }
        if(!BaseUtil.isEmpty(params.get("visaUnit"))){
            sql.append("and a.fVisaUnit like ? ");
            paramList.add("%"+params.get("visaUnit")+"%");
        }
        return toPageSqlQuery(curPage,pageSize,sql.toString(),paramList.toArray());
    }
}
