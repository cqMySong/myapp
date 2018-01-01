package com.myapp.controller.ec.basedata.resouce;

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
import com.myapp.core.model.ColumnModel;
import com.myapp.core.util.EnumUtil;
import com.myapp.enums.ec.ResourceType;
import com.myapp.service.ec.basedata.ResourceItemService;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2018年1月1日 
 * @system:
 * 资料目录（一级）
 *-----------MySong---------------
 */
@Controller
@RequestMapping("ec/basedata/resourceitemF7")
public class ResourceItemF7QueryController extends BaseF7QueryController {

	@Resource
	public ResourceItemService resourceItemService;
	public AbstractBaseService getService() {
		return resourceItemService;
	}
	
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		ColumnModel col = new ColumnModel("number");
		col.setAlias_zh("编码");
		cols.add(col);
		col = new ColumnModel("name");
		col.setAlias_zh("名称");
		cols.add(col);
		col = new ColumnModel("remark");
		col.setAlias_zh("备注");
		cols.add(col);
		return cols;
	}
	
	public void executeQueryParams(Criteria query) {
		super.executeQueryParams(query);
		query.add(Restrictions.eq("enabled",Boolean.TRUE));
		Map searchMap = getSearchPrams();
		if(searchMap!=null&&searchMap.size()>0){
			Object ctxObj = searchMap.get("uiCtx");
			if(ctxObj!=null){
				Map uiMap = JSONObject.parseObject(ctxObj.toString(), new HashMap().getClass());
				if(uiMap!=null&&uiMap.size()>0){
					Object typeObj = uiMap.get("type");
					if(typeObj!=null){
						ResourceType rt = EnumUtil.getEnum(ResourceType.class.getName(), typeObj);
						if(rt!=null){
							query.add(Restrictions.eq("resourceType",rt));
						}
					}
				}
			}
		}
	}
	
	public String getUIWinTitle() {
		String title= "系统资料目录";
		Map uiMap = getUiCtx();
		if(uiMap!=null&&uiMap.size()>0){
			Object typeObj = uiMap.get("type");
			if(typeObj!=null){
				ResourceType rt = EnumUtil.getEnum(ResourceType.class.getName(), typeObj);
				if(rt!=null){
					title = rt.getName();
				}
			}
		}
		return title;
	}
	
	public Order getOrder() {
		return Order.asc("number");
	}
}
