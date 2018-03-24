package com.myapp.controller.ec.basedata.project;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.myapp.core.annotation.AuthorAnn;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.CoreBaseController;
import com.myapp.core.model.WebDataModel;
import com.myapp.service.ec.basedata.ProjectService;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年5月20日 
 * @system:
 *-----------MySong---------------
 */
@Controller
@RequestMapping("ec/basedata/project")
public class ProjectF7Controller extends CoreBaseController{
	@Resource
	public ProjectService projectService;
	public AbstractBaseService getService() {
		return projectService;
	}
	
	@RequestMapping("/f7show")
	@AuthorAnn(doPermission=false)
	public ModelAndView f7show(){
		init();
		Map params = new HashMap();
		try{
			params.put("treeData", projectService.getProjectTreeData(null,getCurWebContext()));
		}catch(Exception e){
			setErrorMesg(e.getMessage());
		}
		return toPage(getF7URL(), params);
	}
	
	@RequestMapping(value="/orgData")
	@AuthorAnn(doPermission=false)
	@ResponseBody
	public WebDataModel treeData() {
		try{
			data = projectService.getProjectTreeData(null);
		}catch(Exception e){
			setErrorMesg(e.getMessage());
		}
		return ajaxModel();
	}
	
	public String getF7URL() {
		return "ec/basedata/project/projectTree";
	}
}
