package com.myapp.controller.ec.basedata.prostructure;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseEditController;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.entity.ec.basedata.ProStructureInfo;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.service.ec.basedata.ProStructureService;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年5月20日 
 * @system: 
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.基础数据.单位(子单位)工程",number="app.ec.basedata.prostructure")
@Controller
@RequestMapping("ec/basedata/prostructure")
public class ProStructureEditController extends BaseEditController{
	@Resource
	public ProStructureService proStructureService;
	
	public AbstractBaseService getService() {
		return proStructureService;
	}

	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("number"));
		cols.add(new ColumnModel("enabled"));
		cols.add(new ColumnModel("remark"));
		ColumnModel parent = new ColumnModel("parent",DataTypeEnum.F7,"id,name");
		parent.setClaz(ProStructureInfo.class);
		cols.add(parent);
		ColumnModel project = new ColumnModel("project",DataTypeEnum.F7,"id,name");
		project.setClaz(ProjectInfo.class);
		cols.add(project);
		return cols;
	}
	
	public Object createNewData() {
		ProStructureInfo proSubInfo = new ProStructureInfo();
		proSubInfo.setEnabled(true);
		return proSubInfo;
	}

	public CoreBaseInfo getEntityInfo() {
		return new ProStructureInfo();
	}
}
