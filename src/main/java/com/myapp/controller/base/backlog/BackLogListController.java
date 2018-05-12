package com.myapp.controller.base.backlog;

import com.myapp.core.annotation.AuthorAnn;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.annotation.PermissionItemAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseListController;
import com.myapp.core.entity.BackLogInfo;
import com.myapp.core.enums.BaseMethodEnum;
import com.myapp.core.enums.PermissionTypeEnum;
import com.myapp.core.exception.db.QueryException;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.model.WebDataModel;
import com.myapp.core.service.act.ActProcessService;
import com.myapp.core.service.act.ActTaskService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
    @Resource
    private ActProcessService processService;
    @Autowired
    private TaskService taskService;
    @Override
    public String getEditUrl() {
        return "backlog/transact";
    }

    @Override
    public String getListUrl() {
        return "backlog/backlogList";
    }

    @AuthorAnn(doLongin=true,doPermission=false)
    @Override
    public WebDataModel toList() {
        init();
        data = actTaskService.backLogInfoList(getCurUser().getNumber());
        return  ajaxModel();
    }

    @AuthorAnn(doLongin=true,doPermission=false)
    @RequestMapping("/transact/{taskId}/{taskDefinitionKey}/{processInstanceId}/{processDefinitionId}/{status}/{executionId}")
    public ModelAndView transact(@PathVariable String taskId,@PathVariable String taskDefinitionKey,
                                 @PathVariable String processInstanceId,@PathVariable String processDefinitionId,
                                 @PathVariable String status,@PathVariable String executionId){
        Map params = new HashMap();
        init();
        try {
            params.put("taskId",taskId);
            params.put("taskDefinitionKey",taskDefinitionKey);
            params.put("processInstanceId",processInstanceId);
            params.put("processDefinitionId",processDefinitionId);
            params.put("status",status);
            params.put("executionId",executionId);
            //获取业务单据id
            params.put("businessKey",actTaskService.getBusinessKey(taskId));
            //获取业务单据路径
            params.put("formKey",actTaskService.getFormKey(processDefinitionId,taskDefinitionKey));
            params.put("needAttention",taskService.getVariable(taskId,"needAttention"));
        } catch (Exception e) {
            e.printStackTrace();
            setExceptionMesg(e.getMessage());
        }
        setBaseMethod(BaseMethodEnum.FLOWAUDIT);
        return toPage(getEditUrl(), params);
    }


    /**
     * 读取带跟踪的图片
     */
    @AuthorAnn(doLongin=true,doPermission=false)
    @RequestMapping(value = "/photo/{procDefId}/{execId}")
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

    /**
     * 读取带跟踪的图片
     */
    @AuthorAnn(doLongin=true,doPermission=false)
    @RequestMapping(value = "/photo/{processInstanceId}")
    public void traceShowPhoto(@PathVariable("processInstanceId") String processInstanceId,
                           HttpServletResponse response) throws Exception {
        response.setContentType("image/jpeg");
        Task task = actTaskService.queryByProcessInstanceId(processInstanceId);
        InputStream imageStream = null;
        if(task!=null){
            imageStream = actTaskService.tracePhoto(task.getProcessDefinitionId(), task.getExecutionId());
        }else{
            HistoricProcessInstance historicProcessInstance = actTaskService.queryHisByProcessInstanceId(processInstanceId);
            imageStream = processService.resourceRead(historicProcessInstance.getProcessDefinitionId(), null, "image");
        }
        // 输出资源内容到相应对象
        byte[] b = new byte[1024];
        int len;
        while ((len = imageStream.read(b, 0, 1024)) != -1) {
            response.getOutputStream().write(b, 0, len);
        }
    }
    /**
     * 功能：跳转到审核意见界面
     * @param procInsId
     * @param model
     * @return
     */
    @AuthorAnn(doLongin=true,doPermission=false)
    @RequestMapping("/histoic/flow/show/{procInsId}")
    public String showTransactFlow(@PathVariable String procInsId, Model model){
        model.addAttribute("procInsId",procInsId);
        return "backlog/transactFlowList";
    }

    /**
     * 功能:审核意见办理列表
     * @param procInsId
     * @return
     * @throws QueryException
     */
    @AuthorAnn(doLongin=true,doPermission=false)
    @RequestMapping("/histoic/flow/{procInsId}")
    @ResponseBody
    public WebDataModel queryTransact(@PathVariable String procInsId) throws QueryException {
        init();
        data = actTaskService.histoicFlowList(procInsId,null,null);
        return ajaxModel();
    }
    @Override
    public AbstractBaseService getService() {
        return this.actTaskService;
    }

    public List<ColumnModel> getDataBinding() {
        List<ColumnModel> cols = super.getDataBinding();
        cols.add(new ColumnModel("taskName"));
        cols.add(new ColumnModel("title"));
        cols.add(new ColumnModel("name"));
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
