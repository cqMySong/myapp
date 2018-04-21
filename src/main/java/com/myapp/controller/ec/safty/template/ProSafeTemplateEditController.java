package com.myapp.controller.ec.safty.template;

import com.myapp.core.annotation.AuthorAnn;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseBillEditController;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.BillState;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.model.WebDataModel;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.entity.ec.basedata.ProjectWbsInfo;
import com.myapp.entity.ec.basedata.SafeTemplateInfo;
import com.myapp.entity.ec.safty.ProSafeTemplateInfo;
import com.myapp.service.ec.safty.ProSafeTemplateDetailService;
import com.myapp.service.ec.safty.ProSafeTemplateService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @path：com.myapp.controller.ec.quality.template
 * @description：项目安全样板
 * @author ： ly
 * @date: 2017-07-30 14:49
 */
@PermissionAnn(name="系统管理.现场管理.安全管理.项目安全样板",number="app.ec.safe.template")
@Controller
@RequestMapping("ec/safe/template")
public class ProSafeTemplateEditController extends BaseBillEditController {
    @Resource
    private ProSafeTemplateService proSafeTemplateService;
    @Resource
    private ProSafeTemplateDetailService proSafeTemplateDetailService;
    @Override
    public Object createNewData() {
        return new ProSafeTemplateInfo();
    }

    @Override
    public CoreBaseInfo getEntityInfo() {
        return new ProSafeTemplateInfo();
    }

    @Override
    public AbstractBaseService getService() {
        return this.proSafeTemplateService;
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
        cols.add(new ColumnModel("createDate", DataTypeEnum.DATETIME));
        cols.add(new ColumnModel("auditDate", DataTypeEnum.DATETIME));
        cols.add(new ColumnModel("lastUpdateDate", DataTypeEnum.DATETIME));
        cols.add(new ColumnModel("safeTemplateInfo", DataTypeEnum.F7,SafeTemplateInfo.class));
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

    @AuthorAnn(doLongin=true,doPermission=false)
    @RequestMapping("/job/require/save")
    @ResponseBody
    public WebDataModel saveJobRequire(String jobRequireItems) {
        WebDataModel webDataModel = new WebDataModel();
        try {
            proSafeTemplateDetailService.saveJobRequire(jobRequireItems,getCurUser());
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
    public WebDataModel queryJobRequire(String proSafeTemplateId) {
        this.data = proSafeTemplateDetailService.queryJobRequireByProSafeTemplateId(proSafeTemplateId);
        return ajaxModel();
    }
}
