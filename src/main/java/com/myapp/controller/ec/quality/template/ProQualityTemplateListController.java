package com.myapp.controller.ec.quality.template;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.annotation.PermissionItemAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseListController;
import com.myapp.core.entity.PositionInfo;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.entity.UserPositionInfo;
import com.myapp.core.enums.*;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.service.UserService;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.util.WebUtil;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.entity.ec.basedata.ProjectWbsInfo;
import com.myapp.entity.ec.basedata.QualityTemplateInfo;
import com.myapp.service.ec.quality.ProQualityTemplateDetailService;
import com.myapp.service.ec.quality.ProQualityTemplateService;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @path：com.myapp.controller.ec.engineering.contract
 * @description：项目质量样板
 * @author ： ly
 * @date: 2017-08-28 21:02
 */
@PermissionAnn(name="系统管理.现场管理.质量管理.项目质量样板",number="app.ec.quality.template")
@Controller
@RequestMapping("ec/quality/templates")
public class ProQualityTemplateListController extends BaseListController {
    @Resource
    private ProQualityTemplateService proQualityTemplateService;
    @Resource
    private ProQualityTemplateDetailService proQualityTemplateDetailService;
    @Resource
    private UserService userService;

    @Override
    public String getEditUrl() {
        return "ec/quality/template/proQualityTemplateEdit";
    }

    @Override
    public String getListUrl() {
        return "ec/quality/template/proQualityTemplateList";
    }

    @Override
    public AbstractBaseService getService() {
        return this.proQualityTemplateService;
    }

    @Override
    public void packageUIParams(Map params) {
        super.packageUIParams(params);
        if(BaseMethodEnum.ADDNEW.equals(baseMethod)){
            if(params!=null&&params.get("uiCtx")!=null){
                String uiCtx = (String) params.get("uiCtx");
                params.put("uiCtx", WebUtil.UUID_ReplaceID(uiCtx));
            }
        }
    }
    @Override
    public void executeQueryParams(Criteria query) {
        super.executeQueryParams(query);
        String serach = request.getParameter("search");
        String projectId = "xyz";
        if(!BaseUtil.isEmpty(serach)){
            Map searchMap = JSONObject.parseObject(serach, new HashMap().getClass());
            Object objTree = searchMap.get("tree");
            if(objTree!=null){
                Map treeMap = JSONObject.parseObject(objTree.toString(), new HashMap().getClass());
                Object idObj = treeMap.get("id");
                Object type = treeMap.get("type");
                if(type!=null&&idObj!=null){
                    if("project".equals(type.toString())){
                        projectId = idObj.toString();
                    }
                }
            }
        }
        query.add(Restrictions.eq("project.id",projectId));
    }
    @Override
    public List<ColumnModel> getDataBinding() {
        List<ColumnModel> cols = super.getDataBinding();
        cols.add(new ColumnModel("name"));
        cols.add(new ColumnModel("number"));
        cols.add(new ColumnModel("expectStartDate",DataTypeEnum.DATE));
        cols.add(new ColumnModel("acceptanceDate",DataTypeEnum.DATE));
        cols.add(new ColumnModel("billState", DataTypeEnum.ENUM,BillState.class));
        cols.add(new ColumnModel("qualityTemplateInfo", DataTypeEnum.F7,QualityTemplateInfo.class));
        cols.add(new ColumnModel("number"));
        cols.add(new ColumnModel("attachs"));

        ColumnModel project = new ColumnModel("project",DataTypeEnum.F7,"id,name");
        project.setClaz(ProjectInfo.class);
        cols.add(project);

        ColumnModel branchBaseWbs = new ColumnModel("branchBaseWbs",DataTypeEnum.F7,"id,name");
        branchBaseWbs.setClaz(ProjectWbsInfo.class);
        cols.add(branchBaseWbs);

        ColumnModel subentry = new ColumnModel("subentry",DataTypeEnum.F7,"id,name");
        subentry.setClaz(ProjectWbsInfo.class);
        cols.add(subentry);

        ColumnModel createUser = new ColumnModel("createUser",DataTypeEnum.F7,"id,name");
        createUser.setClaz(UserInfo.class);
        cols.add(createUser);
        return cols;
    }

    @PermissionItemAnn(name="工作要求",number="jobRequire",type= PermissionTypeEnum.PAGE)
    @RequestMapping("/job/require")
    public String forwardRequire(Model model){
        Map<String,String> params =  getUiCtx();
        model.addAttribute("jobRequire",
                proQualityTemplateDetailService.queryJobRequireByProQualityTemplateId(WebUtil.UUID_ReplaceID(params.get("proQualityTemplateId"))));
        model.addAttribute("parentId",WebUtil.UUID_ReplaceID(params.get("proQualityTemplateId")));
        List<PositionInfo> userPositionInfoSet = userService.queryPositionByMain(getCurUser().getId());
        Map<String,Boolean> mainPosition = new HashMap<>();
        if(userPositionInfoSet!=null){
            for(PositionInfo userPositionInfo:userPositionInfoSet){
                mainPosition.put(userPositionInfo.getId(),true);
            }
        }
        model.addAttribute("mainPosition",mainPosition);
        return "ec/quality/template/proQualityJobRequireEdit";
    }

    @PermissionItemAnn(name="质量样板导入",number="import",type= PermissionTypeEnum.FUNCTION)
    @RequestMapping("/batch/import")
    public ModelAndView forwardBatchImport(){
        Map params = getUiCtx();
        return toPage("ec/quality/template/proQualityTemplateBatchImport", params);
    }
}
