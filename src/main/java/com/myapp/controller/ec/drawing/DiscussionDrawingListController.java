package com.myapp.controller.ec.drawing;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseListController;
import com.myapp.core.controller.BaseTreeListController;
import com.myapp.core.enums.BaseMethodEnum;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.util.WebUtil;
import com.myapp.service.ec.drawing.DiscussionDrawingService;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 包路径：com.myapp.controller.ec.drawing
 * 功能说明：图纸会审列表信息
 * 创建人： ly
 * 创建时间: 2017-07-30 14:49
 */
@PermissionAnn(name="系统管理.图纸.图纸会审",number="app.ec.drawing.discussiondrawing")
@Controller
@RequestMapping("ec/discussiondrawings")
public class DiscussionDrawingListController extends BaseListController {
    @Resource
    private DiscussionDrawingService discussionDrawingService;

    @Override
    public String getEditUrl() {
        return "ec/drawing/discussion/discussionDrawingEdit";
    }

    @Override
    public String getListUrl() {
        return "ec/drawing/discussion/discussionDrawingList";
    }

    @Override
    public AbstractBaseService getService() {
        return this.discussionDrawingService;
    }
    public void executeQueryParams(Criteria query) {
        String search = request.getParameter("search");
        SimpleExpression se = null;
        if(!BaseUtil.isEmpty(search)){
            Map searchMap = JSONObject.parseObject(search, new HashMap().getClass());
            Object objTree = searchMap.get("tree");
            if(objTree!=null){
                Map treeMap = JSONObject.parseObject(objTree.toString(), new HashMap().getClass());
                Object idObj = treeMap.get("belongId");
                Object type = treeMap.get("type");
                se = Restrictions.eq("belongId", idObj.toString());
                query.add(se);
                se = Restrictions.eq("type", type.toString());
                query.add(se);
            }
        }
    }

    @Override
    public void packageUIParams(Map params) {
        super.packageUIParams(params);
        if(BaseMethodEnum.ADDNEW.equals(baseMethod)){
            if(params!=null&&params.get("uiCtx")!=null){
                String uiCtx = (String) params.get("uiCtx");
                params.put("uiCtx",WebUtil.UUID_ReplaceID(uiCtx));
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
        cols.add(new ColumnModel("completeSignature", DataTypeEnum.BOOLEAN));
        cols.add(new ColumnModel("conferenceDate", DataTypeEnum.DATE));
        return cols;
    }
}
