package com.myapp.controller.ec.basedata.sitetransfer;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseBillEditController;
import com.myapp.core.entity.UserInfo;
import com.myapp.core.enums.BillState;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.enums.TransferTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.entity.ec.basedata.ProjectInfo;
import com.myapp.entity.ec.basedata.SiteTransferInfo;
import com.myapp.service.ec.basedata.SiteTransferService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author :ly
 * @date:2017-10-25
 */
@PermissionAnn(name="系统管理.现场管理.基础数据.场地移交",number="app.ec.basedata.sitetransfer")
@Controller
@RequestMapping("ec/basedata/sitetransfer")
public class SiteTransferEditController extends BaseBillEditController {
	
	@Resource
	public SiteTransferService siteTransferService;
	@Override
	public AbstractBaseService getService() {
		return siteTransferService;
	}
	@Override
	public Object createNewData() {
		SiteTransferInfo siteTransferInfo = new SiteTransferInfo();
		siteTransferInfo.setTransferType(TransferTypeEnum.UNIT);
		return siteTransferInfo;
	}
	@Override
	public CoreBaseInfo getEntityInfo() {
		return new SiteTransferInfo();
	}
	@Override
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("number"));
		cols.add(new ColumnModel("transferUnit"));
		cols.add(new ColumnModel("receivingUnit"));
		cols.add(new ColumnModel("transferDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("transferRange"));
		cols.add(new ColumnModel("siteCondition"));
		cols.add(new ColumnModel("receivingDuty"));
		cols.add(new ColumnModel("commitment"));
		cols.add(new ColumnModel("project",DataTypeEnum.F7, ProjectInfo.class));
		cols.add(new ColumnModel("billState",DataTypeEnum.ENUM,BillState.class));
		cols.add(new ColumnModel("transferType",DataTypeEnum.ENUM,TransferTypeEnum.class));
		cols.add(new ColumnModel("createUser",DataTypeEnum.F7,UserInfo.class));
		cols.add(new ColumnModel("lastUpdateUser",DataTypeEnum.F7,UserInfo.class));
		cols.add(new ColumnModel("auditor",DataTypeEnum.F7,UserInfo.class));
		cols.add(new ColumnModel("createDate", DataTypeEnum.DATETIME));
		cols.add(new ColumnModel("auditDate", DataTypeEnum.DATETIME));
		cols.add(new ColumnModel("lastUpdateDate", DataTypeEnum.DATETIME));
		return cols;
	}


}
