package com.myapp.service.ec.stock;

import com.myapp.core.exception.db.SaveException;
import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.entity.ec.stock.StockInfo;
import com.myapp.entity.ec.stock.StockOutDetailInfo;
import com.myapp.entity.ec.stock.StockOutInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @path：com.myapp.service.ec.stock
 * @description：
 * @author： ly
 * @date: 2017-11-07 20:16
 */
@Service("stockOutDetailService")
public class StockOutDetailService extends BaseInterfaceService<StockOutDetailInfo> {
    /**
     * 功能：判断是否被领用
     * @param stockInfo
     * @return
     */
    public boolean isStockOutByStockId(StockInfo stockInfo){
        String hql = "select count(1) from StockOutDetail stockOutDetail where stockOutDetail.stockInfo.id=?";
        List list = findByHQL(hql,new String[]{stockInfo.getId()});
        return false;
    }
}
