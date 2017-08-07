package com.myapp.controller.ec.drawing;

import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.CoreBaseController;
import com.myapp.core.model.WebDataModel;
import com.myapp.service.ec.basedata.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年5月20日 
 * @system:
 *-----------MySong---------------
 */
@Controller
@RequestMapping("ec/discussiondrawing")
public class DiscussionDrawingF7Controller extends CoreBaseController{
	@Resource
	public ProjectService projectService;
	public AbstractBaseService getService() {
		return projectService;
	}

	@RequestMapping("/f7show")
	public ModelAndView f7show(){
		init();
		Map params = new HashMap();
		return toPage(getF7URL(), params);
	}
	
	public String getF7URL() {
		return "ec/drawing/discussion/proSubTree";
	}
}
