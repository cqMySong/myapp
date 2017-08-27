package com.myapp.controller.ec.basedata.datadic;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseDataListController;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.enums.DataDicType;
import com.myapp.service.ec.basedata.DataDicService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * 包路径：com.myapp.controller.ec.basedata.datadic
 * 功能说明：
 * 创建人： ly
 * 创建时间: 2017-08-25 17:28
 */
@PermissionAnn(name="系统管理.现场管理.基础数据.数据字典",number="app.ec.basedata.datadic")
@Controller
@RequestMapping("ec/basedata/datadics")
public class DataDicListController extends BaseDataListController {
    @Resource
    private DataDicService dataDicService;
    @Override
    public String getEditUrl() {
        return "ec/basedata/datadic/dataDicEdit";
    }

    @Override
    public String getListUrl() {
        return "ec/basedata/datadic/dataDicList";
    }

    @Override
    public AbstractBaseService getService() {
        return this.dataDicService;
    }

    public List<ColumnModel> getDataBinding() {
        List<ColumnModel> cols = super.getDataBinding();
        cols.add(new ColumnModel("pinyinCode"));
        cols.add(new ColumnModel("dataDicType",DataTypeEnum.ENUM,DataDicType.class));
        return cols;
    }

}
