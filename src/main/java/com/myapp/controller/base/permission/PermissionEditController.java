package com.myapp.controller.base.permission;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseEditController;
import com.myapp.core.entity.BaseOrgInfo;
import com.myapp.core.entity.PermissionInfo;
import com.myapp.core.enums.BaseMethodEnum;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.enums.PermissionTypeEnum;
import com.myapp.core.enums.UserState;
import com.myapp.core.model.ColumnModel;
import com.myapp.core.service.OrgService;
import com.myapp.core.service.PermissionService;
import com.myapp.core.util.DateUtil;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年5月20日 
 * @system:
 *-----------MySong---------------
 */
@Controller
@RequestMapping("base/permission")
public class PermissionEditController extends BaseEditController{
	@Resource
	public PermissionService permissionService;
	public AbstractBaseService getService() {
		return permissionService;
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
		PermissionInfo info = new PermissionInfo();
		info.setEnabled(true);
		info.setType(PermissionTypeEnum.FUNCTION);
		return info;
	}

	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("number"));
		cols.add(new ColumnModel("remark"));
		cols.add(new ColumnModel("url"));
		cols.add(new ColumnModel("enabled",DataTypeEnum.BOOLEAN));
		cols.add(new ColumnModel("type",DataTypeEnum.ENUM,PermissionTypeEnum.class));
		ColumnModel parent = new ColumnModel("parent",DataTypeEnum.F7,"id,name");
		parent.setClaz(PermissionInfo.class);
		cols.add(parent);
		return cols;
	}

	public CoreBaseInfo getEntityInfo() {
		return new PermissionInfo();
	}
}
