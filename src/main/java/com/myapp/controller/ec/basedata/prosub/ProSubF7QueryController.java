package com.myapp.controller.ec.basedata.prosub;

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
import com.myapp.core.service.base.BaseService;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.util.WebUtil;
import com.myapp.entity.ec.basedata.ProStructureInfo;
import com.myapp.entity.ec.basedata.ProSubInfo;
import com.myapp.entity.ec.basedata.ProjectInfo;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年5月30日 
 * @system:
 *
 *-----------MySong---------------
 */
@Controller
@RequestMapping("ec/basedata/proSubF7")
public class ProSubF7QueryController extends BaseF7QueryController {
	
	@Resource
	public BaseService baseService;
	public AbstractBaseService getService() {
		return baseService.newServicInstance(ProSubInfo.class);
	}
	
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		ColumnModel col = new ColumnModel("project",DataTypeEnum.F7,ProjectInfo.class);
		col.setFormat("id,name");
		col.setAlias_zh("id,工程项目");
		cols.add(col);
		
		col = new ColumnModel("proStruct",DataTypeEnum.F7,ProStructureInfo.class);
		col.setFormat("id,name,displayName");
		col.setAlias_zh("id,工程结构,工程全名");
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
	public boolean showCol(String colName) {
		if("proStruct_name".equals(colName)){
			return false;
		}
		return super.showCol(colName);
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
				Object proStructObj = uiCtx.get("proStructId");
				if(proStructObj!=null){
					query.add(Restrictions.eq("proStruct.id",WebUtil.UUID_ReplaceID(proStructObj.toString())));
				}
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
		return "项目分部工程";
	}
}
