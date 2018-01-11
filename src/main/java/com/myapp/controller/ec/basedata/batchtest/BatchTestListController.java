package com.myapp.controller.ec.basedata.batchtest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseDataListController;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.enums.MenuOpenType;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.model.WebDataModel;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.util.EnumUtil;
import com.myapp.entity.ec.basedata.BatchTestInfo;
import com.myapp.entity.ec.basedata.ProBaseWbsInfo;
import com.myapp.service.ec.basedata.BatchTestService;

@PermissionAnn(name="系统管理.现场管理.基础数据.检验批划分",number="app.ec.basedata.batchtest")
@Controller
@RequestMapping("ec/basedata/batchtests")
public class BatchTestListController extends BaseDataListController {
	
	@Resource
	public BatchTestService batchTestService;

	public AbstractBaseService getService() {
		return batchTestService;
	}
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("proBaseWbs",DataTypeEnum.F7,ProBaseWbsInfo.class,"id,name,displayName"));
		cols.add(new ColumnModel("content"));
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
				Object wbsLongNumber = treeMap.get("longNumber");
				if(wbsLongNumber!=null){
					query.add(Restrictions.like("proBaseWbs.longNumber",wbsLongNumber.toString(),include?MatchMode.START:MatchMode.EXACT));
				}
			}
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/batchTestData",method=RequestMethod.POST)
	public WebDataModel getBatchTestData() {
		String wbsId = request.getParameter("wbsId");
		if(!BaseUtil.isEmpty(wbsId)){
			BatchTestInfo batInfo = batchTestService.getBatchTestByBaseWBS(wbsId, Boolean.TRUE);
			Map retMap = new HashMap();
			if(batInfo!=null){
				retMap.put("name", batInfo.getName());
				retMap.put("number", batInfo.getNumber());
				retMap.put("content", batInfo.getContent());
			}
			data = retMap;
		}
		return ajaxModel();
	}
	
	
	public String getEditUrl() {
		return "ec/basedata/batchtest/batchTestEdit";
	}

	public String getListUrl() {
		return "ec/basedata/batchtest/batchTestList";
	}
	
	public String getHeadTitle() {
		return "检验批划分标准";
	}
	public List<ExcelExportEntity> getExportHeader() {
		List<ExcelExportEntity> entitys = super.getExportHeader();
		ExcelExportEntity proBaseWbs = new ExcelExportEntity("分部分项工程", "proBaseWbs_displayName");
		entitys.add(0,proBaseWbs);
		
		ExcelExportEntity content = new ExcelExportEntity("划分标准", "content");
		content.setWrap(true);
		content.setWidth(60);
		entitys.add(3,content);
		return entitys;
	}

}
