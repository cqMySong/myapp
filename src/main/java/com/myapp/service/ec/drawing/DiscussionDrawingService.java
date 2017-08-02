package com.myapp.service.ec.drawing;

import com.myapp.core.exception.db.SaveException;
import com.myapp.core.service.act.ActTaskService;
import com.myapp.core.service.base.BaseInterfaceService;
import com.myapp.entity.ec.drawing.DiscussionDrawingInfo;
import com.myapp.enums.FlowCategory;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 包路径：com.myapp.service.ec.drawing
 * 功能说明：
 * 创建人： ly
 * 创建时间: 2017-07-30 19:37
 */
@Service("discussionDrawingService")
public class DiscussionDrawingService extends BaseInterfaceService<DiscussionDrawingInfo> implements JavaDelegate{
    @Resource
    private ActTaskService actTaskService;
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println(delegateExecution.getProcessBusinessKey());
    }

    @Override
    public Object submitEntity(Object entity) throws SaveException {
        Object returnObj = super.submitEntity(entity);
        DiscussionDrawingInfo discussionDrawingInfo = (DiscussionDrawingInfo) returnObj;
        actTaskService.startProcess(FlowCategory.DRAWING.getValue(),discussionDrawingInfo.getId(),
                discussionDrawingInfo.getName(),null,discussionDrawingInfo.getCreateUser().getNumber());
        return returnObj;
    }
}
