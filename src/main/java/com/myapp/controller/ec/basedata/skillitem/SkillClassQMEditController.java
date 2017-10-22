package com.myapp.controller.ec.basedata.skillitem;

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
import com.myapp.entity.ec.basedata.SkillItemInfo;
import com.myapp.enums.SkillType;
import com.myapp.service.ec.basedata.SkillItemService;

@PermissionAnn(name="系统管理.现场管理.基础数据.施工技术交底",number="app.ec.basedata.qmskillitem")
@Controller
@RequestMapping("ec/basedata/qmskillitem")
public class SkillClassQMEditController extends BaseDataEditController {
	
	@Resource
	public SkillItemService skillItemService;

	public AbstractBaseService getService() {
		return skillItemService;
	}
	
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("skillType",DataTypeEnum.ENUM,SkillType.class));
		cols.add(new ColumnModel("skillClass",DataTypeEnum.F7,SkillClassInfo.class));
		return cols;
	}

	public Object createNewData() {
		SkillItemInfo info = new SkillItemInfo();
		info.setEnabled(true);
		info.setSkillType(SkillType.QM);
		return info;
	}

	public CoreBaseInfo getEntityInfo() {
		return new SkillItemInfo();
	}

}
