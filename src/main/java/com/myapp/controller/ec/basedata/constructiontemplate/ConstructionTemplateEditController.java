package com.myapp.controller.ec.basedata.constructiontemplate;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.entity.CoreInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseEditController;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.entity.ec.basedata.ConstructionTemplateInfo;
import com.myapp.entity.ec.basedata.ProBaseWbsInfo;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.service.ec.basedata.ConstructionTemplateService;
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
@PermissionAnn(name="系统管理.现场管理.基础数据.施工样板清单",number="app.ec.basedata.constructiontemplate")
@Controller
@RequestMapping("ec/basedata/constructiontemplate")
public class ConstructionTemplateEditController extends BaseEditController {
	@Resource
	public ConstructionTemplateService constructionTemplateService;
	@Override
	public Object createNewData() {
		ConstructionTemplateInfo constructionTemplateInfo = new ConstructionTemplateInfo();
		constructionTemplateInfo.setEnabled(true);
		return constructionTemplateInfo;
	}

	@Override
	public CoreInfo getEntityInfo() {
		return new ConstructionTemplateInfo();
	}

	@Override
	public AbstractBaseService getService() {
		return this.constructionTemplateService;
	}
	@Override
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("engineeringProject"));
		cols.add(new ColumnModel("templateType"));
		cols.add(new ColumnModel("content"));
		cols.add(new ColumnModel("enabled",DataTypeEnum.BOOLEAN));
		cols.add(new ColumnModel("number"));
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("remark"));
		return cols;
	}

}
