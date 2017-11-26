package com.myapp.controller.ec.engineering.sitevisaout;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseBillEditController;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.*;
import com.myapp.core.model.ColumnModel;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.entity.ec.engineering.SiteVisaOutInfo;
import com.myapp.service.ec.engineering.SiteVisaOutService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * @path：com.myapp.controller.ec.sengineering.sitevisaout
 * @description：现场签证(支出)
 * @author ： ly
 * @date: 2017-07-30 14:49
 */
@PermissionAnn(name="系统管理.现场管理.工程合同.现场签证(支出)",number="app.ec.engineering.sitevisaout")
@Controller
@RequestMapping("ec/engineering/sitevisaout")
public class SiteVisaOutEditController extends BaseBillEditController {
    @Resource
    private SiteVisaOutService siteVisaOutService;
    @Override
    public Object createNewData() {
        return new SiteVisaOutInfo();
    }

    @Override
    public CoreBaseInfo getEntityInfo() {
        return new SiteVisaOutInfo();
    }

    @Override
    public AbstractBaseService getService() {
        return this.siteVisaOutService;
    }

    @Override
    public List<ColumnModel> getDataBinding() {
        List<ColumnModel> cols = super.getDataBinding();
        cols.add(new ColumnModel("name"));
        cols.add(new ColumnModel("number"));
        cols.add(new ColumnModel("workPart"));
        cols.add(new ColumnModel("jobContent"));
        cols.add(new ColumnModel("chargingContent"));
        cols.add(new ColumnModel("visaUnit",DataTypeEnum.STRING));
        cols.add(new ColumnModel("amount",DataTypeEnum.NUMBER));
        cols.add(new ColumnModel("visaDate",DataTypeEnum.DATE));
        cols.add(new ColumnModel("chargingBasis", DataTypeEnum.ENUM,ChargingBasis.class));
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

        return cols;
    }
}
