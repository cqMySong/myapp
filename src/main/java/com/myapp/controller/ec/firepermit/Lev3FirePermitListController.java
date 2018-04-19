package com.myapp.controller.ec.firepermit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
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
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.enums.ec.CheckType;
import com.myapp.service.ec.firepermit.Lev3FirePermitService;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年12月07日 
 * @system:3级动火许可
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.动火管理.三级动火许可",number="app.ec.firepermit.lev3firepermit")
@Controller
@RequestMapping("ec/firepermit/lev3firepermits")
public class Lev3FirePermitListController extends BaseListController {
	
	@Resource
	public Lev3FirePermitService lev3FirePermitService;
	public AbstractBaseService getService() {
		return lev3FirePermitService;
	}
	
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("number"));
		cols.add(new ColumnModel("firePart"));
		cols.add(new ColumnModel("fireDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("bizDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("billState",DataTypeEnum.ENUM,BillState.class));
		cols.add(new ColumnModel("project",DataTypeEnum.F7,ProjectInfo.class));
		cols.add(new ColumnModel("welder"));
		cols.add(new ColumnModel("guarder"));
		cols.add(new ColumnModel("proposer"));
		cols.add(new ColumnModel("welderbz"));
		cols.add(new ColumnModel("pzUser",DataTypeEnum.F7,UserInfo.class));
		cols.add(new ColumnModel("jsUser",DataTypeEnum.F7,UserInfo.class));
		cols.add(new ColumnModel("aqUser",DataTypeEnum.F7,UserInfo.class));
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
		return "ec/firepermit/lev3FirePermitEdit";
	}

	public String getListUrl() {
		return "ec/firepermit/lev3FirePermitList";
	}
	@Override
	public String getHeadTitle() {
		return "三级动火许可";
	}
	@Override
	public List<ExcelExportEntity> getExportHeader() {
		List<ExcelExportEntity> entity = new ArrayList<ExcelExportEntity>();
		entity.add(new ExcelExportEntity("申请编码", "number"));
		entity.add(new ExcelExportEntity("单位名称", "name"));
		entity.add(new ExcelExportEntity("申请日期", "bizDate"));
		entity.add(new ExcelExportEntity("动火部位", "firePart"));
		ExcelExportEntity remark = new ExcelExportEntity("防火措施", "remark");
		remark.setWidth(80);
		remark.setWrap(true);
		entity.add(remark);
		entity.add(new ExcelExportEntity("动火日期", "fireDate"));
		entity.add(new ExcelExportEntity("焊工", "welder"));
		entity.add(new ExcelExportEntity("监护人", "guarder"));
		entity.add(new ExcelExportEntity("申请动火人", "proposer"));
		entity.add(new ExcelExportEntity("焊工班长", "welderbz"));
		entity.add(new ExcelExportEntity("批准人", "pzUser_name"));
		entity.add(new ExcelExportEntity("技术科", "jsUser_name"));
		entity.add(new ExcelExportEntity("安全科", "aqUser_name"));
		return entity;
	}
}
