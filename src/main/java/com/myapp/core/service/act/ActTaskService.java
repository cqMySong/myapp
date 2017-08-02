package com.myapp.core.service.act;
import com.myapp.core.entity.BackLogInfo;
import com.myapp.core.model.PageModel;
import com.myapp.core.service.base.BaseInterfaceService;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.*;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 包路径：com.myapp.core.service.act
 * 功能说明：任务管理
 * 创建人： ly
 * 创建时间: 2017-07-24 22:18
 */
@Service("actTaskService")
public class ActTaskService extends BaseInterfaceService<BackLogInfo> {
    @Autowired
    private ProcessEngine processEngine;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private FormService formService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private IdentityService identityService;

    /**
     * 启动流程
     * @param procDefKey 流程定义KEY
     * @param businessId	业务表编号
     * @param title	流程标题，显示在待办任务标题
     * @param vars		流程变量
     * @param userName 流程发起人
     * @return 流程实例ID
     */
    public String startProcess(String procDefKey, String businessId,
                               String title, Map<String, Object> vars,String userName) {
        // 用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
        identityService.setAuthenticatedUserId(userName);
        // 设置流程变量
        if (vars == null){
            vars = new HashMap<>();
        }
        // 设置流程标题
        if (StringUtils.isNotBlank(title)){
            vars.put("title", title);
        }
        // 启动流程
        ProcessInstance procIns = runtimeService.startProcessInstanceByKey(procDefKey,
                businessId, vars);
        return procIns.getId();
    }

    /**
     * 获取待办列表
     * @param userName 流程定义标识
     * @return
     */
    public PageModel<BackLogInfo> backLogInfoList(String userName){
        List<BackLogInfo> result = new ArrayList<BackLogInfo>();
        // =============== 已经签收的任务  ===============
        TaskQuery todoTaskQuery = taskService.createTaskQuery().taskAssignee(userName).active()
                .includeProcessVariables().orderByTaskCreateTime().desc();
        // 查询列表
        List<Task> todoList = todoTaskQuery.list();
        BackLogInfo backLogInfo = null;
        ProcessDefinition processDefinition = null;
        for (Task task : todoList) {
            backLogInfo = new BackLogInfo();
            backLogInfo.setAssignee(task.getAssignee());
            backLogInfo.setId(task.getId());
            backLogInfo.setTaskName(task.getName());
            backLogInfo.setTitle(task.getProcessVariables()!=null?task.getProcessVariables().get("title").toString():"");
            processDefinition = queryProcessDefinitionByProcDefId(task.getProcessDefinitionId());
            backLogInfo.setTaskDefinitionKey(task.getTaskDefinitionKey());
            backLogInfo.setProcessInstanceId(task.getProcessInstanceId());
            backLogInfo.setProcessDefinitionId(task.getProcessDefinitionId());
            backLogInfo.setExecutionId(task.getExecutionId());
            backLogInfo.setCreateDate(task.getCreateTime());
            backLogInfo.setName(processDefinition.getName());
            backLogInfo.setStatus("todo");
            result.add(backLogInfo);
        }

        // =============== 等待签收的任务  ===============
        TaskQuery toClaimQuery = taskService.createTaskQuery().taskCandidateUser(userName)
                .includeProcessVariables().active().orderByTaskCreateTime().desc();

        // 查询列表
        List<Task> toClaimList = toClaimQuery.list();
        for (Task task : toClaimList) {
            backLogInfo = new BackLogInfo();
            backLogInfo.setAssignee(task.getAssignee());
            backLogInfo.setId(task.getId());
            backLogInfo.setTaskName(task.getName());
            backLogInfo.setTaskDefinitionKey(task.getTaskDefinitionKey());
            backLogInfo.setProcessInstanceId(task.getProcessInstanceId());
            backLogInfo.setProcessDefinitionId(task.getProcessDefinitionId());
            backLogInfo.setExecutionId(task.getExecutionId());
            backLogInfo.setCreateDate(task.getCreateTime());
            processDefinition = queryProcessDefinitionByProcDefId(task.getProcessDefinitionId());
            backLogInfo.setName(processDefinition.getName());
            backLogInfo.setTitle(task.getProcessVariables()!=null?task.getProcessVariables().get("title").toString():"");
            backLogInfo.setStatus("claim");
            result.add(backLogInfo);
        }
        PageModel<BackLogInfo> pageModel = new PageModel<BackLogInfo>(1,result.size()==0?1:result.size(),result.size());
        pageModel.setDatas(result);
        return pageModel;
    }

    /**
     * 功能：根据流程定义id,获取流程信息
     * @param procDefId 流程定义ID
     * @return
     */
    public ProcessDefinition queryProcessDefinitionByProcDefId(String procDefId){
        return repositoryService.createProcessDefinitionQuery().processDefinitionId(procDefId).singleResult();
    }

    /**
     * 功能：根据任务id,获取业务主键
     * @param taskId
     * @return
     */
    public String getBusinessKey(String taskId){
        //1  获取任务对象
        Task task  =  taskService.createTaskQuery().taskId(taskId).singleResult();
        //2  通过任务对象获取流程实例
        ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
        //3 通过流程实例获取“业务键”
        return pi.getBusinessKey();
    }

    /**
     * 提交任务, 并保存意见
     * @param backLogInfo 任务ID
     */
    public void complete(BackLogInfo backLogInfo){
        // 添加意见
        if (StringUtils.isNotBlank(backLogInfo.getProcessInstanceId()) && StringUtils.isNotBlank(backLogInfo.getReason())){
            taskService.addComment(backLogInfo.getTaskId(), backLogInfo.getProcessInstanceId(), backLogInfo.getReason());
        }
        Map<String,Object> vars = backLogInfo.getVars();
        // 设置流程标题
        if (StringUtils.isNotBlank(backLogInfo.getTitle())){
            vars.put("title", backLogInfo.getTitle());
        }
        // 提交任务
        taskService.complete(backLogInfo.getTaskId(), vars);
    }

    /**
     * 获取流程表单（首先获取任务节点表单KEY，如果没有则取流程开始节点表单KEY）
     * @return
     */
    public String getFormKey(String procDefId, String taskDefKey){
        String formKey = "";
        if (StringUtils.isNotBlank(procDefId)){
            if (StringUtils.isNotBlank(taskDefKey)){
                try{
                    formKey = formService.getTaskFormKey(procDefId, taskDefKey);
                }catch (Exception e) {
                    formKey = "";
                }
            }
            if (StringUtils.isBlank(formKey)){
                formKey = formService.getStartFormKey(procDefId);
            }
            if (StringUtils.isBlank(formKey)){
                formKey = "/404";
            }
        }
        return formKey;
    }

    /**
     * 读取带跟踪的图片
     * @param executionId	环节ID
     * @return	封装了各种节点信息
     */
    public InputStream tracePhoto(String processDefinitionId, String executionId) {
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
        List<String> activeActivityIds = new ArrayList<String>();
        if (runtimeService.createExecutionQuery().executionId(executionId).count() > 0){
            activeActivityIds = runtimeService.getActiveActivityIds(executionId);
        }
        return processEngine.getProcessEngineConfiguration().getProcessDiagramGenerator()
                .generateDiagram(bpmnModel, "png", activeActivityIds,new ArrayList<String>(),"宋体","宋体","宋体",null,1.0D);
    }

}
