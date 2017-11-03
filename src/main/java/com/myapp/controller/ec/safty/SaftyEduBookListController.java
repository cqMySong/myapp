package com.myapp.controller.ec.safty;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseListController;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.enums.Sex;
import com.myapp.core.model.ColumnModel;
import com.myapp.service.ec.safty.SaftyEduBookService;

@PermissionAnn(name="系统管理.现场管理.安全管理.三级安全教育花名册",number="app.ec.safty.saftyedubook")
@Controller
@RequestMapping("ec/safty/saftyedubooks")
public class SaftyEduBookListController extends BaseListController {
	
	@Resource
	SaftyEduBookService saftyEduBookService;
	public AbstractBaseService getService() {
		return saftyEduBookService;
	}
	
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("sex",DataTypeEnum.ENUM,Sex.class));
		cols.add(new ColumnModel("age"));
		cols.add(new ColumnModel("workType"));
		cols.add(new ColumnModel("homeAddress"));
		cols.add(new ColumnModel("idCardNo"));
		cols.add(new ColumnModel("companyDate",DataTypeEnum.DATE));
		
		return cols;
	}

	public String getEditUrl() {
		return "ec/safty/saftyedubook/saftyEduBookEdit";
	}

	public String getListUrl() {
		return "ec/safty/saftyedubook/saftyEduBookList";
	}

	
}
