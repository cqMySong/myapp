package com.myapp.controller.ec.other.prodisposal;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.entity.CoreInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseBillEditController;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.entity.ec.other.ProDisposalInfo;
import com.myapp.enums.ec.WorkFollow;
import com.myapp.enums.ec.WorkType;
import com.myapp.service.ec.other.ProDisposalService;

/**
 * 
 * @author Phoenix
 *
 */
@PermissionAnn(name="系统管理.现场管理.其他管理.项目主要问题处理",number="app.ec.other.prodisposal")
@Controller
@RequestMapping("ec/other/prodisposal")
public class ProDisposalEditController extends BaseBillEditController{

	@Resource
	private ProDisposalService proDisposalService;
	
	public AbstractBaseService getService() {
		return proDisposalService;
	}
	
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("bizDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("workType",DataTypeEnum.ENUM,WorkType.class));
		cols.add(new ColumnModel("proDescription"));
		cols.add(new ColumnModel("disposal"));
		cols.add(new ColumnModel("workFollow",DataTypeEnum.ENUM,WorkFollow.class));
		cols.add(new ColumnModel("lastestDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("isDone",DataTypeEnum.BOOLEAN));
		cols.add(new ColumnModel("remark"));
		
		return cols;
	}

	public Object createNewData() {
		ProDisposalInfo info = new ProDisposalInfo();
		info.setBizDate(new Date());
		info.setLastestDate(new Date());
		info.setWorkFollow(WorkFollow.NORMAL);
		info.setWorkType(WorkType.QUALITY);
		return info;
	}

	public CoreInfo getEntityInfo() {
		return new ProDisposalInfo();
	}
	
	protected boolean verifyInput(Object editData) {
		return true;
	}
}
