package com.myapp.controller.ec.basedata.safetemplate;

import com.myapp.core.annotation.AuthorAnn;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseDataListController;
import com.myapp.core.entity.PositionInfo;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.model.WebDataModel;
import com.myapp.entity.ec.basedata.ProBaseWbsInfo;
import com.myapp.service.ec.basedata.SafeTemplateDetailService;
import com.myapp.service.ec.basedata.SafeTemplateService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年6月7日 
 * @system:
 *安全、文明样板工作要点
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.基础数据.安全样板工作要点",number="app.ec.basedata.safetemplate")
@Controller
@RequestMapping("ec/basedata/safetemplates")
public class SafeTemplateListController extends BaseDataListController {
	
	@Resource
	public SafeTemplateService safeTemplateService;
	@Resource
	private SafeTemplateDetailService safeTemplateDetailService;
	@Override
	public AbstractBaseService getService() {
		return safeTemplateService;
	}
	@Override
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("branchBaseWbs", DataTypeEnum.F7, ProBaseWbsInfo.class));
		cols.add(new ColumnModel("subentry", DataTypeEnum.F7, ProBaseWbsInfo.class));
		return cols;
	}
	@Override
	public String getEditUrl() {
		return "ec/basedata/safetemplate/safeTemplateEdit";
	}
	@Override
	public String getListUrl() {
		return "ec/basedata/safetemplate/safeTemplateList";
	}

	@AuthorAnn(doLongin=true,doPermission=false)
	@RequestMapping("/jobrequire")
	@ResponseBody
	public WebDataModel showQualityTemplateJobRequire(String safeTemplateId) {
		if(!StringUtils.isEmpty(safeTemplateId)){
			this.data = safeTemplateDetailService.queryBySafeTemplateId(safeTemplateId);
		}
		return super.ajaxModel();
	}

}
