package com.myapp.controller.ec.purchase.contract;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseBillEditController;
import com.myapp.core.entity.MaterialInfo;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.*;
import com.myapp.core.model.ColumnModel;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.entity.ec.purchase.ApplyMaterialDetailInfo;
import com.myapp.entity.ec.purchase.PurchaseContractDetailInfo;
import com.myapp.entity.ec.purchase.PurchaseContractInfo;
import com.myapp.service.ec.purchase.PurchaseContractService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * @path：com.myapp.controller.ec.purchase.contract
 * @description：purchasecontract
 * @author ： ly
 * @date: 2017-07-30 14:49
 */
@PermissionAnn(name="系统管理.现场管理.采购管理.采购合同",number="app.ec.purchase.purchasecontract")
@Controller
@RequestMapping("ec/purchase/purchasecontract")
public class PurchaseContractEditController extends BaseBillEditController {
    @Resource
    private PurchaseContractService purchaseContractService;
    @Override
    public Object createNewData() {
        return new PurchaseContractInfo();
    }

    @Override
    public CoreBaseInfo getEntityInfo() {
        return new PurchaseContractInfo();
    }

    @Override
    public AbstractBaseService getService() {
        return this.purchaseContractService;
    }


    @Override
    public List<ColumnModel> getDataBinding() {
        List<ColumnModel> cols = super.getDataBinding();
        cols.add(new ColumnModel("name"));
        cols.add(new ColumnModel("number"));
        cols.add(new ColumnModel("remark"));
        cols.add(new ColumnModel("paymentMethod", DataTypeEnum.ENUM, PaymentMethod.class));
        cols.add(new ColumnModel("expenseType", DataTypeEnum.ENUM, ExpenseType.class));
        cols.add(new ColumnModel("supplyCompany"));
        cols.add(new ColumnModel("operator", DataTypeEnum.F7,UserInfo.class));
        cols.add(new ColumnModel("contactTel"));
        cols.add(new ColumnModel("amount", DataTypeEnum.NUMBER));
        cols.add(new ColumnModel("billState", DataTypeEnum.ENUM, BillState.class));
        cols.add(new ColumnModel("createUser", DataTypeEnum.F7,UserInfo.class));
        cols.add(new ColumnModel("lastUpdateUser", DataTypeEnum.F7,UserInfo.class));
        cols.add(new ColumnModel("auditor", DataTypeEnum.F7,UserInfo.class));
        cols.add(new ColumnModel("createDate", DataTypeEnum.DATE));
        cols.add(new ColumnModel("auditDate", DataTypeEnum.DATE));
        cols.add(new ColumnModel("lastUpdateDate", DataTypeEnum.DATE));
        ColumnModel project = new ColumnModel("project", DataTypeEnum.F7,"id,name");
        project.setClaz(ProjectInfo.class);
        cols.add(project);

        ColumnModel purchaseContractDetailInfos = new ColumnModel("purchaseContractDetailInfos", DataTypeEnum.ENTRY,
                PurchaseContractDetailInfo.class);

        ColumnModel material = new ColumnModel("material", DataTypeEnum.F7,"id,name");
        material.setClaz(MaterialInfo.class);
        purchaseContractDetailInfos.getCols().add(material);

        ColumnModel applyMaterialDetailInfo = new ColumnModel("applyMaterialDetailInfo", DataTypeEnum.F7,"id");
        applyMaterialDetailInfo.setClaz(ApplyMaterialDetailInfo.class);
        purchaseContractDetailInfos.getCols().add(applyMaterialDetailInfo);

        purchaseContractDetailInfos.getCols().add(new ColumnModel("id", DataTypeEnum.PK));
        purchaseContractDetailInfos.getCols().add(new ColumnModel("materialType", DataTypeEnum.ENUM, MaterialType.class));
        purchaseContractDetailInfos.getCols().add(new ColumnModel("specification"));
        purchaseContractDetailInfos.getCols().add(new ColumnModel("quantity", DataTypeEnum.NUMBER));
        purchaseContractDetailInfos.getCols().add(new ColumnModel("purchasePrice", DataTypeEnum.NUMBER));
        purchaseContractDetailInfos.getCols().add(new ColumnModel("origin"));
        purchaseContractDetailInfos.getCols().add(new ColumnModel("materialName"));
        purchaseContractDetailInfos.getCols().add(new ColumnModel("measureUnitName"));
        purchaseContractDetailInfos.getCols().add(new ColumnModel("totalPrice", DataTypeEnum.NUMBER));
        cols.add(purchaseContractDetailInfos);

        return cols;
    }
}
