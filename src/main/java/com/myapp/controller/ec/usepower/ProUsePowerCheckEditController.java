package com.myapp.controller.ec.usepower;

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
import com.myapp.entity.ec.basedata.SafeUsePowerInfo;
import com.myapp.entity.ec.usepower.ProUsePowerCheckInfo;
import com.myapp.entity.ec.usepower.ProUsePowerCheckItemInfo;
import com.myapp.service.ec.usepower.ProUsePowerCheckService;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年12月13日 
 * @system:
 * 施工现场安全用电检查表
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.用电管理.安全用电检查表",number="app.ec.usepower.prousepowercheck")
@Controller
@RequestMapping("ec/usepower/prousepowercheck")
public class ProUsePowerCheckEditController extends BaseBillEditController{
	@Resource
	public ProUsePowerCheckService proUsePowerCheckService;
	public AbstractBaseService getService() {
		return proUsePowerCheckService;
	}

	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("number"));
		cols.add(new ColumnModel("rectifyIdea"));
		cols.add(new ColumnModel("remark"));
		cols.add(new ColumnModel("bizDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("billState",DataTypeEnum.ENUM,BillState.class));
		cols.add(new ColumnModel("project",DataTypeEnum.F7,ProjectInfo.class));
		cols.add(new ColumnModel("ecWorker",DataTypeEnum.F7,UserInfo.class));
		cols.add(new ColumnModel("ecChecker",DataTypeEnum.F7,UserInfo.class));
		
		cols.add(new ColumnModel("createUser",DataTypeEnum.F7,UserInfo.class));
		cols.add(new ColumnModel("createDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("lastUpdateUser",DataTypeEnum.F7,UserInfo.class));
		cols.add(new ColumnModel("lastUpdateDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("auditor",DataTypeEnum.F7,UserInfo.class));
		cols.add(new ColumnModel("auditDate",DataTypeEnum.DATE));
		
		ColumnModel planItems = new ColumnModel("checkItems",DataTypeEnum.ENTRY,ProUsePowerCheckItemInfo.class);
		planItems.getCols().add(new ColumnModel("id",DataTypeEnum.PK));
		
		ColumnModel col = new ColumnModel("usePower",DataTypeEnum.F7,SafeUsePowerInfo.class);
		col.setFormat("id,name,number,standard");
		planItems.getCols().add(col);
		planItems.getCols().add(new ColumnModel("standard"));
		planItems.getCols().add(new ColumnModel("checkResult"));
		planItems.getCols().add(new ColumnModel("remark"));
		cols.add(planItems);
		return cols;
	}
	
	public Object createNewData() {
		return new ProUsePowerCheckInfo();
	}

	public CoreBaseInfo getEntityInfo() {
		return new ProUsePowerCheckInfo();
	}
}
