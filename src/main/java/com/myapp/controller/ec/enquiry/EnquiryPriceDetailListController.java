package com.myapp.controller.ec.enquiry;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseListController;
import com.myapp.core.enums.BaseMethodEnum;
import com.myapp.core.enums.ContractSignMethod;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.util.WebUtil;
import com.myapp.service.ec.budget.EnquiryPriceDetailService;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @path：com.myapp.controller.ec.purchase.plan
 * @descripition：材料、设备采购准备一览表
 * @author ： ly
 * @date: 2017-08-28 21:02
 */
@PermissionAnn(name="系统管理.现场管理.预算.预算询价",number="app.ec.purchase.procurementplan")
@Controller
@RequestMapping("ec/budget/enquirypricedetail")
public class EnquiryPriceDetailListController extends BaseListController {
    @Resource
    private EnquiryPriceDetailService enquiryPriceDetailService;

    @Override
    public String getEditUrl() {
        return "";
    }

    @Override
    public String getListUrl() {
        return "ec/budget/enquiry/enquiryPriceDetailList";
    }

    @Override
    public AbstractBaseService getService() {
        return this.enquiryPriceDetailService;
    }

    @Override
    public void packageUIParams(Map params) {
        super.packageUIParams(params);
        if(BaseMethodEnum.ADDNEW.equals(baseMethod)){
            if(params!=null&&params.get("uiCtx")!=null){
                String uiCtx = (String) params.get("uiCtx");
                params.put("uiCtx", WebUtil.UUID_ReplaceID(uiCtx));
            }
        }
    }
    @Override
    public void executeQueryParams(Criteria query) {
        super.executeQueryParams(query);
        String serach = request.getParameter("search");
        String projectId = "xyz";
        if(!BaseUtil.isEmpty(serach)){
            Map searchMap = JSONObject.parseObject(serach, new HashMap().getClass());
            Object objTree = searchMap.get("tree");
            if(objTree!=null){
                Map treeMap = JSONObject.parseObject(objTree.toString(), new HashMap().getClass());
                Object idObj = treeMap.get("id");
                Object type = treeMap.get("type");
                if(type!=null&&idObj!=null){
                    if("project".equals(type.toString())){
                        projectId = idObj.toString();
                    }
                }
            }
        }
        query.add(Restrictions.eq("pro.id",projectId));
    }
    @Override
    public List<ColumnModel> getDataBinding() {
        List<ColumnModel> cols = super.getDataBinding();
        cols.add(new ColumnModel("intentionPrice"));
        cols.add(new ColumnModel("paymentMethod"));
        cols.add(new ColumnModel("origin"));
        cols.add(new ColumnModel("supplyCycle"));
        cols.add(new ColumnModel("supplyCompany"));
        cols.add(new ColumnModel("contactMan"));
        cols.add(new ColumnModel("contactTel"));
        cols.add(new ColumnModel("contractSignMethod",DataTypeEnum.ENUM, ContractSignMethod.class));
        cols.add(new ColumnModel("p.createDate",DataTypeEnum.DATE));
        cols.add(new ColumnModel("mat.number"));
        cols.add(new ColumnModel("bdi.quantity"));
        cols.add(new ColumnModel("bdi.materialName"));
        cols.add(new ColumnModel("bdi.budgetaryPrice"));
        cols.add(new ColumnModel("bdi.specification"));
        cols.add(new ColumnModel("bdi.measureUnitInfo"));
        return cols;
    }

    @Override
    public Order getOrder() {
        return Order.asc("budgetingDetailInfo");
    }
}
