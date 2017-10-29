package com.myapp.controller.ec.budget;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseBillEditController;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.BaseMethodEnum;
import com.myapp.core.enums.BillState;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.exception.db.QueryException;
import com.myapp.core.model.ColumnModel;
import com.myapp.entity.ec.basedata.DataDictionaryInfo;
import com.myapp.entity.ec.basedata.ProStructureInfo;
import com.myapp.entity.ec.basedata.ProSubInfo;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.entity.ec.budget.BudgetingDetailInfo;
import com.myapp.entity.ec.budget.BudgetingInfo;
import com.myapp.entity.ec.drawing.DiscussionDrawingInfo;
import com.myapp.entity.ec.plan.ProjectPlanReportItemInfo;
import com.myapp.enums.DataDicType;
import com.myapp.service.ec.budget.BudgetingService;
import com.myapp.service.ec.drawing.DiscussionDrawingService;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 包路径：com.myapp.controller.ec.budget
 * 功能说明：预算编制
 * 创建人： ly
 * 创建时间: 2017-07-30 14:49
 */
@PermissionAnn(name="系统管理.现场管理.预算.预算编制",number="app.ec.budget.budgeting")
@Controller
@RequestMapping("ec/budget/budgeting")
public class BudgetingEditController extends BaseBillEditController {
    @Resource
    private BudgetingService budgetingService;
    @Override
    public Object createNewData() {
        return new BudgetingInfo();
    }

    @Override
    public CoreBaseInfo getEntityInfo() {
        return new BudgetingInfo();
    }

    @Override
    public AbstractBaseService getService() {
        return this.budgetingService;
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

        ColumnModel budgetingDetail = new ColumnModel("budgetingDetailInfos",DataTypeEnum.ENTRY,
                BudgetingDetailInfo.class);

        ColumnModel dataDic = new ColumnModel("dataDic",DataTypeEnum.F7,"id,name");
        dataDic.setClaz(DataDictionaryInfo.class);
        budgetingDetail.getCols().add(dataDic);

        budgetingDetail.getCols().add(new ColumnModel("id",DataTypeEnum.PK));
        budgetingDetail.getCols().add(new ColumnModel("unitPrice",DataTypeEnum.NUMBER));
        budgetingDetail.getCols().add(new ColumnModel("quantity",DataTypeEnum.NUMBER));
        budgetingDetail.getCols().add(new ColumnModel("remark"));
        budgetingDetail.getCols().add(new ColumnModel("totalPrice",DataTypeEnum.NUMBER));
        budgetingDetail.getCols().add(new ColumnModel("dataDicType",DataTypeEnum.ENUM, DataDicType.class));
        cols.add(budgetingDetail);

        return cols;
    }
}