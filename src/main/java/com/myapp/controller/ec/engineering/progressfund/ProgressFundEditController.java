package com.myapp.controller.ec.engineering.progressfund;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseBillEditController;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.BaseMethodEnum;
import com.myapp.core.enums.BillState;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.enums.PaymentType;
import com.myapp.core.exception.db.QueryException;
import com.myapp.core.model.ColumnModel;
import com.myapp.entity.ec.basedata.ECUnitInfo;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.entity.ec.engineering.EngineeringContractInfo;
import com.myapp.entity.ec.engineering.ProgressFundInfo;
import com.myapp.service.ec.engineering.ProgressFundService;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * @path：com.myapp.controller.ec.sengineering.progressfund
 * @description：进度款
 * @author ： ly
 * @date: 2017-07-30 14:49
 */
@PermissionAnn(name="系统管理.现场管理.工程合同.进度款结算",number="app.ec.engineering.progressfund")
@Controller
@RequestMapping("ec/engineering/progressfund")
public class ProgressFundEditController extends BaseBillEditController {
    @Resource
    private ProgressFundService progressFundService;
    @Override
    public Object createNewData() {
        ProgressFundInfo progressFundInfo = new ProgressFundInfo();
        progressFundInfo.setName("进度款单据");
        return progressFundInfo;
    }

    @Override
    public CoreBaseInfo getEntityInfo() {
        return new ProgressFundInfo();
    }

    @Override
    public AbstractBaseService getService() {
        return this.progressFundService;
    }

    @Override
    public void afterOperate(BaseMethodEnum bme) throws HibernateException, QueryException {
        super.afterOperate(bme);
        if(this.data!=null){
            if(!bme.getValue().equals(BaseMethodEnum.ADDNEW.getValue())&&this.data instanceof JSONObject){
                JSONObject jsonObject = (JSONObject) this.data;
                JSONObject engineeringContractInfo = jsonObject.getJSONObject("engineeringContractInfo");
                jsonObject.put("contractAmount",engineeringContractInfo.get("amount"));
                jsonObject.put("contractType",engineeringContractInfo.get("contractType"));
                jsonObject.put("expenseType",engineeringContractInfo.get("expenseType"));
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
        cols.add(new ColumnModel("operator", DataTypeEnum.F7,UserInfo.class));
        cols.add(new ColumnModel("settleDate", DataTypeEnum.DATE));
        cols.add(new ColumnModel("billState", DataTypeEnum.ENUM, BillState.class));
        cols.add(new ColumnModel("paymentType", DataTypeEnum.ENUM, PaymentType.class));
        cols.add(new ColumnModel("createUser", DataTypeEnum.F7,UserInfo.class));
        cols.add(new ColumnModel("lastUpdateUser", DataTypeEnum.F7,UserInfo.class));
        cols.add(new ColumnModel("auditor", DataTypeEnum.F7,UserInfo.class));
        cols.add(new ColumnModel("createDate", DataTypeEnum.DATE));
        cols.add(new ColumnModel("auditDate", DataTypeEnum.DATE));
        cols.add(new ColumnModel("lastUpdateDate", DataTypeEnum.DATE));
        ColumnModel project = new ColumnModel("project", DataTypeEnum.F7,"id,name");
        project.setClaz(ProjectInfo.class);
        cols.add(project);

        ColumnModel engineeringContractInfo = new ColumnModel("engineeringContractInfo",
                DataTypeEnum.F7,"id,name,amount,contractType,expenseType");
        engineeringContractInfo.setClaz(EngineeringContractInfo.class);
        cols.add(engineeringContractInfo);

        ColumnModel ecUnitInfo = new ColumnModel("ecUnitInfo",
                DataTypeEnum.F7,"id,name");
        ecUnitInfo.setClaz(ECUnitInfo.class);
        cols.add(ecUnitInfo);

        return cols;
    }
}
