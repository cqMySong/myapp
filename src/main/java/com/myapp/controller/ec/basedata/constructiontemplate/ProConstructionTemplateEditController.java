package com.myapp.controller.ec.basedata.constructiontemplate;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.entity.CoreInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseEditController;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.entity.ec.basedata.ConstructionTemplateInfo;
import com.myapp.entity.ec.basedata.ProConstructionTemplateInfo;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.service.ec.basedata.ConstructionTemplateService;
import com.myapp.service.ec.basedata.ProConstructionTemplateService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年12月11日 
 * @system: 项目级资料目录 施工样板清单
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.基础数据.项目施工样板清单",number="app.ec.basedata.proconstructiontemplate")
@Controller
@RequestMapping("ec/basedata/proconstructiontemplate")
public class ProConstructionTemplateEditController extends BaseEditController {
	@Resource
	public ProConstructionTemplateService proConstructionTemplateService;
	@Override
	public Object createNewData() {
		ProConstructionTemplateInfo proConstructionTemplateInfo = new ProConstructionTemplateInfo();
		return proConstructionTemplateInfo;
	}

	@Override
	public CoreInfo getEntityInfo() {
		return new ProConstructionTemplateInfo();
	}

	@Override
	public AbstractBaseService getService() {
		return this.proConstructionTemplateService;
	}
	@Override
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("project",DataTypeEnum.F7,ProjectInfo.class));
		cols.add(new ColumnModel("constructionTemplateInfo",DataTypeEnum.F7,ConstructionTemplateInfo.class));
		cols.add(new ColumnModel("content"));
		cols.add(new ColumnModel("number"));
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("remark"));
		return cols;
	}

}
