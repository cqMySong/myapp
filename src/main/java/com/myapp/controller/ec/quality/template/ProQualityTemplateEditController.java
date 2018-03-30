package com.myapp.controller.ec.quality.template;

import com.myapp.core.annotation.AuthorAnn;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseBillEditController;
import com.myapp.core.entity.MeasureUnitInfo;
import com.myapp.core.entity.PositionInfo;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.BillState;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.enums.PaymentMethod;
import com.myapp.core.enums.SubcontractExpenseType;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.model.WebDataModel;
import com.myapp.entity.ec.basedata.*;
import com.myapp.entity.ec.budget.BudgetingDetailInfo;
import com.myapp.entity.ec.engineering.SubcontractInfo;
import com.myapp.entity.ec.quality.ProQualityTemplateDetailInfo;
import com.myapp.entity.ec.quality.ProQualityTemplateInfo;
import com.myapp.service.ec.engineering.SubcontractService;
import com.myapp.service.ec.quality.ProQualityTemplateDetailService;
import com.myapp.service.ec.quality.ProQualityTemplateService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @path：com.myapp.controller.ec.quality.template
 * @description：分包合同
 * @author ： ly
 * @date: 2017-07-30 14:49
 */
@PermissionAnn(name="系统管理.现场管理.质量管理.项目质量样板",number="app.ec.quality.template")
@Controller
@RequestMapping("ec/quality/template")
public class ProQualityTemplateEditController extends BaseBillEditController {
    @Resource
    private ProQualityTemplateService proQualityTemplateService;
    @Resource
    private ProQualityTemplateDetailService proQualityTemplateDetailService;
    @Override
    public Object createNewData() {
        return new ProQualityTemplateInfo();
    }

    @Override
    public CoreBaseInfo getEntityInfo() {
        return new ProQualityTemplateInfo();
    }

    @Override
    public AbstractBaseService getService() {
        return this.proQualityTemplateService;
    }


    @Override
    public List<ColumnModel> getDataBinding() {
        List<ColumnModel> cols = super.getDataBinding();
        cols.add(new ColumnModel("name"));
        cols.add(new ColumnModel("number"));
        cols.add(new ColumnModel("expectStartDate",DataTypeEnum.DATE));
        cols.add(new ColumnModel("acceptanceDate",DataTypeEnum.DATE));
        cols.add(new ColumnModel("billState",DataTypeEnum.ENUM, BillState.class));
        cols.add(new ColumnModel("createUser",DataTypeEnum.F7,UserInfo.class));
        cols.add(new ColumnModel("lastUpdateUser",DataTypeEnum.F7,UserInfo.class));
        cols.add(new ColumnModel("auditor",DataTypeEnum.F7,UserInfo.class));
        cols.add(new ColumnModel("createDate", DataTypeEnum.DATE));
        cols.add(new ColumnModel("auditDate", DataTypeEnum.DATE));
        cols.add(new ColumnModel("qualityTemplateInfo", DataTypeEnum.F7,QualityTemplateInfo.class));
        cols.add(new ColumnModel("lastUpdateDate", DataTypeEnum.DATE));
        ColumnModel project = new ColumnModel("project",DataTypeEnum.F7,"id,name");
        project.setClaz(ProjectInfo.class);
        cols.add(project);

        ColumnModel branchBaseWbs = new ColumnModel("branchBaseWbs",DataTypeEnum.F7,"id,name");
        branchBaseWbs.setClaz(ProjectWbsInfo.class);
        cols.add(branchBaseWbs);

        ColumnModel subentry = new ColumnModel("subentry",DataTypeEnum.F7,"id,name");
        subentry.setClaz(ProjectWbsInfo.class);
        cols.add(subentry);
        return cols;
    }

    @RequestMapping("/job/require/save")
    @ResponseBody
    public WebDataModel saveJobRequire(String jobRequireItems) {
        WebDataModel webDataModel = new WebDataModel();
        try {
            proQualityTemplateDetailService.saveJobRequire(jobRequireItems,getCurUser());
            webDataModel.setStatusCode(STATUSCODE_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            webDataModel.setStatusMesg("保存工作要求失败");
            webDataModel.setStatusCode(STATUSCODE_ERROR);
        }
        return webDataModel;
    }
    @AuthorAnn(doLongin=true,doPermission=false)
    @RequestMapping("/query/job")
    @ResponseBody
    public WebDataModel queryJobRequire(String proQualityTemplateId) {
        this.data = proQualityTemplateDetailService.queryJobRequireByProQualityTemplateId(proQualityTemplateId);
        return ajaxModel();
    }
}
