package com.myapp.controller.ec.basedata.machinemaintenance;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseBillEditController;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.entity.basedate.DataGroupInfo;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.model.WebDataModel;
import com.myapp.core.service.basedata.DataGroupService;
import com.myapp.entity.ec.basedata.MachineMaintenanceDetailInfo;
import com.myapp.entity.ec.basedata.MachineMaintenanceInfo;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.service.ec.basedata.MachineMaintenanceService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @path：com.myapp.controller.ec.basedata.machinemaintenance
 * @description：施工机具例行保养检查
 * @author ： ly
 * @date: 2017-07-30 14:49
 */
@PermissionAnn(name="系统管理.现场管理.基础数据.施工机具例行保养检查",number="app.ec.basedata.machinemaintenance")
@Controller
@RequestMapping("ec/basedata/machinemaintenance")
public class MachineMaintenanceEditController extends BaseBillEditController {
    @Resource
    private MachineMaintenanceService machineMaintenanceService;
    @Resource
    private DataGroupService dataGroupService;
    @Override
    public Object createNewData() {
        MachineMaintenanceInfo machineMaintenanceInfo = new MachineMaintenanceInfo();
        machineMaintenanceInfo.setCreateUser(getCurUser());
        return machineMaintenanceInfo;
    }

    @Override
    public CoreBaseInfo getEntityInfo() {
        return new MachineMaintenanceInfo();
    }

    @Override
    public AbstractBaseService getService() {
        return this.machineMaintenanceService;
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
        cols.add(new ColumnModel("createUser",DataTypeEnum.F7,UserInfo.class));
        cols.add(new ColumnModel("createDate", DataTypeEnum.DATE));
        cols.add(new ColumnModel("lastUpdateDate", DataTypeEnum.DATE));
        cols.add(new ColumnModel("lastUpdateUser",DataTypeEnum.F7,UserInfo.class));

        ColumnModel project = new ColumnModel("project",DataTypeEnum.F7,"id,name");
        project.setClaz(ProjectInfo.class);
        cols.add(project);

        ColumnModel dataGroupInfo = new ColumnModel("dataGroupInfo",DataTypeEnum.F7,"id,name");
        dataGroupInfo.setClaz(DataGroupInfo.class);
        cols.add(dataGroupInfo);

        ColumnModel machineMaintenanceDetailInfos = new ColumnModel("machineMaintenanceDetailInfos", DataTypeEnum.ENTRY,
                MachineMaintenanceDetailInfo.class);
        machineMaintenanceDetailInfos.getCols().add(new ColumnModel("id", DataTypeEnum.PK));
        machineMaintenanceDetailInfos.getCols().add(new ColumnModel("inspectionItem", DataTypeEnum.STRING));
        machineMaintenanceDetailInfos.getCols().add(new ColumnModel("description",DataTypeEnum.STRING));
        machineMaintenanceDetailInfos.getCols().add(new ColumnModel("evaluationOption", DataTypeEnum.STRING));
        cols.add(machineMaintenanceDetailInfos);

        return cols;
    }

    /**
     * 功能:查找检查项目
     * @param parentId
     * @return
     */
    @RequestMapping("/query/inspectionitem")
    @ResponseBody
    public WebDataModel queryMaintenanceObj(String parentId) {
       WebDataModel webDataModel = new WebDataModel();
       webDataModel.setStatusCode(this.statusCode);
       webDataModel.setData(dataGroupService.queryByParentId(parentId));
       return webDataModel;
    }
}
