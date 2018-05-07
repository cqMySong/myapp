package com.myapp.controller.ec.basedata.safetemplate;

import com.myapp.core.annotation.AuthorAnn;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseDataListController;
import com.myapp.core.entity.PositionInfo;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.model.WebDataModel;
import com.myapp.core.util.WebUtil;
import com.myapp.entity.ec.basedata.ProBaseWbsInfo;
import com.myapp.service.ec.basedata.SafeTemplateDetailService;
import com.myapp.service.ec.basedata.SafeTemplateService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年6月7日 
 * @system:
 *安全、文明样板工作要点
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.基础数据.安全样板工作要点",number="app.ec.basedata.safetemplate")
@Controller
@RequestMapping("ec/basedata/safetemplates")
public class SafeTemplateListController extends BaseDataListController {
	
	@Resource
	public SafeTemplateService safeTemplateService;
	@Resource
	private SafeTemplateDetailService safeTemplateDetailService;
	@Override
	public AbstractBaseService getService() {
		return safeTemplateService;
	}
	@Override
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("branchBaseWbs", DataTypeEnum.F7, ProBaseWbsInfo.class));
		cols.add(new ColumnModel("subentry", DataTypeEnum.F7, ProBaseWbsInfo.class));
		return cols;
	}
	@Override
	public String getEditUrl() {
		return "ec/basedata/safetemplate/safeTemplateEdit";
	}
	@Override
	public String getListUrl() {
		return "ec/basedata/safetemplate/safeTemplateList";
	}

	@AuthorAnn(doLongin=true,doPermission=false)
	@RequestMapping("/jobrequire")
	@ResponseBody
	public WebDataModel showQualityTemplateJobRequire(String safeTemplateId) {
		if(!StringUtils.isEmpty(safeTemplateId)){
			this.data = safeTemplateDetailService.queryBySafeTemplateId(safeTemplateId);
		}
		return super.ajaxModel();
	}

	@AuthorAnn(doPermission=false)
	@RequestMapping(value="/show/tree")
	@ResponseBody
	public WebDataModel showTree(String targetId) {
		try{
			init();
			List<Map<String,Object>> result = safeTemplateService.queryProjectSafeTemplate(WebUtil.UUID_ReplaceID(targetId));
			Map<String,List<Map<String,Object>>> proWbsIdGroup = new HashMap<>();
			for(Map<String,Object> wbsMap:result){
				if(proWbsIdGroup.get(wbsMap.get("proWbsId").toString())==null){
					proWbsIdGroup.put(wbsMap.get("proWbsId").toString(),new ArrayList<Map<String,Object>>());
				}
				proWbsIdGroup.get(wbsMap.get("proWbsId").toString()).add(wbsMap);
			}
			List<Map<String,Object>> resultTree = safeTemplateService.queryProjectSafeTemplateTree(WebUtil.UUID_ReplaceID(targetId));
			List<Map<String,Object>> rootList = new ArrayList<>();
			List<Map<String,Object>> trees = new ArrayList<Map<String,Object>>();
			Map<String, Map<String,Object>> nMap = new HashMap<String, Map<String,Object>>();
			Map<String,Object> parent = null;
			Map<String,Object> node = null;
			for (Map<String,Object> wbsTree : resultTree) {
				node = new HashMap<>();
				node.put("name",wbsTree.get("typeName"));
				node.put("id",wbsTree.get("id"));
				node.put("children",proWbsIdGroup.get(wbsTree.get("id")));
				nMap.put(wbsTree.get("id").toString(), node);
				parent = nMap.get(wbsTree.get("parentId").toString());
				if (null != parent) {
					if (null == parent.get("children")) {
						parent.put("children",new ArrayList<Map<String,Object>>());
					}
					((ArrayList<Map<String,Object>>)parent.get("children")).add(node);
				}
				if ("0".equals(wbsTree.get("parentId").toString())) {
					trees.add(node);
				}
			}
			Map root = new HashMap();
			root.put("id", "");
			root.put("name", "安全样板标准");
			root.put("children", trees);
			rootList.add(root);
			data = rootList;
		}catch(Exception e){
			e.printStackTrace();
			setErrorMesg(e.getMessage());
		}
		return ajaxModel();
	}
}
