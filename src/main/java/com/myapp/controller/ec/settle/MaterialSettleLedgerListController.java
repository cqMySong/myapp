package com.myapp.controller.ec.settle;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseListController;
import com.myapp.core.enums.BaseMethodEnum;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.enums.ExpenseType;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.util.WebUtil;
import com.myapp.service.ec.settle.MaterialSettleDetailService;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @path：com.myapp.controller.ec.settle
 * @description：材设结算一览表
 * @author ： ly
 * @date: 2017-08-28 21:02
 */
@PermissionAnn(name="系统管理.现场管理.结算管理.材设结算一览表",number="app.ec.settle.materialsettleledger")
@Controller
@RequestMapping("ec/settle/materialsettleledgers")
public class MaterialSettleLedgerListController extends BaseListController {
    @Resource
    private MaterialSettleDetailService materialSettleDetailService;

    @Override
    public String getEditUrl() {
        return "";
    }

    @Override
    public String getListUrl() {
        return "ec/settle/material/materialSettleLedgerList";
    }

    @Override
    public AbstractBaseService getService() {
        return this.materialSettleDetailService;
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
        query.add(Restrictions.eq("pr.project.id",projectId));
    }
    @Override
    public List<ColumnModel> getDataBinding() {
        List<ColumnModel> cols = super.getDataBinding();
        cols.add(new ColumnModel("contract.expenseType", DataTypeEnum.ENUM,ExpenseType.class));
        cols.add(new ColumnModel("contract.number", DataTypeEnum.STRING));
        cols.add(new ColumnModel("contract.name", DataTypeEnum.STRING));
        cols.add(new ColumnModel("contract.supplyCompany", DataTypeEnum.STRING));
        cols.add(new ColumnModel("contract.amount", DataTypeEnum.NUMBER));
        cols.add(new ColumnModel("settleAmount", DataTypeEnum.NUMBER));
        cols.add(new ColumnModel("op.name", DataTypeEnum.STRING));
        cols.add(new ColumnModel("pr.settleDate", DataTypeEnum.DATE));
        return cols;
    }

    @Override
    public List<Order> getOrders() {
        List<Order> orderList = new ArrayList<>();
        orderList.add(Order.asc("contract.expenseType"));
        orderList.add(Order.asc("contract.supplyCompany"));
        orderList.add(Order.asc("pr.settleDate"));
        return orderList;
    }
}
