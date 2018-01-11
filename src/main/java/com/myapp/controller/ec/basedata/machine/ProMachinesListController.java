package com.myapp.controller.ec.basedata.machine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseListController;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.util.EnumUtil;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.enums.ec.LetterType;
import com.myapp.service.ec.basedata.ProMachinesService;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年12月12日 
 * @system:
 * 现场施工机械一览表
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.施工机械.现场施工机械一览表",number="app.ec.machine.promachines")
@Controller
@RequestMapping("ec/machine/promachiness")
public class ProMachinesListController extends BaseListController {
	
	@Resource
	public ProMachinesService proMachinesService;
	public AbstractBaseService getService() {
		return proMachinesService;
	}

	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("number"));
		cols.add(new ColumnModel("operator"));
		cols.add(new ColumnModel("maintenancer"));
		cols.add(new ColumnModel("checkContent"));
		cols.add(new ColumnModel("attachs",DataTypeEnum.INT));
		cols.add(new ColumnModel("project",DataTypeEnum.F7,ProjectInfo.class));
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
		return "ec/basedata/machine/proMachinesEdit";
	}

	public String getListUrl() {
		return "ec/basedata/machine/proMachinesList";
	}
	
	public String getHeadTitle() {
		return "现场施工机械一览表";
	}
	
	public List<ExcelExportEntity> getExportHeader() {
		List<ExcelExportEntity> entitys = new ArrayList<ExcelExportEntity>();
		entitys.add(new ExcelExportEntity("工程项目", "project_name"));
		entitys.add(new ExcelExportEntity("机械编码", "number"));
		entitys.add(new ExcelExportEntity("机械名称", "name"));
		entitys.add(new ExcelExportEntity("操作人员", "operator"));
		entitys.add(new ExcelExportEntity("维修人员", "maintenancer"));
		entitys.add(new ExcelExportEntity("验收情况", "checkContent"));
		return entitys;
	}
}
