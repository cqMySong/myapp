package com.myapp.controller.ec.purchase.stockin;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseListController;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.BaseMethodEnum;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.util.WebUtil;
import com.myapp.service.ec.purchase.ApplyMaterialDetailService;
import com.myapp.service.ec.purchase.PurchaseStockDetailService;
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
 * @description：材料、设备出入库台账
 * @author： ly
 * @date: 2017-11-19 14:58
 */
@PermissionAnn(name="系统管理.现场管理.采购管理.材料、设备出入库台账",number="app.ec.purchase.stockledger")
@Controller
@RequestMapping("ec/purchase/stockledger")
public class StockLedgerListController extends BaseListController {
    @Resource
    private PurchaseStockDetailService purchaseStockDetailService;

    @Override
    public String getEditUrl() {
        return "";
    }

    @Override
    public String getListUrl() {
        return "ec/purchase/instock/stockLedgerList";
    }

    @Override
    public AbstractBaseService getService() {
        return this.purchaseStockDetailService;
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
        query.createAlias("purchaseContractDetailInfo","pcdi",JoinType.INNER_JOIN);
        query.createAlias("purchaseContractDetailInfo.applyMaterialDetailInfo","amdi",JoinType.INNER_JOIN);
        query.createAlias("purchaseContractDetailInfo.applyMaterialDetailInfo.budgetingDetailInfo",
                "bdi",JoinType.INNER_JOIN);
        query.createAlias("purchaseContractDetailInfo.applyMaterialDetailInfo.budgetingDetailInfo.material",
                "mater",JoinType.INNER_JOIN);
        query.createAlias("parent","pr",JoinType.INNER_JOIN);
        query.createAlias("stockInfo","stock",JoinType.INNER_JOIN);
        query.createAlias("stockInfo.stockOutDetailInfo","stockOut",JoinType.LEFT_OUTER_JOIN);
        query.createAlias("stockInfo.stockOutDetailInfo.parent","stockOutPr",JoinType.LEFT_OUTER_JOIN);
        query.createAlias("stockInfo.stockOutDetailInfo.parent.picker","pick",JoinType.LEFT_OUTER_JOIN);
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
        //query.add(Restrictions.eq("pro.id",projectId));
    }
    @Override
    public List<ColumnModel> getDataBinding() {
        List<ColumnModel> cols = super.getDataBinding();
        cols.add(new ColumnModel("mater.number",DataTypeEnum.STRING));
        cols.add(new ColumnModel("bdi.materialName",DataTypeEnum.STRING));
        cols.add(new ColumnModel("bdi.specification",DataTypeEnum.STRING));
        cols.add(new ColumnModel("bdi.quantity",DataTypeEnum.NUMBER));
        cols.add(new ColumnModel("pr.inStockDate",DataTypeEnum.DATE));
        cols.add(new ColumnModel("count",DataTypeEnum.NUMBER));
        cols.add(new ColumnModel("pr.number",DataTypeEnum.STRING));
        cols.add(new ColumnModel("stockOut.count",DataTypeEnum.NUMBER));
        cols.add(new ColumnModel("stockOutPr.number",DataTypeEnum.STRING));
        cols.add(new ColumnModel("stockOutPr.outStockDate",DataTypeEnum.DATE));
        cols.add(new ColumnModel("pick.name",DataTypeEnum.STRING));
        return cols;
    }

    @Override
    public Order getOrder() {
        return Order.asc("id");
    }

    @Override
    public List<Order> getOrders() {
        List<Order> orderList = new ArrayList<>();

        return orderList;
    }
}
