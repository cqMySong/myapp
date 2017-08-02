package com.myapp.controller.base.backlog;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.annotation.PermissionItemAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseListController;
import com.myapp.core.enums.BaseMethodEnum;
import com.myapp.core.enums.PermissionTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.model.WebDataModel;
import com.myapp.core.service.act.ActTaskService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 包路径：com.myapp.controller.base.flow
 * 功能说明：流程使用信息
 * 创建人： ly
 * 创建时间: 2017-06-24 23:29
 */
@PermissionAnn(name="系统管理.待办事项",number="app.backlog")
@RequestMapping("base/backlogs")
@Controller
public class BackLogListController extends BaseListController {
    @Resource
    private ActTaskService actTaskService;

    @Override
    public String getEditUrl() {
        return "backlog/transact";
    }

    @Override
    public String getListUrl() {
        return "backlog/backlogList";
    }

    @Override
    public WebDataModel toList() {
        data = actTaskService.backLogInfoList(getCurUser().getNumber());
        return  ajaxModel();
    }

    @PermissionItemAnn(name="办理",number="transact",type= PermissionTypeEnum.PAGEADDFUNCTION)
    @RequestMapping("/transact/{taskId}/{taskDefinitionKey}/{processInstanceId}/{processDefinitionId}/{status}")
    public ModelAndView transact(@PathVariable String taskId,@PathVariable String taskDefinitionKey,
                                 @PathVariable String processInstanceId,@PathVariable String processDefinitionId,
                                 @PathVariable String status){
        Map params = new HashMap();
        init();
        try {
            params.put("taskId",taskId);
            params.put("taskDefinitionKey",taskDefinitionKey);
            params.put("processInstanceId",processInstanceId);
            params.put("processDefinitionId",processDefinitionId);
            params.put("status",status);
            //获取业务单据id
            params.put("businessKey",actTaskService.getBusinessKey(taskId));
            //获取业务单据路径
            params.put("formKey",actTaskService.getFormKey(processDefinitionId,taskDefinitionKey));
        } catch (Exception e) {
            e.printStackTrace();
            setExceptionMesg(e.getMessage());
        }
        setBaseMethod(BaseMethodEnum.AUDIT);
        return toPage(getEditUrl(), params);
    }

    /**
     * 读取带跟踪的图片
     */
    @RequestMapping(value = "photo/{procDefId}/{execId}")
    public void tracePhoto(@PathVariable("procDefId") String procDefId, @PathVariable("execId") String execId,
                           HttpServletResponse response) throws Exception {
        response.setContentType("image/jpeg");
        InputStream imageStream = actTaskService.tracePhoto(procDefId, execId);
        // 输出资源内容到相应对象
        byte[] b = new byte[1024];
        int len;
        while ((len = imageStream.read(b, 0, 1024)) != -1) {
            response.getOutputStream().write(b, 0, len);
        }
    }
    @Override
    public AbstractBaseService getService() {
        return this.actTaskService;
    }

    public List<ColumnModel> getDataBinding() {
        List<ColumnModel> cols = super.getDataBinding();
        cols.add(new ColumnModel("taskName"));
        cols.add(new ColumnModel("title"));
        cols.add(new ColumnModel("assignee"));
        cols.add(new ColumnModel("executionId"));
        cols.add(new ColumnModel("taskDefinitionKey"));
        cols.add(new ColumnModel("processInstanceId"));
        cols.add(new ColumnModel("processDefinitionId"));
        cols.add(new ColumnModel("status"));
        cols.add(new ColumnModel("id"));
        cols.add(new ColumnModel("createDate"));
        return cols;
    }

}
