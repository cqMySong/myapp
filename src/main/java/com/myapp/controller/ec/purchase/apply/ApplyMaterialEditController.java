package com.myapp.controller.ec.purchase.apply;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseBillEditController;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.*;
import com.myapp.core.exception.db.QueryException;
import com.myapp.core.model.ColumnModel;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.entity.ec.budget.BudgetingDetailInfo;
import com.myapp.entity.ec.budget.EnquiryPriceDetailInfo;
import com.myapp.entity.ec.purchase.ApplyMaterialDetailInfo;
import com.myapp.entity.ec.purchase.ApplyMaterialInfo;
import com.myapp.service.ec.purchase.ApplyMaterialService;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * @path：com.myapp.controller.ec.purchase.order.apply
 * @description：材料申购单
 * @author： ly
 * @date: 2017-10-29 20:40
 */
@PermissionAnn(name="系统管理.现场管理.采购管理.材料申购",number="app.ec.purchase.applymaterial")
@Controller
@RequestMapping("ec/purchase/applymaterial")
public class ApplyMaterialEditController extends BaseBillEditController {
    @Resource
    private ApplyMaterialService applyMaterialService;

    @Override
    public Object createNewData() {
        return new ApplyMaterialInfo();
    }

    @Override
    public CoreBaseInfo getEntityInfo() {
        return new ApplyMaterialInfo();
    }

    @Override
    public AbstractBaseService getService() {
        return this.applyMaterialService;
    }

    @Override
    public void afterOperate(BaseMethodEnum bme) throws HibernateException, QueryException {
        super.afterOperate(bme);
        if(this.data!=null){
            System.out.println(bme.getValue());
            if(!bme.getValue().equals(BaseMethodEnum.ADDNEW.getValue())&&this.data instanceof JSONObject){
                JSONObject jsonObject = (JSONObject) this.data;
                JSONArray applyMaterialDetails = jsonObject.getJSONArray("applyMaterialDetailInfos");
                JSONArray enquiryPriceDetailsNew = new JSONArray();
                JSONObject applyMaterialDetail = null;
                JSONObject budgetingDetail = null;
                JSONObject materialType = null;
                for(int i=0;i<applyMaterialDetails.size();i++){
                    applyMaterialDetail = applyMaterialDetails.getJSONObject(i);
                    budgetingDetail = applyMaterialDetail.getJSONObject("budgetingDetailInfo");
                    budgetingDetail.put("name",budgetingDetail.get("materialName"));
                    applyMaterialDetail.put("quantity",budgetingDetail.get("quantity"));
                    applyMaterialDetail.put("budgetaryPrice",budgetingDetail.get("budgetaryPrice"));
                    applyMaterialDetail.put("specification",budgetingDetail.get("specification"));
                    materialType = new JSONObject();
                    materialType.put("key",budgetingDetail.get("materialType"));
                    materialType.put("val", MaterialType.map.get(budgetingDetail.getString("materialType")).getName());
                    applyMaterialDetail.put("materialType",materialType);
                    applyMaterialDetail.put("measureUnit",budgetingDetail.get("measureUnitInfo"));
                    enquiryPriceDetailsNew.add(applyMaterialDetail);
                }
                jsonObject.put("enquiryPriceDetailInfos",enquiryPriceDetailsNew);
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

        ColumnModel applyMaterialDetailInfos = new ColumnModel("applyMaterialDetailInfos",DataTypeEnum.ENTRY,
                ApplyMaterialDetailInfo.class);

        ColumnModel budgetingDetailInfo = new ColumnModel("budgetingDetailInfo",DataTypeEnum.F7,
                "id,materialName,specification,quantity,budgetaryPrice,measureUnitInfo,materialType");
        budgetingDetailInfo.setClaz(BudgetingDetailInfo.class);
        applyMaterialDetailInfos.getCols().add(budgetingDetailInfo);

        applyMaterialDetailInfos.getCols().add(new ColumnModel("id",DataTypeEnum.PK));
        applyMaterialDetailInfos.getCols().add(new ColumnModel("purchaseNum",DataTypeEnum.NUMBER));
        applyMaterialDetailInfos.getCols().add(new ColumnModel("arrivalTime",DataTypeEnum.DATE));
        cols.add(applyMaterialDetailInfos);

        return cols;
    }
}
