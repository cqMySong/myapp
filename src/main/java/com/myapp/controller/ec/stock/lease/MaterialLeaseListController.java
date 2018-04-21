package com.myapp.controller.ec.stock.lease;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseListController;
import com.myapp.core.entity.MaterialInfo;
import com.myapp.core.entity.MeasureUnitInfo;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.*;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.util.WebUtil;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.entity.ec.engineering.SiteVisaOutInfo;
import com.myapp.service.ec.engineering.SiteVisaInService;
import com.myapp.service.ec.stock.MaterialLeaseService;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @path：com.myapp.controller.ec.stock.lease
 * @description：周转材料、设备使用(出租)
 * @author ： ly
 * @date: 2017-08-28 21:02
 */
@PermissionAnn(name="系统管理.现场管理.库存管理.周转材料、设备使用(出租)",number="app.ec.stock.lease")
@Controller
@RequestMapping("ec/stock/leases")
public class MaterialLeaseListController extends BaseListController {
    @Resource
    private MaterialLeaseService materialLeaseService;

    @Override
    public String getEditUrl() {
        return "ec/stock/lease/leaseEdit";
    }

    @Override
    public String getListUrl() {
        return "ec/stock/lease/leaseList";
    }

    @Override
    public AbstractBaseService getService() {
        return this.materialLeaseService;
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
        cols.add(new ColumnModel("createDate", DataTypeEnum.DATETIME));
        cols.add(new ColumnModel("leaseUnit",DataTypeEnum.STRING));
        cols.add(new ColumnModel("leaseDate",DataTypeEnum.DATE));
        cols.add(new ColumnModel("leaseCount",DataTypeEnum.NUMBER));
        cols.add(new ColumnModel("remark",DataTypeEnum.STRING));
        cols.add(new ColumnModel("billState",DataTypeEnum.ENUM, BillState.class));

        ColumnModel project = new ColumnModel("project",DataTypeEnum.F7,"id,name");
        project.setClaz(ProjectInfo.class);
        cols.add(project);

        ColumnModel materialInfo = new ColumnModel("materialInfo",DataTypeEnum.F7,"id,name");
        materialInfo.setClaz(MaterialInfo.class);
        cols.add(materialInfo);

        ColumnModel measureUnitInfo = new ColumnModel("measureUnitInfo",DataTypeEnum.F7,"id,name");
        measureUnitInfo.setClaz(MeasureUnitInfo.class);
        cols.add(measureUnitInfo);

        ColumnModel createUser = new ColumnModel("createUser",DataTypeEnum.F7,"id,name");
        createUser.setClaz(UserInfo.class);
        cols.add(createUser);
        return cols;
    }
}
