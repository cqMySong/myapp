package com.myapp.service.ec.drawing;

import com.myapp.core.entity.UserInfo;
import com.myapp.core.exception.db.SaveException;
import com.myapp.core.model.MyWebContext;
import com.myapp.core.service.UserService;
import com.myapp.core.service.act.ActTaskService;
import com.myapp.core.service.base.BaseInterfaceService;
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
@Service("discussionDrawingService")
public class DiscussionDrawingService extends BaseInterfaceService<DiscussionDrawingInfo> implements ExecutionListener {
    @Resource
    private ActTaskService actTaskService;
    @Resource
    private UserService userService;
    @Override
    public Object submitEntity(Object entity) throws SaveException {
        Object returnObj = super.submitEntity(entity);
        DiscussionDrawingInfo discussionDrawingInfo = (DiscussionDrawingInfo) returnObj;
        actTaskService.startProcess(DiscussionDrawingInfo.class,discussionDrawingInfo.getId(),
                discussionDrawingInfo.getName(),null,discussionDrawingInfo.getCreateUser().getNumber());
        return returnObj;
    }

    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {
        String businessKey = delegateExecution.getProcessBusinessKey();
        DiscussionDrawingInfo discussionDrawingInfo = (DiscussionDrawingInfo) getEntity(businessKey);
        MyWebContext myWebContent = new MyWebContext();
        UserInfo userInfo = userService.queryUserByNumber("camel");
        myWebContent.setUserId(userInfo.getId());
        myWebContent.setUserName(userInfo.getName());
        myWebContent.setUserNumber(userInfo.getNumber());
        String pass = delegateExecution.getVariable("pass").toString();
        if("1".equals(pass)){//审核通过
            auditEntity(discussionDrawingInfo,myWebContent);
        }else {
            auditNoPassEntity(discussionDrawingInfo,myWebContent);
        }
    }
    
}
