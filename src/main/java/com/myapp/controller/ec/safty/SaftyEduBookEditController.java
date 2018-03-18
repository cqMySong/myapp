package com.myapp.controller.ec.safty;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.entity.CoreInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseBillEditController;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.enums.Sex;
import com.myapp.core.model.ColumnModel;
import com.myapp.entity.ec.safty.SaftyEduBookInfo;
import com.myapp.service.ec.safty.SaftyEduBookService;

@PermissionAnn(name="系统管理.现场管理.安全管理.三级安全教育花名册",number="app.ec.safe.saftyedubook")
@Controller
@RequestMapping("ec/safty/saftyedubook")
public class SaftyEduBookEditController extends BaseBillEditController{

	@Resource
	public SaftyEduBookService saftyEduBookService;
	public AbstractBaseService getService() {
		return saftyEduBookService;
	}
	
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("sex",DataTypeEnum.ENUM, Sex.class));
		
		cols.add(new ColumnModel("age",DataTypeEnum.INT));
		cols.add(new ColumnModel("homeAddress"));
		cols.add(new ColumnModel("workType"));
		
		cols.add(new ColumnModel("companyDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("idCardNo"));
		return cols;
	}

	public Object createNewData() {
		SaftyEduBookInfo info = new SaftyEduBookInfo();
		return info;
	}

	public CoreInfo getEntityInfo() {
		return new SaftyEduBookInfo();
	}
	
	protected boolean verifyInput(Object editData) {
		return true;
	}
}
