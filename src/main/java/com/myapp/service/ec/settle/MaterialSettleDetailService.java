package com.myapp.service.ec.settle;

import com.myapp.core.exception.db.QueryException;
import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.entity.ec.settle.MaterialSettleDetailInfo;
import org.hibernate.Criteria;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Service;

/**
 * @path：com.myapp.service.ec.settle
 * @description：
 * @author： ly
 * @date: 2017-11-11 15:03
 */
@Service("materialSettleDetailService")
public class MaterialSettleDetailService extends BaseInterfaceService<MaterialSettleDetailInfo> {
    @Override
    public Criteria initQueryCriteria() throws QueryException {
        Criteria criteria = super.initQueryCriteria();
        criteria.createAlias("purchaseContractInfo","contract", JoinType.INNER_JOIN);
        criteria.createAlias("parent","pr", JoinType.INNER_JOIN);
        criteria.createAlias("parent.operator","op", JoinType.INNER_JOIN);
        return criteria;
    }
}
