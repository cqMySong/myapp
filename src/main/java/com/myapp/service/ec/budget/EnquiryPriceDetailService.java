package com.myapp.service.ec.budget;

import com.myapp.core.exception.db.QueryException;
import com.myapp.core.model.PageModel;
import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.core.util.BaseUtil;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.entity.ec.budget.BudgetingDetailInfo;
import com.myapp.entity.ec.budget.EnquiryPriceDetailInfo;
import org.hibernate.Criteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @path：com.myapp.service.ec.budget
 * @desciption：询价明细
 * @author ： ly
 * @date: 2017-08-28 21:15
 */
@Service("enquiryPriceDetailService")
public class EnquiryPriceDetailService extends BaseInterfaceService<EnquiryPriceDetailInfo>{
    /**
     * 功能：材料、设备采购准备一览表
     * @param curPage
     * @param pageSize
     * @param params
     * @return
     * @throws QueryException
     */
    public PageModel queryEnquiryPrice(Integer curPage, Integer pageSize, Map<String,Object> params)
            throws QueryException {
        StringBuffer sql = new StringBuffer();
        sql.append("select a.fcreateDate,b.fIntentionPrice,b.fSupplyCycle,b.fOrigin,")
        .append("b.fSupplyCompany,b.fContactMan,b.fContactTel,d.fnumber,c.fMaterialName,")
        .append("e.fname as unitName,c.fBudgetaryPrice,c.fQuantity,c.fspecification,b.fPaymentMethod,")
        .append("case when b.fContractSignMethod ='CURRENT' then '现有合同条款' ")
        .append("when b.fContractSignMethod ='NEGOTIATION' then '定向谈判' ")
        .append("when b.fContractSignMethod ='BIDDING' then '招投标' else '其它' end as fContractSignMethod ")
        .append("from t_ec_enquiry_price a,t_ec_enquiry_price_detail b,t_ec_budgeting_detail c,")
        .append("t_base_material d,t_base_measureunit e ")
        .append("where a.fid = b.fprentid and b.fBudgetingDetailId = c.fid and c.fMaterialId = d.fid ")
        .append("and e.fid = c.fMeasureUnitId and a.fProjectId=?");
        List<Object> paramList = new ArrayList<>();
        paramList.add(params.get("projectId"));
        if(!BaseUtil.isEmpty(params.get("startDate"))){
            sql.append("and a.fcreateDate>=? ");
            paramList.add(params.get("startDate"));
        }
        if(!BaseUtil.isEmpty(params.get("endDate"))){
            sql.append("and a.fcreateDate<=? ");
            paramList.add(params.get("endDate"));
        }
        if(!BaseUtil.isEmpty(params.get("materialName"))){
            sql.append("and d.fname like ? ");
            paramList.add("%"+params.get("materialName")+"%");
        }
        sql.append(" order by a.fcreateDate,c.fMaterialName,c.fSpecification,c.fBudgetaryPrice");
        return  toPageSqlQuery(curPage,pageSize,sql.toString(),paramList.toArray());
    }
}
