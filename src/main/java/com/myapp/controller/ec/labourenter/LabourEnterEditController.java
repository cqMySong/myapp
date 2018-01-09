package com.myapp.controller.ec.labourenter;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.myapp.core.controller.BaseEditController;
import com.myapp.entity.ec.basedata.ProVisitRecordInfo;
import com.myapp.entity.ec.labour.LabourEnterInfo;
import com.myapp.enums.ec.IDType;
import com.myapp.enums.ec.VisitType;
import com.myapp.service.ec.labour.LabourEnterService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseBillEditController;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.BillState;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.entity.ec.basedata.ProLabourInfo;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.entity.ec.labour.ProLabourWageInfo;
import com.myapp.entity.ec.labour.ProLabourWageItemInfo;
import com.myapp.service.ec.labour.ProLabourWageService;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年12月24日 
 * @system:
 * 劳务人员进场及用工记录
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.劳务人员.劳务人员进场及用工记录",number="app.ec.labour.enter")
@Controller
@RequestMapping("ec/labour/enter")
public class LabourEnterEditController extends BaseEditController {
	@Resource
	public LabourEnterService labourEnterService;
	@Override
	public AbstractBaseService getService() {
		return labourEnterService;
	}
	@Override
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("number"));
		cols.add(new ColumnModel("lastUpdateUser",DataTypeEnum.F7,UserInfo.class));
		cols.add(new ColumnModel("createDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("lastUpdateDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("enterDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("createUser",DataTypeEnum.F7,UserInfo.class));

		ColumnModel project = new ColumnModel("project",DataTypeEnum.F7,"id,name");
		project.setClaz(ProjectInfo.class);
		cols.add(project);
		return cols;
	}
	@Override
	public Object createNewData() {
		LabourEnterInfo labourEnterInfo= new LabourEnterInfo();
		labourEnterInfo.setCreateUser(getCurUser());
		return labourEnterInfo;
	}
	@Override
	public CoreBaseInfo getEntityInfo() {
		return new LabourEnterInfo();
	}
}
