package com.myapp.controller.ec.drawing;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseBillEditController;
import com.myapp.core.controller.BaseEditController;
import com.myapp.core.controller.BaseListController;
import com.myapp.core.entity.BaseOrgInfo;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.BaseMethodEnum;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.exception.db.QueryException;
import com.myapp.core.model.ColumnModel;
import com.myapp.entity.ec.basedata.ProStructureInfo;
import com.myapp.entity.ec.basedata.ProSubInfo;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.entity.ec.drawing.DiscussionDrawingInfo;
import com.myapp.service.ec.drawing.DiscussionDrawingService;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 包路径：com.myapp.controller.ec.drawing
 * 功能说明：图纸会审列表信息
 * 创建人： ly
 * 创建时间: 2017-07-30 14:49
 */
@PermissionAnn(name="系统管理.现场管理.图纸.图纸会审",number="app.ec.drawing.discussiondrawing")
@Controller
@RequestMapping("ec/discussiondrawing")
public class DiscussionDrawingEditController extends BaseBillEditController {
    @Resource
    private DiscussionDrawingService discussionDrawingService;
    @Override
    public Object createNewData() {
        return new DiscussionDrawingInfo();
    }

    @Override
    public CoreBaseInfo getEntityInfo() {
        return new DiscussionDrawingInfo();
    }

    @Override
    public AbstractBaseService getService() {
        return this.discussionDrawingService;
    }

    @Override
    protected void afterOperate(BaseMethodEnum bme) throws HibernateException, QueryException {
        super.afterOperate(bme);
        CoreBaseInfo coreBaseInfo = null;
        if(getEditData()!=null){
            Map data = (Map) getEditData();
            if(data.get("belongId")==null){
                return;
            }
            if("proSub".equals(data.get("type"))){
                coreBaseInfo =discussionDrawingService.getEntity(ProSubInfo.class,data.get("belongId").toString());
            }else if("project".equals(data.get("type"))){
                coreBaseInfo =discussionDrawingService.getEntity(ProjectInfo.class,data.get("belongId").toString());
            }else if("proStructure".equals(data.get("type"))){
                coreBaseInfo =discussionDrawingService.getEntity(ProStructureInfo.class,data.get("belongId").toString());
            }
            if(coreBaseInfo!=null){
                setOtherData(coreBaseInfo.getName());
            }
        }
    }

    @Override
    public List<ColumnModel> getDataBinding() {
        List<ColumnModel> cols = super.getDataBinding();
        cols.add(new ColumnModel("id"));
        cols.add(new ColumnModel("name"));
        cols.add(new ColumnModel("number"));
        cols.add(new ColumnModel("conferencePlace"));
        cols.add(new ColumnModel("subject"));
        cols.add(new ColumnModel("type"));
        cols.add(new ColumnModel("belongId"));
        cols.add(new ColumnModel("participantUnits"));
        cols.add(new ColumnModel("participants"));
        cols.add(new ColumnModel("moderator"));
        cols.add(new ColumnModel("completeSignature",DataTypeEnum.BOOLEAN));
        cols.add(new ColumnModel("createUser",DataTypeEnum.F7,UserInfo.class));
        cols.add(new ColumnModel("lastUpdateUser",DataTypeEnum.F7,UserInfo.class));
        cols.add(new ColumnModel("auditor",DataTypeEnum.F7,UserInfo.class));
        cols.add(new ColumnModel("conferenceDate", DataTypeEnum.DATE));
        cols.add(new ColumnModel("createDate", DataTypeEnum.DATE));
        cols.add(new ColumnModel("auditDate", DataTypeEnum.DATE));
        cols.add(new ColumnModel("lastUpdateDate", DataTypeEnum.DATE));
        return cols;
    }
}
