package com.myapp.controller.ec.basedata.batchtest;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseDataEditController;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.entity.ec.basedata.BatchTestInfo;
import com.myapp.enums.ec.TestGroup;
import com.myapp.service.ec.basedata.BatchTestService;

@PermissionAnn(name="系统管理.现场管理.基础数据.检验批划分",number="app.ec.basedata.batchtest")
@Controller
@RequestMapping("ec/basedata/batchtest")
public class BatchTestEditController extends BaseDataEditController {
	
	@Resource
	public BatchTestService batchTestService;

	public AbstractBaseService getService() {
		return batchTestService;
	}
	
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("testGroup",DataTypeEnum.ENUM,TestGroup.class));
		cols.add(new ColumnModel("content"));
		return cols;
	}

	public Object createNewData() {
		BatchTestInfo info = new BatchTestInfo();
		return info;
	}

	public CoreBaseInfo getEntityInfo() {
		return new BatchTestInfo();
	}

}
