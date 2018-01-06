package com.myapp.service.ec.engineering;

import com.myapp.core.exception.db.QueryException;
import com.myapp.core.model.PageModel;
import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.core.util.BaseUtil;
import com.myapp.entity.ec.engineering.SiteVisaOutInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @path：com.myapp.service.ec.engineering
 * @description：现场签证(支出)
 * @author： ly
 * @date: 2017-11-13 22:46
 */
@Service("siteVisaOutService")
public class SiteVisaOutService extends BaseInterfaceService<SiteVisaOutInfo> {
    /**
     * 功能：获取现场签证支出台帐信息
     * @param curPage
     * @param pageSize
     * @param params
     * @return
     * @throws QueryException
     */
    public PageModel querySiteVisaOutLedger(Integer curPage, Integer pageSize, Map<String,Object> params)
            throws QueryException {
        List<Object> paramList = new ArrayList<>();
        StringBuffer sql = new StringBuffer();
        sql.append("select a.fVisaDate,a.fVisaUnit,a.fWorkPart,a.fJobContent,a.fAmount,")
            .append("b.fTypeOfWork,b.fWorkStartTime,b.fWorkEndTime,b.fMechanicalName,b.fMechanicalStartTime,")
            .append("b.fMechanicalEndTime,b.fMaterialName,b.fUseCount,")
            .append("a.fChargingBasis,a.fcreateDate,a.fChargingContent")
            .append(" from t_ec_site_visa_out a ")
            .append("left join t_ec_site_visa_out_detail b on a.fid = b.fprentid ")
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
