package com.myapp.controller.ec.basedata.bulletin;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseBillEditController;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.*;
import com.myapp.core.exception.db.QueryException;
import com.myapp.core.model.ColumnModel;
import com.myapp.entity.ec.basedata.BulletinInfo;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.entity.ec.engineering.SiteVisaInInfo;
import com.myapp.entity.ec.engineering.SiteVisaOutInfo;
import com.myapp.service.ec.basedata.BulletinService;
import com.myapp.service.ec.engineering.SiteVisaInService;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * @path：com.myapp.controller.ec.basedata.bulletin
 * @description：通知通报
 * @author ： ly
 * @date: 2017-07-30 14:49
 */
@PermissionAnn(name="系统管理.现场管理.基础数据.通知通报",number="app.ec.basedata.bulletin")
@Controller
@RequestMapping("ec/basedata/bulletin")
public class BulletinEditController extends BaseBillEditController {
    @Resource
    private BulletinService bulletinService;
    @Override
    public Object createNewData() {
        BulletinInfo bulletinInfo = new BulletinInfo();
        bulletinInfo.setCreateUser(getCurUser());
        return bulletinInfo;
    }

    @Override
    public CoreBaseInfo getEntityInfo() {
        return new BulletinInfo();
    }

    @Override
    public AbstractBaseService getService() {
        return this.bulletinService;
    }

    @Override
    public List<ColumnModel> getDataBinding() {
        List<ColumnModel> cols = super.getDataBinding();
        cols.add(new ColumnModel("name"));
        cols.add(new ColumnModel("number"));
        cols.add(new ColumnModel("dispatchUnit",DataTypeEnum.STRING));
        cols.add(new ColumnModel("content",DataTypeEnum.STRING));
        cols.add(new ColumnModel("bulletinDate",DataTypeEnum.DATE));
        cols.add(new ColumnModel("createDate", DataTypeEnum.DATE));
        cols.add(new ColumnModel("lastUpdateDate", DataTypeEnum.DATE));

        ColumnModel project = new ColumnModel("project",DataTypeEnum.F7,"id,name");
        project.setClaz(ProjectInfo.class);
        cols.add(project);

        ColumnModel createUser = new ColumnModel("createUser",DataTypeEnum.F7,"id,name");
        createUser.setClaz(UserInfo.class);
        cols.add(createUser);

        return cols;
    }
}
