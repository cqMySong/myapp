package com.myapp.controller.base.org;

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

import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseTreeListController;
import com.myapp.core.entity.BaseOrgInfo;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.enums.OrgTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.model.MyWebContext;
import com.myapp.core.service.OrgService;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.util.EnumUtil;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年5月27日 
 * @system:
 *
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.组织管理",number="app.org")
@Controller
@RequestMapping("base/orgs")
public class OrgListController extends BaseTreeListController {
	@Resource
	public OrgService orgService;
	public AbstractBaseService getService() {
		return orgService;
	}
	
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("number"));
		cols.add(new ColumnModel("remark"));
		cols.add(new ColumnModel("shortCode"));
		cols.add(new ColumnModel("attachs"));
		cols.add(new ColumnModel("orgType",DataTypeEnum.ENUM,OrgTypeEnum.class));
		ColumnModel orgCol = new ColumnModel("parent",DataTypeEnum.F7,"name,number");
		cols.add(orgCol);
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
		String orgType = request.getParameter("orgType");
		if(!BaseUtil.isEmpty(orgType)){
			String[] orgTypes = orgType.split(",");
			List<OrgTypeEnum> params = new ArrayList<OrgTypeEnum>();
			for(String ot:orgTypes){
				params.add(EnumUtil.getEnum(OrgTypeEnum.class.getName(), ot));
			}
			query.add(Restrictions.in("orgType",params));
		}
		MyWebContext webCtx = getCurWebContext();
		if(webCtx!=null){
			BaseOrgInfo curOrg = webCtx.getCurOrg();
			if(curOrg!=null&&BaseUtil.isNotEmpty(curOrg.getLongNumber())){
				String cfln = curOrg.getLongNumber();
				List<String> params = new ArrayList<String>();
				params.add(cfln);
				while(cfln.indexOf("!")>0){
					cfln = cfln.substring(0,cfln.lastIndexOf("!"));
					params.add(cfln);
				}
				query.add(Restrictions.in("longNumber",params));
			}
		}
	}

	public Order getOrder() {
		return Order.asc("longNumber");
	}
	public String getEditUrl() {
		return "org/orgEdit";
	}
	public String getListUrl() {
		return "org/orgList";
	}
	public AbstractBaseService getTreeService() {
		return orgService;
	}
	
	public String getHeadTitle() {
		return "组织机构";
	}
	
	public List<ExcelExportEntity> getExportHeader() {
		List<ExcelExportEntity> entity = new ArrayList<ExcelExportEntity>();
		entity.add(new ExcelExportEntity("编码", "number"));
		entity.add(new ExcelExportEntity("名称", "name"));
		ExcelExportEntity orgType = new ExcelExportEntity("组织类型", "orgType");
		entity.add(orgType);
		entity.add(new ExcelExportEntity("简码", "shortCode"));
		entity.add(new ExcelExportEntity("上级编码", "parent_number"));
		entity.add(new ExcelExportEntity("上级名称", "parent_name"));
		ExcelExportEntity remark = new ExcelExportEntity("备注", "remark");
		remark.setWidth(80);
		remark.setWrap(true);
		entity.add(remark);
		return entity;
	}
	
	public static void main(String[] args){
		String s= "01!02!03";
		while(s.indexOf("!")>0){
			s = s.substring(0,s.lastIndexOf("!"));
			System.out.println(s);
		}
	}
	
}
