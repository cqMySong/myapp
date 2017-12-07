package com.myapp.controller.ec.basedata.resouce;

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
import com.myapp.entity.ec.basedata.ResourceItemInfo;
import com.myapp.enums.ec.ResourceType;
import com.myapp.service.ec.basedata.ResourceItemService;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年5月20日 
 * @system:
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.基础数据.资料明细",number="app.ec.basedata.resourceitem")
@Controller
@RequestMapping("ec/basedata/resourceitem")
public class ResourceItemEditController extends BaseDataEditController{
	@Resource
	public ResourceItemService resourceItemService;
	public AbstractBaseService getService() {
		return resourceItemService;
	}

	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("resourceType",DataTypeEnum.ENUM,ResourceType.class));
		return cols;
	}
	
	public Object createNewData() {
		return new ResourceItemInfo();
	}

	public CoreBaseInfo getEntityInfo() {
		return new ResourceItemInfo();
	}
}
