package com.myapp.controller.ec.stock.stockrevert;

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
import com.myapp.entity.ec.stock.StockOutDetailInfo;
import com.myapp.entity.ec.stock.StockOutInfo;
import com.myapp.entity.ec.stock.StockRevertDetailInfo;
import com.myapp.entity.ec.stock.StockRevertInfo;
import com.myapp.service.ec.stock.StockOutService;
import com.myapp.service.ec.stock.StockRevertService;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * @path：com.myapp.controller.ec.stock.stockrevert
 * @description：领料出库
 * @author ： ly
 * @date: 2017-07-30 14:49
 */
@PermissionAnn(name="系统管理.现场管理.库存管理.库存归还",number="app.ec.stock.stockrevert")
@Controller
@RequestMapping("ec/stock/stockrevert")
public class StockRevertEditController extends BaseBillEditController {
    @Resource
    private StockRevertService stockRevertService;
    @Override
    public Object createNewData() {
        return new StockRevertInfo();
    }

    @Override
    public CoreBaseInfo getEntityInfo() {
        return new StockRevertInfo();
    }

    @Override
    public AbstractBaseService getService() {
        return this.stockRevertService;
    }
    @Override
    public void afterOperate(BaseMethodEnum bme) throws HibernateException, QueryException {
        super.afterOperate(bme);
        if(this.data!=null){
            if(!bme.getValue().equals(BaseMethodEnum.ADDNEW.getValue())&&this.data instanceof JSONObject){
                JSONObject jsonObject = (JSONObject) this.data;
                JSONArray stockRevertDetailInfos = jsonObject.getJSONArray("stockRevertDetailInfos");
                JSONArray stockRevertDetailNew = new JSONArray();
                JSONObject materialInfo = null;
                JSONObject stockRevertDetail = null;
                JSONObject materialType = null;
                for(int i=0;i<stockRevertDetailInfos.size();i++){
                    stockRevertDetail = stockRevertDetailInfos.getJSONObject(i);
                    materialInfo = stockRevertDetail.getJSONObject("material");
                    materialType = new JSONObject();
                    materialType.put("key",materialInfo.get("materialType"));
                    materialType.put("val", MaterialType.map.get(materialInfo.getString("materialType")).getName());
                    stockRevertDetail.put("materialType",materialType);
                    stockRevertDetailNew.add(stockRevertDetail);
                }
                jsonObject.put("stockRevertDetailInfos",stockRevertDetailNew);
                this.data = jsonObject;
            }
        }
    }

    @Override
    public List<ColumnModel> getDataBinding() {
        List<ColumnModel> cols = super.getDataBinding();
        cols.add(new ColumnModel("name"));
        cols.add(new ColumnModel("number"));
        cols.add(new ColumnModel("remark"));
        cols.add(new ColumnModel("returnPerson",DataTypeEnum.STRING));
        cols.add(new ColumnModel("revertStockDate",DataTypeEnum.DATE));
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

        ColumnModel stockRevertDetailInfos = new ColumnModel("stockRevertDetailInfos",DataTypeEnum.ENTRY,
                StockRevertDetailInfo.class);

        ColumnModel stockOutDetailInfo = new ColumnModel("stockOutDetailInfo",DataTypeEnum.F7,"id");
        stockOutDetailInfo.setClaz(StockOutDetailInfo.class);
        stockRevertDetailInfos.getCols().add(stockOutDetailInfo);

        ColumnModel material = new ColumnModel("material",DataTypeEnum.F7,"id,name,materialType");
        material.setClaz(MaterialInfo.class);
        stockRevertDetailInfos.getCols().add(material);

        stockRevertDetailInfos.getCols().add(new ColumnModel("id",DataTypeEnum.PK));
        stockRevertDetailInfos.getCols().add(new ColumnModel("count",DataTypeEnum.NUMBER));
        stockRevertDetailInfos.getCols().add(new ColumnModel("specification"));
        stockRevertDetailInfos.getCols().add(new ColumnModel("measureUnit"));
        stockRevertDetailInfos.getCols().add(new ColumnModel("remark"));
        stockRevertDetailInfos.getCols().add(new ColumnModel("stockOutNumber"));
        cols.add(stockRevertDetailInfos);

        return cols;
    }
}
