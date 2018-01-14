package com.myapp.controller.ec.basedata.probasewbs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.AuthorAnn;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseTreeListController;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.exception.db.AddNewException;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.model.WebDataModel;
import com.myapp.core.util.BaseUtil;
import com.myapp.entity.ec.basedata.ProBaseWbsInfo;
import com.myapp.enums.ec.ProWbsType;
import com.myapp.service.ec.basedata.ProBaseWbsService;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年12月04日 
 * @system:
 *
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.基础数据.工程基础分解结构",number="app.ec.basedata.probasewbs")
@Controller
@RequestMapping("ec/basedata/probasewbss")
public class ProBaseWbsListController extends BaseTreeListController {
	@Resource
	public ProBaseWbsService proBaseWbsService;
	public AbstractBaseService getService() {
		return proBaseWbsService;
	}
	
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("number"));
		cols.add(new ColumnModel("remark"));
		cols.add(new ColumnModel("enabled",DataTypeEnum.BOOLEAN));
		cols.add(new ColumnModel("wbsType",DataTypeEnum.ENUM,ProWbsType.class));
		cols.add(new ColumnModel("parent",DataTypeEnum.F7,ProBaseWbsInfo.class));
		return cols;
	}
	
	public List<ColumnModel> getTreeDataBinding() {
		List<ColumnModel> cols = super.getTreeDataBinding();
		cols.add(new ColumnModel("wbsType",DataTypeEnum.ENUM,ProWbsType.class));
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
				Object lnObj = treeMap.get("longNumber");
				if(lnObj!=null){
					query.add(Restrictions.like("longNumber",lnObj.toString(),include?MatchMode.START:MatchMode.EXACT));
				}
			}
		}
	}
	
	public void executeTreeQueryParams(Criteria query) {
		super.executeQueryParams(query);
		String enabled = request.getParameter("enabled");
		if(!BaseUtil.isEmpty(enabled)){
			Boolean enable = enabled.toLowerCase().equals("true");
			query.add(Restrictions.eq("enabled", enable));
		}
	}

	public Order getOrder() {
		return Order.asc("longNumber");
	}
	public String getEditUrl() {
		return "ec/basedata/probasewbs/proBaseWbsEdit";
	}
	public String getListUrl() {
		return "ec/basedata/probasewbs/proBaseWbsList";
	}
	public AbstractBaseService getTreeService() {
		return proBaseWbsService;
	}
	
	@AuthorAnn(doPermission=false)
	@RequestMapping("/batchimp")
	@ResponseBody
	public WebDataModel batchImp(){
		String structId = request.getParameter("structId");
		try {
			return proBaseWbsService.batchInitProWbsData(structId);
		} catch (AddNewException e) {
			e.printStackTrace();
			setErrorMesg(e.getMessage());
		}
		return ajaxModel();
	}
	
	public String getHeadTitle() {
		return "工程基础分解结构";
	}
	
	public List<ExcelExportEntity> getExportHeader() {
		List<ExcelExportEntity> entitys = new ArrayList<ExcelExportEntity>();
		entitys.add(stringEntity("结构编码", "number"));
		entitys.add(stringEntity("结构名称", "name"));
		entitys.add(stringEntity("上级结构", "parent_name"));
		entitys.add(stringEntity("结构类型", "wbsType"));
		entitys.add(booleanEntity("启用", "enabled"));
		entitys.add(remarkEntity("备注", "remark"));
		return entitys;
	}
}
