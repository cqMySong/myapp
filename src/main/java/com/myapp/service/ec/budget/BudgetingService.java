package com.myapp.service.ec.budget;

import com.myapp.core.entity.UserInfo;
import com.myapp.core.exception.db.SaveException;
import com.myapp.core.model.MyWebContent;
import com.myapp.core.service.UserService;
import com.myapp.core.service.act.ActTaskService;
import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.entity.ec.budget.BudgetingInfo;
import com.myapp.entity.ec.drawing.DiscussionDrawingInfo;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 包路径：com.myapp.service.ec.drawing
 * 功能说明：
 * 创建人： ly
 * 创建时间: 2017-07-30 19:37
 */
@Service("budgetingService")
public class BudgetingService extends BaseInterfaceService<BudgetingInfo> implements ExecutionListener {

    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {

    }
    
}
