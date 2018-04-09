package com.myapp.controller.ec.basedata.constructiontemplate;
import com.alibaba.fastjson.JSONObject;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseDataListController;
import com.myapp.core.entity.basedate.DataGroupInfo;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.util.BaseUtil;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.service.ec.basedata.ConstructionTemplateService;
import com.myapp.service.ec.basedata.ProConstructionTemplateService;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年12月11日 
 * @system: 项目级资料目录 项目施工样板清单
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.基础数据.项目施工样板清单",number="app.ec.basedata.proconstructiontemplate")
@Controller
@RequestMapping("ec/basedata/proconstructiontemplates")
public class ProConstructionTemplateListController extends BaseDataListController {

	@Resource
	public ProConstructionTemplateService proConstructionTemplateService;
	@Override
	public String getEditUrl() {
		return "ec/basedata/constructiontemplate/proConstructionTemplateEdit";
	}

	@Override
	public String getListUrl() {
		return "ec/basedata/constructiontemplate/proConstructionTemplateList";
	}

	@Override
	public AbstractBaseService getService() {
		return this.proConstructionTemplateService;
	}
	@Override
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
					}
				}
			}
		}
		query.add(se);
	}
	@Override
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = new ArrayList<ColumnModel>();
		cols.add(new ColumnModel("id",DataTypeEnum.PK));
		cols.add(new ColumnModel("project",DataTypeEnum.F7,ProjectInfo.class));
		cols.add(new ColumnModel("attachs",DataTypeEnum.INT));
		cols.add(new ColumnModel("remark"));
		cols.add(new ColumnModel("content"));
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("number"));
		return cols;
	}
}
