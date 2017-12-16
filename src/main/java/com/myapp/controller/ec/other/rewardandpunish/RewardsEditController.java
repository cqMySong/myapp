package com.myapp.controller.ec.other.rewardandpunish;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.core.annotation.PermissionAnn;
import com.myapp.core.base.entity.CoreBaseInfo;
import com.myapp.core.base.service.impl.AbstractBaseService;
import com.myapp.core.controller.BaseBillEditController;
import com.myapp.core.enums.DataTypeEnum;
import com.myapp.core.model.ColumnModel;
import com.myapp.entity.ec.other.RewardPunishTypeInfo;
import com.myapp.entity.ec.other.RewardsInfo;
import com.myapp.service.ec.other.RewardsService;

/**
 *-----------MySong---------------
 * ©MySong基础框架搭建
 * @author mySong @date 2017年5月20日 
 * @system:
 *-----------MySong---------------
 */
@PermissionAnn(name="系统管理.现场管理.其他管理.奖励通知单",number="app.ec.other.reward")
@Controller
@RequestMapping("ec/other/reward")
public class RewardsEditController extends BaseBillEditController{
	@Resource
	public RewardsService rewardsService;
	
	public AbstractBaseService getService() {
		return rewardsService;
	}
	
	public List<ColumnModel> getDataBinding() {
		List<ColumnModel> cols = super.getDataBinding();
		cols.add(new ColumnModel("orgUnit"));
		cols.add(new ColumnModel("rewardPunishTypeInfo",DataTypeEnum.F7,RewardPunishTypeInfo.class));
		cols.add(new ColumnModel("grandDate",DataTypeEnum.DATE));
		cols.add(new ColumnModel("item"));
		cols.add(new ColumnModel("measures"));
		cols.add(new ColumnModel("grantOrgUnit"));
		return cols;
	}
	
	protected boolean verifyInput(Object editData) {
		return true;
	}

	public Object createNewData() {
		return new RewardsInfo();
	}

	public CoreBaseInfo getEntityInfo() {
		return new RewardsInfo();
	}
}
