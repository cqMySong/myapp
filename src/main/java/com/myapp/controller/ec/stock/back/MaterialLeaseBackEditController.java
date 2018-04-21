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
import com.myapp.entity.ec.stock.MaterialLeaseBackInfo;
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
        materialLeaseBackInfo.setName("单据名称");
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
    public void afterOperate(BaseMethodEnum bme) throws HibernateException, QueryException {
        super.afterOperate(bme);
        if(this.data!=null){
            if(!bme.getValue().equals(BaseMethodEnum.ADDNEW.getValue())&&this.data instanceof JSONObject){
                JSONObject jsonObject = (JSONObject) this.data;
                JSONObject materialLeaseInfo = jsonObject.getJSONObject("materialLeaseInfo");
                materialLeaseInfo.put("name",materialLeaseInfo.getString("number"));
                jsonObject.put("materialLeaseInfo",materialLeaseInfo);
                jsonObject.put("leaseUnit",materialLeaseInfo.getString("leaseUnit"));
                this.data = jsonObject;
            }
        }
    }

    @Override
    public List<ColumnModel> getDataBinding() {
        List<ColumnModel> cols = super.getDataBinding();
        cols.add(new ColumnModel("name"));
        cols.add(new ColumnModel("number"));
        cols.add(new ColumnModel("backDate",DataTypeEnum.DATE));
        cols.add(new ColumnModel("backCount",DataTypeEnum.NUMBER));
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

        ColumnModel materialLeaseInfo = new ColumnModel("materialLeaseInfo",DataTypeEnum.F7,"id,number,leaseUnit");
        materialLeaseInfo.setClaz(MaterialLeaseInfo.class);
        cols.add(materialLeaseInfo);

        ColumnModel materialInfo = new ColumnModel("materialInfo",DataTypeEnum.F7,"id,name");
        materialInfo.setClaz(MaterialInfo.class);
        cols.add(materialInfo);

        ColumnModel measureUnitInfo = new ColumnModel("measureUnitInfo",DataTypeEnum.F7,"id,name");
        measureUnitInfo.setClaz(MeasureUnitInfo.class);
        cols.add(measureUnitInfo);
        return cols;
    }
}
