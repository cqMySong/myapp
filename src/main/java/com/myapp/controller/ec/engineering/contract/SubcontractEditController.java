package com.myapp.controller.ec.engineering.contract;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseBillEditController;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.*;
import com.myapp.core.model.ColumnModel;
import com.myapp.entity.ec.basedata.ECUnitInfo;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.entity.ec.engineering.SubcontractInfo;
import com.myapp.service.ec.engineering.SubcontractService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * @path：com.myapp.controller.ec.sengineering.contract
 * @description：分包合同
 * @author ： ly
 * @date: 2017-07-30 14:49
 */
@PermissionAnn(name="系统管理.现场管理.工程合同.合同台账",number="app.ec.engineering.subcontract")
@Controller
@RequestMapping("ec/engineering/subcontract")
public class SubcontractEditController extends BaseBillEditController {
    @Resource
    private SubcontractService subcontractService;
    @Override
    public Object createNewData() {
        return new SubcontractInfo();
    }

    @Override
    public CoreBaseInfo getEntityInfo() {
        return new SubcontractInfo();
    }

    @Override
    public AbstractBaseService getService() {
        return this.subcontractService;
    }


    @Override
    public List<ColumnModel> getDataBinding() {
        List<ColumnModel> cols = super.getDataBinding();
        cols.add(new ColumnModel("name"));
        cols.add(new ColumnModel("number"));
        cols.add(new ColumnModel("directorName"));
        cols.add(new ColumnModel("treatyContents"));
        cols.add(new ColumnModel("amount",DataTypeEnum.NUMBER));
        cols.add(new ColumnModel("director"));
        cols.add(new ColumnModel("directorTel",DataTypeEnum.STRING));
        cols.add(new ColumnModel("aptitude",DataTypeEnum.STRING));
        cols.add(new ColumnModel("paymentMethod",DataTypeEnum.ENUM,PaymentMethod.class));
        cols.add(new ColumnModel("contractDate",DataTypeEnum.DATE));
        cols.add(new ColumnModel("subcontractExpenseType", DataTypeEnum.ENUM,SubcontractExpenseType.class));
        cols.add(new ColumnModel("billState",DataTypeEnum.ENUM, BillState.class));
        cols.add(new ColumnModel("createUser",DataTypeEnum.F7,UserInfo.class));
        cols.add(new ColumnModel("lastUpdateUser",DataTypeEnum.F7,UserInfo.class));
        cols.add(new ColumnModel("auditor",DataTypeEnum.F7,UserInfo.class));
        cols.add(new ColumnModel("createDate", DataTypeEnum.DATETIME));
        cols.add(new ColumnModel("auditDate", DataTypeEnum.DATETIME));
        cols.add(new ColumnModel("lastUpdateDate", DataTypeEnum.DATETIME));
        cols.add(new ColumnModel("remark", DataTypeEnum.STRING));
        ColumnModel project = new ColumnModel("project",DataTypeEnum.F7,"id,name");
        project.setClaz(ProjectInfo.class);
        cols.add(project);

        ColumnModel ecUnitInfo = new ColumnModel("ecUnitInfo",DataTypeEnum.F7,"id,name");
        ecUnitInfo.setClaz(ECUnitInfo.class);
        cols.add(ecUnitInfo);


        return cols;
    }
}
