package com.myapp.controller.ec.proresourceitem;

import java.util.List;

import javax.annotation.Resource;

import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseEditController;
import com.myapp.core.enums.BaseMethodEnum;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.entity.ec.basedata.ProResourceItemInfo;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.entity.ec.basedata.ResourceItemInfo;
import com.myapp.enums.ec.ResourceType;
import com.myapp.service.ec.basedata.ProResourceItemService;
/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2018年1月1日 
 * @system: 项目级资料目录（一级）
 *-----------MySong---------------
 */
public abstract class ProResourceItemBaseEditController extends BaseEditController {
	
	@Resource
	public ProResourceItemService proResourceItemService;
	public AbstractBaseService getService() {
		return proResourceItemService;
	}
	
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("number"));
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("project",DataTypeEnum.F7,ProjectInfo.class));
		cols.add(new ColumnModel("resourceItem",DataTypeEnum.F7,ResourceItemInfo.class));
		cols.add(new ColumnModel("remark"));
		return cols;
	}
	
	public abstract ResourceType getResourceType();
	protected void beforeStoreData(BaseMethodEnum bme, Object editData) {
		super.beforeStoreData(bme, editData);
		if(editData!=null&&editData instanceof ProResourceItemInfo){
			ProResourceItemInfo prdInfo = (ProResourceItemInfo) editData;
			prdInfo.setResourceType(getResourceType());
		}
	}
	
	protected boolean verifyInput(Object editData) {
		return super.verifyInput(editData);
	}

	public Object createNewData() {
		ProResourceItemInfo info = new ProResourceItemInfo();
		return info;
	}

	public CoreBaseInfo getEntityInfo() {
		return new ProResourceItemInfo();
	}

}
