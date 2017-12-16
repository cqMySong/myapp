package com.myapp.controller.ec.purchase.apply;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseListController;
import com.myapp.core.entity.MeasureUnitInfo;
import com.myapp.core.enums.BaseMethodEnum;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.util.WebUtil;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.service.ec.purchase.ApplyMaterialDetailService;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @path：com.myapp.controller.ec.purchase.stockin
 * @description：材料申购、供应台账
 * @author： ly
 * @date: 2017-11-19 14:58
 */
@PermissionAnn(name="系统管理.现场管理.采购管理.供应台账",number="app.ec.purchase.supplyledger")
@Controller
@RequestMapping("ec/purchase/supplyledger")
public class SupplyLedgerListController extends BaseListController {
    @Resource
    private ApplyMaterialDetailService applyMaterialDetailService;

    @Override
    public String getEditUrl() {
        return "";
    }

    @Override
    public String getListUrl() {
        return "ec/purchase/apply/supplyLedgerList";
    }

    @Override
    public AbstractBaseService getService() {
        return this.applyMaterialDetailService;
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
        query.createAlias("budgetingDetailInfo","bdi", JoinType.INNER_JOIN);
        query.createAlias("budgetingDetailInfo.material","mater",JoinType.INNER_JOIN);
        query.createAlias("budgetingDetailInfo.measureUnitInfo","mui",JoinType.INNER_JOIN);
        query.createAlias("parent","pr",JoinType.INNER_JOIN);
        query.createAlias("parent.project","pro",JoinType.INNER_JOIN);
        query.createAlias("purchaseStockDetailInfoSet","psdi",JoinType.LEFT_OUTER_JOIN);
        query.createAlias("purchaseStockDetailInfoSet.parent","psdipr",JoinType.LEFT_OUTER_JOIN);
        //query.createAlias("");
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
        cols.add(new ColumnModel("mater.number",DataTypeEnum.STRING));
        cols.add(new ColumnModel("bdi.materialName",DataTypeEnum.STRING));
        cols.add(new ColumnModel("bdi.specification",DataTypeEnum.STRING));
        cols.add(new ColumnModel("bdi.quantity",DataTypeEnum.NUMBER));
        cols.add(new ColumnModel("mui.name",DataTypeEnum.STRING));
        cols.add(new ColumnModel("pr.number",DataTypeEnum.STRING));
        cols.add(new ColumnModel("purchaseNum",DataTypeEnum.NUMBER));
        cols.add(new ColumnModel("arrivalTime",DataTypeEnum.DATE));
        cols.add(new ColumnModel("cumulativePurchaseNum",DataTypeEnum.NUMBER));
        cols.add(new ColumnModel("psdi.count",DataTypeEnum.NUMBER));
        cols.add(new ColumnModel("psdipr.inStockDate",DataTypeEnum.DATE));
        cols.add(new ColumnModel("psdi.cumulativeCount",DataTypeEnum.NUMBER));
        return cols;
    }

    @Override
    public Order getOrder() {
        return Order.asc("mater.number");
    }

    @Override
    public List<Order> getOrders() {
        List<Order> orderList = new ArrayList<>();
        orderList.add(Order.asc("mater.number"));
        orderList.add(Order.asc("pr.number"));
        orderList.add(Order.asc("sno"));
        orderList.add(Order.asc("psdi.sno"));
        return orderList;
    }
}
