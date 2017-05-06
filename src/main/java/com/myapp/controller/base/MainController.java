package com.myapp.controller.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.myapp.core.base.controller.BaseController;
import com.myapp.core.entity.BaseOrgInfo;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.BillState;
import com.myapp.core.enums.Sex;
import com.myapp.core.service.BaseService;
import com.myapp.core.service.OrgService;
import com.myapp.core.service.UserService;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年3月12日 
 * @system:
 *    登陆之后的门户首页
 *-----------MySong---------------
 */
@Controller
@RequestMapping("/")
public class MainController extends BaseController {
	@Resource
	public OrgService orgService;
	@Resource
	public UserService userService;
	
	@RequestMapping("/")
	public ModelAndView index(){
		return redirectAction("main", null);
	}
	
	@RequestMapping("/main")
	public ModelAndView toMain(){
		Map params = new HashMap();
		return toPage("main/main", params);
	}
	
	@RequestMapping("/home")
	public ModelAndView toHome(){
		Map params = new HashMap();
		return toPage("main/home", params);
	}
	
}
