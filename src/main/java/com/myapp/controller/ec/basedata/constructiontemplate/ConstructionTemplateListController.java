package com.myapp.controller.ec.basedata.constructiontemplate;
import com.myapp.core.annotation.AuthorAnn;
import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseDataListController;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.enums.UnitClass;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.model.WebDataModel;
import com.myapp.entity.ec.basedata.ProBaseWbsInfo;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.service.ec.basedata.ConstructionTemplateService;
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
 * @author mySong @date 2017年12月11日 
 * @system: 项目级资料目录 施工样板清单
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.基础数据.施工样板清单",number="app.ec.basedata.constructiontemplate")
@Controller
@RequestMapping("ec/basedata/constructiontemplates")
public class ConstructionTemplateListController extends BaseDataListController {

	@Resource
	public ConstructionTemplateService constructionTemplateService;
	@Override
	public String getEditUrl() {
		return "ec/basedata/constructiontemplate/constructionTemplateEdit";
	}

	@Override
	public String getListUrl() {
		return "ec/basedata/constructiontemplate/constructionTemplateList";
	}

	@Override
	public AbstractBaseService getService() {
		return this.constructionTemplateService;
	}

	@Override
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("engineeringProject"));
		cols.add(new ColumnModel("templateType"));
		cols.add(new ColumnModel("content"));
		return cols;
	}

	@AuthorAnn(doPermission=false)
	@RequestMapping(value="/treeData")
	@ResponseBody
	public WebDataModel treeData() {
		List<Map<String,Object>> rootList = new ArrayList<>();
		List items = constructionTemplateService.queryAllConstructionTemplate();
		Map root = new HashMap();
		root.put("id", "");
		root.put("name", "施工样板种类");
		root.put("open", true);
		root.put("children", items);
		rootList.add(root);
		data = rootList;
		return ajaxModel();
	}
}
