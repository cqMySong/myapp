package com.myapp.controller.ec.basedata.scheme;

import javax.annotation.Resource;

import com.myapp.core.annotation.AuthorAnn;
import com.myapp.core.annotation.PermissionItemAnn;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.*;
import com.myapp.core.exception.db.SaveException;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.model.WebDataModel;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.entity.ec.basedata.SchemeTypeInfo;
import com.myapp.entity.ec.engineering.SiteVisaOutDetailInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseBillEditController;
import com.myapp.entity.ec.basedata.ConstructionSchemeInfo;
import com.myapp.service.ec.basedata.ConstructionSchemeService;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@PermissionAnn(name="系统管理.现场管理.基础数据.施工方案",number="app.ec.basedata.scheme")
@Controller
@RequestMapping("ec/basedata/schemeedit")
public class ConstructionSchemeEditController extends BaseBillEditController {
	
	@Resource
	public ConstructionSchemeService constructionSchemeService;
	@Override
	public AbstractBaseService getService() {
		return constructionSchemeService;
	}
	@Override
	public Object createNewData() {
		ConstructionSchemeInfo info = new ConstructionSchemeInfo();
		info.setSchemeState(SchemeState.COMPANY);
		return info;
	}
	@Override
	public CoreBaseInfo getEntityInfo() {
		return new ConstructionSchemeInfo();
	}

	@Override
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("number"));
		cols.add(new ColumnModel("compileDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("lastFinishDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("schemeType", DataTypeEnum.F7,SchemeTypeInfo.class));
		cols.add(new ColumnModel("compiler", DataTypeEnum.F7,UserInfo.class));
		cols.add(new ColumnModel("schemeState",DataTypeEnum.ENUM, SchemeState.class));
		cols.add(new ColumnModel("createUser",DataTypeEnum.F7,UserInfo.class));
		cols.add(new ColumnModel("lastUpdateUser",DataTypeEnum.F7,UserInfo.class));
		cols.add(new ColumnModel("auditor",DataTypeEnum.F7,UserInfo.class));
		cols.add(new ColumnModel("createDate", DataTypeEnum.DATE));
		cols.add(new ColumnModel("auditDate", DataTypeEnum.DATE));
		cols.add(new ColumnModel("lastUpdateDate", DataTypeEnum.DATE));
		cols.add(new ColumnModel("project",DataTypeEnum.F7,ProjectInfo.class));
		return cols;
	}

	/*@PermissionItemAnn(name="导入保存",number="batchSave")
	@ResponseBody
	@RequestMapping(value="/batch/import",method= RequestMethod.POST)
	public WebDataModel batchImportSave(String batchImport) {
		WebDataModel webDataModel = new WebDataModel();
		try{
			constructionSchemeService.batchSave(batchImport,getCurUser());
			webDataModel.setStatusCode(STATUSCODE_SUCCESS);
		}catch (Exception e) {
			e.printStackTrace();
			setExceptionMesg(e.getMessage());
		}
		return webDataModel;
	}*/

	@PermissionItemAnn(name="导入保存",number="batchSave")
	@ResponseBody
	@RequestMapping(value="/batch/import",method= RequestMethod.POST)
	public WebDataModel batchImp(String structId,String structCode,String wbsIds){
		WebDataModel webDataModel = new WebDataModel();
		try{
			webDataModel = constructionSchemeService.batchSave(structId,getCurUser(),wbsIds);
			webDataModel.setStatusCode(STATUSCODE_SUCCESS);
		}catch (Exception e) {
			e.printStackTrace();
			setExceptionMesg(e.getMessage());
		}
		return webDataModel;
	}
}
