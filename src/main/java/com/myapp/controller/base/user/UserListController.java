package com.myapp.controller.base.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.annotation.PermissionItemAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseListController;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.enums.UserState;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.model.WebDataModel;
import com.myapp.core.service.UserService;
import com.myapp.core.util.BaseUtil;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年5月6日 
 * @system:
 *
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.用户管理",number="app.user")
@Controller
@RequestMapping("base/users")
public class UserListController extends BaseListController {
	@Resource
	public UserService userService;
	
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("number"));
		cols.add(new ColumnModel("remark"));
		cols.add(new ColumnModel("linkers"));
		cols.add(new ColumnModel("createDate"));
		cols.add(new ColumnModel("passWord"));
		cols.add(new ColumnModel("userState",DataTypeEnum.ENUM,UserState.class));
		ColumnModel orgCol = new ColumnModel("defOrg",DataTypeEnum.F7,"name");
		cols.add(orgCol);
		return cols;
	}
	
	public AbstractBaseService getService() {
		return this.userService;
	}
	
	public void executeQueryParams(Criteria query) {
		super.executeQueryParams(query);
		String serach = request.getParameter("search");
		if(!BaseUtil.isEmpty(serach)){
			Map searchMap = JSONObject.parseObject(serach, new HashMap().getClass());
			Object includeChildObj = searchMap.get("includeChild");
			boolean include = true;
			if(includeChildObj!=null&&includeChildObj instanceof Boolean){
				include = (Boolean)includeChildObj;
			}
			Object objTree = searchMap.get("tree");
			if(objTree!=null){
				Map treeMap = JSONObject.parseObject(objTree.toString(), new HashMap().getClass());
				Object lnObj = treeMap.get("longNumber");
				if(lnObj!=null){
					query.add(Restrictions.like("defOrg.longNumber",lnObj.toString(),include?MatchMode.START:MatchMode.EXACT));
				}
			}
		}
	}
	
	public String getEditUrl() {
		return "user/userEdit";
	}

	public String getListUrl() {
		return "user/userList";
	}
	
	
	@PermissionItemAnn(name="用户密码重置",number="resetEncrypt")
	@RequestMapping(value="/resetEncrypt")
	@ResponseBody
	public WebDataModel resetEncrypt() {
		try {
			init();
			String billId = getReuestBillId();
			if(!BaseUtil.isEmpty(billId)){
				setInfoMesg(userService.resetUserEncrypt(billId));
			}else{
				setErrorMesg("单据id为空，无法完成密码重置操作!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			setExceptionMesg(e.getMessage());
		}
		return ajaxModel();
	}
}
