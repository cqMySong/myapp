package com.myapp.controller.ec.basedata.workcheckitem;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseDataEditController;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.entity.ec.basedata.WorkCheckItemInfo;
import com.myapp.enums.ec.WorkCheckType;
import com.myapp.service.ec.basedata.WorkCheckItemService;

@PermissionAnn(name="系统管理.现场管理.基础数据.施工现场检查项目",number="app.ec.basedata.workcheckitem")
@Controller
@RequestMapping("ec/basedata/workcheckitem")
public class WorkCheckItemEditController extends BaseDataEditController {
	
	@Resource
	public WorkCheckItemService workCheckItemService;

	public AbstractBaseService getService() {
		return workCheckItemService;
	}
	
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("workCheckType",DataTypeEnum.ENUM,WorkCheckType.class));
		cols.add(new ColumnModel("checkRequire"));
		return cols;
	}

	public Object createNewData() {
		return new WorkCheckItemInfo();
	}

	public CoreBaseInfo getEntityInfo() {
		return new WorkCheckItemInfo();
	}

}
