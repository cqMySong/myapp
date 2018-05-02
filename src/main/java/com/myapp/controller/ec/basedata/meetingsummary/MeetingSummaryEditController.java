package com.myapp.controller.ec.basedata.meetingsummary;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseBillEditController;
import com.myapp.core.controller.BaseDataEditController;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.BillState;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.enums.MeetingSummaryType;
import com.myapp.core.model.ColumnModel;
import com.myapp.entity.ec.basedata.ECUnitInfo;
import com.myapp.entity.ec.basedata.MeetingSummaryInfo;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.enums.UnitType;
import com.myapp.service.ec.basedata.ECUnitService;
import com.myapp.service.ec.basedata.MeetingSummaryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

@PermissionAnn(name="系统管理.现场管理.基础数据.会议纪要台账",number="app.ec.basedata.meetingsummary")
@Controller
@RequestMapping("ec/basedata/meetingsummary")
public class MeetingSummaryEditController extends BaseBillEditController {
	
	@Resource
	public MeetingSummaryService meetingSummaryService;

	@Override
	public AbstractBaseService getService() {
		return meetingSummaryService;
	}

	@Override
	public Object createNewData() {
		return new MeetingSummaryInfo();
	}

	@Override
	public CoreBaseInfo getEntityInfo() {
		return new MeetingSummaryInfo();
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
		cols.add(new ColumnModel("remark",DataTypeEnum.STRING));
		cols.add(new ColumnModel("createUser",DataTypeEnum.F7,UserInfo.class));
		cols.add(new ColumnModel("lastUpdateUser",DataTypeEnum.F7,UserInfo.class));
		cols.add(new ColumnModel("auditor",DataTypeEnum.F7,UserInfo.class));
		cols.add(new ColumnModel("createDate", DataTypeEnum.DATETIME));
		cols.add(new ColumnModel("auditDate", DataTypeEnum.DATETIME));
		cols.add(new ColumnModel("lastUpdateDate", DataTypeEnum.DATETIME));
		ColumnModel columnModel = new ColumnModel("project",DataTypeEnum.F7,"id,name");
		columnModel.setClaz(ProjectInfo.class);
		cols.add(columnModel);
		return cols;
	}


}
