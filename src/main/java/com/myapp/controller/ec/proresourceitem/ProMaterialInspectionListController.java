package com.myapp.controller.ec.proresourceitem;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.annotation.PermissionItemAnn;
import com.myapp.core.enums.PermissionTypeEnum;
import com.myapp.core.util.WebUtil;
import com.myapp.enums.ec.ResourceType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2018年1月1日 
 * @system: 项目级资料目录 -原材料检查
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.项目资料目录.原材料检查",number="app.ec.proresourcedata.materialinspection")
@Controller
@RequestMapping("ec/proresourceitem/materialinspections")
public class ProMaterialInspectionListController extends ProResourceItemBaseListController {
	
	@Override
	public String getEditUrl() {
		return "ec/proresourceitem/materialInspectionEdit";
	}
	@Override
	public String getListUrl() {
		return "ec/proresourceitem/materialInspectionList";
	}
	@Override
	public ResourceType getResourceType() {
		return ResourceType.YCLJC;
	}

	@PermissionItemAnn(name="原材料检查导入",number="import",type= PermissionTypeEnum.FUNCTION)
	@RequestMapping("/batch/import")
	public ModelAndView forwardBatchImport(){
		Map params = new HashMap();
		toListUIParams(params);
		params.put("uiCtx", WebUtil.UUID_ReplaceID(params.get("uiCtx").toString()));
		return toPage("ec/proresourceitem/materialInspectionBatchTreeImport", params);
	}
	
}
