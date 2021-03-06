package com.myapp.controller.ec.proworkcheck;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
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
import com.myapp.entity.ec.basedata.ProSubInfo;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.service.ec.plan.ProjectTotalPlanService;
import com.myapp.service.ec.proworkcheck.ProDayCheckService;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年11月27日 
 * @system:
 * 项目现场施工日检查记录
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.施工检查.日检查记录",number="app.ec.workcheck.daycheck")
@Controller
@RequestMapping("ec/proworkcheck/prodaychecks")
public class ProDayCheckListController extends BaseListController {
	
	@Resource
	public ProDayCheckService proDayCheckService;
	public AbstractBaseService getService() {
		return proDayCheckService;
	}
	
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("number"));
		cols.add(new ColumnModel("partBChecker"));
		cols.add(new ColumnModel("remark"));
		cols.add(new ColumnModel("createDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("bizDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("billState",DataTypeEnum.ENUM,BillState.class));
		cols.add(new ColumnModel("auditDate",DataTypeEnum.DATE));
		ColumnModel project = new ColumnModel("project",DataTypeEnum.F7,"id,name");
		project.setClaz(ProjectInfo.class);
		cols.add(project);
		ColumnModel proSafer = new ColumnModel("proSafer",DataTypeEnum.F7,"id,name");
		proSafer.setClaz(UserInfo.class);
		cols.add(proSafer);
		
		
		ColumnModel createUser = new ColumnModel("createUser",DataTypeEnum.F7,"id,name");
		createUser.setClaz(UserInfo.class);
		cols.add(createUser);
		ColumnModel auditor = new ColumnModel("auditor",DataTypeEnum.F7,"id,name");
		auditor.setClaz(UserInfo.class);
		cols.add(auditor);
		return cols;
	}
	public void executeQueryParams(Criteria query) {
		super.executeQueryParams(query);
		String serach = request.getParameter("search");
		String projectId = "xyz";
		if(!BaseUtil.isEmpty(serach)){
			Map searchMap = JSONObject.parseObject(serach, new HashMap().getClass());
			Object objTree = searchMap.get("tree");
			if(objTree!=null){
				Map treeMap = JSONObject.parseObject(objTree.toString(), new HashMap().getClass());
				Object idObj = treeMap.get("id");
				Object type = treeMap.get("type");
				if(type!=null&&idObj!=null){
					if("project".equals(type.toString())){
						projectId = idObj.toString();
					}
				}
			}
		}
		query.add(Restrictions.eq("project.id",projectId));
	}
	
	public String getEditUrl() {
		return "ec/proworkcheck/proDayCheckEdit";
	}

	public String getListUrl() {
		return "ec/proworkcheck/proDayCheckList";
	}
}
