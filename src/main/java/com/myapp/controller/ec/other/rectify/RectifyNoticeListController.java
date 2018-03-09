package com.myapp.controller.ec.other.rectify;

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
import com.myapp.service.ec.other.RectifyNoticeService;

/**
 * 
 * @author Phoenix
 *
 */
@PermissionAnn(name="系统管理.现场管理.其他管理.整改通知",number="app.ec.other.rectifynotice")
@Controller
@RequestMapping("ec/other/rectifyNotices")
public class RectifyNoticeListController extends BaseListController{

	@Resource
	public RectifyNoticeService rectifyNoticeService;
	@Override
	public AbstractBaseService getService() {
		return rectifyNoticeService;
	}
	@Override
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("number"));
		cols.add(new ColumnModel("type"));
		cols.add(new ColumnModel("orgUnit"));
		cols.add(new ColumnModel("orgUnitPerson"));
		cols.add(new ColumnModel("problem"));
		cols.add(new ColumnModel("requires"));
		cols.add(new ColumnModel("endDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("checkOrgUnit"));
		cols.add(new ColumnModel("checkOrgPerson"));
		cols.add(new ColumnModel("checkDate",DataTypeEnum.DATE));

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
		return "ec/other/rectifynotice/rectifyNoticeEdit";
	}
	@Override
	public String getListUrl() {
		return "ec/other/rectifynotice/rectifyNoticeList";
	}
}
