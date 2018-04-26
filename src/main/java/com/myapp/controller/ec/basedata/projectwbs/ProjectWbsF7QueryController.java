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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.AuthorAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BasePageListController;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.model.PageModel;
import com.myapp.core.model.WebDataModel;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.util.EnumUtil;
import com.myapp.core.util.WebUtil;
import com.myapp.entity.ec.basedata.ProStructureInfo;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.entity.ec.basedata.ProjectWbsInfo;
import com.myapp.enums.ec.ProWbsType;
import com.myapp.service.ec.basedata.ProjectWbsService;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年12月18日 
 * @system: 工程项目分解结构
 *-----------MySong---------------
 */
@Controller
@RequestMapping("ec/basedata/proWbsF7")
public class ProjectWbsF7QueryController extends BasePageListController {
	
	@Resource
	public ProjectWbsService projectWbsService;
	public AbstractBaseService getService() {
		return projectWbsService;
	}
	
	
	@AuthorAnn(doPermission=false,doLongin = true)
	@RequestMapping("/f7show")
	public ModelAndView f7show(){
		return toPage("ec/basedata/projectwbs/proWbsTreeQuery", getUiCtx());
	}
	
	//项目单位工程结构树
	@AuthorAnn(doLongin=true,doPermission=false)
	@RequestMapping(value="/treeData")
	@ResponseBody
	public WebDataModel treeData() {
		try{
			Object proId = request.getParameter("projectId");
			StringBuffer sql = new StringBuffer();
			sql.append(" select t.fid as id,t.fnumber as number,t.fname as name,'' as parentId");
			sql.append(" ,'01' as longNumber,'project' as type");
			sql.append(" from t_ec_project as t ");
			if(BaseUtil.isNotEmpty(proId)){
				proId  = WebUtil.UUID_ReplaceID(proId.toString());
				sql.append(" where t.fid ='"+proId+"'");
			}
			
			List treeDatas = projectWbsService.executeSQLQuery(sql.toString(), null);
			if(treeDatas!=null){
				//添加项目单位工程
				sql = new StringBuffer();
				sql.append(" select a.fid as id,a.fnumber as number,a.fname as name,case when a.fprentid is null then a.fprojectId else a.fprentid end as parentId,a.flongnumber as longNumber,'proStructure' as type");
				sql.append(" from t_ec_proStructure as a");
				if(BaseUtil.isNotEmpty(proId)){
					proId  = WebUtil.UUID_ReplaceID(proId.toString());
					sql.append(" where a.fprojectId ='"+proId+"'");
				}
				sql.append(" order by a.flongnumber asc");
				List orgStruct = projectWbsService.executeSQLQuery(sql.toString(), null);
				if(orgStruct!=null&&orgStruct.size()>0){
					treeDatas.addAll(orgStruct);
				}
				data = treeDatas;
			}
		}catch(Exception e){
			setErrorMesg(e.getMessage());
		}
		return ajaxModel();
	}
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("number"));
		cols.add(new ColumnModel("displayName"));
		cols.add(new ColumnModel("proStruct",DataTypeEnum.F7,ProStructureInfo.class));
		cols.add(new ColumnModel("project",DataTypeEnum.F7,ProjectInfo.class));
		cols.add(new ColumnModel("parent",DataTypeEnum.F7,ProjectWbsInfo.class,"id,number"));
		cols.add(new ColumnModel("wbsType",DataTypeEnum.ENUM,ProWbsType.class));
		return cols;
	}
	
	public void executeQueryParams(Criteria query) {
		super.executeQueryParams(query);
		query.add(Restrictions.eq("enabled", Boolean.TRUE));
		String serach = request.getParameter("search");
		SimpleExpression se = Restrictions.eq("id","xyz");
		if(!BaseUtil.isEmpty(serach)){
			Map searchMap = JSONObject.parseObject(serach, new HashMap().getClass());
			Object wbsType = searchMap.get("wbsType");
			if(wbsType!=null){
				String wbsTypeStr = wbsType.toString();
				if(!StringUtils.isEmpty(wbsTypeStr)){
					String[] wbsTypeArr = wbsTypeStr.split(",");
					Object[] wbsTypeEnum = new Object[wbsTypeArr.length];
					for(int i=0;i<wbsTypeArr.length;i++){
						wbsTypeEnum[i] = EnumUtil.getEnum(ProWbsType.class.getName(),wbsTypeArr[i]);
					}
					query.add(Restrictions.in("wbsType",wbsTypeEnum));
				}
			}
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
	
	
	@AuthorAnn(doPermission=false,doLongin=true)
	@RequestMapping(value="/f7Data")
	@ResponseBody
	public WebDataModel toList() {
		try{
			Criteria query = initQueryCriteria();
			executeQueryParams(query);
			List<Order> orders = getOrders();
			if(query!=null&&orders!=null&&orders.size()>0){
				for(Order order:orders){
					query.addOrder(order);
				}
			}
			PageModel pm = getService().toPageQuery(query, getProjectionList(), getCurPage(), getPageSize());
			if(pm!=null&&pm.getDatas()!=null){
				List<Map> datas = pm.getDatas();
				String wtEns = ProWbsType.class.getName();
				for(int i=0;i<datas.size();i++){
					Map rowData = datas.get(i);
					if(rowData!=null){
						Object wbsTypeObj = rowData.get("wbsType");
						if(wbsTypeObj!=null){
							rowData.put("wbsType", EnumUtil.getEnum(wtEns, wbsTypeObj.toString()).getName());
						}
					}
				}
				this.data = pm;
			}
		} catch (Exception e) {
			e.printStackTrace();
			setExceptionMesg(e.getMessage());
		}
		return ajaxModel();
	}
	
	public String getUIWinTitle() {
		return "项目分部分项工程";
	}
}
