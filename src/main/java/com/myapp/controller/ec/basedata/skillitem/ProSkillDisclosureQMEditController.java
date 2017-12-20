package com.myapp.controller.ec.basedata.skillitem;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseEditController;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.entity.ec.basedata.ProSkillDisclosureInfo;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.entity.ec.basedata.SkillClassInfo;
import com.myapp.enums.ec.SkillType;
import com.myapp.service.ec.basedata.ProSkillDisclosureService;

@PermissionAnn(name="系统管理.现场管理.技术交底.项目施工技术交底",number="app.ec.skilldisclosure.proqmskill")
@Controller
@RequestMapping("ec/skilldisclosure/proqmskill")
public class ProSkillDisclosureQMEditController extends BaseEditController {
	
	@Resource
	public ProSkillDisclosureService proSkillDisclosureService;
	public AbstractBaseService getService() {
		return proSkillDisclosureService;
	}
	
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("number"));
		cols.add(new ColumnModel("skillType",DataTypeEnum.ENUM,SkillType.class));
		cols.add(new ColumnModel("skillClass",DataTypeEnum.F7,SkillClassInfo.class));
		cols.add(new ColumnModel("project",DataTypeEnum.F7,ProjectInfo.class));
		cols.add(new ColumnModel("disclosurer",DataTypeEnum.F7,UserInfo.class));
		return cols;
	}

	public Object createNewData() {
		ProSkillDisclosureInfo info = new ProSkillDisclosureInfo();
		info.setSkillType(SkillType.QM);
		return info;
	}

	public CoreBaseInfo getEntityInfo() {
		return new ProSkillDisclosureInfo();
	}

}
