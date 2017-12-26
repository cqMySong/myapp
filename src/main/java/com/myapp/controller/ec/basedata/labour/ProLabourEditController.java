package com.myapp.controller.ec.basedata.labour;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseEditController;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.enums.Sex;
import com.myapp.core.model.ColumnModel;
import com.myapp.entity.ec.basedata.ProLabourInfo;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.entity.ec.basedata.WorkTypeInfo;
import com.myapp.service.ec.basedata.ProLabourService;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年12月12日 
 * @system:项目劳务人员备案表
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.劳务人员.项目劳务人员备案",number="app.ec.labour.prolabour")
@Controller
@RequestMapping("ec/labour/prolabour")
public class ProLabourEditController extends BaseEditController{
	@Resource
	public ProLabourService proLabourService;
	public AbstractBaseService getService() {
		return proLabourService;
	}

	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("number"));
		cols.add(new ColumnModel("remark"));
		cols.add(new ColumnModel("addr"));
		cols.add(new ColumnModel("idCard"));
		cols.add(new ColumnModel("bank"));
		cols.add(new ColumnModel("bankNo"));
		cols.add(new ColumnModel("project",DataTypeEnum.F7,ProjectInfo.class));
		cols.add(new ColumnModel("workType",DataTypeEnum.F7,WorkTypeInfo.class));
		cols.add(new ColumnModel("sex",DataTypeEnum.ENUM,Sex.class));
		cols.add(new ColumnModel("age",DataTypeEnum.INT));
		cols.add(new ColumnModel("joinDate",DataTypeEnum.DATE));
		return cols;
	}
	
	public Object createNewData() {
		return new ProLabourInfo();
	}

	public CoreBaseInfo getEntityInfo() {
		return new ProLabourInfo();
	}
}
