package com.myapp.controller.base;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.myapp.core.base.controller.BaseController;
import com.myapp.core.model.WebDataModel;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.util.EnumUtil;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年5月14日 
 * @system:
 *  处理一些共用 通用的web ajax的请求
 *-----------MySong---------------
 */
@Controller
@RequestMapping("/web")
public class WebCommonController extends BaseController {

	@RequestMapping(value="/combox")
	@ResponseBody
	public WebDataModel getItems() {
		try {
			onLoad();
			String enumClaz =  request.getParameter("enum");
			if(!BaseUtil.isEmpty(enumClaz)){
				data = EnumUtil.getEnumKvs(enumClaz);
			}
		} catch (Exception e) {
			e.printStackTrace();
			setExceptionMesg(e.getMessage());
		}
		return ajaxModel();
	}
}
