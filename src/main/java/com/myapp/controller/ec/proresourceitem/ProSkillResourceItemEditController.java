package com.myapp.controller.ec.proresourceitem;

import com.myapp.core.annotation.PermissionItemAnn;
import com.myapp.core.model.WebDataModel;
import com.myapp.enums.ec.SkillType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.enums.ec.ResourceType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2018年1月1日 
 * @system: 项目级资料目录 -安全技术要求及验收目录
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.项目资料目录.安全技术要求及验收目录",number="app.ec.proresourcedata.skillresourceitem")
@Controller
@RequestMapping("ec/proresourceitem/skillresourceitem")
public class ProSkillResourceItemEditController extends ProResourceItemBaseEditController {
	@Override
	public ResourceType getResourceType() {
		return ResourceType.AQJSYQYSML;
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
