package com.myapp.controller.ec.other.prodisposal;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseListController;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
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
@RequestMapping("ec/other/prodisposals")
public class ProDisposalListController extends BaseListController {
	
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

	public String getEditUrl() {
		return "ec/other/prodisposal/proDisposalEdit";
	}

	public String getListUrl() {
		return "ec/other/prodisposal/proDisposalList";
	}

}
