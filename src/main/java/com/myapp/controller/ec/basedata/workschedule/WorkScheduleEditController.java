package com.myapp.controller.ec.basedata.workschedule;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.entity.CoreInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseEditController;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.entity.ec.basedata.WorkScheduleInfo;
import com.myapp.enums.ec.WorkType;
import com.myapp.service.ec.basedata.WorkScheduleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年10月30日 
 * @system: 项目安保预案
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.基础数据.项目主要工作安排摘要",number="app.ec.basedata.workschedule")
@Controller
@RequestMapping("ec/basedata/workschedule")
public class WorkScheduleEditController extends BaseEditController{
	@Resource
	public WorkScheduleService workScheduleService;
	@Override
	public AbstractBaseService getService() {
		return workScheduleService;
	}
	@Override
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("number"));
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("workType",DataTypeEnum.ENUM, WorkType.class));
		cols.add(new ColumnModel("jobContent"));
		cols.add(new ColumnModel("startDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("finishDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("personLiable"));
		cols.add(new ColumnModel("takeOver",DataTypeEnum.BOOLEAN));
		cols.add(new ColumnModel("sendee"));
		cols.add(new ColumnModel("workFollowUp",DataTypeEnum.BOOLEAN));
		cols.add(new ColumnModel("finish",DataTypeEnum.BOOLEAN));
		cols.add(new ColumnModel("remark"));
		cols.add(new ColumnModel("createDate",DataTypeEnum.DATETIME));
		ColumnModel creator = new ColumnModel("creator",DataTypeEnum.F7,"id,name");
		creator.setClaz(UserInfo.class);
		cols.add(creator);
		ColumnModel project = new ColumnModel("project",DataTypeEnum.F7,"id,name");
		project.setClaz(ProjectInfo.class);
		cols.add(project);
		return cols;
	}
	
	@Override
	public Object createNewData() {
		WorkScheduleInfo workScheduleInfo = new WorkScheduleInfo();
		workScheduleInfo.setWorkType(WorkType.SCHEUAL);
		workScheduleInfo.setCreateDate(new Date());
		workScheduleInfo.setCreator(getCurUser());
		workScheduleInfo.setName("项目主要工作安排摘要");
		return workScheduleInfo;
	}
	@Override
	public CoreInfo getEntityInfo() {
		return new WorkScheduleInfo();
	}
}
