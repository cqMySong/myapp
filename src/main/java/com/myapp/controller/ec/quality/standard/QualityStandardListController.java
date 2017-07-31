package com.myapp.controller.ec.quality.standard;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseListController;
import com.myapp.core.util.BaseUtil;
import com.myapp.service.ec.quality.QualityStandardService;


@PermissionAnn(name="系统管理.现场管理.质量管理.质量交底",number="app.ec.quality.qualitystandard")
@Controller
@RequestMapping("ec/quality/standard/qualityStandardList")
public class QualityStandardListController extends BaseListController {
	
	@Resource
	public QualityStandardService qualityStandardService;
	public AbstractBaseService getService() {
		return qualityStandardService;
	}

	public String getEditUrl() {
		return "ec/quality/standard/qualityStandardEdit";
	}

	public String getListUrl() {
		return "ec/quality/standard/qualityStandardList";
	}

	public void executeQueryParams(Criteria query) {
		super.executeQueryParams(query);
		String serach = request.getParameter("search");
		String projectId = "xyz";
		if(!BaseUtil.isEmpty(serach)){
			Map searchMap = JSONObject.parseObject(serach, new HashMap().getClass());
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
		}
		query.add(Restrictions.eq("project.id",projectId));
	}
}
