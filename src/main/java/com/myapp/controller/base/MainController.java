package com.myapp.controller.base;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.AuthorAnn;
import com.myapp.core.base.controller.BaseController;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.entity.BaseOrgInfo;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.UserState;
import com.myapp.core.model.MyWebContext;
import com.myapp.core.model.WebDataModel;
import com.myapp.core.service.UserService;
import com.myapp.core.service.base.WebContextService;
import com.myapp.core.util.BaseUtil;
import com.myapp.entity.ec.plan.ProjectTotalPlanInfo;
import com.myapp.entity.ec.plan.ProjectTotalPlanItemInfo;
import com.myapp.service.ec.plan.ProjectTotalPlanService;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年3月12日 
 * @system:
 *    登陆之后的门户首页
 *-----------MySong---------------
 */
@Controller
@RequestMapping("/main")
public class MainController extends BaseController {
	@Resource
	public UserService userService;
	
	@Resource
	public WebContextService webContextService;
	
	@RequestMapping("/index")
	@AuthorAnn(doLongin=true,doPermission=false)
	public ModelAndView index(){
		Map params = new HashMap();
		return toPage("main/main", params);
	}
	
	@RequestMapping("/home")
	@AuthorAnn(doLongin=true,doPermission=false)
	public ModelAndView toHome(){
		Map params = new HashMap();
		return toPage("main/home", params);
	}
	@AuthorAnn(doLongin=false,doPermission=false)
	@RequestMapping("/login")
	public ModelAndView login(){
		Map params = new HashMap();
		return toPage("main/login", params);
	}
	
	@AuthorAnn(doLongin=false,doPermission=false)
	@RequestMapping("/logout")
	public ModelAndView logout(){
		Map params = new HashMap();
		request.getSession().invalidate();
		return toPage("main/login", params);
	}
	
	@AuthorAnn(doLongin=false,doPermission=false)
	@ResponseBody
	@RequestMapping("/tologin")
	public WebDataModel tologin(){
		init();
		String un = request.getParameter("un");
		String pd = request.getParameter("pd");
		String hql = " from UserInfo where number=?";
		UserInfo uInfo = userService.getEntity(hql, new String[]{un});
		if(uInfo!=null){
			try {
				String dbPd = uInfo.getPassWord();
				if(BaseUtil.isEmpty(dbPd))dbPd = "";
				String inputPd = BaseUtil.md5Encrypt(pd);
				if(inputPd.equals(dbPd)){
					UserState us = uInfo.getUserState();
					if(UserState.ENABLE.equals(us)){
						webContextService.initWebContext(request, uInfo);
						
						JSONObject jsonObj = new JSONObject();
						jsonObj.put("userName", un);
						jsonObj.put("indexUrl", request.getContextPath()+"/main/index");
						this.data = jsonObj;
					}else{
						setInfoMesg("用户状态已经"+us.getName()+",不能登录!");
					}
				}else{
					setInfoMesg("用户名和密码不匹配，请重新输入!");
				}
			} catch (Exception e) {
				setErrorMesg("用户验证发生错误!"+e.getMessage());
				e.printStackTrace();
			}
		}else{
			setInfoMesg("用户名不存在,请重新输入!");
		}
		return ajaxModel();
	}
	
	@RequestMapping("/toUserSet")
	@AuthorAnn(doLongin=true,doPermission=false)
	public ModelAndView toUserSetting(){
		Map params = new HashMap();
		return toPage("main/userSetting", params);
	}
	
	@ResponseBody
	@RequestMapping("/userSet")
	@AuthorAnn(doLongin=true,doPermission=false)
	public WebDataModel userSet(){
		return ajaxModel();
	}
	
}
