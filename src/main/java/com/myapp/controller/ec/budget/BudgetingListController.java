package com.myapp.controller.ec.budget;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.util.PoiPublicUtil;
import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseListController;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.BaseMethodEnum;
import com.myapp.core.enums.BillState;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.util.WebUtil;
import com.myapp.entity.ec.basedata.ProStructureInfo;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.model.BudgetingModel;
import com.myapp.service.ec.budget.BudgetingService;
import com.myapp.service.ec.drawing.DiscussionDrawingService;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 包路径：com.myapp.controller.ec.budget
 * 功能说明：材料设备预算清单列表信息
 * 创建人： ly
 * 创建时间: 2017-07-30 14:49
 */
@PermissionAnn(name="系统管理.现场管理.预算.材料设备预算清单",number="app.ec.budget.budgeting")
@Controller
@RequestMapping("ec/budget/budgetings")
public class BudgetingListController extends BaseListController {
    @Resource
    private BudgetingService budgetingService;

    @Override
    public String getEditUrl() {
        return "ec/budget/budgeting/budgetingEdit";
    }

    @Override
    public String getListUrl() {
        return "ec/budget/budgeting/budgetingList";
    }

    @Override
    public AbstractBaseService getService() {
        return this.budgetingService;
    }

    @Override
    public void packageUIParams(Map params) {
        super.packageUIParams(params);
        if(BaseMethodEnum.ADDNEW.equals(baseMethod)){
            if(params!=null&&params.get("uiCtx")!=null){
                String uiCtx = (String) params.get("uiCtx");
                params.put("uiCtx",WebUtil.UUID_ReplaceID(uiCtx));
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
        cols.add(new ColumnModel("billState",DataTypeEnum.ENUM,BillState.class));
        cols.add(new ColumnModel("remark"));
        ColumnModel project = new ColumnModel("project",DataTypeEnum.F7,"id,name");
        project.setClaz(ProjectInfo.class);
        cols.add(project);

        ColumnModel createUser = new ColumnModel("createUser",DataTypeEnum.F7,"id,name");
        createUser.setClaz(UserInfo.class);
        cols.add(createUser);
        ColumnModel auditor = new ColumnModel("auditor",DataTypeEnum.F7,"id,name");
        auditor.setClaz(UserInfo.class);
        cols.add(auditor);
        return cols;
    }

    public static void main(String[] args) {
        ImportParams params = new ImportParams();
        params.setTitleRows(0);
        params.setHeadRows(1);
        List<BudgetingModel> list = ExcelImportUtil.importExcel(
                new File("D:/材料清单导入测试.xls"),
                BudgetingModel.class, params);
        System.out.println(list.size());
        System.out.println(ReflectionToStringBuilder.toString(list.get(0)));
    }
}
