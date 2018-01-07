package com.myapp.controller.ec.stock.lease;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseF7QueryController;
import com.myapp.core.controller.BaseListController;
import com.myapp.core.entity.MaterialInfo;
import com.myapp.core.entity.MeasureUnitInfo;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.BaseMethodEnum;
import com.myapp.core.enums.BillState;
import com.myapp.core.enums.ChargingBasis;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.service.base.BaseService;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.util.WebUtil;
import com.myapp.entity.ec.basedata.ProBaseWbsInfo;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.entity.ec.engineering.SiteVisaOutInfo;
import com.myapp.entity.ec.stock.MaterialLeaseInfo;
import com.myapp.service.ec.stock.MaterialLeaseService;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
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
@RequestMapping("ec/stock/leaseF7")
public class MaterialLeaseF7QueryController extends BaseF7QueryController {
    @Resource
    public BaseService baseService;
    @Override
    public AbstractBaseService getService() {
        return baseService.newServicInstance(MaterialLeaseInfo.class);
    }
    @Override
    public List<ColumnModel> getDataBinding() {
        List<ColumnModel> cols = super.getDataBinding();
        ColumnModel col =  new ColumnModel("number",DataTypeEnum.STRING);
        col.setAlias_zh("出租单号");
        cols.add(col);

        col =  new ColumnModel("materialInfo",DataTypeEnum.F7,MaterialInfo.class);
        col.setFormat("id,name");
        col.setAlias_zh("id,物料名称");
        cols.add(col);

        col =  new ColumnModel("measureUnitInfo",DataTypeEnum.F7,MeasureUnitInfo.class);
        col.setFormat("id,name");
        col.setAlias_zh("id,计量单位");
        cols.add(col);

        col = new ColumnModel("leaseUnit",DataTypeEnum.STRING);
        col.setAlias_zh("出租单位");
        cols.add(col);

        col = new ColumnModel("leaseDate",DataTypeEnum.DATE);
        col.setAlias_zh("出租日期");
        cols.add(col);

        col = new ColumnModel("leaseCount",DataTypeEnum.NUMBER);
        col.setAlias_zh("出租数量");
        cols.add(col);

        return cols;
    }
    @Override
    public Order getOrder() {
        return Order.asc("number");
    }
    @Override
    public String getUIWinTitle() {
        return "周转材料、设备使用(出租)";
    }

    @Override
    public void executeQueryParams(Criteria query) {
        super.executeQueryParams(query);
        query.createAlias("materialLeaseBackInfo","a", JoinType.LEFT_OUTER_JOIN);
        query.add(Restrictions.isNull("a.id"));
    }
}
