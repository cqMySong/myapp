package com.myapp.controller.ec.labourenter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.myapp.service.ec.labour.LabourEnterService;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseListController;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.BillState;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.util.BaseUtil;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.service.ec.labour.ProLabourWageService;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年12月24日 
 * @system:
 * 劳务人员进场及用工记录
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.劳务人员.劳务人员进场及用工记录",number="app.ec.labour.enter")
@Controller
@RequestMapping("ec/labour/enters")
public class LabourEnterListController extends BaseListController {
	
	@Resource
	public LabourEnterService labourEnterService;
	@Override
	public AbstractBaseService getService() {
		return labourEnterService;
	}
	@Override
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("name",DataTypeEnum.STRING));
		cols.add(new ColumnModel("number",DataTypeEnum.STRING));
		cols.add(new ColumnModel("enterDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("project",DataTypeEnum.F7,ProjectInfo.class));
		cols.add(new ColumnModel("createUser",DataTypeEnum.F7,UserInfo.class));;
		cols.add(new ColumnModel("createDate",DataTypeEnum.DATE));
		return cols;
	}
	@Override
	public void executeQueryParams(Criteria query) {
		super.executeQueryParams(query);
		String serach = request.getParameter("search");
		SimpleExpression se = Restrictions.eq("id","xyz");
		if(!BaseUtil.isEmpty(serach)){
			Map searchMap = JSONObject.parseObject(serach, new HashMap().getClass());
			Object objTree = searchMap.get("tree");
			if(objTree!=null){
				Map treeMap = JSONObject.parseObject(objTree.toString(), new HashMap().getClass());
				Object idObj = treeMap.get("id");
				Object type = treeMap.get("type");
				if(type!=null&&idObj!=null){
					if("project".equals(type.toString())){
						se = Restrictions.eq("project.id", idObj.toString());
					}
				}
			}
		}
		query.add(se);
	}
	@Override
	public String getEditUrl() {
		return "ec/labourenter/labourEnterEdit";
	}
	@Override
	public String getListUrl() {
		return "ec/labourenter/labourEnterList";
	}

}
