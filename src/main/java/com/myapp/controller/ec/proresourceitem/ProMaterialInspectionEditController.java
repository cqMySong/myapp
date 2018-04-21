package com.myapp.controller.ec.proresourceitem;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.annotation.PermissionItemAnn;
import com.myapp.core.model.WebDataModel;
import com.myapp.enums.ec.ResourceType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2018年1月1日 
 * @system: 原材料检查
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.项目资料目录.原材料检查",number="app.ec.proresourcedata.materialinspection")
@Controller
@RequestMapping("ec/proresourceitem/materialinspection")
public class ProMaterialInspectionEditController extends ProResourceItemBaseEditController {
	@Override
	public ResourceType getResourceType() {
		return ResourceType.YCLJC;
	}
	@PermissionItemAnn(name="导入保存",number="batchSave")
	@ResponseBody
	@RequestMapping(value="/batch/import",method= RequestMethod.POST)
	public WebDataModel batchImportSave(String structId, String structCode, String wbsIds) {
		WebDataModel webDataModel = new WebDataModel();
		try{
			proResourceItemService.batchSave(structId,getCurUser(),wbsIds, getResourceType());
			webDataModel.setStatusCode(STATUSCODE_SUCCESS);
		}catch (Exception e) {
			e.printStackTrace();
			setExceptionMesg(e.getMessage());
		}
		return webDataModel;
	}
}
