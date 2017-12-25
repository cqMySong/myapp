package com.myapp.controller.ec.basedata.testpiece;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseListController;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.BillState;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.enums.MeetingSummaryType;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.util.BaseUtil;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.service.ec.basedata.MeetingSummaryService;
import com.myapp.service.ec.basedata.TestPieceService;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ly
 */
@PermissionAnn(name="系统管理.现场管理.基础数据.试件管理",number="app.ec.basedata.testpiece")
@Controller
@RequestMapping("ec/basedata/testpieces")
public class TestPieceListController extends BaseListController {
	
	@Resource
	public TestPieceService testPieceService;

	@Override
	public AbstractBaseService getService() {
		return testPieceService;
	}

	@Override
	public String getEditUrl() {
		return "ec/basedata/testpiece/testPieceEdit";
	}

	@Override
	public String getListUrl() {
		return "ec/basedata/testpiece/testPieceList";
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
		query.add(Restrictions.eq("projectInfo.id",projectId));
	}
	@Override
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("name",DataTypeEnum.STRING));
		cols.add(new ColumnModel("number",DataTypeEnum.STRING));
		cols.add(new ColumnModel("specification",DataTypeEnum.STRING));
		cols.add(new ColumnModel("productionDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("inspectionDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("inspectionNo",DataTypeEnum.STRING));
		cols.add(new ColumnModel("inspectionResult",DataTypeEnum.BOOLEAN));
		cols.add(new ColumnModel("evidentialTesting",DataTypeEnum.BOOLEAN));
		cols.add(new ColumnModel("remark",DataTypeEnum.STRING));
		ColumnModel project = new ColumnModel("projectInfo",DataTypeEnum.F7,"id,name");
		project.setClaz(ProjectInfo.class);
		cols.add(project);

		ColumnModel creator = new ColumnModel("creator",DataTypeEnum.F7,"id,name");
		creator.setClaz(UserInfo.class);
		cols.add(creator);
		return cols;
	}


}
