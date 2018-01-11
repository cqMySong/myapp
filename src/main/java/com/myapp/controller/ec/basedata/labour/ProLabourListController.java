package com.myapp.controller.ec.basedata.labour;

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
import com.myapp.core.enums.Sex;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.util.EnumUtil;
import com.myapp.core.util.WebUtil;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.entity.ec.basedata.WorkTypeInfo;
import com.myapp.service.ec.basedata.ProLabourService;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年12月12日 
 * @system:
 * 项目劳务人员备案表
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.劳务人员.项目劳务人员备案",number="app.ec.labour.prolabour")
@Controller
@RequestMapping("ec/labour/prolabours")
public class ProLabourListController extends BaseListController {
	
	@Resource
	public ProLabourService proLabourService;
	public AbstractBaseService getService() {
		return proLabourService;
	}

	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("number"));
		cols.add(new ColumnModel("addr"));
		cols.add(new ColumnModel("idCard"));
		cols.add(new ColumnModel("bank"));
		cols.add(new ColumnModel("bankNo"));
		cols.add(new ColumnModel("project",DataTypeEnum.F7,ProjectInfo.class));
		cols.add(new ColumnModel("workType",DataTypeEnum.F7,WorkTypeInfo.class));
		cols.add(new ColumnModel("sex",DataTypeEnum.ENUM,Sex.class));
		cols.add(new ColumnModel("age",DataTypeEnum.INT));
		cols.add(new ColumnModel("joinDate",DataTypeEnum.DATE));
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
		return "ec/basedata/labour/proLabourEdit";
	}

	public String getListUrl() {
		return "ec/basedata/labour/proLabourList";
	}
	public String getHeadTitle() {
		return "项目劳务人员备案表";
	}
	
	public List<ExcelExportEntity> getExportHeader() {
		List<ExcelExportEntity> entitys = new ArrayList<ExcelExportEntity>();
		entitys.add(new ExcelExportEntity("工程项目", "project_name"));
		entitys.add(new ExcelExportEntity("工号", "number"));
		entitys.add(new ExcelExportEntity("姓名", "name"));
		entitys.add(new ExcelExportEntity("身份证号", "idCard",20));
		ExcelExportEntity sex = new ExcelExportEntity("性别", "sex");
		sex.setReplace(EnumUtil.enum2Strs(Sex.class.getName()));
		entitys.add(sex);
		entitys.add(new ExcelExportEntity("年龄", "age"));
		entitys.add(new ExcelExportEntity("工种", "workType_name"));
		ExcelExportEntity joinDate = new ExcelExportEntity("进场时间", "joinDate");
		joinDate.setFormat("yyyy-MM-dd");
		entitys.add(joinDate);
		ExcelExportEntity addr = new ExcelExportEntity("地址", "addr");
		addr.setWidth(80);
		entitys.add(addr);
		return entitys;
	}
	

}
