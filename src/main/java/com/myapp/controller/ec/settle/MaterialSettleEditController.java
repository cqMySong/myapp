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
import com.myapp.entity.ec.purchase.PurchaseContractInfo;
import com.myapp.entity.ec.settle.MaterialSettleDetailInfo;
import com.myapp.entity.ec.settle.MaterialSettleInfo;
import com.myapp.entity.ec.stock.StockOutDetailInfo;
import com.myapp.entity.ec.stock.StockOutInfo;
import com.myapp.service.ec.settle.MaterialSettleService;
import com.myapp.service.ec.stock.StockOutService;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * @path：com.myapp.controller.ec.stock.stockout
 * @description：领料出库
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
                JSONObject purchaseContract = null;
                JSONObject contractType = null;
                for(int i=0;i<materialSettleDetailInfos.size();i++){
                    materialSettleDetail = materialSettleDetailInfos.getJSONObject(i);
                    purchaseContract = materialSettleDetail.getJSONObject("purchaseContractInfo");
                    materialSettleDetail.put("supplyCompany",purchaseContract.getString("supplyCompany"));
                    materialSettleDetail.put("amount",purchaseContract.getBigDecimal("amount"));
                    materialSettleDetail.put("contractName",purchaseContract.getString("name"));
                    contractType = new JSONObject();
                    contractType.put("key",purchaseContract.get("contractType"));
                    contractType.put("val", ContractType.map.get(purchaseContract.getString("contractType")).getName());
                    materialSettleDetail.put("contractType",contractType);
                    materialSettleDetailNew.add(materialSettleDetail);
                }
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
        cols.add(new ColumnModel("operator",DataTypeEnum.F7,UserInfo.class));
        cols.add(new ColumnModel("settleDate",DataTypeEnum.DATE));
        cols.add(new ColumnModel("settleTotalAmount",DataTypeEnum.NUMBER));
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

        ColumnModel materialSettleDetailInfos = new ColumnModel("materialSettleDetailInfos",DataTypeEnum.ENTRY,
                MaterialSettleDetailInfo.class);

        ColumnModel purchaseContractInfo = new ColumnModel("purchaseContractInfo",DataTypeEnum.F7,
                "id,name,number,contractType,amount,supplyCompany");
        purchaseContractInfo.setClaz(PurchaseContractInfo.class);
        materialSettleDetailInfos.getCols().add(purchaseContractInfo);

        materialSettleDetailInfos.getCols().add(new ColumnModel("id",DataTypeEnum.PK));
        materialSettleDetailInfos.getCols().add(new ColumnModel("settleAmount",DataTypeEnum.NUMBER));
        materialSettleDetailInfos.getCols().add(new ColumnModel("remark"));
        cols.add(materialSettleDetailInfos);

        return cols;
    }
}
