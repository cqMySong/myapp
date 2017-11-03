package com.myapp.controller.ec.other.designchange;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.entity.CoreInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseBillEditController;
import com.myapp.core.enums.BillState;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.entity.ec.other.DesignChangeInfo;
import com.myapp.enums.ec.ChangeType;
import com.myapp.enums.ec.DisclosureType;
import com.myapp.service.ec.other.DesignChangeService;


@PermissionAnn(name="系统管理.现场管理.其他管理.设计变更(洽商)",number="app.ec.other.designchange")
@Controller
@RequestMapping("ec/other/designchange")
public class DesignChangeEditController extends BaseBillEditController {
	
	@Resource
	public DesignChangeService designChangeService;
	public AbstractBaseService getService() {
		return designChangeService;
	}
	
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("number"));
		cols.add(new ColumnModel("changeType",DataTypeEnum.ENUM, ChangeType.class));
		
		cols.add(new ColumnModel("changePlace"));
		cols.add(new ColumnModel("changeContent"));
		cols.add(new ColumnModel("changeDate",DataTypeEnum.DATE));
		
		cols.add(new ColumnModel("disclosureType",DataTypeEnum.ENUM, DisclosureType.class));
		cols.add(new ColumnModel("remark"));
		return cols;
	}

	public Object createNewData() {
		DesignChangeInfo info = new DesignChangeInfo();
		info.setChangeType(ChangeType.SJBG);
		info.setDisclosureType(DisclosureType.WJD);
		return info;
	}

	public CoreInfo getEntityInfo() {
		return new DesignChangeInfo();
	}
	
	protected boolean verifyInput(Object editData) {
		return true;
	}

}
