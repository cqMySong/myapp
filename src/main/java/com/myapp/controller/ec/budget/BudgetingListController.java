package com.myapp.controller.ec.budget;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseListController;
import com.myapp.core.enums.BaseMethodEnum;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.util.WebUtil;
import com.myapp.service.ec.budget.BudgetingService;
import com.myapp.service.ec.drawing.DiscussionDrawingService;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 包路径：com.myapp.controller.ec.budget
 * 功能说明：预算编制列表信息
 * 创建人： ly
 * 创建时间: 2017-07-30 14:49
 */
@PermissionAnn(name="系统管理.现场管理.预算.预算编制",number="app.ec.budget.budgeting")
@Controller
@RequestMapping("ec/budget/budgetings")
public class BudgetingListController extends BaseListController {
    @Resource
    private BudgetingService budgetingService;

    @Override
    public String getEditUrl() {
        return "ec/budget/budgetingEdit";
    }

    @Override
    public String getListUrl() {
        return "ec/budget/budgetingList";
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
    public List<ColumnModel> getDataBinding() {
        List<ColumnModel> cols = super.getDataBinding();
        cols.add(new ColumnModel("name"));
        cols.add(new ColumnModel("number"));
        return cols;
    }
}
