package com.myapp.service.ec.stock;

import com.myapp.core.entity.MaterialInfo;
import com.myapp.core.exception.db.DeleteException;
import com.myapp.core.exception.db.SaveException;
import com.myapp.core.exception.db.UpdateException;
import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.entity.ec.purchase.PurchaseContractDetailInfo;
import com.myapp.entity.ec.purchase.PurchaseStockDetailInfo;
import com.myapp.entity.ec.stock.StockInfo;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
    @Resource
    private StockOutDetailService stockOutDetailService;
    /**
     * 功能：根据物料信息查询库存
     * @param materialInfo
     * @return
     */
    public StockInfo getByMaterialId(MaterialInfo materialInfo,ProjectInfo projectInfo){
        String hql = "select stockInfo from StockInfo stockInfo where stockInfo.materialInfo.id=? and " +
                "stockInfo.projectInfo.id=?";
        return queryEntity(StockInfo.class,hql,new Object[]{materialInfo.getId(),projectInfo.getId()});
    }

    /**
     * 入库时，保存和修改库存信息
     * @param projectInfo
     * @param count
     * @param materialInfo
     * @return
     * @throws SaveException
     */
    public StockInfo saveByMaterialInfo(ProjectInfo projectInfo, BigDecimal count, MaterialInfo materialInfo)
            throws SaveException {
        StockInfo stockInfo = getByMaterialId(materialInfo,projectInfo);
        if(stockInfo==null){
            stockInfo = new StockInfo();
            stockInfo.setMaterialInfo(materialInfo);
            stockInfo.setCount(count);
            stockInfo.setMeasureUnit(materialInfo.getUnit().getName());
            stockInfo.setProjectInfo(projectInfo);
            stockInfo.setSpecification(materialInfo.getSpecification());
            stockInfo.setMaterialType(materialInfo.getMaterialType());
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

    /**
     * 功能：删除库存信息
     * @param purchaseStockDetailInfo
     * @throws DeleteException 
     */
    public void deleteByInStock(PurchaseStockDetailInfo purchaseStockDetailInfo) throws DeleteException{
        String hql = "select stockInfo from StockInfo stockInfo where stockInfo.purchaseStockDetailInfo.id=?";
        StockInfo stockInfo = queryEntity(StockInfo.class,hql,new String[]{purchaseStockDetailInfo.getId()});
        if(stockOutDetailService.isStockOutByStockId(stockInfo)){
            throw new RuntimeException("已出库,不能删除");
        }
        deleteEntity(stockInfo);
    }

    /**
     * 加减库存数量
     * @param count
     * @param stockInfo
     */
    public void subByStock(BigDecimal count,StockInfo stockInfo){
        String hql ="update StockInfo stockInfo set stockInfo.count=stockInfo.count-? where stockInfo.id=? and stockInfo.count>=?";
        try {
            executeUpdata(hql,new Object[]{count,stockInfo.getId(),count});
        } catch (UpdateException e) {
            throw new RuntimeException("更新库存信息失败");
        }
    }
}
