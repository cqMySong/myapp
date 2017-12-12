package com.myapp.controller.ec.proresourcedata;

import java.util.List;

import javax.annotation.Resource;

import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseDataEditController;
import com.myapp.core.entity.basedate.DataGroupInfo;
import com.myapp.core.enums.BaseMethodEnum;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.entity.ec.basedata.ProResourceDataInfo;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.service.ec.basedata.ProResourceDataService;
/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年12月11日 
 * @system: 项目级资料目录(安全 ，技术)
 *-----------MySong---------------
 */
public abstract class ProResourceDataBaseEditController extends BaseDataEditController {
	
	@Resource
	public ProResourceDataService proResourceDataService;
	public AbstractBaseService getService() {
		return proResourceDataService;
	}
	
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("code"));
		cols.add(new ColumnModel("group",DataTypeEnum.F7,DataGroupInfo.class));
		cols.add(new ColumnModel("project",DataTypeEnum.F7,ProjectInfo.class));
		return cols;
	}
	
	public abstract String getCode();
	protected void beforeStoreData(BaseMethodEnum bme, Object editData) {
		super.beforeStoreData(bme, editData);
		if(editData!=null&&editData instanceof ProResourceDataInfo){
			ProResourceDataInfo prdInfo = (ProResourceDataInfo) editData;
			prdInfo.setCode(getCode());
		}
	}
	
	protected boolean verifyInput(Object editData) {
		return super.verifyInput(editData);
	}

	public Object createNewData() {
		ProResourceDataInfo info = new ProResourceDataInfo();
		return info;
	}

	public CoreBaseInfo getEntityInfo() {
		return new ProResourceDataInfo();
	}

}
