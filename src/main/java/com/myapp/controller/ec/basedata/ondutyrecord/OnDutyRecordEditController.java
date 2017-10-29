package com.myapp.controller.ec.basedata.ondutyrecord;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.entity.CoreInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseEditController;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.entity.ec.basedata.OnDutyRecordInfo;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.service.ec.basedata.OnDutyRecordService;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年5月20日 
 * @system:
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.基础数据.安保值班记录",number="app.ec.basedata.ondutyrecord")
@Controller
@RequestMapping("ec/basedata/ondutyrecord")
public class OnDutyRecordEditController extends BaseEditController{
	@Resource
	public OnDutyRecordService onDutyRecordService;
	public AbstractBaseService getService() {
		return onDutyRecordService;
	}

	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("dutyDate",DataTypeEnum.DATETIME));
		cols.add(new ColumnModel("createDate",DataTypeEnum.DATETIME));
		cols.add(new ColumnModel("dutyPosition"));
		cols.add(new ColumnModel("planDutyor"));
		cols.add(new ColumnModel("realDutyor"));
		cols.add(new ColumnModel("content"));
		ColumnModel creator = new ColumnModel("creator",DataTypeEnum.F7,"id,name");
		creator.setClaz(UserInfo.class);
		cols.add(creator);
		ColumnModel project = new ColumnModel("project",DataTypeEnum.F7,"id,name");
		project.setClaz(ProjectInfo.class);
		cols.add(project);
		return cols;
	}
	
	protected void initDefDataValue(Object editData) {
		super.initDefDataValue(editData);
		if(editData!=null&&editData instanceof OnDutyRecordInfo){
			OnDutyRecordInfo odrInfo = (OnDutyRecordInfo) editData;
			odrInfo.setCreateDate(new Date());
			odrInfo.setCreator(getCurUser());
			odrInfo.setDutyDate(new Date());
		}
	}
	
	public Object createNewData() {
		return new OnDutyRecordInfo();
	}

	public CoreInfo getEntityInfo() {
		return new OnDutyRecordInfo();
	}
}
