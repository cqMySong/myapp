package com.myapp.controller.ec.purchase.apply;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseListController;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.BillState;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.util.BaseUtil;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.service.ec.purchase.ApplyMaterialService;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @path：com.myapp.controller.ec.purchase.order.apply
 * @description：材料申购单
 * @author： ly
 * @date: 2017-10-29 20:40
 */
@PermissionAnn(name="系统管理.现场管理.采购管理.材料申购",number="app.ec.purchase.applymaterial")
@Controller
@RequestMapping("ec/purchase/applymaterials")
public class ApplyMaterialListController extends BaseListController {
    @Resource
    private ApplyMaterialService applyMaterialService;
    @Override
    public String getEditUrl() {
        return "ec/purchase/apply/applyMaterialEdit";
    }

    @Override
    public String getListUrl() {
        return "ec/purchase/apply/applyMaterialList";
    }

    @Override
    public AbstractBaseService getService() {
        return this.applyMaterialService;
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
        cols.add(new ColumnModel("billState", DataTypeEnum.ENUM,BillState.class));
        cols.add(new ColumnModel("auditState", DataTypeEnum.ENUM,BillState.class));
        cols.add(new ColumnModel("remark"));
        cols.add(new ColumnModel("processInstanceId"));
        cols.add(new ColumnModel("createDate",DataTypeEnum.DATE));
        ColumnModel project = new ColumnModel("project",DataTypeEnum.F7,"id,name");
        project.setClaz(ProjectInfo.class);
        cols.add(project);

        ColumnModel createUser = new ColumnModel("createUser",DataTypeEnum.F7,"id,name");
        createUser.setClaz(UserInfo.class);
        cols.add(createUser);
        ColumnModel auditor = new ColumnModel("auditor",DataTypeEnum.F7,"id,name");
        auditor.setClaz(UserInfo.class);
        return cols;
    }
}
