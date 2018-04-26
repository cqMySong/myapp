package com.myapp.controller.ec.proresourcedata;

import com.myapp.core.annotation.PermissionItemAnn;
import com.myapp.core.model.WebDataModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.core.annotation.PermissionAnn;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年12月11日 
 * @system: 项目级资料目录 -安全
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.项目资料目录.安全资料目录",number="app.ec.proresourcedata.safedata")
@Controller
@RequestMapping("ec/proresourcedata/safedata")
public class ProSafeDataEditController extends ProResourceDataBaseEditController {
	@Override
	public String getCode(){
		return "safedata";
	}

	@PermissionItemAnn(name="导入保存",number="batchSave")
	@ResponseBody
	@RequestMapping(value="/batch/import",method= RequestMethod.POST)
	public WebDataModel batchImportSave(String structId, String structCode, String wbsIds) {
		WebDataModel webDataModel = new WebDataModel();
		try{
			proResourceDataService.batchSave(structId,getCurUser(),wbsIds, getCode());
			webDataModel.setStatusCode(STATUSCODE_SUCCESS);
		}catch (Exception e) {
			e.printStackTrace();
			setExceptionMesg(e.getMessage());
		}
		return webDataModel;
	}

}
