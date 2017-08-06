package com.myapp.controller.base.backlog;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseEditController;
import com.myapp.core.entity.BackLogInfo;
import com.myapp.core.enums.BaseMethodEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.model.WebDataModel;
import com.myapp.core.service.act.ActTaskService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * 包路径：com.myapp.controller.base.backlog
 * 功能说明：待办事项编辑信息
 * 创建人： ly
 * 创建时间: 2017-06-24 23:29
 */
@PermissionAnn(name="系统管理.待办事项",number="app.backlog")
@RequestMapping("base/backlog")
@Controller
public class BackLogEditController extends BaseEditController {
    @Resource
    private ActTaskService actTaskService;

    @Override
    public Object createNewData() {
        return new BackLogInfo();
    }

    @Override
    public CoreBaseInfo getEntityInfo() {
        return new BackLogInfo();
    }

    @Override
    protected boolean verifyInput(Object editData) {
        return  true;
    }

    @RequestMapping("/audit")
    @ResponseBody
    public WebDataModel subAudit(){
        try {
            super.beforeOperate(BaseMethodEnum.SAVE);
            BackLogInfo backLogInfo = (BackLogInfo)getEditData();
            backLogInfo.setAssignee(getCurUser().getNumber());
            actTaskService.complete(backLogInfo);
            setInfoMesg("审核操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            setInfoMesg(e.getMessage());
        }
        return ajaxModel();
    }

    @Override
    public AbstractBaseService getService() {
        return this.actTaskService;
    }

    @Override
    public List<ColumnModel> getDataBinding() {
        List<ColumnModel> cols = super.getDataBinding();
        cols.add(new ColumnModel("pass"));
        cols.add(new ColumnModel("reason"));
        cols.add(new ColumnModel("taskId"));
        cols.add(new ColumnModel("taskDefinitionKey"));
        cols.add(new ColumnModel("processInstanceId"));
        return  cols;
    }
}
