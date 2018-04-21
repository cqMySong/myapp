package com.myapp.controller.ec.stock.lease;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseBillEditController;
import com.myapp.core.entity.MaterialInfo;
import com.myapp.core.entity.MeasureUnitInfo;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.*;
import com.myapp.core.exception.db.QueryException;
import com.myapp.core.model.ColumnModel;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.entity.ec.engineering.SiteVisaInInfo;
import com.myapp.entity.ec.engineering.SiteVisaOutInfo;
import com.myapp.entity.ec.stock.MaterialLeaseInfo;
import com.myapp.service.ec.engineering.SiteVisaInService;
import com.myapp.service.ec.stock.MaterialLeaseService;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * @path：com.myapp.controller.ec.stock.lease
 * @description：周转材料、设备使用(出租)
 * @author ： ly
 * @date: 2017-07-30 14:49
 */
@PermissionAnn(name="系统管理.现场管理.库存管理.周转材料、设备使用(出租)",number="app.ec.stock.lease")
@Controller
@RequestMapping("ec/stock/lease")
public class MaterialLeaseEditController extends BaseBillEditController {
    @Resource
    private MaterialLeaseService materialLeaseService;
    @Override
    public Object createNewData() {
        MaterialLeaseInfo materialLeaseInfo = new MaterialLeaseInfo();
        materialLeaseInfo.setName("单据名称");
        return materialLeaseInfo;
    }

    @Override
    public CoreBaseInfo getEntityInfo() {
        return new MaterialLeaseInfo();
    }

    @Override
    public AbstractBaseService getService() {
        return this.materialLeaseService;
    }

    @Override
    public List<ColumnModel> getDataBinding() {
        List<ColumnModel> cols = super.getDataBinding();
        cols.add(new ColumnModel("name"));
        cols.add(new ColumnModel("number"));
        cols.add(new ColumnModel("leaseUnit",DataTypeEnum.STRING));
        cols.add(new ColumnModel("leaseDate",DataTypeEnum.DATE));
        cols.add(new ColumnModel("leaseCount",DataTypeEnum.NUMBER));
        cols.add(new ColumnModel("remark",DataTypeEnum.STRING));
        cols.add(new ColumnModel("billState",DataTypeEnum.ENUM, BillState.class));
        cols.add(new ColumnModel("createUser",DataTypeEnum.F7,UserInfo.class));
        cols.add(new ColumnModel("lastUpdateUser",DataTypeEnum.F7,UserInfo.class));
        cols.add(new ColumnModel("auditor",DataTypeEnum.F7,UserInfo.class));
        cols.add(new ColumnModel("createDate", DataTypeEnum.DATETIME));
        cols.add(new ColumnModel("auditDate", DataTypeEnum.DATETIME));
        cols.add(new ColumnModel("lastUpdateDate", DataTypeEnum.DATETIME));

        ColumnModel project = new ColumnModel("project",DataTypeEnum.F7,"id,name");
        project.setClaz(ProjectInfo.class);
        cols.add(project);

        ColumnModel materialInfo = new ColumnModel("materialInfo",DataTypeEnum.F7,"id,name");
        materialInfo.setClaz(MaterialInfo.class);
        cols.add(materialInfo);

        ColumnModel measureUnitInfo = new ColumnModel("measureUnitInfo",DataTypeEnum.F7,"id,name");
        measureUnitInfo.setClaz(MeasureUnitInfo.class);
        cols.add(measureUnitInfo);
        return cols;
    }
}
