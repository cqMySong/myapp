package com.myapp.controller.ec.basedata.scheme;

import javax.annotation.Resource;

import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.BillState;
import com.myapp.core.enums.ChargingBasis;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.enums.TypeOfWork;
import com.myapp.core.model.ColumnModel;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.entity.ec.basedata.SchemeTypeInfo;
import com.myapp.entity.ec.engineering.SiteVisaOutDetailInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseBillEditController;
import com.myapp.entity.ec.basedata.ConstructionSchemeInfo;
import com.myapp.service.ec.basedata.ConstructionSchemeService;

import java.util.List;

@PermissionAnn(name="系统管理.现场管理.基础数据.施工方案",number="app.ec.basedata.scheme")
@Controller
@RequestMapping("ec/basedata/schemeedit")
public class ConstructionSchemeEditController extends BaseBillEditController {
	
	@Resource
	public ConstructionSchemeService constructionSchemeService;
	@Override
	public AbstractBaseService getService() {
		return constructionSchemeService;
	}
	@Override
	public Object createNewData() {
		ConstructionSchemeInfo info = new ConstructionSchemeInfo();
		return info;
	}
	@Override
	public CoreBaseInfo getEntityInfo() {
		return new ConstructionSchemeInfo();
	}

	@Override
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("number"));
		cols.add(new ColumnModel("compileDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("lastFinishDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("schemeType", DataTypeEnum.F7,SchemeTypeInfo.class));
		cols.add(new ColumnModel("compiler", DataTypeEnum.F7,UserInfo.class));
		cols.add(new ColumnModel("billState",DataTypeEnum.ENUM, BillState.class));
		cols.add(new ColumnModel("createUser",DataTypeEnum.F7,UserInfo.class));
		cols.add(new ColumnModel("lastUpdateUser",DataTypeEnum.F7,UserInfo.class));
		cols.add(new ColumnModel("auditor",DataTypeEnum.F7,UserInfo.class));
		cols.add(new ColumnModel("createDate", DataTypeEnum.DATE));
		cols.add(new ColumnModel("auditDate", DataTypeEnum.DATE));
		cols.add(new ColumnModel("lastUpdateDate", DataTypeEnum.DATE));
		cols.add(new ColumnModel("project",DataTypeEnum.F7,ProjectInfo.class));
		return cols;
	}
}
