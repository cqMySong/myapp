package com.myapp.controller.base.datagroup;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseDataEditController;
import com.myapp.core.entity.BaseOrgInfo;
import com.myapp.core.entity.basedate.DataGroupInfo;
import com.myapp.core.enums.BaseMethodEnum;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.service.basedata.DataGroupService;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年5月20日 
 * @system:
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.数据分类",number="app.datagroup")
@Controller
@RequestMapping("base/datagroup")
public class DataGroupEditController extends BaseDataEditController{
	@Resource
	public DataGroupService dataGroupService;
	public AbstractBaseService getService() {
		return dataGroupService;
	}
	
	protected boolean verifyInput(Object editData) {
		return super.verifyInput(editData);
	}
	
	protected void beforeStoreData(BaseMethodEnum bme,Object editData) {
		super.beforeStoreData(bme,editData);
		//保存前可以做对数据进行处理
		if(BaseMethodEnum.SAVE.equals(bme)){
			
		}
	}

	public Object createNewData() {
		DataGroupInfo info = new DataGroupInfo();
		return info;
	}

	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("code"));
		ColumnModel parent = new ColumnModel("parent",DataTypeEnum.F7,"id,name");
		parent.setClaz(DataGroupInfo.class);
		cols.add(parent);
		return cols;
	}

	public CoreBaseInfo getEntityInfo() {
		return new DataGroupInfo();
	}
}
