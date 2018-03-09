package com.myapp.controller.ec.other.rectifyfeedback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.util.BaseUtil;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseListController;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.entity.ec.other.RectifyNoticeInfo;
import com.myapp.service.ec.other.RectifyFeedBackService;

/**
 * 
 * @author Phoenix
 *
 */
@PermissionAnn(name="系统管理.现场管理.其他管理.整改回复",number="app.ec.other.rectifyfeedback")
@Controller
@RequestMapping("ec/other/rectifyFeedBacks")
public class RectifyFeedBackListController extends BaseListController{

	@Resource
	public RectifyFeedBackService rectifyFeedBackService;
	@Override
	public AbstractBaseService getService() {
		return rectifyFeedBackService;
	}
	@Override
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("number"));
		cols.add(new ColumnModel("rectifyNotice",DataTypeEnum.F7,RectifyNoticeInfo.class));
		cols.add(new ColumnModel("requires"));
		cols.add(new ColumnModel("isDone",DataTypeEnum.BOOLEAN));
		cols.add(new ColumnModel("doneDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("endDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("isFeedBack",DataTypeEnum.BOOLEAN));
		return cols;
	}

	@Override
	public void executeQueryParams(Criteria query) {
		super.executeQueryParams(query);
		String serach = request.getParameter("search");
		String projectId = "xyz";
		if(!BaseUtil.isEmpty(serach)){
			Map searchMap = JSONObject.parseObject(serach, new HashMap().getClass());
			Object objTree = searchMap.get("tree");
			if(objTree!=null){
				Map treeMap = JSONObject.parseObject(objTree.toString(), new HashMap().getClass());
				Object idObj = treeMap.get("id");
				Object type = treeMap.get("type");
				if(type!=null&&idObj!=null){
					if("project".equals(type.toString())){
						projectId = idObj.toString();
					}
				}
			}
		}
		query.add(Restrictions.eq("project.id",projectId));
	}
	@Override
	public String getEditUrl() {
		return "ec/other/rectifyfeedback/rectifyFeedBackEdit";
	}
	@Override
	public String getListUrl() {
		return "ec/other/rectifyfeedback/rectifyFeedBackList";
	}
}
