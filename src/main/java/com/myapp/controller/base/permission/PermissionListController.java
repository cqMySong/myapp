package com.myapp.controller.base.permission;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseTreeListController;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.enums.PermissionTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.model.WebDataModel;
import com.myapp.core.service.PermissionService;
import com.myapp.core.util.BaseUtil;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年5月27日 
 * @system:
 *
 *-----------MySong---------------
 */
@Controller
@RequestMapping("base/permissions")
public class PermissionListController extends BaseTreeListController {
	@Resource
	public PermissionService permissionService;
	public AbstractBaseService getService() {
		return permissionService;
	}
	
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("number"));
		cols.add(new ColumnModel("longNumber"));
		cols.add(new ColumnModel("url"));
		cols.add(new ColumnModel("remark"));
		cols.add(new ColumnModel("level"));
		cols.add(new ColumnModel("type",DataTypeEnum.ENUM,PermissionTypeEnum.class));
		ColumnModel orgCol = new ColumnModel("parent",DataTypeEnum.F7,"id,name,number");
		cols.add(orgCol);
		return cols;
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
					query.add(Restrictions.like("longNumber",lnObj.toString(),include?MatchMode.START:MatchMode.EXACT));
				}
			}
		}
	}
	
	/**
	 * 同步系统权限
	 */
	@RequestMapping(value="/sync")
	@ResponseBody
	public WebDataModel toSync() {
		try {
			data = permissionService.syncSysPermission(request);
		} catch (Exception e) {
			e.printStackTrace();
			setExceptionMesg(e.getMessage());
		}
		return ajaxModel();
	}
	
	
	public String getEditUrl() {
		return "permission/permissionEdit";
	}
	public String getListUrl() {
		return "permission/permissionList";
	}
	public AbstractBaseService getTreeService() {
		return permissionService;
	}
}
