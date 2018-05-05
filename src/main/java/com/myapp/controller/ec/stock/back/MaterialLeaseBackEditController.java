package com.myapp.controller.ec.stock.back;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseBillEditController;
import com.myapp.core.entity.MaterialInfo;
import com.myapp.core.entity.MeasureUnitInfo;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.BaseMethodEnum;
import com.myapp.core.enums.BillState;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.enums.MaterialType;
import com.myapp.core.exception.db.QueryException;
import com.myapp.core.model.ColumnModel;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.entity.ec.stock.MaterialLeaseBackDetailInfo;
import com.myapp.entity.ec.stock.MaterialLeaseBackInfo;
import com.myapp.entity.ec.stock.MaterialLeaseDetailInfo;
import com.myapp.entity.ec.stock.MaterialLeaseInfo;
import com.myapp.service.ec.stock.MaterialLeaseBackService;
import com.myapp.service.ec.stock.MaterialLeaseService;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * @path：com.myapp.controller.ec.stock.back
 * @description：周转材料、设备使用(归还)
 * @author ： ly
 * @date: 2017-07-30 14:49
 */
@PermissionAnn(name="系统管理.现场管理.库存管理.周转材料、设备使用(归还)",number="app.ec.stock.back")
@Controller
@RequestMapping("ec/stock/back")
public class MaterialLeaseBackEditController extends BaseBillEditController {
    @Resource
    private MaterialLeaseBackService materialLeaseBackService;
    @Override
    public Object createNewData() {
        MaterialLeaseBackInfo materialLeaseBackInfo = new MaterialLeaseBackInfo();
        return materialLeaseBackInfo;
    }

    @Override
    public CoreBaseInfo getEntityInfo() {
        return new MaterialLeaseBackInfo();
    }

    @Override
    public AbstractBaseService getService() {
        return this.materialLeaseBackService;
    }


    @Override
    public List<ColumnModel> getDataBinding() {
        List<ColumnModel> cols = super.getDataBinding();
        cols.add(new ColumnModel("name"));
        cols.add(new ColumnModel("number"));
        cols.add(new ColumnModel("backDate",DataTypeEnum.DATE));
        cols.add(new ColumnModel("leaseUnit",DataTypeEnum.STRING));
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

        ColumnModel materialLeaseBackDetailInfos = new ColumnModel("materialLeaseBackDetailInfos", DataTypeEnum.ENTRY,
                MaterialLeaseBackDetailInfo.class);
        ColumnModel materialInfo = new ColumnModel("materialInfo", DataTypeEnum.F7,"id,name");
        materialInfo.setClaz(MaterialInfo.class);
        materialLeaseBackDetailInfos.getCols().add(materialInfo);

        ColumnModel measureUnitInfo = new ColumnModel("measureUnitInfo", DataTypeEnum.F7,"id,name");
        measureUnitInfo.setClaz(MeasureUnitInfo.class);
        materialLeaseBackDetailInfos.getCols().add(measureUnitInfo);

        materialLeaseBackDetailInfos.getCols().add(new ColumnModel("materialType", DataTypeEnum.ENUM, MaterialType.class));
        materialLeaseBackDetailInfos.getCols().add(new ColumnModel("backCount", DataTypeEnum.NUMBER));
        materialLeaseBackDetailInfos.getCols().add(new ColumnModel("remark", DataTypeEnum.STRING));
        materialLeaseBackDetailInfos.getCols().add(new ColumnModel("specification", DataTypeEnum.STRING));
        cols.add(materialLeaseBackDetailInfos);
        return cols;
    }
}
