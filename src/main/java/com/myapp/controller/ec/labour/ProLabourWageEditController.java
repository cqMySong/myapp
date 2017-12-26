package com.myapp.controller.ec.labour;

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
 * 项目民工工资表
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.劳务人员.项目民工工资表",number="app.ec.labour.prolabourwage")
@Controller
@RequestMapping("ec/labour/prolabourwage")
public class ProLabourWageEditController extends BaseBillEditController{
	@Resource
	public ProLabourWageService proLabourWageService;
	public AbstractBaseService getService() {
		return proLabourWageService;
	}

	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("number"));
		cols.add(new ColumnModel("labourGroup"));
		cols.add(new ColumnModel("project",DataTypeEnum.F7,ProjectInfo.class));
		cols.add(new ColumnModel("createUser",DataTypeEnum.F7,UserInfo.class));
		cols.add(new ColumnModel("lastUpdateUser",DataTypeEnum.F7,UserInfo.class));
		cols.add(new ColumnModel("auditor",DataTypeEnum.F7,UserInfo.class));
		cols.add(new ColumnModel("bizDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("createDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("lastUpdateDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("auditDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("billState",DataTypeEnum.ENUM,BillState.class));
		cols.add(new ColumnModel("remark"));
		
		ColumnModel items = new ColumnModel("labourwageItem",DataTypeEnum.ENTRY,ProLabourWageItemInfo.class);
		items.getCols().add(new ColumnModel("labour",DataTypeEnum.F7,ProLabourInfo.class,"id,name,idCard"));
		items.getCols().add(new ColumnModel("id",DataTypeEnum.PK));
		items.getCols().add(new ColumnModel("bankNo"));
		items.getCols().add(new ColumnModel("idCard"));
		items.getCols().add(new ColumnModel("phone"));
		items.getCols().add(new ColumnModel("workDays",DataTypeEnum.NUMBER));
		items.getCols().add(new ColumnModel("wage",DataTypeEnum.NUMBER));
		items.getCols().add(new ColumnModel("signDate",DataTypeEnum.DATE));
		cols.add(items);
		return cols;
	}
	
	public Object createNewData() {
		return new ProLabourWageInfo();
	}

	public CoreBaseInfo getEntityInfo() {
		return new ProLabourWageInfo();
	}
}
