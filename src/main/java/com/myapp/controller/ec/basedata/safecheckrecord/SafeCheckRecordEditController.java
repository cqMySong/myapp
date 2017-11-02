package com.myapp.controller.ec.basedata.safecheckrecord;

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
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.entity.ec.basedata.SafeCheckRecordInfo;
import com.myapp.enums.ec.SafeCheckItem;
import com.myapp.service.ec.basedata.SafeCheckRecordService;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年5月20日 
 * @system:
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.基础数据.安保监督检查",number="app.ec.basedata.safecheckrecord")
@Controller
@RequestMapping("ec/basedata/safecheckrecord")
public class SafeCheckRecordEditController extends BaseEditController{
	@Resource
	public SafeCheckRecordService safeCheckRecordService;
	public AbstractBaseService getService() {
		return safeCheckRecordService;
	}

	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("createDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("checkDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("position"));
		cols.add(new ColumnModel("question"));
		cols.add(new ColumnModel("content",DataTypeEnum.MUTILENUM,SafeCheckItem.class));
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
		if(editData!=null&&editData instanceof SafeCheckRecordInfo){
			SafeCheckRecordInfo odrInfo = (SafeCheckRecordInfo) editData;
			odrInfo.setCreateDate(new Date());
			odrInfo.setCreator(getCurUser());
			odrInfo.setCheckDate(new Date());
		}
	}
	
	public Object createNewData() {
		return new SafeCheckRecordInfo();
	}

	public CoreInfo getEntityInfo() {
		return new SafeCheckRecordInfo();
	}
}
