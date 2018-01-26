package com.myapp.controller.ec.basedata.safetemplate;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseDataEditController;
import com.myapp.core.entity.PositionInfo;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.entity.ec.basedata.*;
import com.myapp.service.ec.basedata.QualityTemplateService;
import com.myapp.service.ec.basedata.SafeTemplateService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年10月26日 
 * @system:安全样板工作要点
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.基础数据.安全样板工作要点",number="app.ec.basedata.safetemplate")
@Controller
@RequestMapping("ec/basedata/safetemplate")
public class SafeTemplateEditController extends BaseDataEditController{
	@Resource
	public SafeTemplateService safeTemplateService;
	@Override
	public AbstractBaseService getService() {
		return safeTemplateService;
	}
	@Override
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("branchBaseWbs", DataTypeEnum.F7, ProBaseWbsInfo.class));
		cols.add(new ColumnModel("subentry", DataTypeEnum.F7, ProBaseWbsInfo.class));

		ColumnModel safeTemplateDetail = new ColumnModel("safeTemplateDetailInfos",DataTypeEnum.ENTRY,
				SafeTemplateDetailInfo.class);

		ColumnModel position = new ColumnModel("position",DataTypeEnum.F7,"id,name");
		position.setClaz(PositionInfo.class);
		safeTemplateDetail.getCols().add(position);

		safeTemplateDetail.getCols().add(new ColumnModel("id",DataTypeEnum.PK));
		safeTemplateDetail.getCols().add(new ColumnModel("jobRequirement",DataTypeEnum.STRING));
		safeTemplateDetail.getCols().add(new ColumnModel("enable",DataTypeEnum.BOOLEAN));

		cols.add(safeTemplateDetail);
		return cols;
	}
	@Override
	public Object createNewData() {
		return new SafeTemplateInfo();
	}
	@Override
	public CoreBaseInfo getEntityInfo() {
		return new SafeTemplateInfo();
	}
}
