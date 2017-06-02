package com.myapp.controller.base.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseListController;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.enums.UserState;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.service.UserService;
import com.myapp.core.util.DateUtil;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年5月6日 
 * @system:
 *
 *-----------MySong---------------
 */
@Controller
@RequestMapping("base/users")
public class UserListController extends BaseListController {
	@Resource
	public UserService userService;
	
	@RequestMapping("/toUsers")
	public ModelAndView toUsers(){
		Map params = new HashMap();
		return toPage("user/userList", params);
	}
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("number"));
		cols.add(new ColumnModel("remark"));
		cols.add(new ColumnModel("createDate"));
		cols.add(new ColumnModel("passWord"));
		ColumnModel orgCol = new ColumnModel("defOrg",DataTypeEnum.F7,"name");
		cols.add(orgCol);
		return cols;
	}
	
	public AbstractBaseService getService() {
		return this.userService;
	}
	
	public String getEditUrl() {
		return "user/userEdit";
	}
	
}
