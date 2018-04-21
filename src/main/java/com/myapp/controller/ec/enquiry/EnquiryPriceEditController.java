package com.myapp.controller.ec.enquiry;

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
import com.myapp.entity.ec.budget.EnquiryPriceInfo;
import com.myapp.service.ec.budget.EnquiryPriceService;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * @path：com.myapp.controller.ec.purchase.plan
 * @description：预算询价
 * @author ： ly
 * @date: 2017-07-30 14:49
 */
@PermissionAnn(name="系统管理.现场管理.预算.预算询价",number="app.ec.budget.enquiry")
@Controller
@RequestMapping("ec/budget/enquiryprice")
public class EnquiryPriceEditController extends BaseBillEditController {
    @Resource
    private EnquiryPriceService enquiryPriceService;
    @Override
    public Object createNewData() {
        return new EnquiryPriceInfo();
    }

    @Override
    public CoreBaseInfo getEntityInfo() {
        return new EnquiryPriceInfo();
    }

    @Override
    public AbstractBaseService getService() {
        return this.enquiryPriceService;
    }

    @Override
    public void afterOperate(BaseMethodEnum bme) throws HibernateException, QueryException {
        super.afterOperate(bme);
        if(this.data!=null){
            if(!bme.getValue().equals(BaseMethodEnum.ADDNEW.getValue())&&this.data instanceof JSONObject){
                JSONObject jsonObject = (JSONObject) this.data;
                JSONArray enquiryPriceDetails = jsonObject.getJSONArray("enquiryPriceDetailInfos");
                JSONArray enquiryPriceDetailsNew = new JSONArray();
                JSONObject enquiryPriceDetail = null;
                JSONObject budgetingDetail = null;
                JSONObject materialType = null;
                if(enquiryPriceDetails==null){
                    return;
                }
                for(int i=0;i<enquiryPriceDetails.size();i++){
                    enquiryPriceDetail = enquiryPriceDetails.getJSONObject(i);
                    budgetingDetail = enquiryPriceDetail.getJSONObject("budgetingDetailInfo");
                    budgetingDetail.put("name",budgetingDetail.get("materialName"));
                    enquiryPriceDetail.put("quantity",budgetingDetail.get("quantity"));
                    enquiryPriceDetail.put("budgetaryPrice",budgetingDetail.get("budgetaryPrice"));
                    enquiryPriceDetail.put("specification",budgetingDetail.get("specification"));
                    materialType = new JSONObject();
                    materialType.put("key",budgetingDetail.get("materialType"));
                    materialType.put("val",MaterialType.map.get(budgetingDetail.getString("materialType")).getName());
                    enquiryPriceDetail.put("materialType",materialType);
                    enquiryPriceDetail.put("measureUnit",budgetingDetail.get("measureUnitInfo"));
                    enquiryPriceDetailsNew.add(enquiryPriceDetail);
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
        cols.add(new ColumnModel("createDate", DataTypeEnum.DATETIME));
        cols.add(new ColumnModel("auditDate", DataTypeEnum.DATETIME));
        cols.add(new ColumnModel("lastUpdateDate", DataTypeEnum.DATETIME));
        ColumnModel project = new ColumnModel("project",DataTypeEnum.F7,"id,name");
        project.setClaz(ProjectInfo.class);
        cols.add(project);

        ColumnModel enquiryPriceDetailInfos = new ColumnModel("enquiryPriceDetailInfos",DataTypeEnum.ENTRY,
                EnquiryPriceDetailInfo.class);

        ColumnModel budgetingDetailInfo = new ColumnModel("budgetingDetailInfo",DataTypeEnum.F7,
                "id,materialName,specification,quantity,budgetaryPrice,measureUnitInfo,materialType");
        budgetingDetailInfo.setClaz(BudgetingDetailInfo.class);
        enquiryPriceDetailInfos.getCols().add(budgetingDetailInfo);

        enquiryPriceDetailInfos.getCols().add(new ColumnModel("id",DataTypeEnum.PK));
        enquiryPriceDetailInfos.getCols().add(new ColumnModel("intentionPrice",DataTypeEnum.NUMBER));
        enquiryPriceDetailInfos.getCols().add(new ColumnModel("paymentMethod"));
        enquiryPriceDetailInfos.getCols().add(new ColumnModel("origin"));
        enquiryPriceDetailInfos.getCols().add(new ColumnModel("supplyCycle"));
        enquiryPriceDetailInfos.getCols().add(new ColumnModel("supplyCompany"));
        enquiryPriceDetailInfos.getCols().add(new ColumnModel("contactMan"));
        enquiryPriceDetailInfos.getCols().add(new ColumnModel("contactTel"));
        enquiryPriceDetailInfos.getCols().add(new ColumnModel("contractSignMethod",
                DataTypeEnum.ENUM, ContractSignMethod.class));
        cols.add(enquiryPriceDetailInfos);

        return cols;
    }
}
