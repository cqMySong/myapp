package com.myapp.controller.ec.basedata.prosubitem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseF7QueryController;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.util.WebUtil;
import com.myapp.entity.ec.basedata.ProStructureInfo;
import com.myapp.entity.ec.basedata.ProSubItemInfo;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.service.ec.basedata.ProSubItemService;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年5月30日 
 * @system:
 *
 *-----------MySong---------------
 */
@Controller
@RequestMapping("ec/basedata/proSubItemF7")
public class ProSubItemF7QueryController extends BaseF7QueryController {
	
	@Resource
	public ProSubItemService proSubItemService;
	public AbstractBaseService getService() {
		return proSubItemService;
	}
	
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		ColumnModel col = new ColumnModel("project",DataTypeEnum.F7,ProjectInfo.class);
		col.setFormat("id,name");
		col.setAlias_zh("id,工程项目");
		cols.add(col);
		
		col = new ColumnModel("proStruct",DataTypeEnum.F7,ProStructureInfo.class);
		col.setFormat("id,displayName");
		col.setAlias_zh("id,工程结构");
		cols.add(col);
		
		col = new ColumnModel("proSub",DataTypeEnum.F7,ProSubItemInfo.class);
		col.setFormat("id,name");
		col.setAlias_zh("id,分部工程");
		cols.add(col);
		
		col = new ColumnModel("number");
		col.setAlias_zh("编码");
		cols.add(col);
		
		col = new ColumnModel("name");
		col.setAlias_zh("名称");
		cols.add(col);
		
		col = new ColumnModel("enabled",DataTypeEnum.BOOLEAN);
		col.setAlias_zh("启用");
		cols.add(col);
		col = new ColumnModel("remark");
		col.setAlias_zh("备注");
		cols.add(col);
		return cols;
	}
	public void executeQueryParams(Criteria query) {
		super.executeQueryParams(query);
		query.add(Restrictions.eq("enabled",Boolean.TRUE));
		String serach = request.getParameter("search");
		String proId = "xyz";
		if(!BaseUtil.isEmpty(serach)){
			Map searchMap = JSONObject.parseObject(serach, new HashMap().getClass());
			Object uiCtxObj = searchMap.get("uiCtx");
			if(uiCtxObj!=null){
				Map uiCtx = JSONObject.parseObject(uiCtxObj.toString(), new HashMap().getClass());
				Object proIdObj =  uiCtx.get("projectId");
				if(proIdObj!=null) proId = WebUtil.UUID_ReplaceID(proIdObj.toString());
			}
		}
		if(!BaseUtil.isEmpty(proId)){
			query.add(Restrictions.eq("project.id",proId));
		}
	}
	public Order getOrder() {
		return Order.asc("number");
	}
	public String getUIWinTitle() {
		return "项目分项工程";
	}
}
