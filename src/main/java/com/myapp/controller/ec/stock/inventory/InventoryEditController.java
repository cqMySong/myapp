package com.myapp.controller.ec.stock.inventory;

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
import com.myapp.core.model.WebDataModel;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.entity.ec.stock.*;
import com.myapp.service.ec.stock.StockInventoryDetailService;
import com.myapp.service.ec.stock.StockInventoryService;
import com.myapp.service.ec.stock.StockOutService;
import com.myapp.service.ec.stock.StockService;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * @path：com.myapp.controller.ec.stock.inventory
 * @description：盘存记录
 * @author ： ly
 * @date: 2017-07-30 14:49
 */
@PermissionAnn(name="系统管理.现场管理.库存管理.盘存记录",number="app.ec.stock.inventory")
@Controller
@RequestMapping("ec/stock/inventory")
public class InventoryEditController extends BaseBillEditController {
    @Resource
    private StockInventoryService stockInventoryService;
    @Resource
    private StockInventoryDetailService stockInventoryDetailService;
    @Resource
    private StockService stockService;
    @Override
    public Object createNewData() {
        StockInventoryInfo stockInventoryInfo = new StockInventoryInfo();
        Set<StockInventoryDetailInfo> stockInventoryDetailInfos = new HashSet<>();
        stockInventoryInfo.setStockInventoryDetailInfos(stockInventoryDetailInfos);
        String str = request.getParameter("uiCtx");
        if(!StringUtils.isEmpty(str)){
            JSONObject jsonObject = JSONObject.parseObject(str);
            String projectId =  jsonObject.getJSONObject("tree").getString("id");
            List<Map> stockInfoList = stockService.queryStockByProjectId(projectId);
            if(stockInfoList!=null){
                StockInventoryDetailInfo stockInventoryDetailInfo = null;
                StockInfo stockInfo = null;
                MaterialInfo materialInfo = null;
                for(Map stockMap:stockInfoList){
                    stockInventoryDetailInfo = new StockInventoryDetailInfo();
                    stockInventoryDetailInfo.setStockCount(new BigDecimal(stockMap.get("count").toString()));
                    stockInventoryDetailInfo.setQuantity(stockMap.get("quantity")==null?BigDecimal.ZERO:new BigDecimal(stockMap.get("quantity").toString()));
                    stockInventoryDetailInfo.setInStockCount(stockMap.get("inStockCount")==null?BigDecimal.ZERO:new BigDecimal(stockMap.get("inStockCount").toString()));
                    stockInfo = new StockInfo();
                    stockInfo.setId((String) stockMap.get("id"));
                    stockInfo.setSpecification((String) stockMap.get("specification"));
                    stockInfo.setMeasureUnit((String) stockMap.get("measureUnit"));
                    materialInfo = new MaterialInfo();
                    MaterialInfo materialMap = (MaterialInfo) stockMap.get("materialInfo");
                    materialInfo.setId(materialMap.getId());
                    materialInfo.setName(materialMap.getName());
                    materialInfo.setNumber(materialMap.getNumber());
                    materialInfo.setMaterialType(materialMap.getMaterialType());
                    stockInventoryDetailInfo.setMaterial(materialInfo);
                    stockInventoryDetailInfo.setStockInfo(stockInfo);
                    stockInventoryDetailInfos.add(stockInventoryDetailInfo);
                }
            }
        }

        return stockInventoryInfo;
    }

    @Override
    public CoreBaseInfo getEntityInfo() {
        return new StockInventoryInfo();
    }

    @Override
    public AbstractBaseService getService() {
        return this.stockInventoryService;
    }
    @Override
    public void afterOperate(BaseMethodEnum bme) throws HibernateException, QueryException {
        super.afterOperate(bme);
        if(this.data!=null){
            JSONObject jsonObject = (JSONObject) this.data;
            JSONArray stockInventoryDetailInfos = jsonObject.getJSONArray("stockInventoryDetailInfos");
            JSONArray stockInventoryDetailNew = new JSONArray();
            JSONObject stockInfo = null;
            JSONObject stockInventoryDetail = null;
            JSONObject materialType = null;
            JSONObject materialInfo = null;
            for(int i=0;i<stockInventoryDetailInfos.size();i++){
                stockInventoryDetail = stockInventoryDetailInfos.getJSONObject(i);
                stockInfo = stockInventoryDetail.getJSONObject("stockInfo");
                stockInventoryDetail.put("stockInfo",stockInfo);
                stockInventoryDetail.put("specification",stockInfo.getString("specification"));
                stockInventoryDetail.put("measureUnit",stockInfo.getString("measureUnit"));
                materialInfo = stockInventoryDetail.getJSONObject("material");
                materialType = new JSONObject();
                materialType.put("key",materialInfo.get("materialType"));
                materialType.put("val", MaterialType.map.get(materialInfo.getString("materialType")).getName());
                stockInventoryDetail.put("materialType",materialType);
                stockInventoryDetail.put("number",materialInfo.getString("number"));
                stockInventoryDetailNew.add(stockInventoryDetail);
            }
            jsonObject.put("stockInventoryDetailInfos",stockInventoryDetailNew);
            this.data = jsonObject;
        }
    }

    @Override
    public List<ColumnModel> getDataBinding() {
        List<ColumnModel> cols = super.getDataBinding();
        cols.add(new ColumnModel("name"));
        cols.add(new ColumnModel("number"));
        cols.add(new ColumnModel("remark"));
        cols.add(new ColumnModel("startDate",DataTypeEnum.DATE));
        cols.add(new ColumnModel("endDate",DataTypeEnum.DATE));
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

        ColumnModel stockInventoryDetailInfos = new ColumnModel("stockInventoryDetailInfos",DataTypeEnum.ENTRY,
                StockInventoryDetailInfo.class);

        ColumnModel stockInfo = new ColumnModel("stockInfo",DataTypeEnum.F7,"id,specification,measureUnit");
        stockInfo.setClaz(StockInfo.class);
        stockInventoryDetailInfos.getCols().add(stockInfo);

        ColumnModel material = new ColumnModel("material",DataTypeEnum.F7,"id,name,materialType,number");
        material.setClaz(MaterialInfo.class);
        stockInventoryDetailInfos.getCols().add(material);

        stockInventoryDetailInfos.getCols().add(new ColumnModel("id",DataTypeEnum.PK));
        stockInventoryDetailInfos.getCols().add(new ColumnModel("stockCount",DataTypeEnum.NUMBER));
        stockInventoryDetailInfos.getCols().add(new ColumnModel("inventoryCount",DataTypeEnum.NUMBER));
        stockInventoryDetailInfos.getCols().add(new ColumnModel("quantity",DataTypeEnum.NUMBER));
        stockInventoryDetailInfos.getCols().add(new ColumnModel("inStockCount",DataTypeEnum.NUMBER));
        stockInventoryDetailInfos.getCols().add(new ColumnModel("remark"));
        cols.add(stockInventoryDetailInfos);

        return cols;
    }

    @RequestMapping("/detail")
    @ResponseBody
    public WebDataModel getByStockInventoryId(String inventoryId) {
        WebDataModel webDataModel = new WebDataModel();
        webDataModel.setData(stockInventoryDetailService.queryByInventoryId(inventoryId));
        webDataModel.setStatusCode(STATUSCODE_SUCCESS);
        return webDataModel;
    }
}
