package com.myapp.controller.base.org;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.EnumType;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.druid.sql.dialect.mysql.ast.expr.MySqlIntervalUnit;
import com.alibaba.fastjson.JSONObject;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseF7QueryController;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.enums.OrgTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.service.OrgService;
import com.myapp.core.util.EnumUtil;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年5月30日 
 * @system:
 *
 *-----------MySong---------------
 */
@Controller
@RequestMapping("base/orgf7")
public class OrgF7QueryController extends BaseF7QueryController {
	
	@Resource
	public OrgService orgService;
	public AbstractBaseService getService() {
		return orgService;
	}
	
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		ColumnModel col = new ColumnModel("name");
		col.setAlias_zh("组织名称");
		cols.add(col);
		col = new ColumnModel("number");
		col.setAlias_zh("组织编码");
		cols.add(col);
		ColumnModel orgCol = new ColumnModel("parent",DataTypeEnum.F7,"name,number");
		orgCol.setAlias_zh("上级名称,上级编码");
		cols.add(orgCol);
		return cols;
	}
	public Order getOrder() {
		return Order.asc("longNumber");
	}
	
	public void executeQueryParams(Criteria query) {
		super.executeQueryParams(query);
		Map search = getSearchPrams();
		if(search!=null&&search.containsKey("uiCtx")){
			Map uiCtx = JSONObject.parseObject(search.get("uiCtx").toString(), new HashMap().getClass());
			Object objType = uiCtx.get("orgType");
			if(objType!=null){
				List<OrgTypeEnum> orgTypeEnums = new ArrayList<OrgTypeEnum>();
				String[] typs = objType.toString().split(",");
				for(String type:typs){
					OrgTypeEnum ote = EnumUtil.getEnum(OrgTypeEnum.class.getName(), type);
					if(ote!=null) orgTypeEnums.add(ote);
				}
				if(orgTypeEnums.size()>0)
					query.add(Restrictions.in("orgType",orgTypeEnums.toArray()));
			}
		}
	}

}
