package com.myapp.controller.ec.other.rectify;

import java.util.List;

import javax.annotation.Resource;

import com.myapp.entity.ec.basedata.ProjectInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.entity.CoreInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseBillEditController;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.entity.ec.other.RectifyNoticeInfo;
import com.myapp.service.ec.other.RectifyNoticeService;


@PermissionAnn(name="系统管理.现场管理.其他管理.整改通知",number="app.ec.other.designchange")
@Controller
@RequestMapping("ec/other/rectifyNotice")
public class RectifyNoticeEditController extends BaseBillEditController {
	
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
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("type"));
		cols.add(new ColumnModel("orgUnit"));
		cols.add(new ColumnModel("orgUnitPerson"));
		cols.add(new ColumnModel("problem"));
		cols.add(new ColumnModel("requires"));
		cols.add(new ColumnModel("endDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("checkDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("checkOrgUnit"));
		cols.add(new ColumnModel("checkOrgPerson"));
		cols.add(new ColumnModel("project",DataTypeEnum.F7, ProjectInfo.class));
		return cols;
	}
	@Override
	public Object createNewData() {
		RectifyNoticeInfo info = new RectifyNoticeInfo();
		return info;
	}
	@Override
	public CoreInfo getEntityInfo() {
		return new RectifyNoticeInfo();
	}

}
