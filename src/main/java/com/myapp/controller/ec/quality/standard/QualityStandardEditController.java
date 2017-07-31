package com.myapp.controller.ec.quality.standard;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseBillEditController;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.entity.ec.basedata.ECUnitInfo;
import com.myapp.entity.ec.basedata.ProStructureInfo;
import com.myapp.entity.ec.basedata.ProSubItemInfo;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.entity.ec.quality.QualityStandardInfo;
import com.myapp.service.ec.quality.QualityStandardService;


@PermissionAnn(name="系统管理.现场管理.质量管理.质量交底",number="app.ec.quality.qualitystandard")
@Controller
@RequestMapping("ec/quality/standard/qualityStandardEdit")
public class QualityStandardEditController extends BaseBillEditController {
	
	@Resource
	public QualityStandardService qualityStandardService;
	public AbstractBaseService getService() {
		return qualityStandardService;
	}

	public Object createNewData() {
		QualityStandardInfo info = new QualityStandardInfo();
		return info;
	}

	public CoreBaseInfo getEntityInfo() {
		return new QualityStandardInfo();
	}

	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("project",DataTypeEnum.F7,ProjectInfo.class));
		cols.add(new ColumnModel("proSubItem",DataTypeEnum.F7,ProSubItemInfo.class));
		cols.add(new ColumnModel("proStructure",DataTypeEnum.F7,ProStructureInfo.class));
		cols.add(new ColumnModel("finishDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("constructionClass",DataTypeEnum.STRING));
		cols.add(new ColumnModel("participants",DataTypeEnum.STRING));
		cols.add(new ColumnModel("unitInfo",DataTypeEnum.F7,ECUnitInfo.class));
		cols.add(new ColumnModel("qualityDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("techUser",DataTypeEnum.F7,UserInfo.class));
		cols.add(new ColumnModel("qualityUser",DataTypeEnum.F7,UserInfo.class));
		cols.add(new ColumnModel("accepter",DataTypeEnum.F7,UserInfo.class));
		return cols;
	}
}
