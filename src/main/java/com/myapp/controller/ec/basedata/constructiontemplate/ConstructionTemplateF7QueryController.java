package com.myapp.controller.ec.basedata.constructiontemplate;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseF7QueryController;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.util.WebUtil;
import com.myapp.entity.ec.basedata.ProBaseWbsInfo;
import com.myapp.service.ec.basedata.ConstructionTemplateService;
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
@RequestMapping("ec/basedata/constructiontemplateF7")
public class ConstructionTemplateF7QueryController extends BaseF7QueryController {
	
	@Resource
	public ConstructionTemplateService constructionTemplateService;
	@Override
	public AbstractBaseService getService() {
		return constructionTemplateService;
	}
	@Override
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		ColumnModel col = new ColumnModel("number");
		col.setAlias_zh("编码");
		cols.add(col);
		col = new ColumnModel("name");
		col.setAlias_zh("名称");
		cols.add(col);
		col = new ColumnModel("templateType");
		col.setAlias_zh("样板种类");
		cols.add(col);
		col = new ColumnModel("content");
		col.setAlias_zh("配合的技术交底");
		cols.add(col);
		return cols;
	}

	@Override
	public Order getOrder(){
		return Order.asc("number");
	}
	
}
