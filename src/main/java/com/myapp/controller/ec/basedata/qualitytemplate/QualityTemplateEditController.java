package com.myapp.controller.ec.basedata.qualitytemplate;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseDataEditController;
import com.myapp.core.entity.MaterialInfo;
import com.myapp.core.entity.MeasureUnitInfo;
import com.myapp.core.entity.PositionInfo;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.entity.ec.basedata.ProBaseWbsInfo;
import com.myapp.entity.ec.basedata.QualityTemplateDetailInfo;
import com.myapp.entity.ec.basedata.QualityTemplateInfo;
import com.myapp.entity.ec.budget.BudgetingDetailInfo;
import com.myapp.service.ec.basedata.QualityTemplateService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年10月26日 
 * @system:质量样板工作要点
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.基础数据.质量样板工作要点",number="app.ec.basedata.qualitytemplate")
@Controller
@RequestMapping("ec/basedata/qualitytemplate")
public class QualityTemplateEditController extends BaseDataEditController{
	@Resource
	public QualityTemplateService qualityTemplateService;
	@Override
	public AbstractBaseService getService() {
		return qualityTemplateService;
	}
	@Override
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("branchBaseWbs", DataTypeEnum.F7, ProBaseWbsInfo.class));
		cols.add(new ColumnModel("subentry", DataTypeEnum.F7, ProBaseWbsInfo.class));

		ColumnModel qualityTemplateDetail = new ColumnModel("qualityTemplateDetailInfos",DataTypeEnum.ENTRY,
				QualityTemplateDetailInfo.class);

		ColumnModel position = new ColumnModel("position",DataTypeEnum.F7,"id,name");
		position.setClaz(PositionInfo.class);
		qualityTemplateDetail.getCols().add(position);

		qualityTemplateDetail.getCols().add(new ColumnModel("id",DataTypeEnum.PK));
		qualityTemplateDetail.getCols().add(new ColumnModel("jobRequirement",DataTypeEnum.STRING));
		qualityTemplateDetail.getCols().add(new ColumnModel("enable",DataTypeEnum.BOOLEAN));

		cols.add(qualityTemplateDetail);
		return cols;
	}
	@Override
	public Object createNewData() {
		return new QualityTemplateInfo();
	}
	@Override
	public CoreBaseInfo getEntityInfo() {
		return new QualityTemplateInfo();
	}
}
