package com.myapp.controller.base.material;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseDataListController;
import com.myapp.core.entity.MeasureUnitInfo;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.enums.MaterialType;
import com.myapp.core.service.MaterialService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * 包路径：com.myapp.controller.base.material
 * 功能说明：
 * @author ： ly
 * @date: 2017-08-25 17:28
 */
@PermissionAnn(name="系统管理.基础资料.物料信息",number="app.basedata.material")
@Controller
@RequestMapping("base/materials")
public class MaterialListController extends BaseDataListController {
    @Resource
    private MaterialService materialService;
    @Override
    public String getEditUrl() {
        return "basedata/material/materialEdit";
    }

    @Override
    public String getListUrl() {
        return "basedata/material/materialList";
    }

    @Override
    public AbstractBaseService getService() {
        return this.materialService;
    }
    @Override
    public List<ColumnModel> getDataBinding() {
        List<ColumnModel> cols = super.getDataBinding();
        cols.add(new ColumnModel("pinyin"));
        cols.add(new ColumnModel("specification"));
        ColumnModel measureUnitInfo = new ColumnModel("unit",DataTypeEnum.F7,"id,name");
        measureUnitInfo.setClaz(MeasureUnitInfo.class);
        cols.add(measureUnitInfo);
        cols.add(new ColumnModel("materialType",DataTypeEnum.ENUM,MaterialType.class));
        return cols;
    }

}
