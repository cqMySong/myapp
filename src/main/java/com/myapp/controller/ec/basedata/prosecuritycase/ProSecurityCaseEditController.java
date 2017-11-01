package com.myapp.controller.ec.basedata.prosecuritycase;

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
import com.myapp.entity.ec.basedata.ProSecurityCaseInfo;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.service.ec.basedata.ProSecurityCaseService;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年10月30日 
 * @system: 项目安保预案
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.基础数据.安保预案",number="app.ec.basedata.prosecuritycase")
@Controller
@RequestMapping("ec/basedata/prosecuritycase")
public class ProSecurityCaseEditController extends BaseEditController{
	@Resource
	public ProSecurityCaseService proSecurityCaseService;
	public AbstractBaseService getService() {
		return proSecurityCaseService;
	}

	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("createDate",DataTypeEnum.DATETIME));
		cols.add(new ColumnModel("part"));
		cols.add(new ColumnModel("secCase"));
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
		if(editData!=null&&editData instanceof ProSecurityCaseInfo){
			ProSecurityCaseInfo odrInfo = (ProSecurityCaseInfo) editData;
			odrInfo.setCreateDate(new Date());
			odrInfo.setCreator(getCurUser());
		}
	}
	
	public Object createNewData() {
		return new ProSecurityCaseInfo();
	}

	public CoreInfo getEntityInfo() {
		return new ProSecurityCaseInfo();
	}
}
