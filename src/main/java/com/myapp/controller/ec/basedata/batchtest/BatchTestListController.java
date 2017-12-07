package com.myapp.controller.ec.basedata.batchtest;

import java.util.ArrayList;
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
import com.myapp.core.annotation.AuthorAnn;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseDataListController;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.model.WebDataModel;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.util.EnumUtil;
import com.myapp.entity.ec.basedata.ProBaseWbsInfo;
import com.myapp.enums.ec.TestGroup;
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
		cols.add(new ColumnModel("proBaseWbs",DataTypeEnum.F7,ProBaseWbsInfo.class));
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
	
	public String getEditUrl() {
		return "ec/basedata/batchtest/batchTestEdit";
	}

	public String getListUrl() {
		return "ec/basedata/batchtest/batchTestList";
	}

}
