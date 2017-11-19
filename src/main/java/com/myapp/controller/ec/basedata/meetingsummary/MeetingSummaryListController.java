package com.myapp.controller.ec.basedata.meetingsummary;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseDataListController;
import com.myapp.core.controller.BaseListController;
import com.myapp.core.enums.BillState;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.enums.MeetingSummaryType;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.util.BaseUtil;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.enums.UnitType;
import com.myapp.service.ec.basedata.ECUnitService;
import com.myapp.service.ec.basedata.MeetingSummaryService;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@PermissionAnn(name="系统管理.现场管理.基础数据.会议纪要",number="app.ec.basedata.meetingsummary")
@Controller
@RequestMapping("ec/basedata/meetingsummaries")
public class MeetingSummaryListController extends BaseListController {
	
	@Resource
	public MeetingSummaryService meetingSummaryService;

	@Override
	public AbstractBaseService getService() {
		return meetingSummaryService;
	}

	@Override
	public String getEditUrl() {
		return "ec/basedata/meetingsummary/meetingSummaryEdit";
	}

	@Override
	public String getListUrl() {
		return "ec/basedata/meetingsummary/meetingSummaryList";
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
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("name",DataTypeEnum.STRING));
		cols.add(new ColumnModel("number",DataTypeEnum.STRING));
		cols.add(new ColumnModel("billState",DataTypeEnum.ENUM, BillState.class));
		cols.add(new ColumnModel("meetingSummaryType",DataTypeEnum.ENUM,MeetingSummaryType.class));
		cols.add(new ColumnModel("meetingDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("content",DataTypeEnum.STRING));
		ColumnModel project = new ColumnModel("project",DataTypeEnum.F7,"id,name");
		project.setClaz(ProjectInfo.class);
		cols.add(project);
		return cols;
	}


}
