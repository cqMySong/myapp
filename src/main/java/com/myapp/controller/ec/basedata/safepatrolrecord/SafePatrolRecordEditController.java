package com.myapp.controller.ec.basedata.safepatrolrecord;

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
import com.myapp.entity.ec.basedata.SafePatrolRecordInfo;
import com.myapp.enums.ec.DoWayType;
import com.myapp.service.ec.basedata.SafePatrolRecordService;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年11月02日 
 * @system:
 * 安保巡查记录
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.基础数据.安保巡查记录",number="app.ec.basedata.safepatrolrecord")
@Controller
@RequestMapping("ec/basedata/safepatrolrecord")
public class SafePatrolRecordEditController extends BaseEditController{
	@Resource
	public SafePatrolRecordService safePatrolRecordService;
	public AbstractBaseService getService() {
		return safePatrolRecordService;
	}

	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("createDate",DataTypeEnum.DATETIME));
		cols.add(new ColumnModel("patrolDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("range"));
		cols.add(new ColumnModel("attention"));
		cols.add(new ColumnModel("hasQuestion",DataTypeEnum.BOOLEAN));
		cols.add(new ColumnModel("doWay",DataTypeEnum.MUTILENUM,DoWayType.class));
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
		if(editData!=null&&editData instanceof SafePatrolRecordInfo){
			SafePatrolRecordInfo odrInfo = (SafePatrolRecordInfo) editData;
			odrInfo.setCreateDate(new Date());
			odrInfo.setCreator(getCurUser());
			odrInfo.setPatrolDate(new Date());
			odrInfo.setHasQuestion(false);
		}
	}
	
	public Object createNewData() {
		return new SafePatrolRecordInfo();
	}

	public CoreInfo getEntityInfo() {
		return new SafePatrolRecordInfo();
	}
}
