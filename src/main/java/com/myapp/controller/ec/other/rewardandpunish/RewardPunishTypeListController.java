package com.myapp.controller.ec.other.rewardandpunish;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseListController;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.enums.ec.ChangeType;
import com.myapp.enums.ec.DisclosureType;
import com.myapp.enums.ec.RewardPunish;
import com.myapp.enums.ec.WorkFollow;
import com.myapp.enums.ec.WorkType;
import com.myapp.service.ec.other.RewardPunishTypeService;

/**
 * 
 * @author Phoenix
 *
 */
@PermissionAnn(name="系统管理.现场管理.其他管理.奖惩类型",number="app.ec.other.rewardpunishtypes")
@Controller
@RequestMapping("ec/other/rewardpunishtypes")
public class RewardPunishTypeListController extends BaseListController{

	@Resource
	public RewardPunishTypeService rewardPunishTypeService;
	public AbstractBaseService getService() {
		return rewardPunishTypeService;
	}
	
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("number"));
		cols.add(new ColumnModel("name"));
		cols.add(new ColumnModel("rewardPunish",DataTypeEnum.ENUM,RewardPunish.class));
		cols.add(new ColumnModel("enabled"));
		cols.add(new ColumnModel("remark"));
		return cols;
	}
	
	public String getEditUrl() {
		return "ec/other/rewardpunish/rewardPunishTypeEdit";
	}

	public String getListUrl() {
		return "ec/other/rewardpunish/rewardPunishTypeList";
	}
}
