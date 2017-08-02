package com.myapp.core.service.act;

import com.myapp.core.entity.ActProcessInfo;
import com.myapp.core.model.PageModel;
import com.myapp.core.service.base.BaseInterfaceService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.List;

/**
 * 包路径：com.myapp.core.service.act
 * 功能说明：
 * 创建人： ly
 * 创建时间: 2017-07-23 14:59
 */
@Service("actProcessService")
public class ActProcessService  extends BaseInterfaceService<ActProcessInfo> {
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    /**
     * 流程定义列表
     */
    public PageModel<ActProcessInfo> processList(Integer currentPage, Integer pageSize, String category) {

        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery()
                .latestVersion().orderByProcessDefinitionKey().asc();

        if (StringUtils.isNotEmpty(category)){
            processDefinitionQuery.processDefinitionCategory(category);
        }
        PageModel page = new PageModel(currentPage,pageSize,processDefinitionQuery.count());
        List<ProcessDefinition> processDefinitionList = processDefinitionQuery.listPage(
        (page.getCurrentPage()-1)*page.getPageSize(), page.getPageSize());
        for (ProcessDefinition processDefinition : processDefinitionList) {
            String deploymentId = processDefinition.getDeploymentId();
            Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
            ActProcessInfo actProcessInfo = new ActProcessInfo();
            actProcessInfo.setXmlResourcesName(processDefinition.getResourceName());
            actProcessInfo.setPictureResourcesName(processDefinition.getDiagramResourceName());
            actProcessInfo.setDeployDate(deployment.getDeploymentTime());
            actProcessInfo.setCategory(processDefinition.getCategory());
            actProcessInfo.setVersion("V:"+processDefinition.getVersion());
            actProcessInfo.setName(deployment.getName());
            actProcessInfo.setId(processDefinition.getId());
            actProcessInfo.setNumber(processDefinition.getKey());
            actProcessInfo.setHasGraphicalNotation(processDefinition.hasGraphicalNotation());
            actProcessInfo.setHasStartFormKey(processDefinition.hasStartFormKey());
            actProcessInfo.setTenantId(processDefinition.getTenantId());
            actProcessInfo.setDeploymentId(processDefinition.getDeploymentId());
            actProcessInfo.setDescription(processDefinition.getDescription());
            actProcessInfo.setSuspended(processDefinition.isSuspended());
            page.getDatas().add(actProcessInfo);
        }
        return page;
    }

    /**
     * 运行中流程定义列表
     */
    public PageModel<ProcessInstance> runningList(PageModel<ProcessInstance> page, String procInsId, String procDefKey) {

        ProcessInstanceQuery processInstanceQuery = runtimeService.createProcessInstanceQuery();

        if (StringUtils.isNotBlank(procInsId)){
            processInstanceQuery.processInstanceId(procInsId);
        }

        if (StringUtils.isNotBlank(procDefKey)){
            processInstanceQuery.processDefinitionKey(procDefKey);
        }

        page.setTotalRows(processInstanceQuery.count());
        page.setDatas(processInstanceQuery.listPage((page.getCurrentPage()-1)*page.getPageSize(), page.getPageSize()));
        return page;
    }

    /**
     * 读取资源，通过部署ID
     * @param procDefId  流程定义ID
     * @param proInsId 流程实例ID
     * @param resType 资源类型(xml|image)
     */
    public InputStream resourceRead(String procDefId, String proInsId, String resType) throws Exception {

        if (StringUtils.isBlank(procDefId)){
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(proInsId).singleResult();
            procDefId = processInstance.getProcessDefinitionId();
        }
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(procDefId).singleResult();

        String resourceName = "";
        if (resType.equals("image")) {
            resourceName = processDefinition.getDiagramResourceName();
        } else if (resType.equals("xml")) {
            resourceName = processDefinition.getResourceName();
        }

        InputStream resourceAsStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), resourceName);
        return resourceAsStream;
    }

    /**
     * 挂起、激活流程实例
     */
    public String updateState(String state, String procDefId) {
        if (state.equals("active")) {
            repositoryService.activateProcessDefinitionById(procDefId, true, null);
            return "已激活ID为[" + procDefId + "]的流程定义。";
        } else if (state.equals("suspend")) {
            repositoryService.suspendProcessDefinitionById(procDefId, true, null);
            return "已挂起ID为[" + procDefId + "]的流程定义。";
        }
        return "无操作";
    }

    /**
     * 删除部署的流程，级联删除流程实例
     * @param deploymentId 流程部署ID
     */
    public void deleteEntity(String deploymentId) {
        repositoryService.deleteDeployment(deploymentId, true);
    }
}
