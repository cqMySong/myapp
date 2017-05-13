package com.myapp.controller.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseListController;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.service.UserService;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年5月6日 
 * @system:
 *
 *-----------MySong---------------
 */
@Controller
@RequestMapping("/user")
public class UserListController extends BaseListController {
	@Resource
	public UserService userService;
	
	@RequestMapping("/toUsers")
	public ModelAndView toUsers(){
		Map params = new HashMap();
//		for(int i=1;i<50;i++){
//			UserInfo u = new UserInfo();
//			u.setName("宋军_"+i);
//			u.setNumber(10000+i+"");
//			u.setPassWord("abc124"+i);
//			u.setRemark("批量初始化插入_"+i);
//			userService.addNewEntity(u);
//		}
		return toPage("user/userList", params);
	}

	public AbstractBaseService getService() {
		return this.userService;
	}
	public String querySQL() {
		return "select u.id as id,u.name as name,u.number as number"
				+ ",u.passWord as pwd ,u.createDate as cdate from UserInfo u";
	}
	
	public List executeQueryParams() {
		List params = super.executeQueryParams();
		return params;
	}
	
}
