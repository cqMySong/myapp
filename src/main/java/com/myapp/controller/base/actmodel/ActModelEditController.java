package com.myapp.controller.base.actmodel;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseEditController;
import com.myapp.core.entity.ActModelInfo;
import com.myapp.core.enums.BaseMethodEnum;
import com.myapp.core.exception.db.QueryException;
import com.myapp.core.exception.db.SaveException;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.service.act.ActModelService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * 包路径：com.myapp.controller.base.flow
 * 功能说明：流程模型编辑信息
 * 创建人： ly
 * 创建时间: 2017-06-24 23:29
 */
@PermissionAnn(name="系统管理.模型管理",number="app.actmodel")
@RequestMapping("base/actmodel")
@Controller
public class ActModelEditController extends BaseEditController {
    @Resource
    private ActModelService actModelService;

    @Override
    public Object createNewData() {
        return new ActModelInfo();
    }

    @Override
    public CoreBaseInfo getEntityInfo() {
        return new ActModelInfo();
    }

    @Override
    protected void storeData(BaseMethodEnum bme) throws SaveException, QueryException {
        if(BaseMethodEnum.SAVE.equals(bme)){
            getService().saveEntity(getEditData());
        }
    }

    protected void beforeStoreData(BaseMethodEnum bme, Object editData) {
        super.beforeStoreData(bme,editData);
        //保存前可以做对数据进行处理
        if(BaseMethodEnum.SAVE.equals(bme)){
            if(editData!=null&&editData instanceof ActModelInfo){
                ActModelInfo actModelInfo = (ActModelInfo) editData;
                actModelInfo.setUserInfo(getCurUser());
            }
        }
    }

    @Override
    public AbstractBaseService getService() {
        return this.actModelService;
    }

    @Override
    public List<ColumnModel> getDataBinding() {
        List<ColumnModel> cols = super.getDataBinding();
        cols.add(new ColumnModel("name"));
        cols.add(new ColumnModel("number"));
        cols.add(new ColumnModel("category"));
        cols.add(new ColumnModel("description"));
        return  cols;
    }
}
