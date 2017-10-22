package com.myapp.controller.ec.basedata.skillclass;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseDataEditController;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.entity.ec.basedata.SkillClassInfo;
import com.myapp.enums.SkillType;
import com.myapp.service.ec.basedata.SkillClassService;

@PermissionAnn(name="系统管理.现场管理.基础数据.技术分类",number="app.ec.basedata.skillclass")
@Controller
@RequestMapping("ec/basedata/skillclass")
public class SkillClassEditController extends BaseDataEditController {
	
	@Resource
	public SkillClassService skillClassService;

	public AbstractBaseService getService() {
		return skillClassService;
	}
	
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("skillType",DataTypeEnum.ENUM,SkillType.class));
		return cols;
	}

	public Object createNewData() {
		SkillClassInfo info = new SkillClassInfo();
		return info;
	}

	public CoreBaseInfo getEntityInfo() {
		return new SkillClassInfo();
	}

}
