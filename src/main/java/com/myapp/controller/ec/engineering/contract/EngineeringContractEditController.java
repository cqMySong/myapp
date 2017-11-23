package com.myapp.controller.ec.engineering.contract;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseBillEditController;
import com.myapp.core.entity.MaterialInfo;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.*;
import com.myapp.core.exception.db.QueryException;
import com.myapp.core.model.ColumnModel;
import com.myapp.entity.ec.basedata.ECUnitInfo;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.entity.ec.engineering.EngineeringContractInfo;
import com.myapp.entity.ec.stock.StockInfo;
import com.myapp.entity.ec.stock.StockInventoryDetailInfo;
import com.myapp.entity.ec.stock.StockInventoryInfo;
import com.myapp.service.ec.engineering.EngineeringContractService;
import com.myapp.service.ec.stock.StockInventoryService;
import com.myapp.service.ec.stock.StockService;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @path：com.myapp.controller.ec.sengineering.contract
 * @description：工程合同
 * @author ： ly
 * @date: 2017-07-30 14:49
 */
@PermissionAnn(name="系统管理.现场管理.工程合同.合同管理",number="app.ec.engineering.contract")
@Controller
@RequestMapping("ec/engineering/contract")
public class EngineeringContractEditController extends BaseBillEditController {
    @Resource
    private EngineeringContractService engineeringContractService;
    @Override
    public Object createNewData() {
        return new EngineeringContractInfo();
    }

    @Override
    public CoreBaseInfo getEntityInfo() {
        return new EngineeringContractInfo();
    }

    @Override
    public AbstractBaseService getService() {
        return this.engineeringContractService;
    }


    @Override
    public List<ColumnModel> getDataBinding() {
        List<ColumnModel> cols = super.getDataBinding();
        cols.add(new ColumnModel("name"));
        cols.add(new ColumnModel("number"));
        cols.add(new ColumnModel("directorName"));
        cols.add(new ColumnModel("treatyContents"));
        cols.add(new ColumnModel("amount",DataTypeEnum.NUMBER));
        cols.add(new ColumnModel("director",DataTypeEnum.F7,UserInfo.class));
        cols.add(new ColumnModel("contractDate",DataTypeEnum.DATE));
        cols.add(new ColumnModel("contractType", DataTypeEnum.ENUM,ContractType.class));
        cols.add(new ColumnModel("expenseType", DataTypeEnum.ENUM,ExpenseType.class));
        cols.add(new ColumnModel("billState",DataTypeEnum.ENUM, BillState.class));
        cols.add(new ColumnModel("createUser",DataTypeEnum.F7,UserInfo.class));
        cols.add(new ColumnModel("lastUpdateUser",DataTypeEnum.F7,UserInfo.class));
        cols.add(new ColumnModel("auditor",DataTypeEnum.F7,UserInfo.class));
        cols.add(new ColumnModel("createDate", DataTypeEnum.DATE));
        cols.add(new ColumnModel("auditDate", DataTypeEnum.DATE));
        cols.add(new ColumnModel("lastUpdateDate", DataTypeEnum.DATE));
        ColumnModel project = new ColumnModel("project",DataTypeEnum.F7,"id,name");
        project.setClaz(ProjectInfo.class);
        cols.add(project);

        ColumnModel ecUnitInfo = new ColumnModel("ecUnitInfo",DataTypeEnum.F7,"id,name");
        ecUnitInfo.setClaz(ECUnitInfo.class);
        cols.add(ecUnitInfo);


        return cols;
    }
}
