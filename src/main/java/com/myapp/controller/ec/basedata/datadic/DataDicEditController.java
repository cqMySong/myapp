package com.myapp.controller.ec.basedata.datadic;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseDataEditController;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.entity.ec.basedata.DataDictionaryInfo;
import com.myapp.enums.DataDicType;
import com.myapp.enums.UnitType;
import com.myapp.service.ec.basedata.DataDicService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

@PermissionAnn(name="系统管理.现场管理.基础数据.数据字典",number="app.ec.basedata.datadic")
@Controller
@RequestMapping("ec/basedata/datadic")
public class DataDicEditController extends BaseDataEditController {
	
	@Resource
	public DataDicService dataDicService;
	
	public AbstractBaseService getService() {
		return dataDicService;
	}

	public Object createNewData() {
		DataDictionaryInfo dataDictionaryInfo = new DataDictionaryInfo();
		return dataDictionaryInfo;
	}

	public CoreBaseInfo getEntityInfo() {
		return new DataDictionaryInfo();
	}
	
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("pinyinCode"));
		cols.add(new ColumnModel("dataDicType",DataTypeEnum.ENUM,DataDicType.class));
		return cols;
	}


}
