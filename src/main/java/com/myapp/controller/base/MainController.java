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
import com.myapp.core.annotation.NeedLoginAnn;
import com.myapp.core.base.controller.BaseController;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.entity.BaseOrgInfo;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.UserState;
import com.myapp.core.model.MyWebContent;
import com.myapp.core.model.WebDataModel;
import com.myapp.core.service.UserService;
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
	public ProjectTotalPlanService projectTotalPlanService;
	public AbstractBaseService getService() {
		return projectTotalPlanService;
	}
	
	@RequestMapping("/index")
	public ModelAndView index(){
		Map params = new HashMap();
		return toPage("main/main", params);
	}
	
	@RequestMapping("/home")
	public ModelAndView toHome(){
		Map params = new HashMap();
		return toPage("main/home", params);
	}
	@NeedLoginAnn(doLongin=false)
	@RequestMapping("/login")
	public ModelAndView login(){
		Map params = new HashMap();
		return toPage("main/login", params);
	}
	
	@NeedLoginAnn(doLongin=false)
	@RequestMapping("/logout")
	public ModelAndView logout(){
		Map params = new HashMap();
		request.getSession().invalidate();
		return toPage("main/login", params);
	}
	
	@NeedLoginAnn(doLongin=false)
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
						JSONObject jsonObj = new JSONObject();
						jsonObj.put("userName", un);
						jsonObj.put("indexUrl", request.getContextPath()+"/main/index");
						MyWebContent myWebCtx = new MyWebContent();
						myWebCtx.setUserId(uInfo.getId());
						myWebCtx.setUserName(uInfo.getName());
						myWebCtx.setUserNumber(uInfo.getNumber());
						BaseOrgInfo org = uInfo.getDefOrg();
						if(org!=null){
							myWebCtx.setOrgId(org.getId());
							myWebCtx.setOrgName(org.getName());
						}
						request.getSession().setAttribute("webCtx", myWebCtx);
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
	public ModelAndView toUserSetting(){
		Map params = new HashMap();
		return toPage("main/userSetting", params);
	}
	
	@ResponseBody
	@RequestMapping("/userSet")
	public WebDataModel userSet(){
		return ajaxModel();
	}
	
}
