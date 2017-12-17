package com.myapp.controller.ec.settle;

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
import com.myapp.entity.ec.purchase.PurchaseContractDetailInfo;
import com.myapp.entity.ec.purchase.PurchaseContractInfo;
import com.myapp.entity.ec.settle.MaterialSettleDetailInfo;
import com.myapp.entity.ec.settle.MaterialSettleInfo;
import com.myapp.service.ec.settle.MaterialSettleService;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * @path：com.myapp.controller.ec.stock.stockout
 * @description：物料结算
 * @author ： ly
 * @date: 2017-07-30 14:49
 */
@PermissionAnn(name="系统管理.现场管理.结算管理.物料结算",number="app.ec.settle.materialsettle")
@Controller
@RequestMapping("ec/settle/materialsettle")
public class MaterialSettleEditController extends BaseBillEditController {
    @Resource
    private MaterialSettleService materialSettleService;
    @Override
    public Object createNewData() {
        return new MaterialSettleInfo();
    }

    @Override
    public CoreBaseInfo getEntityInfo() {
        return new MaterialSettleInfo();
    }

    @Override
    public AbstractBaseService getService() {
        return this.materialSettleService;
    }
    @Override
    public void afterOperate(BaseMethodEnum bme) throws HibernateException, QueryException {
        super.afterOperate(bme);
        if(this.data!=null){
            if(!bme.getValue().equals(BaseMethodEnum.ADDNEW.getValue())&&this.data instanceof JSONObject){
                JSONObject jsonObject = (JSONObject) this.data;
                JSONArray materialSettleDetailInfos = jsonObject.getJSONArray("materialSettleDetailInfos");
                JSONArray materialSettleDetailNew = new JSONArray();
                JSONObject materialSettleDetail = null;
                JSONObject purchaseContractDetail = null;
                JSONObject materialType = null;
                for(int i=0;i<materialSettleDetailInfos.size();i++){
                    materialSettleDetail = materialSettleDetailInfos.getJSONObject(i);
                    purchaseContractDetail = materialSettleDetail.getJSONObject("purchaseContractDetailInfo");
                    materialType = new JSONObject();
                    materialType.put("key",purchaseContractDetail.get("materialType"));
                    materialType.put("val", MaterialType.map.get(purchaseContractDetail.getString("materialType")).getName());
                    materialSettleDetail.put("materialType",materialType);
                    materialSettleDetail.put("specification",purchaseContractDetail.getString("specification"));
                    materialSettleDetail.put("measureUnitName",purchaseContractDetail.getString("measureUnitName"));
                    materialSettleDetail.put("materialName",purchaseContractDetail.getString("materialName"));
                    materialSettleDetailNew.add(materialSettleDetail);
                }
                jsonObject.put("supplyCompany",jsonObject.getJSONObject("purchaseContractInfo").get("supplyCompany"));
                jsonObject.put("materialSettleDetailInfos",materialSettleDetailNew);
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
        cols.add(new ColumnModel("operator", DataTypeEnum.F7,UserInfo.class));
        cols.add(new ColumnModel("startDate", DataTypeEnum.DATE));
        cols.add(new ColumnModel("endDate", DataTypeEnum.DATE));
        cols.add(new ColumnModel("settleAmount", DataTypeEnum.NUMBER));
        cols.add(new ColumnModel("billState", DataTypeEnum.ENUM, BillState.class));
        cols.add(new ColumnModel("createUser", DataTypeEnum.F7,UserInfo.class));
        cols.add(new ColumnModel("lastUpdateUser", DataTypeEnum.F7,UserInfo.class));
        cols.add(new ColumnModel("auditor", DataTypeEnum.F7,UserInfo.class));
        cols.add(new ColumnModel("createDate", DataTypeEnum.DATE));
        cols.add(new ColumnModel("auditDate", DataTypeEnum.DATE));
        cols.add(new ColumnModel("lastUpdateDate", DataTypeEnum.DATE));

        ColumnModel purchaseContractInfo = new ColumnModel("purchaseContractInfo", DataTypeEnum.F7,
                "id,name,supplyCompany");
        purchaseContractInfo.setClaz(PurchaseContractInfo.class);
        cols.add(purchaseContractInfo);

        ColumnModel project = new ColumnModel("project", DataTypeEnum.F7,"id,name");
        project.setClaz(ProjectInfo.class);
        cols.add(project);

        ColumnModel materialSettleDetailInfos = new ColumnModel("materialSettleDetailInfos", DataTypeEnum.ENTRY,
                MaterialSettleDetailInfo.class);

        ColumnModel purchaseContractDetailInfo = new ColumnModel("purchaseContractDetailInfo", DataTypeEnum.F7,
                "id,materialType,specification,measureUnitName,materialName");
        purchaseContractDetailInfo.setClaz(PurchaseContractDetailInfo.class);
        materialSettleDetailInfos.getCols().add(purchaseContractDetailInfo);

        ColumnModel materialInfo = new ColumnModel("materialInfo", DataTypeEnum.F7,
                "id,name");
        materialInfo.setClaz(MaterialInfo.class);
        materialSettleDetailInfos.getCols().add(materialInfo);

        materialSettleDetailInfos.getCols().add(new ColumnModel("id", DataTypeEnum.PK));
        materialSettleDetailInfos.getCols().add(new ColumnModel("settleCount", DataTypeEnum.NUMBER));
        materialSettleDetailInfos.getCols().add(new ColumnModel("price", DataTypeEnum.NUMBER));
        materialSettleDetailInfos.getCols().add(new ColumnModel("remark"));
        cols.add(materialSettleDetailInfos);

        return cols;
    }
}
