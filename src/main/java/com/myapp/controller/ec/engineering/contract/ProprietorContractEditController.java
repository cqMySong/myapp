package com.myapp.controller.ec.engineering.contract;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseBillEditController;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.BillState;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.enums.ProprietorContractType;
import com.myapp.core.enums.SubcontractExpenseType;
import com.myapp.core.model.ColumnModel;
import com.myapp.entity.ec.basedata.ECUnitInfo;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.entity.ec.engineering.ProprietorContractInfo;
import com.myapp.entity.ec.engineering.SubcontractInfo;
import com.myapp.service.ec.engineering.ProprietorContractService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * @path：com.myapp.controller.ec.sengineering.contract
 * @description：业主合同
 * @author ： ly
 * @date: 2017-07-30 14:49
 */
@PermissionAnn(name="系统管理.现场管理.工程合同.业主合同",number="app.ec.engineering.proprietor")
@Controller
@RequestMapping("ec/engineering/proprietorcontract")
public class ProprietorContractEditController extends BaseBillEditController {
    @Resource
    private ProprietorContractService proprietorContractService;
    @Override
    public Object createNewData() {
        return new ProprietorContractInfo();
    }

    @Override
    public CoreBaseInfo getEntityInfo() {
        return new ProprietorContractInfo();
    }

    @Override
    public AbstractBaseService getService() {
        return this.proprietorContractService;
    }


    @Override
    public List<ColumnModel> getDataBinding() {
        List<ColumnModel> cols = super.getDataBinding();
        cols.add(new ColumnModel("name"));
        cols.add(new ColumnModel("number"));
        cols.add(new ColumnModel("rangeValuation"));
        cols.add(new ColumnModel("basisValuation"));
        cols.add(new ColumnModel("amount",DataTypeEnum.NUMBER));
        cols.add(new ColumnModel("billState",DataTypeEnum.ENUM, BillState.class));
        cols.add(new ColumnModel("proprietorContractType",DataTypeEnum.ENUM,ProprietorContractType.class));
        cols.add(new ColumnModel("createUser",DataTypeEnum.F7,UserInfo.class));
        cols.add(new ColumnModel("lastUpdateUser",DataTypeEnum.F7,UserInfo.class));
        cols.add(new ColumnModel("auditor",DataTypeEnum.F7,UserInfo.class));
        cols.add(new ColumnModel("createDate", DataTypeEnum.DATE));
        cols.add(new ColumnModel("auditDate", DataTypeEnum.DATE));
        cols.add(new ColumnModel("lastUpdateDate", DataTypeEnum.DATE));
        ColumnModel project = new ColumnModel("project",DataTypeEnum.F7,"id,name");
        project.setClaz(ProjectInfo.class);
        cols.add(project);

        return cols;
    }
}
