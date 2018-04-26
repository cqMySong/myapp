package com.myapp.controller.ec.basedata.sitetransfer;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseListController;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.enums.TransferTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.service.ec.basedata.SiteTransferService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * @path：com.myapp.controller.base.sitetransfer
 * @description：
 * @author ： ly
 * @date: 2017-08-25 17:28
 */
@PermissionAnn(name="系统管理.现场管理.基础数据.场地移交协议",number="app.ec.basedata.sitetransfer")
@Controller
@RequestMapping("ec/basedata/sitetransfers")
public class SiteTransferListController extends BaseListController {
    @Resource
    private SiteTransferService siteTransferService;
    @Override
    public String getEditUrl() {
        return "ec/basedata/sitetransfer/siteTransferEdit";
    }

    @Override
    public String getListUrl() {
        return "ec/basedata/sitetransfer//siteTransferList";
    }

    @Override
    public AbstractBaseService getService() {
        return this.siteTransferService;
    }
    @Override
    public List<ColumnModel> getDataBinding() {
        List<ColumnModel> cols = super.getDataBinding();
        cols.add(new ColumnModel("name"));
        cols.add(new ColumnModel("number"));
        cols.add(new ColumnModel("transferType",DataTypeEnum.ENUM, TransferTypeEnum.class));
        cols.add(new ColumnModel("transferUnit"));
        cols.add(new ColumnModel("receivingUnit"));
        cols.add(new ColumnModel("transferDate", DataTypeEnum.DATE));
        cols.add(new ColumnModel("transferRange"));
        return cols;
    }

}
