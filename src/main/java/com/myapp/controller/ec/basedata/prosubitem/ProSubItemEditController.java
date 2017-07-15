package com.myapp.controller.ec.basedata.prosubitem;

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
import com.myapp.entity.ec.basedata.ProSubInfo;
import com.myapp.entity.ec.basedata.ProSubItemInfo;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.service.ec.basedata.ProSubItemService;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年5月20日 
 * @system:
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.基础数据.项目分部分项",number="app.ec.basedata.prosubitem")
@Controller
@RequestMapping("ec/basedata/prosubitem")
public class ProSubItemEditController extends BaseEditController{
	@Resource
	public ProSubItemService proSubItemService;
	
	public AbstractBaseService getService() {
		return proSubItemService;
	}

	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("number"));
		cols.add(new ColumnModel("enabled"));
		cols.add(new ColumnModel("remark"));
		ColumnModel proSub = new ColumnModel("proSub",DataTypeEnum.F7,"id,name");
		proSub.setClaz(ProSubInfo.class);
		cols.add(proSub);
		ColumnModel proStruct = new ColumnModel("proStruct",DataTypeEnum.F7,"id,name");
		proStruct.setClaz(ProStructureInfo.class);
		cols.add(proStruct);
		ColumnModel project = new ColumnModel("project",DataTypeEnum.F7,"id,name");
		project.setClaz(ProjectInfo.class);
		cols.add(project);
		return cols;
	}
	
	public Object createNewData() {
		ProSubItemInfo proSubInfo = new ProSubItemInfo();
		proSubInfo.setEnabled(true);
		return proSubInfo;
	}

	public CoreBaseInfo getEntityInfo() {
		return new ProSubItemInfo();
	}
}
