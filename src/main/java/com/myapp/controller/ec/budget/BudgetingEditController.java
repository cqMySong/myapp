package com.myapp.controller.ec.budget;

import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.base.service.IImportConvertService;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseBillEditImportController;
import com.myapp.core.entity.MeasureUnitInfo;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.BillState;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.entity.MaterialInfo;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.entity.ec.budget.BudgetingDetailInfo;
import com.myapp.entity.ec.budget.BudgetingInfo;
import com.myapp.core.enums.MaterialType;
import com.myapp.model.BudgetingModel;
import com.myapp.service.ec.budget.BudgetingService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * @path：com.myapp.controller.ec.budget
 * @description：预算编制
 * @author ： ly
 * @date: 2017-07-30 14:49
 */
@PermissionAnn(name="系统管理.现场管理.预算.预算编制",number="app.ec.budget.budgeting")
@Controller
@RequestMapping("ec/budget/budgeting")
public class BudgetingEditController extends BaseBillEditImportController {
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
    public IImportConvertService getImportConvertService() {
       return this.budgetingService;
    }

    @Override
    public String getImportTemplateName() {
        return "budget.xls";
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

        ColumnModel material = new ColumnModel("material",DataTypeEnum.F7,"id,name,specification");
        material.setClaz(MaterialInfo.class);
        budgetingDetail.getCols().add(material);

        ColumnModel measureUnitInfo = new ColumnModel("measureUnitInfo",DataTypeEnum.F7,"id,name");
        measureUnitInfo.setClaz(MeasureUnitInfo.class);
        budgetingDetail.getCols().add(measureUnitInfo);

        budgetingDetail.getCols().add(new ColumnModel("id",DataTypeEnum.PK));
        budgetingDetail.getCols().add(new ColumnModel("budgetaryPrice",DataTypeEnum.NUMBER));
        budgetingDetail.getCols().add(new ColumnModel("quantity",DataTypeEnum.NUMBER));
        budgetingDetail.getCols().add(new ColumnModel("specification"));
        budgetingDetail.getCols().add(new ColumnModel("remark"));
        budgetingDetail.getCols().add(new ColumnModel("materialName"));
        budgetingDetail.getCols().add(new ColumnModel("totalPrice",DataTypeEnum.NUMBER));
        budgetingDetail.getCols().add(new ColumnModel("materialType",DataTypeEnum.ENUM, MaterialType.class));
        cols.add(budgetingDetail);

        return cols;
    }

    @Override
    public Class getExcelToEntityClass() {
        return BudgetingModel.class;
    }

    @Override
    public ImportParams getExcelImportParams() {
        ImportParams params = new ImportParams();
        params.setTitleRows(0);
        params.setHeadRows(1);
        return params;
    }
}
