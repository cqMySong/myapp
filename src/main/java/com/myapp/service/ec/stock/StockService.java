package com.myapp.service.ec.stock;

import com.myapp.core.entity.MaterialInfo;
import com.myapp.core.exception.db.SaveException;
import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.entity.ec.purchase.PurchaseContractDetailInfo;
import com.myapp.entity.ec.stock.StockInfo;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @path：com.myapp.service.ec.stock
 * @description：库存信息
 * @author： ly
 * @date: 2017-11-03 15:23
 */
@Service("stockService")
public class StockService  extends BaseInterfaceService<StockInfo> {
    /**
     * 功能：根据物料信息查询库存
     * @param materialInfo
     * @return
     */
    public StockInfo getStockInfoByMaterialId(MaterialInfo materialInfo){
        String hql = "select stockInfo from StockInfo stockInfo where stockInfo.materialInfo.id=?";
        return queryEntity(StockInfo.class,hql,new Object[]{materialInfo.getId()});
    }

    /**
     * 入库时，保存和修改库存信息
     * @param projectInfo
     * @param count
     * @param purchaseContractDetailInfo
     * @return
     * @throws SaveException
     */
    public StockInfo saveStockByMaterialId(ProjectInfo projectInfo, BigDecimal count,MaterialInfo materialInfo,
                                              PurchaseContractDetailInfo purchaseContractDetailInfo)
            throws SaveException {
        StockInfo stockInfo = getStockInfoByMaterialId(materialInfo);
        if(stockInfo==null){
            stockInfo = new StockInfo();
            stockInfo.setMaterialInfo(materialInfo);
            stockInfo.setCount(count);
            stockInfo.setMeasureUnit(purchaseContractDetailInfo.getMeasureUnitName());
            stockInfo.setProjectInfo(projectInfo);
            stockInfo.setSpecification(purchaseContractDetailInfo.getSpecification());
            stockInfo.setRemark("");
        }else{
            stockInfo.setCount(stockInfo.getCount().add(count));
        }
        return (StockInfo) saveEntity(stockInfo);
    }

    /**
     * 功能：根据单位工程id查询库存信息
     * @param projectId
     * @return
     */
    public List<Map> queryStockByProjectId(String projectId){
        String hql = "select stock.id as id,stock.count as count," +
                "stock.specification as specification,stock.measureUnit as measureUnit," +
                "stock.materialInfo as materialInfo from StockInfo stock where stock.projectInfo.id=?";
        return findByHQL(hql,new String[]{projectId});
    }
}
