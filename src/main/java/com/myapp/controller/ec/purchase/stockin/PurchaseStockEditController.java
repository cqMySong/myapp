package com.myapp.controller.ec.purchase.stockin;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseBillEditController;
import com.myapp.core.entity.MaterialInfo;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.*;
import com.myapp.core.exception.db.QueryException;
import com.myapp.core.model.ColumnModel;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.entity.ec.purchase.*;
import com.myapp.service.ec.purchase.PurchaseStockService;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * @path：com.myapp.controller.ec.purchase.contract
 * @description：采购入库
 * @author ： ly
 * @date: 2017-07-30 14:49
 */
@PermissionAnn(name="系统管理.现场管理.采购管理.采购入库",number="app.ec.purchase.purchasestock")
@Controller
@RequestMapping("ec/purchase/purchasestock")
public class PurchaseStockEditController extends BaseBillEditController {
    @Resource
    private PurchaseStockService purchaseStockService;
    @Override
    public Object createNewData() {
        return new PurchaseStockInfo();
    }

    @Override
    public CoreBaseInfo getEntityInfo() {
        return new PurchaseStockInfo();
    }

    @Override
    public AbstractBaseService getService() {
        return this.purchaseStockService;
    }
    @Override
    public void afterOperate(BaseMethodEnum bme) throws HibernateException, QueryException {
        super.afterOperate(bme);
        if(this.data!=null){
            if(!bme.getValue().equals(BaseMethodEnum.ADDNEW.getValue())&&this.data instanceof JSONObject){
                JSONObject jsonObject = (JSONObject) this.data;
                JSONArray purchaseStockDetailInfos = jsonObject.getJSONArray("purchaseStockDetailInfos");
                JSONArray purchaseStockDetailNew = new JSONArray();
                JSONObject purchaseStockDetail = null;
                JSONObject purchaseContractDetail = null;
                JSONObject materialType = null;
                for(int i=0;i<purchaseStockDetailInfos.size();i++){
                    purchaseStockDetail = purchaseStockDetailInfos.getJSONObject(i);
                    purchaseContractDetail = purchaseStockDetail.getJSONObject("purchaseContractDetailInfo");
                    purchaseStockDetail.put("specification",purchaseContractDetail.getString("specification"));
                    purchaseStockDetail.put("measureUnitName",purchaseContractDetail.getString("measureUnitName"));
                    purchaseStockDetail.put("purchasePrice",purchaseContractDetail.getBigDecimal("purchasePrice"));
                    purchaseStockDetail.put("quantity",purchaseContractDetail.getBigDecimal("quantity"));
                    materialType = new JSONObject();
                    materialType.put("key",purchaseContractDetail.get("materialType"));
                    materialType.put("val", MaterialType.map.get(purchaseContractDetail.getString("materialType")).getName());
                    purchaseStockDetail.put("materialType",materialType);
                    JSONObject material = new JSONObject();
                    material.put("name",purchaseContractDetail.getString("materialName"));
                    purchaseStockDetail.put("material",material);
                    purchaseStockDetailNew.add(purchaseStockDetail);
                }
                jsonObject.put("purchaseStockDetailInfos",purchaseStockDetailNew);
                this.data = jsonObject;
            }
        }
    }

    @Override
    public List<ColumnModel> getDataBinding() {
        List<ColumnModel> cols = super.getDataBinding();
        cols.add(new ColumnModel("name"));
        cols.add(new ColumnModel("number"));
        cols.add(new ColumnModel("remark"));
        cols.add(new ColumnModel("consignee",DataTypeEnum.F7,UserInfo.class));
        cols.add(new ColumnModel("inStockDate",DataTypeEnum.DATE));
        cols.add(new ColumnModel("totalPrice",DataTypeEnum.NUMBER));
        cols.add(new ColumnModel("billState",DataTypeEnum.ENUM, BillState.class));
        cols.add(new ColumnModel("createUser",DataTypeEnum.F7,UserInfo.class));
        cols.add(new ColumnModel("lastUpdateUser",DataTypeEnum.F7,UserInfo.class));
        cols.add(new ColumnModel("auditor",DataTypeEnum.F7,UserInfo.class));
        cols.add(new ColumnModel("createDate", DataTypeEnum.DATE));
        cols.add(new ColumnModel("auditDate", DataTypeEnum.DATE));
        cols.add(new ColumnModel("lastUpdateDate", DataTypeEnum.DATE));
        ColumnModel project = new ColumnModel("project",DataTypeEnum.F7,"id,name");
        project.setClaz(ProjectInfo.class);
        cols.add(project);

        ColumnModel purchaseStockDetailInfos = new ColumnModel("purchaseStockDetailInfos",DataTypeEnum.ENTRY,
                PurchaseStockDetailInfo.class);

        ColumnModel purchaseContractInfo = new ColumnModel("purchaseContractInfo",DataTypeEnum.F7,"id,name");
        purchaseContractInfo.setClaz(PurchaseContractInfo.class);
        purchaseStockDetailInfos.getCols().add(purchaseContractInfo);

        ColumnModel purchaseContractDetailInfo = new ColumnModel("purchaseContractDetailInfo",DataTypeEnum.F7,
                "id,materialName,materialType,specification,measureUnitName,quantity,purchasePrice");
        purchaseContractDetailInfo.setClaz(PurchaseContractDetailInfo.class);
        purchaseStockDetailInfos.getCols().add(purchaseContractDetailInfo);

        purchaseStockDetailInfos.getCols().add(new ColumnModel("id",DataTypeEnum.PK));
        purchaseStockDetailInfos.getCols().add(new ColumnModel("count",DataTypeEnum.NUMBER));
        purchaseStockDetailInfos.getCols().add(new ColumnModel("remark"));
        cols.add(purchaseStockDetailInfos);

        return cols;
    }
}
