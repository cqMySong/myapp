package com.myapp.controller.ec.basedata.workcheckitem;

import java.util.List;

import javax.annotation.Resource;

import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseDataEditController;
import com.myapp.core.enums.BaseMethodEnum;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.entity.ec.basedata.WorkCheckItemInfo;
import com.myapp.enums.ec.WorkCheckGroup;
import com.myapp.enums.ec.WorkCheckType;
import com.myapp.service.ec.basedata.WorkCheckItemService;

public abstract class WorkCheckItemEditController extends BaseDataEditController {
	@Resource
	public WorkCheckItemService workCheckItemService;
	public abstract WorkCheckGroup getWorkCheckGroup();
	
	public AbstractBaseService getService() {
		return workCheckItemService;
	}
	
	protected void beforeStoreData(BaseMethodEnum bme, Object editData) {
		if(editData!=null&&editData instanceof WorkCheckItemInfo){
			WorkCheckItemInfo wcInfo = (WorkCheckItemInfo) editData;
			WorkCheckGroup wcg = getWorkCheckGroup();
			if(wcg==null) wcg = WorkCheckGroup.DAY;
			wcInfo.setWorkCheckGroup(wcg);
		}
		super.beforeStoreData(bme, editData);
	}
	
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("workCheckGroup",DataTypeEnum.ENUM,WorkCheckGroup.class));
		cols.add(new ColumnModel("workCheckType",DataTypeEnum.ENUM,WorkCheckType.class));
		cols.add(new ColumnModel("checkRequire"));
		return cols;
	}

	public Object createNewData() {
		return new WorkCheckItemInfo();
	}

	public CoreBaseInfo getEntityInfo() {
		return new WorkCheckItemInfo();
	}

}
