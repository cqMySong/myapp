package com.myapp.controller.ec.basedata.ecdraw;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseEditController;
import com.myapp.core.entity.basedate.DataGroupInfo;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.entity.ec.basedata.ProEcDrawInfo;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.service.ec.basedata.ProEcDrawService;
/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年12月21日 
 * @system: 项目施工图资料
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.施工图.项目施工图",number="app.ec.ecdraw.proecdraw")
@Controller
@RequestMapping("ec/ecdraw/proecdraw")
public class ProEcDrawEditController extends BaseEditController {
	
	@Resource
	public ProEcDrawService proEcDrawService;
	public AbstractBaseService getService() {
		return proEcDrawService;
	}
	
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("number"));
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("group",DataTypeEnum.F7,DataGroupInfo.class,"id,name,displayName"));
		cols.add(new ColumnModel("project",DataTypeEnum.F7,ProjectInfo.class));
		return cols;
	}

	public Object createNewData() {
		ProEcDrawInfo info = new ProEcDrawInfo();
		return info;
	}

	public CoreBaseInfo getEntityInfo() {
		return new ProEcDrawInfo();
	}

}
