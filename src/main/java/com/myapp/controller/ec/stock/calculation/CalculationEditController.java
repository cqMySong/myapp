package com.myapp.controller.ec.stock.calculation;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseBillEditController;
import com.myapp.core.entity.MaterialInfo;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.BaseMethodEnum;
import com.myapp.core.enums.BillState;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.enums.MaterialType;
import com.myapp.core.exception.db.QueryException;
import com.myapp.core.model.ColumnModel;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.entity.ec.stock.*;
import com.myapp.service.ec.stock.StockCalculationService;
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
 * @path：com.myapp.controller.ec.stock.calculation
 * @description：当期图算用量
 * @author ： ly
 * @date: 2017-07-30 14:49
 */
@PermissionAnn(name="系统管理.现场管理.库存管理.当期图算用量",number="app.ec.stock.calculation")
@Controller
@RequestMapping("ec/stock/calculation")
public class CalculationEditController extends BaseBillEditController {
    @Resource
    private StockCalculationService stockCalculationService;
    @Override
    public Object createNewData() {
        return new StockCalculationInfo();
    }

    @Override
    public CoreBaseInfo getEntityInfo() {
        return new StockCalculationInfo();
    }

    @Override
    public AbstractBaseService getService() {
        return this.stockCalculationService;
    }
    @Override
    public void afterOperate(BaseMethodEnum bme) throws HibernateException, QueryException {
        super.afterOperate(bme);
        if(this.data!=null&&!bme.getValue().equals(BaseMethodEnum.ADDNEW.getValue())&&this.data instanceof JSONObject){
            JSONObject jsonObject = (JSONObject) this.data;
            JSONArray stockCalculationDetailInfos = jsonObject.getJSONArray("stockCalculationDetailInfos");
            JSONArray stockCalculationDetailNew = new JSONArray();
            JSONObject stockInfo = null;
            JSONObject stockCalculationDetail = null;
            JSONObject materialType = null;
            JSONObject materialInfo = null;
            JSONObject stockInventoryDetailInfo = null;
            for(int i=0;i<stockCalculationDetailInfos.size();i++){
                stockCalculationDetail = stockCalculationDetailInfos.getJSONObject(i);
                materialInfo = stockCalculationDetail.getJSONObject("material");
                materialType = new JSONObject();
                materialType.put("key",materialInfo.get("materialType"));
                materialType.put("val", MaterialType.map.get(materialInfo.getString("materialType")).getName());
                stockCalculationDetail.put("materialType",materialType);
                stockCalculationDetail.put("number",materialInfo.getString("number"));
                stockCalculationDetail.put("specification",materialInfo.getString("specification"));
                stockInventoryDetailInfo = stockCalculationDetail.getJSONObject("stockInventoryDetailInfo");
                stockCalculationDetail.put("stockCount",stockInventoryDetailInfo.get("stockCount"));
                stockCalculationDetail.put("inventoryCount",stockInventoryDetailInfo.get("inventoryCount"));
                stockCalculationDetailNew.add(stockCalculationDetail);
            }
            JSONObject stockInventoryInfo = jsonObject.getJSONObject("stockInventoryInfo");
            jsonObject.put("startDate",stockInventoryInfo.get("startDate"));
            jsonObject.put("endDate",stockInventoryInfo.get("endDate"));
            jsonObject.put("stockCalculationDetailInfos",stockCalculationDetailNew);
            this.data = jsonObject;
        }
    }

    @Override
    public List<ColumnModel> getDataBinding() {
        List<ColumnModel> cols = super.getDataBinding();
        cols.add(new ColumnModel("name"));
        cols.add(new ColumnModel("number"));
        cols.add(new ColumnModel("remark"));
        cols.add(new ColumnModel("billState",DataTypeEnum.ENUM, BillState.class));
        cols.add(new ColumnModel("createUser",DataTypeEnum.F7,UserInfo.class));
        cols.add(new ColumnModel("lastUpdateUser",DataTypeEnum.F7,UserInfo.class));
        cols.add(new ColumnModel("auditor",DataTypeEnum.F7,UserInfo.class));
        cols.add(new ColumnModel("createDate", DataTypeEnum.DATETIME));
        cols.add(new ColumnModel("auditDate", DataTypeEnum.DATETIME));
        cols.add(new ColumnModel("lastUpdateDate", DataTypeEnum.DATETIME));
        ColumnModel project = new ColumnModel("project",DataTypeEnum.F7,"id,name");
        project.setClaz(ProjectInfo.class);
        cols.add(project);
        ColumnModel stockInventoryInfo = new ColumnModel("stockInventoryInfo",DataTypeEnum.F7,"id,name,startDate,endDate");
        stockInventoryInfo.setClaz(StockInventoryInfo.class);
        cols.add(stockInventoryInfo);

        ColumnModel stockCalculationDetailInfos = new ColumnModel("stockCalculationDetailInfos",DataTypeEnum.ENTRY,
                StockCalculationDetailInfo.class);

        ColumnModel stockInventoryDetailInfo = new ColumnModel("stockInventoryDetailInfo",DataTypeEnum.F7,"id,stockCount,inventoryCount");
        stockInventoryDetailInfo.setClaz(StockInventoryDetailInfo.class);
        stockCalculationDetailInfos.getCols().add(stockInventoryDetailInfo);

        ColumnModel material = new ColumnModel("material",DataTypeEnum.F7,"id,name,materialType,number,specification");
        material.setClaz(MaterialInfo.class);
        stockCalculationDetailInfos.getCols().add(material);

        stockCalculationDetailInfos.getCols().add(new ColumnModel("id",DataTypeEnum.PK));
        stockCalculationDetailInfos.getCols().add(new ColumnModel("calculationCount",DataTypeEnum.NUMBER));
        stockCalculationDetailInfos.getCols().add(new ColumnModel("remark"));
        stockCalculationDetailInfos.getCols().add(new ColumnModel("measureUnit"));
        cols.add(stockCalculationDetailInfos);

        return cols;
    }
}
