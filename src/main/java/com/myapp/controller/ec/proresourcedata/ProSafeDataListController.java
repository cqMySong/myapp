package com.myapp.controller.ec.proresourcedata;

import com.myapp.core.annotation.PermissionItemAnn;
import com.myapp.core.enums.PermissionTypeEnum;
import com.myapp.core.util.WebUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.core.annotation.PermissionAnn;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年12月11日 
 * @system: 项目级资料目录 -安全
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.项目资料目录.安全资料目录",number="app.ec.proresourcedata.safedata")
@Controller
@RequestMapping("ec/proresourcedata/safedatas")
public class ProSafeDataListController extends ProResourceDataBaseListController {
	@Override
	public String getCode(){
		return "safedata";
	}
	@Override
	public String getEditUrl() {
		return "ec/proresourcedata/safeDataEdit";
	}
	@Override
	public String getListUrl() {
		return "ec/proresourcedata/safeDataList";
	}

	@PermissionItemAnn(name="安全资料目录导入",number="import",type= PermissionTypeEnum.FUNCTION)
	@RequestMapping("/batch/import")
	public ModelAndView forwardBatchImport(){
		Map params = new HashMap();
		toListUIParams(params);
		params.put("uiCtx", WebUtil.UUID_ReplaceID(params.get("uiCtx").toString()));
		return toPage("ec/proresourcedata/safeDataBatchTreeImport", params);
	}
	
}
