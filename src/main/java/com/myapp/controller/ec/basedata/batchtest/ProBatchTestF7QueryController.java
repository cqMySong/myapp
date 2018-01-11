package com.myapp.controller.ec.basedata.batchtest;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseF7QueryController;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.util.WebUtil;
import com.myapp.entity.ec.basedata.ProBaseWbsInfo;
import com.myapp.enums.ec.ProWbsType;
import com.myapp.service.ec.basedata.ProBaseWbsService;
import com.myapp.service.ec.basedata.ProBatchTestService;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年12月18日 
 * @system: 项目检验批划分
 *-----------MySong---------------
 */
@Controller
@RequestMapping("ec/basedata/proBatchTestF7")
public class ProBatchTestF7QueryController extends BaseF7QueryController {
	
	@Resource
	public ProBatchTestService proBatchTestService;
	@Override
	public AbstractBaseService getService() {
		return proBatchTestService;
	}
	@Override
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		ColumnModel col = new ColumnModel("proBaseWbs",DataTypeEnum.F7,ProBaseWbsInfo.class);
		col.setFormat("id,displayName");
		col.setAlias_zh("id,分部分项");
		cols.add(col);
		col = new ColumnModel("number");
		col.setAlias_zh("编码");
		cols.add(col);
		col = new ColumnModel("name");
		col.setAlias_zh("检验批");
		cols.add(col);
		return cols;
	}
	@Override
	public void executeQueryParams(Criteria query) {
		super.executeQueryParams(query);
		String search = request.getParameter("search");
		String projectId = "-1";
		if(!BaseUtil.isEmpty(search)) {
			Map searchMap = JSONObject.parseObject(search, new HashMap().getClass());
			if(searchMap!=null&&searchMap.get("uiCtx")!=null){
				projectId = ((JSONObject)searchMap.get("uiCtx")).getString("projectId");
			}
		}
		query.add(Restrictions.eq("project.id", WebUtil.UUID_ReplaceID(projectId)));
	}
	@Override
	public Order getOrder(){
		return Order.asc("number");
	}
	
}
