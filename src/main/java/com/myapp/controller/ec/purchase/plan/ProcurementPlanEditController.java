package com.myapp.controller.ec.purchase.plan;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseBillEditController;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.BillState;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.entity.ec.basedata.DataDictionaryInfo;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.entity.ec.purchase.ProcurementPlanDetailInfo;
import com.myapp.entity.ec.purchase.ProcurementPlanInfo;
import com.myapp.enums.DataDicType;
import com.myapp.service.ec.purchase.ProcurementPlanService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * 包路径：com.myapp.controller.ec.purchase.plan
 * 功能说明：采购计划
 * 创建人： ly
 * 创建时间: 2017-07-30 14:49
 */
@PermissionAnn(name="系统管理.现场管理.采购管理.采购计划",number="app.ec.purchase.procurementplan")
@Controller
@RequestMapping("ec/purchase/plan/procurementplan")
public class ProcurementPlanEditController extends BaseBillEditController {
    @Resource
    private ProcurementPlanService procurementPlanService;
    @Override
    public Object createNewData() {
        return new ProcurementPlanInfo();
    }

    @Override
    public CoreBaseInfo getEntityInfo() {
        return new ProcurementPlanInfo();
    }

    @Override
    public AbstractBaseService getService() {
        return this.procurementPlanService;
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

        ColumnModel procurementPlanDetails = new ColumnModel("procurementPlanDetails",DataTypeEnum.ENTRY,
                ProcurementPlanDetailInfo.class);

        ColumnModel dataDic = new ColumnModel("dataDic",DataTypeEnum.F7,"id,name");
        dataDic.setClaz(DataDictionaryInfo.class);
        procurementPlanDetails.getCols().add(dataDic);

        procurementPlanDetails.getCols().add(new ColumnModel("id",DataTypeEnum.PK));
        procurementPlanDetails.getCols().add(new ColumnModel("unitPrice",DataTypeEnum.NUMBER));
        procurementPlanDetails.getCols().add(new ColumnModel("quantity",DataTypeEnum.NUMBER));
        procurementPlanDetails.getCols().add(new ColumnModel("remark"));
        procurementPlanDetails.getCols().add(new ColumnModel("totalPrice",DataTypeEnum.NUMBER));
        procurementPlanDetails.getCols().add(new ColumnModel("dataDicType",DataTypeEnum.ENUM, DataDicType.class));
        cols.add(procurementPlanDetails);

        return cols;
    }
}
