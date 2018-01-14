package com.myapp.controller.ec.basedata.machinemaintenance;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseListController;
import com.myapp.core.entity.basedate.DataGroupInfo;
import com.myapp.core.enums.BaseMethodEnum;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.util.WebUtil;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.service.ec.basedata.MachineMaintenanceService;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @path：com.myapp.controller.ec.basedata.machinemaintenance
 * @description：施工机具例行保养检查
 * @author ： ly
 * @date: 2017-08-28 21:02
 */
@PermissionAnn(name="系统管理.现场管理.基础数据.施工机具例行保养检查",number="app.ec.basedata.machinemaintenance")
@Controller
@RequestMapping("ec/basedata/machinemaintenances")
public class MachineMaintenanceListController extends BaseListController {
    @Resource
    private MachineMaintenanceService machineMaintenanceService;

    @Override
    public String getEditUrl() {
        return "ec/basedata/machinemaintenance/machineMaintenanceEdit";
    }

    @Override
    public String getListUrl() {
        return "ec/basedata/machinemaintenance/machineMaintenanceList";
    }

    @Override
    public AbstractBaseService getService() {
        return this.machineMaintenanceService;
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
        cols.add(new ColumnModel("name",DataTypeEnum.STRING));
        cols.add(new ColumnModel("number",DataTypeEnum.STRING));
        cols.add(new ColumnModel("inspector",DataTypeEnum.STRING));
        cols.add(new ColumnModel("inspectionOption",DataTypeEnum.STRING));
        cols.add(new ColumnModel("remedialResult",DataTypeEnum.STRING));
        cols.add(new ColumnModel("maintenanceCompany",DataTypeEnum.STRING));
        cols.add(new ColumnModel("inspectionDate",DataTypeEnum.DATE));

        ColumnModel project = new ColumnModel("project",DataTypeEnum.F7,"id,name");
        project.setClaz(ProjectInfo.class);
        cols.add(project);

        ColumnModel dataGroupInfo = new ColumnModel("dataGroupInfo",DataTypeEnum.F7,"id,name");
        dataGroupInfo.setClaz(DataGroupInfo.class);
        cols.add(dataGroupInfo);
        return cols;
    }
}
