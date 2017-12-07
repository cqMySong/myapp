package com.myapp.controller.ec.firepermit;

import java.util.List;

import javax.annotation.Resource;

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
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.entity.ec.basedata.WorkCheckItemInfo;
import com.myapp.entity.ec.firepermit.Lev3FirePermitInfo;
import com.myapp.entity.ec.proworkcheck.ProSafeCheckInfo;
import com.myapp.entity.ec.proworkcheck.ProSafeCheckItemInfo;
import com.myapp.enums.ec.CheckType;
import com.myapp.enums.ec.WorkCheckType;
import com.myapp.service.ec.firepermit.Lev3FirePermitService;
import com.myapp.service.ec.proworkcheck.ProSafeCheckService;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年12月07日 
 * @system:3级动火许可
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.动火管理.三级动火许可",number="app.ec.firepermit.lev3firepermit")
@Controller
@RequestMapping("ec/firepermit/lev3firepermit")
public class Lev3FirePermitEditController extends BaseBillEditController{
	@Resource
	public Lev3FirePermitService lev3FirePermitService;
	public AbstractBaseService getService() {
		return lev3FirePermitService;
	}

	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("number"));
		cols.add(new ColumnModel("firePart"));
		cols.add(new ColumnModel("fireDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("bizDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("billState",DataTypeEnum.ENUM,BillState.class));
		cols.add(new ColumnModel("project",DataTypeEnum.F7,ProjectInfo.class));
		cols.add(new ColumnModel("welder"));
		cols.add(new ColumnModel("guarder"));
		cols.add(new ColumnModel("proposer"));
		cols.add(new ColumnModel("welderbz"));
		cols.add(new ColumnModel("pzUser",DataTypeEnum.F7,UserInfo.class));
		cols.add(new ColumnModel("jsUser",DataTypeEnum.F7,UserInfo.class));
		cols.add(new ColumnModel("aqUser",DataTypeEnum.F7,UserInfo.class));
		
		cols.add(new ColumnModel("remark"));
		
		cols.add(new ColumnModel("createUser",DataTypeEnum.F7,UserInfo.class));
		cols.add(new ColumnModel("createDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("lastUpdateUser",DataTypeEnum.F7,UserInfo.class));
		cols.add(new ColumnModel("lastUpdateDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("auditor",DataTypeEnum.F7,UserInfo.class));
		cols.add(new ColumnModel("auditDate",DataTypeEnum.DATE));
		
		return cols;
	}
	
	public Object createNewData() {
		return new Lev3FirePermitInfo();
	}

	public CoreBaseInfo getEntityInfo() {
		return new Lev3FirePermitInfo();
	}
}
