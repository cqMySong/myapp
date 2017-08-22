package com.myapp.core.service.act;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.myapp.core.entity.ActModelInfo;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.exception.db.SaveException;
import com.myapp.core.model.PageModel;
import com.myapp.core.service.base.BaseInterfaceService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ModelQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 包路径：com.myapp.core.service.act
 * 功能说明：工作流模型管理
 * 创建人： ly
 * 创建时间: 2017-07-02 20:22
 */
@Service("actModelService")
public class ActModelService extends BaseInterfaceService<ActModelInfo> {
    @Autowired
    private RepositoryService repositoryService;
    protected ObjectMapper objectMapper = new ObjectMapper();


    /**
     * 创建模型
     * @throws UnsupportedEncodingException
     */
    @Override
    public Object saveEntity(Object entity) throws SaveException {
        ActModelInfo actModelInfo = (ActModelInfo)entity;
        ObjectNode editorNode = objectMapper.createObjectNode();
        editorNode.put("id", "canvas");
        editorNode.put("resourceId", "canvas");
        ObjectNode properties = objectMapper.createObjectNode();
        properties.put("process_author", actModelInfo.getUserInfo().getName());
        properties.put("process_id", actModelInfo.getCategory());
        properties.put("name",actModelInfo.getName());
        properties.put("documentation",actModelInfo.getDescription());
        editorNode.put("properties", properties);
        ObjectNode stencilset = objectMapper.createObjectNode();
        stencilset.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
        editorNode.put("stencilset", stencilset);

        Model modelData = repositoryService.newModel();
        String description = StringUtils.defaultString(actModelInfo.getDescription());
        modelData.setKey(StringUtils.defaultString(actModelInfo.getNumber()));
        modelData.setName(actModelInfo.getName());
        modelData.setCategory(actModelInfo.getCategory());
        modelData.setVersion(Integer.parseInt(String.valueOf(repositoryService.createModelQuery().modelKey(modelData.getKey()).count()+1)));

        ObjectNode modelObjectNode = objectMapper.createObjectNode();
        modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, actModelInfo.getName());
        modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, modelData.getVersion());
        modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
        modelData.setMetaInfo(modelObjectNode.toString());
        repositoryService.saveModel(modelData);
        try {
            repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            throw new SaveException(e);
        }
        actModelInfo.setId(modelData.getId());
        return actModelInfo;
    }

    /**
     * 流程模型列表
     */
    public PageModel<Model> modelList(Integer currentPage,Integer pageSize, String category) {

        ModelQuery modelQuery = repositoryService.createModelQuery().latestVersion().orderByLastUpdateTime().desc();

        if (StringUtils.isNotEmpty(category)){
            modelQuery.modelCategory(category);
        }
        PageModel<Model> pageModel = new PageModel<Model>(currentPage,pageSize,modelQuery.count());
        pageModel.setDatas(modelQuery.listPage((currentPage-1)*pageSize, pageSize));
        return pageModel;
    }

    /**
     * 删除模型
     * @param id
     */
    public void deleteEntity(String id) {
        repositoryService.deleteModel(id);
    }
    //部署流程
    public String deploy(String id) {
        String message = "";
        try {
            Model modelData = repositoryService.getModel(id);
            BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
            JsonNode editorNode = new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));
            BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(editorNode);
            BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
            byte[] bpmnBytes = xmlConverter.convertToXML(bpmnModel);

            String processName = modelData.getName();
            if (!StringUtils.endsWith(processName, ".bpmn20.xml")){
                processName += ".bpmn20.xml";
            }
            ByteArrayInputStream in = new ByteArrayInputStream(bpmnBytes);
            Deployment deployment = repositoryService.createDeployment().name(modelData.getName())
                    .category(modelData.getCategory()).tenantId(modelData.getTenantId())
                    .addInputStream(processName, in).deploy();
            // 设置流程分类
            List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).list();
            for (ProcessDefinition processDefinition : list) {
                repositoryService.setProcessDefinitionCategory(processDefinition.getId(), modelData.getCategory());
                message = "部署成功，流程ID=" + processDefinition.getId();
            }
            if (list.size() == 0){
                message = "部署失败，没有流程。";
            }
        } catch (Exception e) {
            throw new ActivitiException("设计模型图不正确，检查模型正确性，模型ID="+id+e.getMessage(), e);
        }
        return message;
    }

    /**
     * 导出model的xml文件
     * @throws IOException
     * @throws JsonProcessingException
     */
    public byte[] export(String id) {
        try {
            Model modelData = repositoryService.getModel(id);
            BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
            JsonNode editorNode = new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));
            BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(editorNode);
            BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
            byte[] bpmnBytes = xmlConverter.convertToXML(bpmnModel);
            return bpmnBytes;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ActivitiException("导出model的xml文件失败，模型ID="+id, e);
        }

    }
}
