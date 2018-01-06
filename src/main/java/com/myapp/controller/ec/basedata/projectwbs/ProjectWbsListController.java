package com.myapp.controller.ec.basedata.projectwbs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseDataListController;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.model.WebDataModel;
import com.myapp.core.util.BaseUtil;
import com.myapp.entity.ec.basedata.ProStructureInfo;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.entity.ec.basedata.ProjectWbsInfo;
import com.myapp.enums.ec.ProWbsType;
import com.myapp.service.ec.basedata.ProjectService;
import com.myapp.service.ec.basedata.ProjectWbsService;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年12月03日 
 * @system:
 * 工程项目分解结构
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.基础数据.工程项目分解结构",number="app.ec.basedata.prowbs")
@Controller
@RequestMapping("ec/basedata/prowbss")
public class ProjectWbsListController extends BaseDataListController {
	
	@Resource
	public ProjectWbsService projectWbsService;
	public AbstractBaseService getService() {
		return projectWbsService;
	}
	
	@Resource
	public ProjectService projectService;
	

	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("displayName"));
		cols.add(new ColumnModel("proStruct",DataTypeEnum.F7,ProStructureInfo.class));
		cols.add(new ColumnModel("project",DataTypeEnum.F7,ProjectInfo.class));
		cols.add(new ColumnModel("parent",DataTypeEnum.F7,ProjectWbsInfo.class,"id,number"));
		cols.add(new ColumnModel("wbsType",DataTypeEnum.ENUM,ProWbsType.class));
		return cols;
	}
	public void executeQueryParams(Criteria query) {
		super.executeQueryParams(query);
		String serach = request.getParameter("search");
		SimpleExpression se = Restrictions.eq("id","xyz");
		if(!BaseUtil.isEmpty(serach)){
			Map searchMap = JSONObject.parseObject(serach, new HashMap().getClass());
			Object objTree = searchMap.get("tree");
			if(objTree!=null){
				Map treeMap = JSONObject.parseObject(objTree.toString(), new HashMap().getClass());
				Object idObj = treeMap.get("id");
				Object type = treeMap.get("type");
				if(type!=null&&idObj!=null){
					if("project".equals(type.toString())){
						se = Restrictions.eq("project.id", idObj.toString());
					}else if("proStructure".equals(type.toString())){
						Object lnObj = treeMap.get("longNumber");
						se = Restrictions.like("proStruct.longNumber", lnObj.toString()+"%");
					}
				}
			}
		}
		query.add(se);
	}
	public List<Order> getOrders() {
		List<Order> orders = new ArrayList<Order>();
		orders.add(Order.asc("proStruct.longNumber"));
		orders.add(Order.asc("longNumber"));
		return orders;
	}
	
	public String getEditUrl() {
		return "ec/basedata/projectwbs/proWbsEdit";
	}

	public String getListUrl() {
		return "ec/basedata/projectwbs/proWbsList";
	}

	//工程项目》单位工程》分解结构（分部，子分部，分项）
	@RequestMapping(value="/projectWbsTree")
	@ResponseBody
	public WebDataModel treeData() {
		try{
			Map params = new HashMap();
			List orgList = projectService.getProjectTreeData(params);//项目组织树
			if(orgList!=null&&orgList.size()>0){
				//添加项目单位工程
				StringBuffer sql = new StringBuffer();
				sql.append(" select a.fid as id,a.fnumber as number,a.fname as name"
						+ ",case when a.fprentid is null then a.fprojectId else a.fprentid end as parentId"
						+ ",a.flongnumber as longNumber,'proStructure' as type");
				sql.append(" from t_ec_proStructure as a");
				sql.append(" left join t_ec_project as b on b.fid = a.fprojectId");
				sql.append(" order by a.flongnumber asc");
				List orgStruct = projectService.executeSQLQuery(sql.toString(), null);
				if(orgStruct!=null&&orgStruct.size()>0){
					orgList.addAll(orgStruct);
				}
				sql = new StringBuffer();
				sql.append(" select a.fid as id,a.fnumber as number,a.fname as name");
				sql.append(",case when a.fwbstype = 'FBGC' then a.fproStructId else a.fprentid end as parentId");
				sql.append(",a.flongnumber as longNumber,a.fwbstype as type");
				sql.append(" from t_ec_projectwbs as a");
				sql.append(" where a.fwbstype in('FBGC','ZFBGC','FXGC')");//只是查询分部工程，子分部工程，分项工程
				List proSubList = projectWbsService.executeSQLQuery(sql.toString(),null);
				if(proSubList!=null&&proSubList.size()!=0){
					orgList.addAll(proSubList);
				}
				data = orgList;
			}
		}catch(Exception e){
			setErrorMesg(e.getMessage());
		}
		return ajaxModel();
	}
}
