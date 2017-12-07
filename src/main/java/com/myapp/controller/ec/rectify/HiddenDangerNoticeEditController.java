package com.myapp.controller.ec.rectify;

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
import com.myapp.entity.ec.rectify.HiddenDangerNoticeInfo;
import com.myapp.service.ec.rectify.HiddenDangerNoticeService;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年12月07日 
 * @system:隐患整改通知单
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.整改管理.隐患整改通知单",number="app.ec.rectify.hiddendangernotice")
@Controller
@RequestMapping("ec/rectify/hiddendangernotice")
public class HiddenDangerNoticeEditController extends BaseBillEditController{
	@Resource
	public HiddenDangerNoticeService hiddenDangerNoticeService;
	public AbstractBaseService getService() {
		return hiddenDangerNoticeService;
	}

	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("number"));
		cols.add(new ColumnModel("checkDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("bizDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("endDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("billState",DataTypeEnum.ENUM,BillState.class));
		cols.add(new ColumnModel("project",DataTypeEnum.F7,ProjectInfo.class));
		cols.add(new ColumnModel("sendUnit"));
		cols.add(new ColumnModel("danger"));
		cols.add(new ColumnModel("checker",DataTypeEnum.F7,UserInfo.class));
		
		cols.add(new ColumnModel("createUser",DataTypeEnum.F7,UserInfo.class));
		cols.add(new ColumnModel("createDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("lastUpdateUser",DataTypeEnum.F7,UserInfo.class));
		cols.add(new ColumnModel("lastUpdateDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("auditor",DataTypeEnum.F7,UserInfo.class));
		cols.add(new ColumnModel("auditDate",DataTypeEnum.DATE));
		
		return cols;
	}
	
	public Object createNewData() {
		return new HiddenDangerNoticeInfo();
	}

	public CoreBaseInfo getEntityInfo() {
		return new HiddenDangerNoticeInfo();
	}
}
