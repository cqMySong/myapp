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
import com.myapp.entity.ec.engineering.ProprietorPaymentInfo;
import com.myapp.entity.ec.engineering.SubContractPaymentInfo;
import com.myapp.entity.ec.engineering.SubcontractInfo;
import com.myapp.service.ec.engineering.ProprietorPaymentService;
import com.myapp.service.ec.engineering.SubContractPaymentService;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * @path：com.myapp.controller.ec.sengineering.progressfund
 * @description：工程付款
 * @author ： ly
 * @date: 2017-07-30 14:49
 */
@PermissionAnn(name="系统管理.现场管理.工程合同.工程付款",number="app.ec.engineering.proprietorpayment")
@Controller
@RequestMapping("ec/engineering/proprietorpayment")
public class ProprietorPaymentEditController extends BaseBillEditController {
    @Resource
    private ProprietorPaymentService proprietorPaymentService;
    @Override
    public Object createNewData() {
        return new ProprietorPaymentInfo();
    }

    @Override
    public CoreBaseInfo getEntityInfo() {
        return new ProprietorPaymentInfo();
    }

    @Override
    public AbstractBaseService getService() {
        return this.proprietorPaymentService;
    }

    @Override
    public void afterOperate(BaseMethodEnum bme) throws HibernateException, QueryException {
        super.afterOperate(bme);
        if(this.data!=null){
            if(!bme.getValue().equals(BaseMethodEnum.ADDNEW.getValue())&&this.data instanceof JSONObject){

            }
        }
    }
    @Override
    public List<ColumnModel> getDataBinding() {
        List<ColumnModel> cols = super.getDataBinding();
        cols.add(new ColumnModel("name"));
        cols.add(new ColumnModel("number"));
        cols.add(new ColumnModel("remark"));
        cols.add(new ColumnModel("operator", DataTypeEnum.STRING));
        cols.add(new ColumnModel("deliveryAmount", DataTypeEnum.NUMBER));
        cols.add(new ColumnModel("deliveryDate", DataTypeEnum.DATE));
        cols.add(new ColumnModel("approvedAmount", DataTypeEnum.NUMBER));
        cols.add(new ColumnModel("paymentAmount", DataTypeEnum.NUMBER));
        cols.add(new ColumnModel("paymentRatio", DataTypeEnum.NUMBER));
        cols.add(new ColumnModel("actualRatio", DataTypeEnum.NUMBER));
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

        return cols;
    }
}
