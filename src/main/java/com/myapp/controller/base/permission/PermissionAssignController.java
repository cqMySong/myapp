package com.myapp.controller.base.permission;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.CoreBaseController;
import com.myapp.core.entity.BaseOrgInfo;
import com.myapp.core.entity.PositionInfo;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.enums.OrgTypeEnum;
import com.myapp.core.exception.db.QueryException;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.model.WebDataModel;
import com.myapp.core.service.OrgService;
import com.myapp.core.service.PermissionAssignService;
import com.myapp.core.service.PermissionService;
import com.myapp.core.service.UserService;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.util.WebUtil;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年9月2日 
 * @system:
 *
 *-----------MySong---------------
 */
@Controller
@RequestMapping("base/permissionAssign")
public class PermissionAssignController extends CoreBaseController {
	private ProjectionList treeProjections;
	@Resource
	public PermissionService permissionService;
	public AbstractBaseService getService() {
		return permissionService;
	}
	
	@Resource
	public PermissionAssignService permissionAssignService;
	@Resource
	public UserService userService;
	@Resource
	public OrgService orgService;
	
	@RequestMapping("/show")
	public ModelAndView toView(){
		init();
		Map params = new HashMap();
		packageUIParams(params);
		String targetId = getTargetId();
		params.put("targetId",targetId);
		String target = request.getParameter("target");
		target = BaseUtil.isEmpty(target)?"BLANK":target.toUpperCase();
		params.put("target",target);
		if(BaseUtil.isNotEmpty(targetId)){
			if(target.equals("USER")){//权限分配到人
				List<PositionInfo> ps = userService.queryPosition(targetId);
				if(ps!=null&&ps.size()>0){
					Map proOrgIds = new HashMap();
					List<Map<String,String>> orgs = new ArrayList<Map<String,String>>();
					for(PositionInfo p:ps){
						if(p!=null){
							BaseOrgInfo org = p.getOrg();
							if(org!=null){
								BaseOrgInfo projectOrg = orgService.getCurOrg(org.getId(), OrgTypeEnum.PROJECTORG);
								if(projectOrg!=null){
									if(!proOrgIds.containsKey(projectOrg.getId())){
										Map<String,String> orgMap = new HashMap<String, String>();
										orgMap.put("id", projectOrg.getId());
										orgMap.put("name", projectOrg.getName());
										orgs.add(orgMap);
										proOrgIds.put(projectOrg.getId(),projectOrg.getId());
									}
								}
							}
						}
					}
					if(orgs.size()>0){
						params.put("orgs", JSONObject.toJSON(orgs));
					}
				}
			}
		}
		return toPage(getAssignURL(), params);
	}
	
	public void packageUIParams(Map params){
		String ciCtx = request.getParameter("uiCtx");
		if(!BaseUtil.isEmpty(ciCtx)){
			ciCtx = ciCtx.replaceAll("\'", "\"");
			Map uiCtx = JSONObject.parseObject(ciCtx, new HashMap().getClass());
			params.put("uiCtx", ciCtx);
		}
	}
	
	public List<ColumnModel> getTreeDataBinding(){
		List<ColumnModel> cols = new ArrayList<ColumnModel>();
		cols.add(new ColumnModel("id",DataTypeEnum.PK));
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("number"));
		cols.add(new ColumnModel("isLeaf"));
		cols.add(new ColumnModel("longNumber"));
		ColumnModel orgCol = new ColumnModel("parent",DataTypeEnum.F7,"id,name");
		cols.add(orgCol);
		return cols;
	}
	
	protected Criteria initTreeQueryCriteria() throws QueryException{
		Criteria criter = getService().initQueryCriteria();
		treeProjections = Projections.projectionList();
		List<ColumnModel> cols = getTreeDataBinding();
		for(ColumnModel cm:cols){
			packageCriteria(criter,"",treeProjections,cm);
		}
		return criter;
	}
	
	public void executeTreeQueryParams(Criteria query) {
		
	}
	
	@RequestMapping(value="/tree")
	@ResponseBody
	public WebDataModel getSysTree(){
		try {
			init();
			Criteria query = initTreeQueryCriteria();
			executeTreeQueryParams(query);
			Order order = Order.asc("longNumber");
			if(order!=null){
				query.addOrder(order);
			}
			String sourceId = getTargetId();
			String orgId = getOrgId();
			Map<String,String> hasAssignMap = new HashMap<String,String>();
			if(!BaseUtil.isEmpty(sourceId)){
				List hasAsign = null;
				if(BaseUtil.isNotEmpty(orgId)){
					hasAsign = permissionAssignService.getHasAssignUserOrgPermissions(sourceId, orgId);
				}else{
					hasAsign = permissionAssignService.getHasAssignPermissions(sourceId);
				}
				if(hasAsign!=null&&hasAsign.size()>0){
					for(int i=0;i<hasAsign.size();i++){
						Map thisMap = (Map)hasAsign.get(i);
						String pid = (String) thisMap.get("pid");
						hasAssignMap.put(pid, pid);
					}
				}
			}
			List treeData = excueteTreeQuery(query);
			if(treeData!=null&&treeData.size()>0){
				for(int i=0;i<treeData.size();i++){
					Map treeNodeMap = (Map) treeData.get(i);
					boolean hasAssign = hasAssignMap.containsKey(treeNodeMap.get("id"));
					treeNodeMap.put("nocheck", hasAssign);
					treeNodeMap.put("checked", hasAssign);
				}
			}
			data = treeData;
		} catch (Exception e) {
			e.printStackTrace();
			setExceptionMesg(e.getMessage());
		}
		return ajaxModel();
	}
	
	@RequestMapping(value="/assign")
	@ResponseBody
	public WebDataModel assign(){
		try {
			init();
			String msg = permissionAssignService.toAssign(getTargetId(), getPermissionIds(),getOrgId());
			if(!BaseUtil.isEmpty(msg)){
				setInfoMesg(msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
			setExceptionMesg(e.getMessage());
		}
		return ajaxModel();
	}
	
	@RequestMapping(value="/unassign")
	@ResponseBody
	public WebDataModel unAssign(){
		try {
			init();
			permissionAssignService.unAssign(getPermissionIds());
		} catch (Exception e) {
			e.printStackTrace();
			setExceptionMesg(e.getMessage());
		}
		return ajaxModel();
	}
	
	public List excueteTreeQuery(Criteria query){
		if(treeProjections!=null){
			query.setProjection(treeProjections);
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}
	@RequestMapping(value="/hasAssign")
	@ResponseBody
	public WebDataModel getHasAssignData(){
		init();
		data = permissionAssignService.getHasAssignPermissions(getTargetId(),getPermName(),null);
		return ajaxModel();
	}
	/**
	 * 分配用户的岗位权限 
	 */
	@RequestMapping(value="/assignPosition")
	@ResponseBody
	public WebDataModel assignPosition(){
		try {
			init();
			String msg = permissionAssignService.assignUserPosition(getTargetId());
			if(!BaseUtil.isEmpty(msg)){
				setInfoMesg(msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
			setExceptionMesg(e.getMessage());
		}
		return ajaxModel();
	}
	public String getAssignURL(){
		return "permission/permissionAssign";
	}
	
	public String getOrgId(){
		return WebUtil.UUID_ReplaceID(request.getParameter("orgId"));
	}
	public String getTargetId() {
		return WebUtil.UUID_ReplaceID(request.getParameter("targetId"));
	}
	public String getPermName() {
		return WebUtil.UUID_ReplaceID(request.getParameter("permName"));
	}

	public String getPermissionIds() {
		return WebUtil.UUID_ReplaceID(request.getParameter("permissionIds"));
	}
	
}
