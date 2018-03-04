package com.myapp.controller.ec.basedata.scheme;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.AuthorAnn;
import com.myapp.core.annotation.PermissionItemAnn;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.BillState;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.enums.PermissionTypeEnum;
import com.myapp.core.exception.db.QueryException;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.model.WebDataModel;
import com.myapp.core.util.BaseUtil;
import com.myapp.core.util.WebUtil;
import com.myapp.entity.ec.basedata.SchemeTypeInfo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseListController;
import com.myapp.service.ec.basedata.ConstructionSchemeService;

import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@PermissionAnn(name="系统管理.现场管理.基础数据.施工方案",number="app.ec.basedata.scheme")
@Controller
@RequestMapping("ec/basedata/schemelist")
public class ConstructionSchemeListController extends BaseListController {
	
	@Resource
	public ConstructionSchemeService constructionSchemeService;
	@Override
	public AbstractBaseService getService() {
		return constructionSchemeService;
	}
	@Override
	public String getEditUrl() {
		return "ec/basedata/scheme/constructionSchemeEdit";
	}
	@Override
	public String getListUrl() {
		return "ec/basedata/scheme/constructionSchemeList";
	}

	@AuthorAnn(doLongin=false,doPermission=false)
	public WebDataModel toList() {
		init();
		String search = request.getParameter("search");
		String projectId = "-1";
		Map<String,Object> params = new HashMap<>();
		if(!BaseUtil.isEmpty(search)) {
			Map searchMap = JSONObject.parseObject(search, new HashMap().getClass());
			Object objTree = searchMap.get("tree");
			if(objTree!=null){
				Map treeMap = JSONObject.parseObject(objTree.toString(), new HashMap().getClass());
				Object idObj = treeMap.get("id");
				Object type = treeMap.get("type");
				if(type!=null&&idObj!=null){
					if("project".equals(type.toString())){
						projectId = idObj.toString();
					}
				}
			}
			if(searchMap.get("key")!=null){
				params.put("key",searchMap.get("key").toString());
				params.put("value",searchMap.get("value").toString());
			}
		}
		params.put("projectId",projectId);
		try {
			this.data = constructionSchemeService.queryConstructionSchemeLedger(getCurPage(),getPageSize(),params);
		} catch (QueryException e) {
			e.printStackTrace();
		}
		return ajaxModel();
	}


	@PermissionItemAnn(name="施工方案导入",number="btnImport",type= PermissionTypeEnum.FUNCTION)
	@RequestMapping("/batch/import")
	public ModelAndView forwardBatchImport(){
		Map params = new HashMap();
		toListUIParams(params);
		params.put("uiCtx",WebUtil.UUID_ReplaceID(params.get("uiCtx").toString()));
		return toPage("ec/basedata/scheme/constructionSchemeBatchImport", params);
	}
}
