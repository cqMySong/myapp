package com.myapp.core.entity;

import com.myapp.core.base.entity.CoreBaseInfo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 包路径：com.myapp.core.entity
 * 功能说明：代办信息
 * 创建人： ly
 * 创建时间: 2017-07-24 23:49
 */
public class BackLogInfo extends CoreBaseInfo {
    private String title;//任务标题
    private String taskName;//当前办理环节名称
    private String taskId;//当前任务id
    private String assignee;//办理人
    private String assigneeName;//办理人姓名
    private String executionId;//执行id
    private String taskDefinitionKey;//任务定义key
    private String processInstanceId;//流程实例id
    private String processDefinitionId;//流程定义id
    private String status;//状态
    private String pass;//审核状态
    private String reason;//审核意见
    private Map<String,Object> vars;//流程变量
    private Date startTime;//办理开始时间
    private Date endTime;//办理结束时间

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getExecutionId() {
        return executionId;
    }

    public void setExecutionId(String executionId) {
        this.executionId = executionId;
    }

    public String getTaskDefinitionKey() {
        return taskDefinitionKey;
    }

    public void setTaskDefinitionKey(String taskDefinitionKey) {
        this.taskDefinitionKey = taskDefinitionKey;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getProcessDefinitionId() {
        return processDefinitionId;
    }

    public void setProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Map<String, Object> getVars() {
        if(vars==null){
            return new HashMap<String,Object>();
        }
        return vars;
    }

    public void setVars(Map<String, Object> vars) {
        this.vars = vars;
    }

    public String getAssigneeName() {
        return assigneeName;
    }

    public void setAssigneeName(String assigneeName) {
        this.assigneeName = assigneeName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
