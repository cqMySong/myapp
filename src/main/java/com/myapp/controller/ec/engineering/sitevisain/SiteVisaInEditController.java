package com.myapp.controller.ec.engineering.sitevisain;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseBillEditController;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.*;
import com.myapp.core.exception.db.QueryException;
import com.myapp.core.model.ColumnModel;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.entity.ec.engineering.SiteVisaInInfo;
import com.myapp.entity.ec.engineering.SiteVisaOutInfo;
import com.myapp.service.ec.engineering.SiteVisaInService;
import com.myapp.service.ec.engineering.SiteVisaOutService;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * @path：com.myapp.controller.ec.sengineering.sitevisain
 * @description：现场签证(收入)
 * @author ： ly
 * @date: 2017-07-30 14:49
 */
@PermissionAnn(name="系统管理.现场管理.工程合同.现场签证(收入)",number="app.ec.engineering.sitevisain")
@Controller
@RequestMapping("ec/engineering/sitevisain")
public class SiteVisaInEditController extends BaseBillEditController {
    @Resource
    private SiteVisaInService siteVisaInService;
    @Override
    public Object createNewData() {
        return new SiteVisaInInfo();
    }

    @Override
    public CoreBaseInfo getEntityInfo() {
        return new SiteVisaInInfo();
    }

    @Override
    public AbstractBaseService getService() {
        return this.siteVisaInService;
    }

    @Override
    public void afterOperate(BaseMethodEnum bme) throws HibernateException, QueryException {
        super.afterOperate(bme);
        if (this.data != null) {
            if (!bme.getValue().equals(BaseMethodEnum.ADDNEW.getValue()) && this.data instanceof JSONObject) {
                JSONObject siteVisaInJson = ((JSONObject) this.data);
                JSONObject visaOutJson = siteVisaInJson.getJSONObject("siteVisaOutInfo");
                siteVisaInJson.put("visaOutNumber",visaOutJson.getString("number"));
                this.data = siteVisaInJson;
            }
        }
    }
    @Override
    public List<ColumnModel> getDataBinding() {
        List<ColumnModel> cols = super.getDataBinding();
        cols.add(new ColumnModel("name"));
        cols.add(new ColumnModel("number"));
        cols.add(new ColumnModel("chargingContent"));
        cols.add(new ColumnModel("visaUnit",DataTypeEnum.STRING));
        cols.add(new ColumnModel("amount",DataTypeEnum.NUMBER));
        cols.add(new ColumnModel("visaDate",DataTypeEnum.DATE));
        cols.add(new ColumnModel("chargingBasis", DataTypeEnum.ENUM,ChargingBasis.class));
        cols.add(new ColumnModel("handleType", DataTypeEnum.ENUM,HandleType.class));
        cols.add(new ColumnModel("billState",DataTypeEnum.ENUM, BillState.class));
        cols.add(new ColumnModel("createUser",DataTypeEnum.F7,UserInfo.class));
        cols.add(new ColumnModel("lastUpdateUser",DataTypeEnum.F7,UserInfo.class));
        cols.add(new ColumnModel("auditor",DataTypeEnum.F7,UserInfo.class));
        cols.add(new ColumnModel("createDate", DataTypeEnum.DATETIME));
        cols.add(new ColumnModel("auditDate", DataTypeEnum.DATETIME));
        cols.add(new ColumnModel("lastUpdateDate", DataTypeEnum.DATETIME));

        ColumnModel siteVisaOutInfo = new ColumnModel("siteVisaOutInfo",DataTypeEnum.F7,"id,name,number");
        siteVisaOutInfo.setClaz(SiteVisaOutInfo.class);
        cols.add(siteVisaOutInfo);

        ColumnModel project = new ColumnModel("project",DataTypeEnum.F7,"id,name");
        project.setClaz(ProjectInfo.class);
        cols.add(project);

        return cols;
    }
}
