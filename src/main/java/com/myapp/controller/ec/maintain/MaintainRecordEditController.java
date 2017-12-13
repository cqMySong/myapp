package com.myapp.controller.ec.maintain;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseEditController;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.entity.ec.maintain.MaintainRecordInfo;
import com.myapp.service.ec.maintain.MaintainRecordService;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年12月13日 
 * @system:
 * 维修记录表
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.维修管理.维修记录表",number="app.ec.maintain.maintainrecord")
@Controller
@RequestMapping("ec/maintain/maintainrecord")
public class MaintainRecordEditController extends BaseEditController{
	@Resource
	public MaintainRecordService maintainRecordService;
	public AbstractBaseService getService() {
		return maintainRecordService;
	}

	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("number"));
		cols.add(new ColumnModel("content"));
		cols.add(new ColumnModel("remark"));
		cols.add(new ColumnModel("bizDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("createDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("project",DataTypeEnum.F7,ProjectInfo.class));
		cols.add(new ColumnModel("maintainer",DataTypeEnum.F7,UserInfo.class));
		cols.add(new ColumnModel("createUser",DataTypeEnum.F7,UserInfo.class));
		return cols;
	}
	
	public Object createNewData() {
		MaintainRecordInfo mrInfo = new MaintainRecordInfo();
		mrInfo.setCreateUser(getCurUser());
		mrInfo.setBizDate(new Date());
		return mrInfo;
	}

	public CoreBaseInfo getEntityInfo() {
		return new MaintainRecordInfo();
	}
}
