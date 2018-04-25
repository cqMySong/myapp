package com.myapp.controller.ec.basedata.workprocess;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.entity.CoreInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseEditController;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.entity.ec.basedata.WorkProcessInfo;
import com.myapp.enums.ec.WorkType;
import com.myapp.service.ec.basedata.WorkProcessService;
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
@PermissionAnn(name="系统管理.现场管理.基础数据.项目主要问题处理摘要",number="app.ec.basedata.workprocess")
@Controller
@RequestMapping("ec/basedata/workprocess")
public class WorkProcessEditController extends BaseEditController{
	@Resource
	public WorkProcessService workProcessService;
	@Override
	public AbstractBaseService getService() {
		return workProcessService;
	}
	@Override
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("number"));
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("workType",DataTypeEnum.ENUM, WorkType.class));
		cols.add(new ColumnModel("problemDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("problemContent"));
		cols.add(new ColumnModel("solution"));
		cols.add(new ColumnModel("workFollowUp",DataTypeEnum.BOOLEAN));
		cols.add(new ColumnModel("endSolveDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("solve",DataTypeEnum.BOOLEAN));
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
		WorkProcessInfo workProcessInfo = new WorkProcessInfo();
		workProcessInfo.setWorkType(WorkType.SCHEUAL);
		workProcessInfo.setCreateDate(new Date());
		workProcessInfo.setCreator(getCurUser());
		workProcessInfo.setName("项目主要问题处理摘要");
		return workProcessInfo;
	}
	@Override
	public CoreInfo getEntityInfo() {
		return new WorkProcessInfo();
	}
}
