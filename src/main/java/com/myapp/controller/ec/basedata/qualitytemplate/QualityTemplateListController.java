package com.myapp.controller.ec.basedata.qualitytemplate;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseDataListController;
import com.myapp.core.entity.PositionInfo;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.entity.ec.basedata.ProBaseWbsInfo;
import com.myapp.service.ec.basedata.QualityTemplateService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年6月7日 
 * @system:
 *质量样板工作要点
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.基础数据.质量样板工作要点",number="app.ec.basedata.qualitytemplate")
@Controller
@RequestMapping("ec/basedata/qualitytemplates")
public class QualityTemplateListController extends BaseDataListController {
	
	@Resource
	public QualityTemplateService qualityTemplateService;
	@Override
	public AbstractBaseService getService() {
		return qualityTemplateService;
	}
	@Override
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("position", DataTypeEnum.F7, PositionInfo.class));
		cols.add(new ColumnModel("branchBaseWbs", DataTypeEnum.F7, ProBaseWbsInfo.class));
		cols.add(new ColumnModel("subentry", DataTypeEnum.F7, ProBaseWbsInfo.class));
		cols.add(new ColumnModel("jobRequirement"));
		return cols;
	}
	@Override
	public String getEditUrl() {
		return "ec/basedata/qualitytemplate/qualityTemplateEdit";
	}
	@Override
	public String getListUrl() {
		return "ec/basedata/qualitytemplate/qualityTemplateList";
	}

}
