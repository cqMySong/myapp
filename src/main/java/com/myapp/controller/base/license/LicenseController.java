package com.myapp.controller.base.license;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.AuthorAnn;
import com.myapp.core.base.controller.BaseController;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.UserState;
import com.myapp.core.exception.db.QueryException;
import com.myapp.core.exception.db.SaveException;
import com.myapp.core.model.WebDataModel;
import com.myapp.core.service.MainMenuService;
import com.myapp.core.service.UserService;
import com.myapp.core.service.base.WebContextService;
import com.myapp.core.util.BaseUtil;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2018年01月24日 
 * @system: license 相关的界面上操作
 *-----------MySong---------------
 */
@Controller
@RequestMapping("/license")
public class LicenseController extends BaseController {
	
	@Resource
	public MainMenuService mainMenuService;
	
	@RequestMapping("/show")
	@AuthorAnn(doLongin=false,doPermission=false)
	public ModelAndView index(){
		Map params = new HashMap();
		return toPage("main/license", params);
	}
}
