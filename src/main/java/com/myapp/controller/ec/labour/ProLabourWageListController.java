package com.myapp.controller.ec.labour;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

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
 * 项目民工工资表
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.劳务人员.项目民工工资表",number="app.ec.labour.prolabourwage")
@Controller
@RequestMapping("ec/labour/prolabourwages")
public class ProLabourWageListController extends BaseListController {
	
	@Resource
	public ProLabourWageService proLabourWageService;
	public AbstractBaseService getService() {
		return proLabourWageService;
	}

	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("number"));
		cols.add(new ColumnModel("labourGroup"));
		cols.add(new ColumnModel("project",DataTypeEnum.F7,ProjectInfo.class));
		cols.add(new ColumnModel("createUser",DataTypeEnum.F7,UserInfo.class));
		cols.add(new ColumnModel("auditor",DataTypeEnum.F7,UserInfo.class));
		cols.add(new ColumnModel("bizDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("createDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("auditDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("billState",DataTypeEnum.ENUM,BillState.class));
		return cols;
	}
	
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
	public String getEditUrl() {
		return "ec/labour/proLabourWageEdit";
	}

	public String getListUrl() {
		return "ec/labour/proLabourWageList";
	}

}
