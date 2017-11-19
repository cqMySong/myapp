package com.myapp.service.ec.budget;

import com.myapp.core.exception.db.QueryException;
import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.entity.ec.budget.BudgetingDetailInfo;
import com.myapp.entity.ec.budget.EnquiryPriceDetailInfo;
import org.hibernate.Criteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Service;

/**
 * @path：com.myapp.service.ec.budget
 * @desciption：询价明细
 * @author ： ly
 * @date: 2017-08-28 21:15
 */
@Service("enquiryPriceDetailService")
public class EnquiryPriceDetailService extends BaseInterfaceService<EnquiryPriceDetailInfo>{
    @Override
    public Criteria initQueryCriteria() throws QueryException {
        Criteria criteria = super.initQueryCriteria();
        criteria.createAlias("budgetingDetailInfo","bdi", JoinType.INNER_JOIN);
        criteria.createAlias("parent","p", JoinType.INNER_JOIN);
        criteria.createAlias("parent.project","pro", JoinType.INNER_JOIN);
        criteria.createAlias("budgetingDetailInfo.material","mat", JoinType.INNER_JOIN);
        return criteria;
    }
}
