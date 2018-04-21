package com.myapp.controller.ec.engineering.payment;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseBillEditController;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.BaseMethodEnum;
import com.myapp.core.enums.BillState;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.enums.SettleType;
import com.myapp.core.exception.db.QueryException;
import com.myapp.core.model.ColumnModel;
import com.myapp.entity.ec.basedata.ECUnitInfo;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.entity.ec.engineering.SubcontractInfo;
import com.myapp.entity.ec.engineering.SubContractPaymentInfo;
import com.myapp.service.ec.engineering.SubContractPaymentService;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * @path：com.myapp.controller.ec.sengineering.progressfund
 * @description：分包付款
 * @author ： ly
 * @date: 2017-07-30 14:49
 */
@PermissionAnn(name="系统管理.现场管理.工程合同.分包付款",number="app.ec.engineering.subcontractpayment")
@Controller
@RequestMapping("ec/engineering/subcontractpayment")
public class SubContractPaymentEditController extends BaseBillEditController {
    @Resource
    private SubContractPaymentService subContractPaymentService;
    @Override
    public Object createNewData() {
        return new SubContractPaymentInfo();
    }

    @Override
    public CoreBaseInfo getEntityInfo() {
        return new SubContractPaymentInfo();
    }

    @Override
    public AbstractBaseService getService() {
        return this.subContractPaymentService;
    }

    @Override
    public void afterOperate(BaseMethodEnum bme) throws HibernateException, QueryException {
        super.afterOperate(bme);
        if(this.data!=null){
            if(!bme.getValue().equals(BaseMethodEnum.ADDNEW.getValue())&&this.data instanceof JSONObject){
                JSONObject jsonObject = (JSONObject) this.data;
                JSONObject subcontractInfo = jsonObject.getJSONObject("subcontractInfo");
                jsonObject.put("contractAmount",subcontractInfo.get("amount"));
                jsonObject.put("subcontractExpenseType",subcontractInfo.get("subcontractExpenseType"));
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
        cols.add(new ColumnModel("jobContent"));
        cols.add(new ColumnModel("settleAmount", DataTypeEnum.NUMBER));
        cols.add(new ColumnModel("operator", DataTypeEnum.STRING));
        cols.add(new ColumnModel("startDate", DataTypeEnum.DATE));
        cols.add(new ColumnModel("endDate", DataTypeEnum.DATE));
        cols.add(new ColumnModel("billState", DataTypeEnum.ENUM, BillState.class));
        cols.add(new ColumnModel("settleType", DataTypeEnum.ENUM, SettleType.class));
        cols.add(new ColumnModel("createUser", DataTypeEnum.F7,UserInfo.class));
        cols.add(new ColumnModel("lastUpdateUser", DataTypeEnum.F7,UserInfo.class));
        cols.add(new ColumnModel("auditor", DataTypeEnum.F7,UserInfo.class));
        cols.add(new ColumnModel("createDate", DataTypeEnum.DATETIME));
        cols.add(new ColumnModel("auditDate", DataTypeEnum.DATETIME));
        cols.add(new ColumnModel("lastUpdateDate", DataTypeEnum.DATETIME));
        ColumnModel project = new ColumnModel("project", DataTypeEnum.F7,"id,name");
        project.setClaz(ProjectInfo.class);
        cols.add(project);

        ColumnModel subcontractInfo = new ColumnModel("subcontractInfo",
                DataTypeEnum.F7,"id,name,amount,subcontractExpenseType");
        subcontractInfo.setClaz(SubcontractInfo.class);
        cols.add(subcontractInfo);

        ColumnModel ecUnitInfo = new ColumnModel("ecUnitInfo",DataTypeEnum.F7,"id,name");
        ecUnitInfo.setClaz(ECUnitInfo.class);
        cols.add(ecUnitInfo);

        return cols;
    }
}
