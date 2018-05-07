package com.myapp.controller.ec.basedata.qualitytemplate;

import com.myapp.core.annotation.AuthorAnn;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseDataListController;
import com.myapp.core.entity.PositionInfo;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.model.WebDataModel;
import com.myapp.core.util.EnumUtil;
import com.myapp.core.util.WebUtil;
import com.myapp.entity.ec.basedata.ProBaseWbsInfo;
import com.myapp.enums.ec.SkillType;
import com.myapp.service.ec.basedata.QualityTemplateDetailService;
import com.myapp.service.ec.basedata.QualityTemplateService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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
 *质量样板工作要点
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.基础数据.质量样板工作要点",number="app.ec.basedata.qualitytemplate")
@Controller
@RequestMapping("ec/basedata/qualitytemplates")
public class QualityTemplateListController extends BaseDataListController {
	
	@Resource
	public QualityTemplateService qualityTemplateService;
	@Resource
	private QualityTemplateDetailService qualityTemplateDetailService;
	@Override
	public AbstractBaseService getService() {
		return qualityTemplateService;
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
		return "ec/basedata/qualitytemplate/qualityTemplateEdit";
	}
	@Override
	public String getListUrl() {
		return "ec/basedata/qualitytemplate/qualityTemplateList";
	}

	@AuthorAnn(doLongin=true,doPermission=false)
	@RequestMapping("/jobrequire")
	@ResponseBody
	public WebDataModel showQualityTemplateJobRequire(String qualityTemplateId) {
		if(!StringUtils.isEmpty(qualityTemplateId)){
			this.data = qualityTemplateDetailService.queryByQualityTemplateId(qualityTemplateId);
		}
		return super.ajaxModel();
	}

	@AuthorAnn(doPermission=false)
	@RequestMapping(value="/show/tree")
	@ResponseBody
	public WebDataModel showTree(String targetId) {
		try{
			init();
			List<Map<String,Object>> result = qualityTemplateService.queryProjectQualityTemplate(WebUtil.UUID_ReplaceID(targetId));
			Map<String,List<Map<String,Object>>> proWbsIdGroup = new HashMap<>();
			for(Map<String,Object> wbsMap:result){
				if(proWbsIdGroup.get(wbsMap.get("proWbsId").toString())==null){
					proWbsIdGroup.put(wbsMap.get("proWbsId").toString(),new ArrayList<Map<String,Object>>());
				}
				proWbsIdGroup.get(wbsMap.get("proWbsId").toString()).add(wbsMap);
			}
			List<Map<String,Object>> resultTree = qualityTemplateService.queryProjectQualityTemplateTree(WebUtil.UUID_ReplaceID(targetId));
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
			root.put("name", "质量样板标准");
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
