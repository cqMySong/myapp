package com.myapp.controller.ec.engineering.payment;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseListController;
import com.myapp.core.enums.BaseMethodEnum;
import com.myapp.core.enums.BillState;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.util.WebUtil;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.entity.ec.engineering.SubcontractInfo;
import com.myapp.service.ec.engineering.SubContractPaymentService;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @path：com.myapp.controller.ec.engineering.progressfund
 * @description：分包付款
 * @author ： ly
 * @date: 2017-08-28 21:02
 */
@PermissionAnn(name="系统管理.现场管理.工程合同.分包付款",number="app.ec.engineering.subcontractpayment")
@Controller
@RequestMapping("ec/engineering/subcontractpayments")
public class SubContractPaymentListController extends BaseListController {
    @Resource
    private SubContractPaymentService subContractPaymentService;

    @Override
    public String getEditUrl() {
        return "ec/engineering/settle/subContractPaymentEdit";
    }

    @Override
    public String getListUrl() {
        return "ec/engineering/settle/subContractPaymentList";
    }

    @Override
    public AbstractBaseService getService() {
        return this.subContractPaymentService;
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
        cols.add(new ColumnModel("billState", DataTypeEnum.ENUM,BillState.class));
        cols.add(new ColumnModel("operator", DataTypeEnum.STRING));
        cols.add(new ColumnModel("startDate", DataTypeEnum.DATE));
        cols.add(new ColumnModel("endDate", DataTypeEnum.DATE));
        cols.add(new ColumnModel("settleAmount", DataTypeEnum.NUMBER));

        ColumnModel project = new ColumnModel("project", DataTypeEnum.F7,"id,name");
        project.setClaz(ProjectInfo.class);
        cols.add(project);

        ColumnModel subcontractInfo = new ColumnModel("subcontractInfo",
                DataTypeEnum.F7,"id,name,number,amount");
        subcontractInfo.setClaz(SubcontractInfo.class);
        cols.add(subcontractInfo);
        return cols;
    }
}
