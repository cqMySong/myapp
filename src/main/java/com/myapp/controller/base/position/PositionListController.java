package com.myapp.controller.base.position;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.AuthorAnn;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseDataListController;
import com.myapp.core.controller.BaseListController;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.service.PositionService;
import com.myapp.core.util.BaseUtil;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年8月16日 
 * @system:
 *
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.岗位管理",number="app.position")
@Controller
@RequestMapping("base/positions")
public class PositionListController extends BaseDataListController {
	
	@Resource
	public PositionService positionService;
	
	public AbstractBaseService getService() {
		return positionService;
	}
	
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		ColumnModel orgCol = new ColumnModel("org",DataTypeEnum.F7,"id,name,displayName");
		cols.add(orgCol);
		ColumnModel parent = new ColumnModel("parent",DataTypeEnum.F7,"name");
		cols.add(parent);
		cols.add(new ColumnModel("respible", DataTypeEnum.BOOLEAN));
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
					query.add(Restrictions.like("org.longNumber",lnObj.toString(),include?MatchMode.START:MatchMode.EXACT));
				}
			}
		}
	}

	public String getEditUrl() {
		return "position/positionEdit";
	}

	public String getListUrl() {
		return "position/positionList";
	}
	
	@AuthorAnn(doPermission=false,doLongin=true)
	@RequestMapping("/treeShow")
	public ModelAndView treeShow(){
		init();
		Map params = new HashMap();
		return toPage("position/positionTreeQuery", params);
	}

}
