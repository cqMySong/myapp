package com.myapp.controller.ec.basedata.batchtest;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseEditController;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.entity.ec.basedata.ProBaseWbsInfo;
import com.myapp.entity.ec.basedata.ProBatchTestInfo;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.service.ec.basedata.ProBatchTestService;
/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年12月18日 
 * @system:
 * 项目检验批划分
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.基础数据.项目检验批划分",number="app.ec.basedata.probatchtest")
@Controller
@RequestMapping("ec/basedata/probatchtest")
public class ProBatchTestEditController extends BaseEditController {
	
	@Resource
	public ProBatchTestService proBatchTestService;
	public AbstractBaseService getService() {
		return proBatchTestService;
	}
	
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("proBaseWbs",DataTypeEnum.F7,ProBaseWbsInfo.class));
		cols.add(new ColumnModel("project",DataTypeEnum.F7,ProjectInfo.class));
		cols.add(new ColumnModel("content"));
		cols.add(new ColumnModel("number"));
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("remark"));
		return cols;
	}

	public Object createNewData() {
		ProBatchTestInfo info = new ProBatchTestInfo();
		return info;
	}

	public CoreBaseInfo getEntityInfo() {
		return new ProBatchTestInfo();
	}

}
