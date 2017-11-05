package com.myapp.controller.base.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.myapp.core.annotation.AuthorAnn;
import com.myapp.core.base.controller.BaseController;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年11月05日 
 * @system: web font 字体展示
 *-----------MySong---------------
 */
@Controller
@RequestMapping("/base/web")
public class webIconFontController extends BaseController {

	@RequestMapping(value="/font")
	@AuthorAnn(doLongin=true,doPermission=false)
	@ResponseBody
	public ModelAndView fonts() {
		return toPage("common/webIconFont", null);
	}
}
