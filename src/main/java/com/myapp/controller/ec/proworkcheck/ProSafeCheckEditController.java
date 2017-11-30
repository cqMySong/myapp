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
import com.myapp.entity.ec.proworkcheck.ProSafeCheckInfo;
import com.myapp.entity.ec.proworkcheck.ProSafeCheckItemInfo;
import com.myapp.enums.ec.CheckType;
import com.myapp.enums.ec.WorkCheckType;
import com.myapp.service.ec.proworkcheck.ProSafeCheckService;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年11月30日 
 * @system:
 * 项目现场施工周(月)安全检查记录
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.施工检查.周(月)检查记录",number="app.ec.workcheck.safecheck")
@Controller
@RequestMapping("ec/proworkcheck/prosafecheck")
public class ProSafeCheckEditController extends BaseBillEditController{
	@Resource
	public ProSafeCheckService proSafeCheckService;
	public AbstractBaseService getService() {
		return proSafeCheckService;
	}

	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("number"));
		cols.add(new ColumnModel("partAChecker"));
		cols.add(new ColumnModel("partBChecker"));
		cols.add(new ColumnModel("remark"));
		cols.add(new ColumnModel("bizDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("checkType",DataTypeEnum.ENUM,CheckType.class));
		cols.add(new ColumnModel("billState",DataTypeEnum.ENUM,BillState.class));
		cols.add(new ColumnModel("project",DataTypeEnum.F7,ProjectInfo.class));
		cols.add(new ColumnModel("proManager",DataTypeEnum.F7,UserInfo.class));
		cols.add(new ColumnModel("proSkiller",DataTypeEnum.F7,UserInfo.class));
		
		cols.add(new ColumnModel("createUser",DataTypeEnum.F7,UserInfo.class));
		cols.add(new ColumnModel("createDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("lastUpdateUser",DataTypeEnum.F7,UserInfo.class));
		cols.add(new ColumnModel("lastUpdateDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("auditor",DataTypeEnum.F7,UserInfo.class));
		cols.add(new ColumnModel("auditDate",DataTypeEnum.DATE));
		
		ColumnModel planItems = new ColumnModel("checkItems",DataTypeEnum.ENTRY,ProSafeCheckItemInfo.class);
		planItems.getCols().add(new ColumnModel("id",DataTypeEnum.PK));
		planItems.getCols().add(new ColumnModel("workCheckItem",DataTypeEnum.F7,WorkCheckItemInfo.class));
		planItems.getCols().add(new ColumnModel("workCheckType",DataTypeEnum.ENUM,WorkCheckType.class));
		planItems.getCols().add(new ColumnModel("checkItem"));
		planItems.getCols().add(new ColumnModel("checkRequire"));
		planItems.getCols().add(new ColumnModel("checkResult"));
		planItems.getCols().add(new ColumnModel("rectifyIdea"));
		planItems.getCols().add(new ColumnModel("rectifyResult"));
		planItems.getCols().add(new ColumnModel("remark"));
		cols.add(planItems);
		return cols;
	}
	
	public Object createNewData() {
		return new ProSafeCheckInfo();
	}

	public CoreBaseInfo getEntityInfo() {
		return new ProSafeCheckInfo();
	}
}
