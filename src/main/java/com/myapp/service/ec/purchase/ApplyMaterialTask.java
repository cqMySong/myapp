package com.myapp.service.ec.purchase;

import com.myapp.core.entity.UserInfo;
import com.myapp.core.exception.db.QueryException;
import com.myapp.core.service.UserService;
import com.myapp.entity.ec.purchase.ApplyMaterialInfo;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.el.FixedValue;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @path：com.myapp.service.ec.purchase
 * @description：
 * @author： ly
 * @date: 2018-05-10 22:11
 */
@Service("applyMaterialTaskService")
public class ApplyMaterialTask implements TaskListener {
    /**
     * 审批事项
     */
    private FixedValue needAttention;
    /**
     * 工作职责代码
     */
    private FixedValue dutyCode;
    @Resource
    private ApplyMaterialService applyMaterialService;
    @Autowired
    private ProcessEngine processEngine;
    @Resource
    private UserService userService;
    @Override
    public void notify(DelegateTask delegateTask){
        String businessKey = delegateTask.getExecution().getProcessBusinessKey();
        ApplyMaterialInfo applyMaterialInfo = applyMaterialService.loadEntity(businessKey);
        List<String> candidateUsers= new ArrayList<>();
        //当前部门负责人
        if("local".equals(dutyCode.getExpressionText())){
            List<Map> deptManager = null;
            try {
                deptManager = userService.queryDeptManager(applyMaterialInfo.getCreateUser().getId(),
                        applyMaterialInfo.getProject().getId());
            } catch (QueryException e) {

            }
            if(deptManager==null||deptManager.size()==0){
                throw new RuntimeException("未找到当前操作人的主要岗位所在部门信息");
            }
            boolean isLeader = false;
            for(Map userInfo:deptManager){
                if(applyMaterialInfo.getCreateUser().getId().equals(userInfo.get("id").toString())){
                    isLeader = true;
                }
                candidateUsers.add(userInfo.get("number").toString());
            }
        }else if("edit".equals(dutyCode.getExpressionText())){
            UserInfo userInfo = userService.loadEntity(applyMaterialInfo.getCreateUser().getId());
            candidateUsers.add(userInfo.getNumber());
            delegateTask.addCandidateUsers(candidateUsers);
            processEngine.getTaskService().claim(delegateTask.getId(),candidateUsers.get(0));
        }else{
            List<Map> userNumberList = null;
            try {
                userNumberList = userService.queryUserByPosition(applyMaterialInfo.getProject().getId(),
                        dutyCode.getExpressionText());
            } catch (QueryException e) {
                e.printStackTrace();
            }
            if(userNumberList!=null){
                for(Map map:userNumberList){
                    candidateUsers.add(map.get("number").toString());
                }
            }
        }
        if(candidateUsers==null){
            throw new RuntimeException("未找到下一步审核负责人");
        }
        delegateTask.addCandidateUsers(candidateUsers);
        if(candidateUsers.size()==1){
            processEngine.getTaskService().claim(delegateTask.getId(),candidateUsers.get(0));
        }
        delegateTask.setVariable("needAttention",needAttention.getExpressionText());
    }

    public FixedValue getNeedAttention() {
        return needAttention;
    }

    public void setNeedAttention(FixedValue needAttention) {
        this.needAttention = needAttention;
    }

    public FixedValue getDutyCode() {
        return dutyCode;
    }

    public void setDutyCode(FixedValue dutyCode) {
        this.dutyCode = dutyCode;
    }
}
