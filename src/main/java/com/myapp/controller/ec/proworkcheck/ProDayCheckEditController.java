package com.myapp.controller.ec.proworkcheck;

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
import com.myapp.entity.ec.proworkcheck.ProDayCheckInfo;
import com.myapp.entity.ec.proworkcheck.ProDayCheckItemInfo;
import com.myapp.enums.ec.WorkCheckType;
import com.myapp.enums.ec.YesNoEnum;
import com.myapp.service.ec.proworkcheck.ProDayCheckService;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年11月27日 
 * @system:
 * 项目现场施工日检查记录
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.施工检查.日检查记录",number="app.ec.workcheck.daycheck")
@Controller
@RequestMapping("ec/proworkcheck/prodaycheck")
public class ProDayCheckEditController extends BaseBillEditController{
	@Resource
	public ProDayCheckService proDayCheckService;
	public AbstractBaseService getService() {
		return proDayCheckService;
	}

	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("number"));
		cols.add(new ColumnModel("partBChecker"));
		cols.add(new ColumnModel("remark"));
		cols.add(new ColumnModel("bizDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("billState",DataTypeEnum.ENUM,BillState.class));
		cols.add(new ColumnModel("project",DataTypeEnum.F7,ProjectInfo.class));
		cols.add(new ColumnModel("proSafer",DataTypeEnum.F7,UserInfo.class));
		
		cols.add(new ColumnModel("createUser",DataTypeEnum.F7,UserInfo.class));
		cols.add(new ColumnModel("createDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("lastUpdateUser",DataTypeEnum.F7,UserInfo.class));
		cols.add(new ColumnModel("lastUpdateDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("auditor",DataTypeEnum.F7,UserInfo.class));
		cols.add(new ColumnModel("auditDate",DataTypeEnum.DATE));
		
		ColumnModel planItems = new ColumnModel("dayCheckItems",DataTypeEnum.ENTRY,ProDayCheckItemInfo.class);
		planItems.getCols().add(new ColumnModel("id",DataTypeEnum.PK));
		planItems.getCols().add(new ColumnModel("workCheckItem",DataTypeEnum.F7,WorkCheckItemInfo.class));
		planItems.getCols().add(new ColumnModel("workCheckType",DataTypeEnum.ENUM,WorkCheckType.class));
		planItems.getCols().add(new ColumnModel("safePeril",DataTypeEnum.ENUM,YesNoEnum.class));
		planItems.getCols().add(new ColumnModel("removePeril",DataTypeEnum.ENUM,YesNoEnum.class));
		planItems.getCols().add(new ColumnModel("checkItem"));
		planItems.getCols().add(new ColumnModel("checkRequire"));
		planItems.getCols().add(new ColumnModel("perilContent"));
		planItems.getCols().add(new ColumnModel("remark"));
		cols.add(planItems);
		return cols;
	}
	
	public Object createNewData() {
		return new ProDayCheckInfo();
	}

	public CoreBaseInfo getEntityInfo() {
		return new ProDayCheckInfo();
	}
}
